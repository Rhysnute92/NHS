CREATE TABLE PatientCredentials (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    UserName VARCHAR(255),
    UserPassword VARCHAR(255)
);
CREATE TABLE Patients (
    PatientEmail VARCHAR(255),
    PatientMobile INT,
    NHSNumber INT,
    PatientDOB DATE,
    PatientName VARCHAR(255),
    PatientLastName VARCHAR(255),
    Title VARCHAR(100),
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES PatientCredentials (UserID)
);
CREATE TABLE PatientCondition (
    Condition VARCHAR (255),
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES Patients (UserID)
);
CREATE TABLE ProviderCredentials (
    ProviderID INT AUTO_INCREMENT PRIMARY KEY,
    ProviderName VARCHAR(255),
    ProviderPassword VARCHAR(255)
);
CREATE TABLE Providers ()
CREATE TABLE PatientDiagnosis ()
CREATE TABLE Widgets ()
CREATE TABLE UserDashboard ()
CREATE TABLE Tasks ()
CREATE TABLE UserTasks ()
CREATE TABLE Appointments ()
CREATE TABLE Photos ()
CREATE TABLE Measurments ()
CREATE TABLE Symptoms ()
CREATE TABLE DiaryEntry ()
CREATE TABLE Questions ()
CREATE TABLE Questionnaires ()
CREATE TABLE UserQuestionnaire ()
CREATE TABLE InfoAssets ()
CREATE TABLE InfoSections ()
CREATE TABLE Articles ()
CREATE TABLE PatientPlan ()
CREATE TABLE Admin ()
CREATE TABLE Event ()
CREATE TABLE Record ()
CREATE TABLE DiaryQuestions ()
CREATE TABLE UserMedication ()
CREATE TABLE PlanMedication ()
CREATE TABLE Log ()
CREATE TABLE Changes ()
CREATE TABLE Reminders ()
CREATE TABLE LoginRecords ()
CREATE TABLE PatientRecordsOld ()
CREATE TABLE PatientRecordsOther ()
CREATE TABLE Clininc ()
CREATE TABLE AppointmentRecord ()