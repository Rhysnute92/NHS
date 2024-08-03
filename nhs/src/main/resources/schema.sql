-- Drop all tables for testing purposes
DROP TABLE IF EXISTS UserTasks;
DROP TABLE IF EXISTS Tasks;
DROP TABLE IF EXISTS InfoAssets;
DROP TABLE IF EXISTS InfoSections;
DROP TABLE IF EXISTS Articles;
DROP TABLE IF EXISTS UserResponses;
DROP TABLE IF EXISTS UserQuestionnaires;
DROP TABLE IF EXISTS Questions;
DROP TABLE IF EXISTS Questionnaires;
DROP TABLE IF EXISTS DiaryEntries;
DROP TABLE IF EXISTS Symptoms;
DROP TABLE IF EXISTS Measurments;
DROP TABLE IF EXISTS Photos;
DROP TABLE IF EXISTS Appointments;
DROP TABLE IF EXISTS UserWidgets;
DROP TABLE IF EXISTS Providers;
DROP TABLE IF EXISTS PatientDiagnosis;
DROP TABLE IF EXISTS Patients;
DROP TABLE IF EXISTS ProviderCredentials;
DROP TABLE IF EXISTS PatientCredentials;
--Log in information and credentials--
CREATE TABLE PatientCredentials (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    UserName VARCHAR(255),
    UserPassword VARCHAR(255)
);
CREATE TABLE ProviderCredentials (
    ProviderID INT AUTO_INCREMENT PRIMARY KEY,
    ProviderName VARCHAR(255),
    ProviderPassword VARCHAR(255)
);
--Not implemented yet--
--CREATE TABLE Admin ()
-----------------------
--Patient information--
CREATE TABLE Patients (
    PatientEmail VARCHAR(255),
    PatientMobile INT,
    NHSNumber INT,
    PatientDOB DATE,
    PatientName VARCHAR(255),
    PatientLastName VARCHAR(255),
    PatientTitle VARCHAR(100),
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES PatientCredentials (UserID)
);
CREATE TABLE PatientDiagnosis (
    UserID INT,
    PrimaryDiagnosis TEXT,
    DiagnosisDate DATETIME,
    IssueLocation VARCHAR(255),
    DiagnosisSeverity VARCHAR(255),
    DiagnosisDetails TEXT,
    FOREIGN KEY (UserID) REFERENCES PatientCredentials(UserID)
);
--Not implemented yet--
--CREATE TABLE PatientRecordsOld ()
--CREATE TABLE PatientRecordsOther ()
--CREATE TABLE UserMedication ()
-----------------------
--Provider information--
CREATE TABLE Providers (
    ProviderFirstName VARCHAR(255),
    ProviderLastName VARCHAR(255),
    ProviderTitle VARCHAR(255),
    ProviderOccupation VARCHAR(100),
    ProviderID INT,
    FOREIGN KEY (ProviderID) REFERENCES ProviderCredentials (ProviderID)
);
--Dashboard--
CREATE TABLE UserWidgets (
    UserWidgetID BIGINT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
    WidgetName VARCHAR(255) NOT NULL,
    Position INT,
    FOREIGN KEY (UserID) REFERENCES PatientCredentials(UserID)
);
--Calendar--
CREATE TABLE Appointments (
    ApptID INT AUTO_INCREMENT PRIMARY KEY,
    ApptTime DATETIME,
    ApptType VARCHAR(255),
    ApptProvider VARCHAR(255),
    ApptInfo TEXT,
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES PatientCredentials (UserID)
);
--Diary--
CREATE TABLE Photos (
    PhotoID INT AUTO_INCREMENT PRIMARY KEY,
    PhotoDateTaken DATETIME,
    PhotoBodypart VARCHAR(255)
);
CREATE TABLE Measurments (
    MeasurmentID INT AUTO_INCREMENT PRIMARY KEY,
    MeasurmentType VARCHAR(255),
    MeasurmentValue FLOAT,
    MeasurmentUnit VARCHAR(100)
);
CREATE TABLE Symptoms (
    SymptomID INT AUTO_INCREMENT PRIMARY KEY,
    SymptomName VARCHAR(255),
    SymptomSeverity VARCHAR(255),
    SymptomStartDate DATETIME,
    SymptomIsActive BOOLEAN
);
CREATE TABLE DiaryEntries (
    EntryID INT AUTO_INCREMENT PRIMARY KEY,
    EntryType VARCHAR(255),
    PhotoID INT,
    MeasurmentID INT,
    SymptomID INT,
    EntryNotes TEXT,
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES PatientCredentials(UserID),
    FOREIGN KEY (PhotoID) REFERENCES Photos(PhotoID),
    FOREIGN KEY (MeasurmentID) REFERENCES Measurments(MeasurmentID),
    FOREIGN KEY (SymptomID) REFERENCES Symptoms(SymptomID)
);
--Not implemented yet--
--CREATE TABLE Event ()
--CREATE TABLE DiaryQuestions ()
-----------------------
--Managment plan--
CREATE TABLE Questionnaires (
    QuestionnaireID INT AUTO_INCREMENT PRIMARY KEY,
    QuestionnaireType VARCHAR(255),
    QuestionnaireName VARCHAR(255),
    QuestionnaireDesc TEXT
);
CREATE TABLE Questions (
    QuestionID INT AUTO_INCREMENT PRIMARY KEY,
    Question TEXT,
    QuestionCategory VARCHAR(255),
    QuestionResponseType VARCHAR(255),
    QuestionnaireID INT,
    FOREIGN KEY (QuestionnaireID) REFERENCES Questionnaires(QuestionnaireID)
);
CREATE TABLE UserQuestionnaires (
    UserQuestionnaireID INT AUTO_INCREMENT PRIMARY KEY,
    QuestionnaireID INT,
    UserID INT,
    QUestionnaireStartDate DATETIME,
    QuestionnaireIsCompleted BOOLEAN,
    QuestionnaireCompletionDate DATETIME,
    FOREIGN KEY (UserID) REFERENCES PatientCredentials(UserID),
    FOREIGN KEY (QuestionnaireID) REFERENCES Questionnaires(QuestionnaireID)
);
CREATE TABLE UserResponses (
    UserQuestionnaireID INT,
    QuestionID INT,
    UserResponse TEXT,
    FOREIGN KEY (QuestionID) REFERENCES Questions(QuestionID),
    FOREIGN KEY (UserQuestionnaireID) REFERENCES UserQuestionnaires(UserQuestionnaireID)
);
--Not implemented yet--
--CREATE TABLE PatientPlan ()
--CREATE TABLE PlanMedication ()
--CREATE TABLE AppointmentRecord ()
-----------------------
--Education--
CREATE TABLE Articles (
    ArticleID INT AUTO_INCREMENT PRIMARY KEY,
    ArticleTitle VARCHAR(255),
    ArticleCreated DATETIME,
    ArticleUpdated DATETIME
);
CREATE TABLE InfoSections (
    SectionID INT AUTO_INCREMENT PRIMARY KEY,
    SectionTitle VARCHAR(255),
    SectionLevel VARCHAR(255),
    SectionInfo TEXT,
    SectionCanRemind BOOLEAN,
    ArticleID INT,
    FOREIGN KEY (ArticleID) REFERENCES Articles(ArticleID)
);
CREATE TABLE InfoAssets (
    AssetID INT AUTO_INCREMENT PRIMARY KEY,
    AssetType VARCHAR(255),
    AssetName VARCHAR(255),
    AssetLink TEXT,
    AssetSource TEXT,
    SectionID INT,
    FOREIGN KEY (SectionID) REFERENCES InfoSections(SectionID)
);
--General use--
CREATE TABLE Tasks (
    TaskID INT AUTO_INCREMENT PRIMARY KEY,
    TaskType VARCHAR(255),
    TaskName VARCHAR(255),
    TaskDesc TEXT
);
CREATE TABLE UserTasks (
    UserTaskID INT AUTO_INCREMENT PRIMARY KEY,
    TaskIsCompleted BOOLEAN,
    TaskDuedate DATETIME,
    TaskIsRepeatable BOOLEAN,
    TaskRepeatPeriod TIMESTAMP,
    TaskID INT,
    FOREIGN KEY (TaskID) REFERENCES Tasks(TaskID)
);
--Not implemented yet--
--CREATE TABLE Reminders ()
--CREATE TABLE Clinic ()
--CREATE TABLE Log ()
--CREATE TABLE Changes ()
--CREATE TABLE LoginRecords ()
--CREATE TABLE Record ()
-----------------------