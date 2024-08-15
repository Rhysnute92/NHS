INSERT INTO UserCredentials (UserName, UserPassword, UserRole)
VALUES 
('admin', '$2y$10$J68pzi1vv7Sjez4Yt5siU.v4FrsXxD58APhQHiXNEldGNRa1um5uK', 'ROLE_ADMIN'),
('testUser', '$2a$10$hznjz2afk0Dlu3ye10eoPuLWDvnFTuI3ZPegsFs1KJ1mbbfH1MVzq', 'ROLE_PATIENT'),
('testProvider', '$2a$10$cibnUtr2BBkAQ2HibZDFWOarJcCtfRg1uj0HcfEd8z2WQyN4EePlC', 'ROLE_PROVIDER');


INSERT INTO UserWidgets (UserID, WidgetName, Position)
VALUES (1, 'task-completion', 1);

INSERT INTO Patients (PatientEmail, PatientMobile, NHSNumber, PatientDOB, 
PatientName, PatientLastName, PatientTitle, PatientClinic, UserID, EncryptionKey)
VALUES
('test@test.com', '7777 4444', '12345678', '2000-08-15', 'Test', 
'McTesterson', 'Lord', 'The best one', '2', 'tempkey')