/*
    Hospital Management System - Database Setup Script
    Target: Microsoft SQL Server Management Studio (SSMS)
    Description: This script creates the database, all necessary tables, 
                 and populates them with realistic sample data for testing.
*/

-- 1. Create the Database
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'Hospital_Management_System')
BEGIN
    CREATE DATABASE Hospital_Management_System;
END
GO

USE Hospital_Management_System;
GO

-- 2. Drop Tables if they exist (to allow re-running the script)
-- Note: Order matters due to foreign key constraints
IF OBJECT_ID('MedicalRecords', 'U') IS NOT NULL DROP TABLE MedicalRecords;
IF OBJECT_ID('Appointments', 'U') IS NOT NULL DROP TABLE Appointments;
IF OBJECT_ID('rooms', 'U') IS NOT NULL DROP TABLE rooms;
IF OBJECT_ID('Secretaries', 'U') IS NOT NULL DROP TABLE Secretaries;
IF OBJECT_ID('Nurses', 'U') IS NOT NULL DROP TABLE Nurses;
IF OBJECT_ID('Doctors', 'U') IS NOT NULL 
BEGIN
    -- Remove circular dependency if exists
    IF EXISTS (SELECT 1 FROM sys.foreign_keys WHERE name = 'FK_Departments_HeadDoctor')
        ALTER TABLE Departments DROP CONSTRAINT FK_Departments_HeadDoctor;
    DROP TABLE Doctors;
END
IF OBJECT_ID('Patients', 'U') IS NOT NULL DROP TABLE Patients;
IF OBJECT_ID('Departments', 'U') IS NOT NULL DROP TABLE Departments;
GO

-- 3. Create Tables

-- Departments Table
CREATE TABLE Departments (
    departmentId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    headDoctorId INT -- Will be updated later to avoid circular reference
);

-- Doctors Table
CREATE TABLE Doctors (
    doctorId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    specialization NVARCHAR(100),
    phone NVARCHAR(20),
    departmentId INT FOREIGN KEY REFERENCES Departments(departmentId)
);

-- Add Foreign Key to Departments (Head Doctor)
ALTER TABLE Departments 
ADD CONSTRAINT FK_Departments_HeadDoctor 
FOREIGN KEY (headDoctorId) REFERENCES Doctors(doctorId);

-- Patients Table
CREATE TABLE Patients (
    patientId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    gender NVARCHAR(20),
    bloodType NVARCHAR(20),
    phone NVARCHAR(20),
    address NVARCHAR(255),
    dateOfBirth DATE
);

-- Nurses Table
CREATE TABLE Nurses (
    nurseId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    shift NVARCHAR(50),
    availability NVARCHAR(50)
);

-- Secretaries Table
CREATE TABLE Secretaries (
    secretaryId INT PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20)
);

-- Rooms Table (Note: lowercase 'rooms' as per RoomDAO)
CREATE TABLE rooms (
    roomId NVARCHAR(20) PRIMARY KEY,
    roomType NVARCHAR(50),
    capacity INT,
    availabilityStatus NVARCHAR(50),
    dailyRate DECIMAL(10, 2)
);

-- Appointments Table
CREATE TABLE Appointments (
    appointmentId INT PRIMARY KEY,
    patientId INT FOREIGN KEY REFERENCES Patients(patientId),
    doctorId INT FOREIGN KEY REFERENCES Doctors(doctorId),
    appointmentTime DATETIME NOT NULL,
    type NVARCHAR(50),
    status NVARCHAR(50) DEFAULT 'Scheduled'
);

-- MedicalRecords Table
CREATE TABLE MedicalRecords (
    recordId INT PRIMARY KEY,
    patientId INT FOREIGN KEY REFERENCES Patients(patientId),
    doctorId INT FOREIGN KEY REFERENCES Doctors(doctorId),
    diagnosis NVARCHAR(MAX),
    complaint NVARCHAR(MAX),
    recordDate DATE
);
GO

-- 4. Insert Sample Data

-- 4.1 Departments
INSERT INTO Departments (departmentId, name) VALUES 
(1, 'Cardiology'),
(2, 'Neurology'),
(3, 'Pediatrics'),
(4, 'Orthopedics'),
(5, 'Dermatology');

-- 4.2 Doctors
INSERT INTO Doctors (doctorId, name, specialization, phone, departmentId) VALUES 
(1, 'Dr. Ahmed Mansour', 'Cardiologist', '01012345678', 1),
(2, 'Dr. Sarah Hassan', 'Neurologist', '01123456789', 2),
(3, 'Dr. Mohamed Ibrahim', 'Pediatrician', '01234567890', 3),
(4, 'Dr. Mona Zaki', 'Orthopedic Surgeon', '01545678901', 4),
(5, 'Dr. Omar Farouk', 'Dermatologist', '01098765432', 5),
(6, 'Dr. Laila Nour', 'Cardiologist', '01187654321', 1),
(7, 'Dr. Khaled Said', 'Neurologist', '01276543210', 2),
(8, 'Dr. Fatma Ali', 'Pediatrician', '01565432109', 3),
(9, 'Dr. Youssef Kamal', 'Orthopedic Surgeon', '01054321098', 4),
(10, 'Dr. Mariam El-Sayed', 'Dermatologist', '01143210987', 5);

-- Update Department Heads
UPDATE Departments SET headDoctorId = 1 WHERE departmentId = 1;
UPDATE Departments SET headDoctorId = 2 WHERE departmentId = 2;
UPDATE Departments SET headDoctorId = 3 WHERE departmentId = 3;
UPDATE Departments SET headDoctorId = 4 WHERE departmentId = 4;
UPDATE Departments SET headDoctorId = 5 WHERE departmentId = 5;

-- 4.3 Patients
INSERT INTO Patients (patientId, name, gender, bloodType, phone, address, dateOfBirth) VALUES 
(1, 'Mahmoud Abbas', 'Male', 'A_Pos', '01011122233', 'Cairo, Nasr City', '1985-05-15'),
(2, 'Zainab Soliman', 'Female', 'B_Pos', '01122233344', 'Giza, Dokki', '1990-10-20'),
(3, 'Mostafa Ragab', 'Male', 'O_Neg', '01233344455', 'Alexandria, Smouha', '1975-03-12'),
(4, 'Amira Fawzy', 'Female', 'AB_Pos', '01544455566', 'Cairo, Maadi', '2000-08-05'),
(5, 'Hany Shaker', 'Male', 'A_Neg', '01055566677', 'Giza, Haram', '1982-12-30'),
(6, 'Salma Ahmed', 'Female', 'O_Pos', '01166677788', 'Cairo, Heliopolis', '1995-07-14'),
(7, 'Karim Walid', 'Male', 'B_Neg', '01277788899', 'Suez, Port Tawfik', '1988-02-28'),
(8, 'Nour El-Din', 'Male', 'AB_Neg', '01588899900', 'Mansoura, Talkha', '1992-11-11'),
(9, 'Yasmin Gamal', 'Female', 'A_Pos', '01099900011', 'Tanta, Sea St.', '1998-04-09'),
(10, 'Sherif Mounir', 'Male', 'O_Pos', '01100011122', 'Cairo, Zamalek', '1970-01-01');

-- 4.4 Nurses
INSERT INTO Nurses (nurseId, name, phone, shift, availability) VALUES 
(1, 'Noura Ali', '01012312312', 'Morning', 'Available'),
(2, 'Heba Mahmoud', '01123423423', 'Evening', 'Available'),
(3, 'Mona Ahmed', '01234534534', 'Night', 'Busy'),
(4, 'Eman Said', '01545645645', 'Morning', 'Available'),
(5, 'Rania Youssef', '01056756756', 'Evening', 'OffDuty');

-- 4.5 Secretaries
INSERT INTO Secretaries (secretaryId, name, phone) VALUES 
(1, 'Sahar Fouad', '01011122233'),
(2, 'Dina Hamdy', '01122233344'),
(3, 'Mai Khaled', '01233344455');

-- 4.6 Rooms
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

-- 4.7 Appointments
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

-- 4.8 Medical Records
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
(10, 10, 10, 'Psoriasis', 'Scaly skin patches', '2026-03-06');
GO

PRINT 'Database setup and data population completed successfully.';
