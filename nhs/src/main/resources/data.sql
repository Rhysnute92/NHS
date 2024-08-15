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
('tUR2SzL4c4rM5vYyYV+qsw==', 'RaAr3fuWzhPBFnKFxkkcew==', '12345678', '1969-06-09', '4xLXfYk4oPqqdLVhpNfkpQ==', 
'/hnIldHqwWhUyQshrXH1bw==', 'Lord', 'Sherwood Forrest Hospitals', '2', 'xlHXWQORPy97Go4sJsrY+XNNEx59m/NaDZ7K7OOuEVY=')