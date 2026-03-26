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

-- 2. Drop foreign keys and tables in correct order to avoid circular dependencies
-- Drop constraints first to avoid dependency errors
IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_DoctorRoles_Doctors')
    ALTER TABLE DoctorRoles DROP CONSTRAINT FK_DoctorRoles_Doctors;

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Appointments_Patients')
    ALTER TABLE Appointments DROP CONSTRAINT FK_Appointments_Patients;

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Appointments_Doctors')
    ALTER TABLE Appointments DROP CONSTRAINT FK_Appointments_Doctors;

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Appointments_Rooms')
    ALTER TABLE Appointments DROP CONSTRAINT FK_Appointments_Rooms;

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_MedicalRecords_Patients')
    ALTER TABLE MedicalRecords DROP CONSTRAINT FK_MedicalRecords_Patients;

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_MedicalRecords_Doctors')
    ALTER TABLE MedicalRecords DROP CONSTRAINT FK_MedicalRecords_Doctors;

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Bills_Patients')
    ALTER TABLE Bills DROP CONSTRAINT FK_Bills_Patients;

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Reports_Patients')
    ALTER TABLE Reports DROP CONSTRAINT FK_Reports_Patients;

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Reports_Doctors')
    ALTER TABLE Reports DROP CONSTRAINT FK_Reports_Doctors;

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Doctors_Departments')
    ALTER TABLE Doctors DROP CONSTRAINT FK_Doctors_Departments;

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_Departments_HeadDoctor')
    ALTER TABLE Departments DROP CONSTRAINT FK_Departments_HeadDoctor;

-- Drop tables in order
DROP TABLE IF EXISTS DoctorRoles;
DROP TABLE IF EXISTS Appointments;
DROP TABLE IF EXISTS MedicalRecords;
DROP TABLE IF EXISTS Bills;
DROP TABLE IF EXISTS Reports;
DROP TABLE IF EXISTS Notifications;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS Doctors;
DROP TABLE IF EXISTS Departments;
DROP TABLE IF EXISTS Patients;
DROP TABLE IF EXISTS Nurses;
DROP TABLE IF EXISTS Secretaries;
DROP TABLE IF EXISTS Admins;
DROP TABLE IF EXISTS Security;
DROP TABLE IF EXISTS Rooms; -- Fixed case sensitivity from 'rooms' to 'Rooms'
GO

-- 4. Create Tables

-- Departments
CREATE TABLE Departments (
    departmentId NVARCHAR(20) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    headDoctorId NVARCHAR(20) NULL
);

-- Doctors
CREATE TABLE Doctors (
    doctorId NVARCHAR(20) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    specialization NVARCHAR(100),
    phone NVARCHAR(20),
    email NVARCHAR(100),
    departmentId NVARCHAR(20) NOT NULL,
    AvailabilityStatus NVARCHAR(50),
    salary DECIMAL(10,2),
    salaryDescription VARCHAR(1000),
    CONSTRAINT FK_Doctors_Departments FOREIGN KEY (departmentId) REFERENCES Departments(departmentId)
);

-- Patients
CREATE TABLE Patients (
    patientId NVARCHAR(20) PRIMARY KEY,
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
    nurseId NVARCHAR(20) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100),
    shift NVARCHAR(50),
    availability NVARCHAR(50)
);

-- Secretaries
CREATE TABLE Secretaries (
    secretaryId NVARCHAR(20) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100)
);

-- Admins
CREATE TABLE Admins (
    adminId NVARCHAR(20) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100)
);

-- Security
CREATE TABLE Security (
    securityId NVARCHAR(20) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100)
);

-- Rooms
CREATE TABLE Rooms (
    roomId NVARCHAR(20) PRIMARY KEY,
    roomType NVARCHAR(50),
    capacity INT,
    availabilityStatus NVARCHAR(50),
    dailyRate DECIMAL(10,2)
);

-- Appointments
CREATE TABLE Appointments (
    appointmentId NVARCHAR(20) PRIMARY KEY,
    patientId NVARCHAR(20) NOT NULL,
    doctorId NVARCHAR(20) NOT NULL,
    appointmentTime DATETIME NOT NULL,
    type NVARCHAR(50),
    status NVARCHAR(50) DEFAULT 'Scheduled',
    roomId NVARCHAR(20) NULL,
    daysOfStay INT NULL,
    CONSTRAINT FK_Appointments_Patients FOREIGN KEY (patientId) REFERENCES Patients(patientId),
    CONSTRAINT FK_Appointments_Doctors FOREIGN KEY (doctorId) REFERENCES Doctors(doctorId),
    CONSTRAINT FK_Appointments_Rooms FOREIGN KEY (roomId) REFERENCES Rooms(roomId)
);

-- MedicalRecords
CREATE TABLE MedicalRecords (
    recordId NVARCHAR(20) PRIMARY KEY,
    patientId NVARCHAR(20) NOT NULL,
    doctorId NVARCHAR(20) NOT NULL,
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
    personId NVARCHAR(20) NULL,
    full_name NVARCHAR(100) NOT NULL,
    username NVARCHAR(50) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    role NVARCHAR(20) NOT NULL CHECK (role IN ('Doctor', 'Nurse', 'Secretary', 'Admin', 'Patient')),
    createdAt DATETIME DEFAULT GETDATE(),
    lastLogin DATETIME NULL,
    accountStatus NVARCHAR(20) DEFAULT 'Active'
);

-- Bills
CREATE TABLE Bills(
    billId NVARCHAR(20) PRIMARY KEY,
    patientId NVARCHAR(20) NULL,
    amount DECIMAL(10,2),
    billDate DATETIME DEFAULT GETDATE(),
    status NVARCHAR(50),
    CONSTRAINT FK_Bills_Patients FOREIGN KEY (patientId) REFERENCES Patients(patientId)
);

-- Reports
CREATE TABLE Reports(
    reportId NVARCHAR(20) PRIMARY KEY,
    patientId NVARCHAR(20) NULL,
    doctorId NVARCHAR(20) NULL,
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
    channel NVARCHAR(20) NOT NULL,
    status NVARCHAR(20) NOT NULL,
    sentAt DATETIME DEFAULT GETDATE()
);

-- DoctorRoles (New Table for Decorator Pattern)
CREATE TABLE DoctorRoles (
    roleId NVARCHAR(20) PRIMARY KEY,
    doctorId NVARCHAR(20) NOT NULL,
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
IF NOT EXISTS (SELECT 1 FROM Departments WHERE departmentId = 'D1')
INSERT INTO Departments (departmentId, name) VALUES 
('D1', 'Cardiology'),
('D2', 'Neurology'),
('D3', 'Pediatrics'),
('D4', 'Orthopedics'),
('D5', 'Dermatology');

-- Doctors
IF NOT EXISTS (SELECT 1 FROM Doctors WHERE doctorId = 'DOC001')
INSERT INTO Doctors (doctorId, name, specialization, phone, email, departmentId, AvailabilityStatus) VALUES 
('DOC001', 'Dr. Ahmed Mansour', 'Cardiologist', '01012345678', 'ahmed@hospital.com', 'D1', 'Available'),
('DOC002', 'Dr. Sarah Hassan', 'Neurologist', '01123456789', 'sara@hospital.com', 'D2', 'Busy'),
('DOC003', 'Dr. Mohamed Ibrahim', 'Pediatrician', '01234567890', 'mohamed@hospital.com', 'D3', 'Available'),
('DOC004', 'Dr. Mona Zaki', 'Orthopedic Surgeon', '01545678901', 'mona@hospital.com', 'D4', 'Busy'),
('DOC005', 'Dr. Omar Farouk', 'Dermatologist', '01098765432', 'omar@hospital.com', 'D5', 'Available'),
('DOC006', 'Dr. Laila Nour', 'Cardiologist', '01187654321', 'laila@hospital.com', 'D1', 'Busy'),
('DOC007', 'Dr. Khaled Said', 'Neurologist', '01276543210', 'khaled@hospital.com', 'D2', 'Available'),
('DOC008', 'Dr. Fatma Ali', 'Pediatrician', '01565432109', 'fatma@hospital.com', 'D3', 'Busy'),
('DOC009', 'Dr. Youssef Kamal', 'Orthopedic Surgeon', '01054321098', 'youssef@hospital.com', 'D4', 'Available'),
('DOC010', 'Dr. Mariam El-Sayed', 'Dermatologist', '01143210987', 'mariam@hospital.com', 'D5', 'Available');

-- Update Department Heads
UPDATE Departments SET headDoctorId = 'DOC001' WHERE departmentId = 'D1';
UPDATE Departments SET headDoctorId = 'DOC002' WHERE departmentId = 'D2';
UPDATE Departments SET headDoctorId = 'DOC003' WHERE departmentId = 'D3';
UPDATE Departments SET headDoctorId = 'DOC004' WHERE departmentId = 'D4';
UPDATE Departments SET headDoctorId = 'DOC005' WHERE departmentId = 'D5';

-- Patients
IF NOT EXISTS (SELECT 1 FROM Patients WHERE patientId = 'PAT001')
INSERT INTO Patients (patientId, name, gender, bloodType, phone, email, address, dateOfBirth) VALUES 
('PAT001', 'Mahmoud Abbas', 'Male', 'A_Pos', '01011122233', 'mahmoud@hospital.com', 'Cairo, Nasr City', '1985-05-15'),
('PAT002', 'Zainab Soliman', 'Female', 'B_Pos', '01122233344', 'zainab@hospital.com', 'Giza, Dokki', '1990-10-20'),
('PAT003', 'Mostafa Ragab', 'Male', 'O_Neg', '01233344455', 'mostafa@hospital.com', 'Alexandria, Smouha', '1975-03-12'),
('PAT004', 'Amira Fawzy', 'Female', 'AB_Pos', '01544455566', 'amira@hospital.com', 'Cairo, Maadi', '2000-08-05'),
('PAT005', 'Hany Shaker', 'Male', 'A_Neg', '01055566677', 'hany@hospital.com', 'Giza, Haram', '1982-12-30'),
('PAT006', 'Salma Ahmed', 'Female', 'O_Pos', '01166677788', 'salma@hospital.com', 'Cairo, Heliopolis', '1995-07-14'),
('PAT007', 'Karim Walid', 'Male', 'B_Neg', '01277788899', 'karim_p@hospital.com', 'Suez, Port Tawfik', '1988-02-28'),
('PAT008', 'Nour El-Din', 'Male', 'AB_Neg', '01588899900', 'nour@hospital.com', 'Mansoura, Talkha', '1992-11-11'),
('PAT009', 'Yasmin Gamal', 'Female', 'A_Pos', '01099900011', 'yasmin@hospital.com', 'Tanta, Sea St.', '1998-04-09'),
('PAT010', 'Sherif Mounir', 'Male', 'O_Pos', '01100011122', 'sherif@hospital.com', 'Cairo, Zamalek', '1970-01-01');

-- Nurses
IF NOT EXISTS (SELECT 1 FROM Nurses WHERE nurseId = 'N001')
INSERT INTO Nurses (nurseId, name, phone, email, shift, availability) VALUES 
('N001', 'Noura Ali', '01012312312', 'noura@hospital.com', 'Morning', 'Available'),
('N002', 'Heba Mahmoud', '01123423423', 'heba@hospital.com', 'Evening', 'Available'),
('N003', 'Mona Ahmed', '01234534534', 'mona_n@hospital.com', 'Night', 'Busy'),
('N004', 'Eman Said', '01545645645', 'eman@hospital.com', 'Morning', 'Available'),
('N005', 'Rania Youssef', '01056756756', 'rania@hospital.com', 'Evening', 'OffDuty');

-- Secretaries
IF NOT EXISTS (SELECT 1 FROM Secretaries WHERE secretaryId = 'S001')
INSERT INTO Secretaries (secretaryId, name, phone, email) VALUES 
('S001', 'Sahar Fouad', '01011122233', 'sahar@hospital.com'),
('S002', 'Dina Hamdy', '01122233344', 'dina@hospital.com'),
('S003', 'Mai Khaled', '01233344455', 'mai@hospital.com');

-- Admins
IF NOT EXISTS (SELECT 1 FROM Admins WHERE adminId = 'A001')
INSERT INTO Admins (adminId, name, phone, email) VALUES 
('A001', 'Admin Karim', '01000000000', 'karim@hospital.com');

-- Security
IF NOT EXISTS (SELECT 1 FROM Security WHERE securityId = 'SEC001')
INSERT INTO Security (securityId, name, phone, email) VALUES 
('SEC001', 'Sec Guard Ali', '01011111111', 'ali_sec@hospital.com');

-- Rooms
IF NOT EXISTS (SELECT 1 FROM Rooms WHERE roomId = 'ROOM001')
INSERT INTO Rooms (roomId, roomType, capacity, availabilityStatus, dailyRate) VALUES 
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
INSERT INTO Appointments (appointmentId, patientId, doctorId, appointmentTime, type, status, roomId, daysOfStay) VALUES 
('APT001', 'PAT001', 'DOC001', '2026-03-08 10:00:00', 'Stay', 'Scheduled', 'ROOM001', 5),
('APT002', 'PAT002', 'DOC002', '2026-03-08 11:30:00', 'Visiting', 'Scheduled', NULL, NULL),
('APT003', 'PAT003', 'DOC003', '2026-03-08 09:15:00', 'Stay', 'Scheduled', 'ROOM003', 3),
('APT004', 'PAT004', 'DOC004', '2026-03-09 13:00:00', 'Visiting', 'Scheduled', NULL, NULL),
('APT005', 'PAT005', 'DOC005', '2026-03-09 14:45:00', 'Stay', 'Scheduled', 'ROOM005', 7),
('APT006', 'PAT006', 'DOC006', '2026-03-10 08:30:00', 'Visiting', 'Scheduled', NULL, NULL),
('APT007', 'PAT007', 'DOC007', '2026-03-10 12:00:00', 'Visiting', 'Scheduled', NULL, NULL),
('APT008', 'PAT008', 'DOC008', '2026-03-11 10:45:00', 'Visiting', 'Scheduled', NULL, NULL),
('APT009', 'PAT009', 'DOC009', '2026-03-11 15:30:00', 'Visiting', 'Scheduled', NULL, NULL),
('APT010', 'PAT010', 'DOC010', '2026-03-12 11:00:00', 'Visiting', 'Scheduled', NULL, NULL);

-- MedicalRecords
INSERT INTO MedicalRecords (recordId, patientId, doctorId, diagnosis, complaint, recordDate) VALUES 
('MR001', 'PAT001', 'DOC001', 'Hypertension', 'Headache and dizziness', '2026-02-01'),
('MR002', 'PAT002', 'DOC002', 'Migraine', 'Severe headache', '2026-02-05'),
('MR003', 'PAT003', 'DOC003', 'Common Cold', 'Fever and cough', '2026-02-10'),
('MR004', 'PAT004', 'DOC004', 'Fractured Ankle', 'Pain after falling', '2026-02-15'),
('MR005', 'PAT005', 'DOC005', 'Eczema', 'Skin rash and itching', '2026-02-20'),
('MR006', 'PAT006', 'DOC006', 'Stable Angina', 'Chest pain on exertion', '2026-02-25'),
('MR007', 'PAT007', 'DOC007', 'Epilepsy', 'Seizures', '2026-03-01'),
('MR008', 'PAT008', 'DOC008', 'Asthma', 'Shortness of breath', '2026-03-03'),
('MR009', 'PAT009', 'DOC009', 'Knee Osteoarthritis', 'Chronic knee pain', '2026-03-05'),
('MR010', 'PAT010', 'DOC010', 'Psoriasis', 'Scaly skin patches', '2026-03-06'),
('MR011', 'PAT001', 'DOC001', 'Type 2 Diabetes', 'Increased thirst and frequent urination', '2026-03-10'),
('MR012', 'PAT002', 'DOC002', 'Anxiety Disorder', 'Persistent worry and restlessness', '2026-03-15');

-- Roles
INSERT INTO Roles (roleName) VALUES
('Admin'), ('Doctor'), ('Nurse'), ('Secretary');

-- Users
INSERT INTO Users (personId, full_name, username, password, email, role) VALUES
('A001', 'Admin Karim', 'A001', 'admin123', 'karim@hospital.com', 'Admin'),
('DOC001', 'Dr. Ahmed Mansour', 'D001', 'doctor456', 'ahmed.mansour.d001@hospital.com', 'Doctor'),
('DOC002', 'Dr. Sarah Hassan', 'D002', 'doctor789', 'sarah.hassan.d002@hospital.com', 'Doctor'),
('N001', 'Noura Ali', 'N001', 'nurse123', 'noura.ali.n001@hospital.com', 'Nurse'),
('N002', 'Heba Mahmoud', 'N002', 'nurse456', 'heba.mahmoud.n002@hospital.com', 'Nurse'),
('S001', 'Sahar Fouad', 'SCT001', 'sec123', 'sahar.fouad.sct001@hospital.com', 'Secretary');

-- Bills
INSERT INTO Bills (billId, patientId, amount, status) VALUES
('BILL001', 'PAT001', 500, 'Paid'),
('BILL002', 'PAT002', 1200, 'Unpaid'),
('BILL003', 'PAT003', 350, 'Paid'),
('BILL004', 'PAT004', 800, 'Unpaid'),
('BILL005', 'PAT005', 200, 'Paid');

-- Reports
INSERT INTO Reports (reportId, patientId, doctorId, reportType, formatType, reportContent) VALUES
('REP001', 'PAT001', 'DOC001', 'MedicalReport', 'PDF', 'Patient diagnosed with hypertension. Requires monthly follow-up.'),
('REP002', 'PAT002', 'DOC002', 'MedicalReport', 'PDF', 'Migraine treatment plan and medication prescribed.'),
('REP003', 'PAT003', 'DOC003', 'MedicalReport', 'Excel', 'Cold symptoms treated with rest and medication.'),
('REP004', 'PAT004', 'DOC004', 'RadiologyReport', 'PDF', 'X-ray confirms ankle fracture. Cast applied.'),
('REP005', 'PAT005', 'DOC005', 'DermatologyReport', 'PDF', 'Skin rash identified as eczema. Topical cream prescribed.');
GO

-- Notifications
INSERT INTO Notifications (message, recipient, channel, status) VALUES
('Your appointment is confirmed for tomorrow.', 'Mahmoud Abbas', 'SMS', 'SENT'),
('Please check your latest medical report.', 'Zainab Soliman', 'EMAIL', 'SENT'),
('A new message from Dr. Ahmed.', 'Mahmoud Abbas', 'MOBILE', 'SENT');
GO

-- Doctor Roles (Decorator Pattern Data)
IF NOT EXISTS (SELECT 1 FROM DoctorRoles WHERE doctorId = 'DOC001')
BEGIN
    INSERT INTO DoctorRoles (roleId, doctorId, roleName) VALUES 
    ('DR001', 'DOC001', 'Surgeon'),
    ('DR002', 'DOC001', 'Head of Department'),
    ('DR003', 'DOC004', 'Surgeon'),
    ('DR004', 'DOC009', 'On-call');
END
GO

PRINT 'Full Database setup completed successfully.';