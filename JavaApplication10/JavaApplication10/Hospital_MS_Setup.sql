/*
    Hospital Management System - Safe Full Setup Script
    Target: Microsoft SQL Server Management Studio (SSMS)
*/

-- 1. Create Database if not exists
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'hospital_mangament_system')
BEGIN
    CREATE DATABASE hospital_mangament_system;
END
GO

USE hospital_mangament_system;
GO

-- 2. Drop foreign keys first to avoid errors
-- Drop FKs dynamically
DECLARE @sql NVARCHAR(MAX) = N'';

SELECT @sql += 'ALTER TABLE ' + QUOTENAME(OBJECT_NAME(parent_object_id)) +
               ' DROP CONSTRAINT ' + QUOTENAME(name) + ';' + CHAR(13)
FROM sys.foreign_keys;

EXEC sp_executesql @sql;
GO

-- 3. Drop tables if exist
DECLARE @tables NVARCHAR(MAX) = N'';

SELECT @tables += 'DROP TABLE IF EXISTS ' + QUOTENAME(name) + ';' + CHAR(13)
FROM sys.tables;

EXEC sp_executesql @tables;
GO

-- 4. Create Tables

-- Departments
CREATE TABLE Departments (
    departmentId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    headDoctorId INT NULL
);

-- Doctors
CREATE TABLE Doctors (
    doctorId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    specialization NVARCHAR(100),
    phone NVARCHAR(20),
    email NVARCHAR(100),
    departmentId INT NOT NULL,
    CONSTRAINT FK_Doctors_Departments FOREIGN KEY (departmentId) REFERENCES Departments(departmentId)
);

-- Patients
CREATE TABLE Patients (
    patientId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    gender NVARCHAR(20),
    bloodType NVARCHAR(20),
    phone NVARCHAR(20),
    email NVARCHAR(100),
    address NVARCHAR(255),
    dateOfBirth DATE
);

-- Nurses
CREATE TABLE Nurses (
    nurseId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100),
    shift NVARCHAR(50),
    availability NVARCHAR(50)
);

-- Secretaries
CREATE TABLE Secretaries (
    secretaryId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100)
);

-- Admins
CREATE TABLE Admins (
    adminId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100)
);

-- Security
CREATE TABLE Security (
    securityId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100)
);

-- Rooms
CREATE TABLE rooms (
    roomId NVARCHAR(20) PRIMARY KEY,
    roomType NVARCHAR(50),
    capacity INT,
    availabilityStatus NVARCHAR(50),
    dailyRate DECIMAL(10,2)
);

-- Appointments
CREATE TABLE Appointments (
    appointmentId INT PRIMARY KEY,
    patientId INT NOT NULL,
    doctorId INT NOT NULL,
    appointmentTime DATETIME NOT NULL,
    type NVARCHAR(50),
    status NVARCHAR(50) DEFAULT 'Scheduled',
    CONSTRAINT FK_Appointments_Patients FOREIGN KEY (patientId) REFERENCES Patients(patientId),
    CONSTRAINT FK_Appointments_Doctors FOREIGN KEY (doctorId) REFERENCES Doctors(doctorId)
);

-- MedicalRecords
CREATE TABLE MedicalRecords (
    recordId INT PRIMARY KEY,
    patientId INT NOT NULL,
    doctorId INT NOT NULL,
    diagnosis NVARCHAR(MAX),
    complaint NVARCHAR(MAX),
    recordDate DATE,
    treatment NVARCHAR(MAX) NULL,
    visitDate DATE NULL,
    CONSTRAINT FK_MedicalRecords_Patients FOREIGN KEY (patientId) REFERENCES Patients(patientId),
    CONSTRAINT FK_MedicalRecords_Doctors FOREIGN KEY (doctorId) REFERENCES Doctors(doctorId)
);

-- Roles
CREATE TABLE Roles(
    roleId INT IDENTITY(1,1) PRIMARY KEY,
    roleName NVARCHAR(50) UNIQUE NOT NULL
);

-- Users
CREATE TABLE Users(
    id INT IDENTITY(1,1) PRIMARY KEY,
    personId INT NULL, -- Optional link to doctorId, nurseId, etc.
    full_name NVARCHAR(100) NOT NULL,
    username NVARCHAR(50) UNIQUE NOT NULL, -- This will store generated IDs (D001, A001, etc.)
    password NVARCHAR(255) NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    role NVARCHAR(20) NOT NULL CHECK (role IN ('Doctor', 'Nurse', 'Secretary', 'Admin', 'Patient')),
    createdAt DATETIME DEFAULT GETDATE(),
    lastLogin DATETIME NULL,
    accountStatus NVARCHAR(20) DEFAULT 'Active'
);

-- Bills
CREATE TABLE Bills(
    billId INT IDENTITY(1,1) PRIMARY KEY,
    patientId INT NULL,
    amount DECIMAL(10,2),
    billDate DATETIME DEFAULT GETDATE(),
    status NVARCHAR(50),
    CONSTRAINT FK_Bills_Patients FOREIGN KEY (patientId) REFERENCES Patients(patientId)
);

-- Reports
CREATE TABLE Reports(
    reportId INT IDENTITY(1,1) PRIMARY KEY,
    patientId INT NULL,
    doctorId INT NULL,
    reportType NVARCHAR(50),
    formatType NVARCHAR(50),
    reportContent NVARCHAR(MAX),
    visitDate DATE NULL,
    treatment NVARCHAR(MAX) NULL,
    createdAt DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Reports_Patients FOREIGN KEY (patientId) REFERENCES Patients(patientId),
    CONSTRAINT FK_Reports_Doctors FOREIGN KEY (doctorId) REFERENCES Doctors(doctorId)
);

-- Notifications
CREATE TABLE Notifications (
    id INT PRIMARY KEY IDENTITY(1,1),
    message NVARCHAR(MAX) NOT NULL,
    recipient NVARCHAR(100) NOT NULL,
    channel NVARCHAR(20) NOT NULL, -- EMAIL, SMS, MOBILE
    status NVARCHAR(20) NOT NULL,  -- SENT, FAILED
    sentAt DATETIME DEFAULT GETDATE()
);

-- DoctorRoles (New Table for Decorator Pattern)
CREATE TABLE DoctorRoles (
    roleId INT PRIMARY KEY IDENTITY(1,1),
    doctorId INT NOT NULL,
    roleName NVARCHAR(50) NOT NULL,
    assignedAt DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_DoctorRoles_Doctors FOREIGN KEY (doctorId) REFERENCES Doctors(doctorId) ON DELETE CASCADE
);

-- Add FK for Department head after Doctors table exists
ALTER TABLE Departments
ADD CONSTRAINT FK_Departments_HeadDoctor FOREIGN KEY (headDoctorId) REFERENCES Doctors(doctorId);
GO

-- 5. Insert Sample Data (Safe Insert)

-- Departments
IF NOT EXISTS (SELECT 1 FROM Departments WHERE departmentId = 1)
INSERT INTO Departments (departmentId, name) VALUES 
(1, 'Cardiology'),
(2, 'Neurology'),
(3, 'Pediatrics'),
(4, 'Orthopedics'),
(5, 'Dermatology');

-- Doctors
IF NOT EXISTS (SELECT 1 FROM Doctors WHERE doctorId = 1)
INSERT INTO Doctors (doctorId, name, specialization, phone, email, departmentId) VALUES 
(1, 'Dr. Ahmed Mansour', 'Cardiologist', '01012345678', 'ahmed@hospital.com', 1),
(2, 'Dr. Sarah Hassan', 'Neurologist', '01123456789', 'sara@hospital.com', 2),
(3, 'Dr. Mohamed Ibrahim', 'Pediatrician', '01234567890', 'mohamed@hospital.com', 3),
(4, 'Dr. Mona Zaki', 'Orthopedic Surgeon', '01545678901', 'mona@hospital.com', 4),
(5, 'Dr. Omar Farouk', 'Dermatologist', '01098765432', 'omar@hospital.com', 5),
(6, 'Dr. Laila Nour', 'Cardiologist', '01187654321', 'laila@hospital.com', 1),
(7, 'Dr. Khaled Said', 'Neurologist', '01276543210', 'khaled@hospital.com', 2),
(8, 'Dr. Fatma Ali', 'Pediatrician', '01565432109', 'fatma@hospital.com', 3),
(9, 'Dr. Youssef Kamal', 'Orthopedic Surgeon', '01054321098', 'youssef@hospital.com', 4),
(10, 'Dr. Mariam El-Sayed', 'Dermatologist', '01143210987', 'mariam@hospital.com', 5);

-- Update Department Heads
UPDATE Departments SET headDoctorId = 1 WHERE departmentId = 1;
UPDATE Departments SET headDoctorId = 2 WHERE departmentId = 2;
UPDATE Departments SET headDoctorId = 3 WHERE departmentId = 3;
UPDATE Departments SET headDoctorId = 4 WHERE departmentId = 4;
UPDATE Departments SET headDoctorId = 5 WHERE departmentId = 5;

-- Patients
IF NOT EXISTS (SELECT 1 FROM Patients WHERE patientId = 1)
INSERT INTO Patients (patientId, name, gender, bloodType, phone, email, address, dateOfBirth) VALUES 
(1, 'Mahmoud Abbas', 'Male', 'A_Pos', '01011122233', 'mahmoud@hospital.com', 'Cairo, Nasr City', '1985-05-15'),
(2, 'Zainab Soliman', 'Female', 'B_Pos', '01122233344', 'zainab@hospital.com', 'Giza, Dokki', '1990-10-20'),
(3, 'Mostafa Ragab', 'Male', 'O_Neg', '01233344455', 'mostafa@hospital.com', 'Alexandria, Smouha', '1975-03-12'),
(4, 'Amira Fawzy', 'Female', 'AB_Pos', '01544455566', 'amira@hospital.com', 'Cairo, Maadi', '2000-08-05'),
(5, 'Hany Shaker', 'Male', 'A_Neg', '01055566677', 'hany@hospital.com', 'Giza, Haram', '1982-12-30'),
(6, 'Salma Ahmed', 'Female', 'O_Pos', '01166677788', 'salma@hospital.com', 'Cairo, Heliopolis', '1995-07-14'),
(7, 'Karim Walid', 'Male', 'B_Neg', '01277788899', 'karim_p@hospital.com', 'Suez, Port Tawfik', '1988-02-28'),
(8, 'Nour El-Din', 'Male', 'AB_Neg', '01588899900', 'nour@hospital.com', 'Mansoura, Talkha', '1992-11-11'),
(9, 'Yasmin Gamal', 'Female', 'A_Pos', '01099900011', 'yasmin@hospital.com', 'Tanta, Sea St.', '1998-04-09'),
(10, 'Sherif Mounir', 'Male', 'O_Pos', '01100011122', 'sherif@hospital.com', 'Cairo, Zamalek', '1970-01-01');

-- Nurses
IF NOT EXISTS (SELECT 1 FROM Nurses WHERE nurseId = 1)
INSERT INTO Nurses (nurseId, name, phone, email, shift, availability) VALUES 
(1, 'Noura Ali', '01012312312', 'noura@hospital.com', 'Morning', 'Available'),
(2, 'Heba Mahmoud', '01123423423', 'heba@hospital.com', 'Evening', 'Available'),
(3, 'Mona Ahmed', '01234534534', 'mona_n@hospital.com', 'Night', 'Busy'),
(4, 'Eman Said', '01545645645', 'eman@hospital.com', 'Morning', 'Available'),
(5, 'Rania Youssef', '01056756756', 'rania@hospital.com', 'Evening', 'OffDuty');

-- Secretaries
IF NOT EXISTS (SELECT 1 FROM Secretaries WHERE secretaryId = 1)
INSERT INTO Secretaries (secretaryId, name, phone, email) VALUES 
(1, 'Sahar Fouad', '01011122233', 'sahar@hospital.com'),
(2, 'Dina Hamdy', '01122233344', 'dina@hospital.com'),
(3, 'Mai Khaled', '01233344455', 'mai@hospital.com');

-- Admins
IF NOT EXISTS (SELECT 1 FROM Admins WHERE adminId = 1)
INSERT INTO Admins (adminId, name, phone, email) VALUES 
(1, 'Admin Karim', '01000000000', 'karim@hospital.com');

-- Security
IF NOT EXISTS (SELECT 1 FROM Security WHERE securityId = 1)
INSERT INTO Security (securityId, name, phone, email) VALUES 
(1, 'Sec Guard Ali', '01011111111', 'ali_sec@hospital.com');

-- Rooms
IF NOT EXISTS (SELECT 1 FROM rooms WHERE roomId = 'ROOM001')
INSERT INTO rooms (roomId, roomType, capacity, availabilityStatus, dailyRate) VALUES 
('ROOM001', 'GeneralWard', 4, 'Available', 200.0),
('ROOM002', 'GeneralWard', 4, 'Occupied', 200.0),
('ROOM003', 'PrivateRoom', 1, 'Available', 1000.0),
('ROOM004', 'PrivateRoom', 1, 'Occupied', 1000.0),
('ROOM005', 'ICU', 1, 'Available', 5000.0),
('ROOM006', 'ICU', 1, 'Occupied', 5000.0),
('ROOM007', 'GeneralWard', 4, 'UnderMaintenance', 200.0),
('ROOM008', 'PrivateRoom', 1, 'Available', 1000.0),
('ROOM009', 'ICU', 1, 'Available', 5000.0),
('ROOM010', 'GeneralWard', 4, 'Available', 200.0);

-- Appointments
INSERT INTO Appointments (appointmentId, patientId, doctorId, appointmentTime, type, status) VALUES 
(1, 1, 1, '2026-03-08 10:00:00', 'WalkIn', 'Scheduled'),
(2, 2, 2, '2026-03-08 11:30:00', 'FollowUp', 'Scheduled'),
(3, 3, 3, '2026-03-08 09:15:00', 'Online', 'Scheduled'),
(4, 4, 4, '2026-03-09 13:00:00', 'WalkIn', 'Scheduled'),
(5, 5, 5, '2026-03-09 14:45:00', 'FollowUp', 'Scheduled'),
(6, 6, 6, '2026-03-10 08:30:00', 'Online', 'Scheduled'),
(7, 7, 7, '2026-03-10 12:00:00', 'WalkIn', 'Scheduled'),
(8, 8, 8, '2026-03-11 10:45:00', 'FollowUp', 'Scheduled'),
(9, 9, 9, '2026-03-11 15:30:00', 'Online', 'Scheduled'),
(10, 10, 10, '2026-03-12 11:00:00', 'WalkIn', 'Scheduled');

-- MedicalRecords
INSERT INTO MedicalRecords (recordId, patientId, doctorId, diagnosis, complaint, recordDate) VALUES 
(1, 1, 1, 'Hypertension', 'Headache and dizziness', '2026-02-01'),
(2, 2, 2, 'Migraine', 'Severe headache', '2026-02-05'),
(3, 3, 3, 'Common Cold', 'Fever and cough', '2026-02-10'),
(4, 4, 4, 'Fractured Ankle', 'Pain after falling', '2026-02-15'),
(5, 5, 5, 'Eczema', 'Skin rash and itching', '2026-02-20'),
(6, 6, 6, 'Stable Angina', 'Chest pain on exertion', '2026-02-25'),
(7, 7, 7, 'Epilepsy', 'Seizures', '2026-03-01'),
(8, 8, 8, 'Asthma', 'Shortness of breath', '2026-03-03'),
(9, 9, 9, 'Knee Osteoarthritis', 'Chronic knee pain', '2026-03-05'),
(10, 10, 10, 'Psoriasis', 'Scaly skin patches', '2026-03-06'),
(11, 1, 1, 'Type 2 Diabetes', 'Increased thirst and frequent urination', '2026-03-10'),
(12, 2, 2, 'Anxiety Disorder', 'Persistent worry and restlessness', '2026-03-15');

-- Roles
INSERT INTO Roles (roleName) VALUES
('Admin'), ('Doctor'), ('Nurse'), ('Secretary');

-- Users
INSERT INTO Users (personId, full_name, username, password, email, role) VALUES
(1, 'Admin Karim', 'A001', 'admin123', 'karim@hospital.com', 'Admin'),
(1, 'Dr. Ahmed Mansour', 'D001', 'doctor456', 'ahmed.mansour.d001@hospital.com', 'Doctor'),
(2, 'Dr. Sarah Hassan', 'D002', 'doctor789', 'sarah.hassan.d002@hospital.com', 'Doctor'),
(1, 'Noura Ali', 'N001', 'nurse123', 'noura.ali.n001@hospital.com', 'Nurse'),
(2, 'Heba Mahmoud', 'N002', 'nurse456', 'heba.mahmoud.n002@hospital.com', 'Nurse'),
(1, 'Sahar Fouad', 'SCT001', 'sec123', 'sahar.fouad.sct001@hospital.com', 'Secretary');

-- Bills
INSERT INTO Bills (patientId,amount,status) VALUES
(1,500,'Paid'),
(2,1200,'Unpaid'),
(3,350,'Paid'),
(4,800,'Unpaid'),
(5,200,'Paid');

-- Reports
INSERT INTO Reports (patientId,doctorId,reportType,formatType,reportContent) VALUES
(1,1,'MedicalReport','PDF','Patient diagnosed with hypertension. Requires monthly follow-up.'),
(2,2,'MedicalReport','PDF','Migraine treatment plan and medication prescribed.'),
(3,3,'MedicalReport','Excel','Cold symptoms treated with rest and medication.'),
(4,4,'RadiologyReport','PDF','X-ray confirms ankle fracture. Cast applied.'),
(5,5,'DermatologyReport','PDF','Skin rash identified as eczema. Topical cream prescribed.');
GO

-- Notifications
INSERT INTO Notifications (message, recipient, channel, status) VALUES
('Your appointment is confirmed for tomorrow.', 'Mahmoud Abbas', 'SMS', 'SENT'),
('Please check your latest medical report.', 'Zainab Soliman', 'EMAIL', 'SENT'),
('A new message from Dr. Ahmed.', 'Mahmoud Abbas', 'MOBILE', 'SENT');
GO

-- Doctor Roles (Decorator Pattern Data)
IF NOT EXISTS (SELECT 1 FROM DoctorRoles WHERE doctorId = 1)
BEGIN
    INSERT INTO DoctorRoles (doctorId, roleName) VALUES 
    (1, 'Surgeon'),
    (1, 'Head of Department'),
    (4, 'Surgeon'),
    (9, 'On-call');
END
GO

PRINT 'Full Database setup completed successfully.';