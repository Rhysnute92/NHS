-- Drop all tables for testing purposes
DROP TABLE IF EXISTS UserTaskLog;
DROP TABLE IF EXISTS UserTask;
DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS InfoAssets;
DROP TABLE IF EXISTS InfoSections;
DROP TABLE IF EXISTS Articles;
DROP TABLE IF EXISTS UserQuestion;
DROP TABLE IF EXISTS UserResponses;
DROP TABLE IF EXISTS UserQuestionnaires;
DROP TABLE IF EXISTS Question;
DROP TABLE IF EXISTS Questions;
DROP TABLE IF EXISTS Questionnaires;
DROP TABLE IF EXISTS Treatments;
DROP TABLE IF EXISTS DiarySymptoms;
DROP TABLE IF EXISTS DiaryPhotos;
DROP TABLE IF EXISTS DiaryMeasurements;
DROP TABLE IF EXISTS Symptoms;
DROP TABLE IF EXISTS Measurements;
DROP TABLE IF EXISTS Photos;
DROP TABLE IF EXISTS DiaryEntries;
DROP TABLE IF EXISTS Events;
DROP TABLE IF EXISTS Appointments;
DROP TABLE IF EXISTS UserWidgets;
DROP TABLE IF EXISTS Providers;
DROP TABLE IF EXISTS PatientDiagnosis;
DROP TABLE IF EXISTS Patients;
DROP TABLE IF EXISTS ProviderCredentials;
DROP TABLE IF EXISTS PatientCredentials;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS UserCredentials;
--Log in information and credentials--
CREATE TABLE UserCredentials (
    UserID BIGINT AUTO_INCREMENT PRIMARY KEY,
    UserName VARCHAR(255),
    UserPassword VARCHAR(255),
    UserRole VARCHAR(255),
    PasswordSetupToken VARCHAR(255)
);
--Patient information--
CREATE TABLE Patients (
    PatientEmail VARCHAR(255),
    PatientMobile VARCHAR(255),
    NHSNumber INT,
    PatientDOB DATE,
    PatientName VARCHAR(255),
    PatientLastName VARCHAR(255),
    PatientTitle VARCHAR(100),
    PatientClinic VARCHAR(255),
    UserID BIGINT,
    EncryptionKey VARCHAR(255),
    FOREIGN KEY (UserID) REFERENCES UserCredentials (UserID),
    PRIMARY KEY (UserID)
);
CREATE TABLE PatientDiagnosis (
    UserID BIGINT,
    PrimaryDiagnosis TEXT,
    DiagnosisDate DATETIME,
    IssueLocation VARCHAR(255),
    DiagnosisSeverity VARCHAR(255),
    DiagnosisDetails TEXT,
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
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
    UserID BIGINT,
    FOREIGN KEY (UserID) REFERENCES UserCredentials (UserID)
);
--Dashboard--
CREATE TABLE UserWidgets (
    UserWidgetID BIGINT AUTO_INCREMENT PRIMARY KEY,
    UserID BIGINT,
    WidgetName VARCHAR(255) NOT NULL,
    Position INT,
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);
--Calendar--
CREATE TABLE Appointments (
    ApptID BIGINT AUTO_INCREMENT PRIMARY KEY,
    ApptDateTime DATETIME NOT NULL,
    ApptType VARCHAR(255),
    ApptProvider VARCHAR(255),
    ApptLocation VARCHAR(255),
    ApptInfo TEXT,
    UserID BIGINT,
    FOREIGN KEY (UserID) REFERENCES UserCredentials (UserID)
);
--Events--
CREATE TABLE Events (
    EventID BIGINT AUTO_INCREMENT PRIMARY KEY,
    EventDate DATE NOT NULL,
    EventDuration INT,
    UserID BIGINT,
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);
--Diary--
CREATE TABLE DiaryEntries (
    EntryID BIGINT AUTO_INCREMENT PRIMARY KEY,
    EntryDateTime DATETIME NOT NULL,
    EntryMood TINYINT,
    EntryNotes TEXT,
    UserID BIGINT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);
CREATE TABLE Photos (
    PhotoID BIGINT AUTO_INCREMENT PRIMARY KEY,
    PhotoURL TEXT,
    PhotoBodypart VARCHAR(255),
    PhotoDate DATE,
    UserID BIGINT,
    EventID BIGINT,
    EntryID BIGINT,
    FOREIGN KEY (EventID) REFERENCES Events(EventID),
    FOREIGN KEY (EntryID) REFERENCES DiaryEntries(EntryID),
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);
CREATE TABLE Measurements (
    MeasurementID BIGINT AUTO_INCREMENT PRIMARY KEY,
    MeasurementType VARCHAR(255),
    MeasurementValue FLOAT,
    MeasurementUnit VARCHAR(100),
    MeasurementLocation VARCHAR(255),
    MeasurementDate DATE,
    MeasurementSide VARCHAR(100),
    UserID BIGINT,
    EntryID BIGINT,
    FOREIGN KEY (EntryID) REFERENCES DiaryEntries(EntryID),
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);
CREATE TABLE Symptoms (
    SymptomID BIGINT AUTO_INCREMENT PRIMARY KEY,
    SymptomName VARCHAR(255),
    SymptomSeverity INT,
    SymptomDate DATE,
    SymptomIsActive BOOLEAN,
    UserID BIGINT,
    EventID BIGINT,
    EntryID BIGINT,
    FOREIGN KEY (EventID) REFERENCES Events(EventID),
    FOREIGN KEY (EntryID) REFERENCES DiaryEntries(EntryID),
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);
CREATE TABLE Treatments (
    TreatmentID BIGINT AUTO_INCREMENT PRIMARY KEY,
    TreatmentType VARCHAR(255),
    TreatmentDetails TEXT,
    TreatmentDate DATE,
    UserID BIGINT,
    EventID BIGINT,
    FOREIGN KEY (EventID) REFERENCES Events(EventID),
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);
--Not implemented yet--
--CREATE TABLE Event ()
--CREATE TABLE DiaryQuestions ()
-----------------------
--Managment plan--
CREATE TABLE Questionnaires (
    QuestionnaireID BIGINT AUTO_INCREMENT PRIMARY KEY,
    QuestionnaireType VARCHAR(255),
    QuestionnaireName VARCHAR(255),
    QuestionnaireDesc TEXT,
    QuestionnaireCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE Question (
    QuestionID BIGINT AUTO_INCREMENT PRIMARY KEY,
    QuestionText TEXT,
    QuestionCategory VARCHAR(255),
    QuestionResponseType VARCHAR(255),
    QuestionnaireID BIGINT,
    FOREIGN KEY (QuestionnaireID) REFERENCES Questionnaires(QuestionnaireID)
);
CREATE TABLE UserQuestionnaires (
    UserQuestionnaireID BIGINT AUTO_INCREMENT PRIMARY KEY,
    QuestionnaireID BIGINT NOT NULL,
    UserID BIGINT NOT NULL,
    QuestionnaireCreatedDate DATETIME NOT NULL,
    QuestionnaireDueDate DATETIME NOT NULL,
    QuestionnaireStartDate DATETIME DEFAULT NULL,
    QuestionnaireInProgress BOOLEAN DEFAULT FALSE,
    QuestionnaireIsCompleted BOOLEAN DEFAULT FALSE,
    QuestionnaireCompletionDate DATETIME,
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID),
    FOREIGN KEY (QuestionnaireID) REFERENCES Questionnaires(QuestionnaireID)
);
CREATE TABLE UserQuestion (
    UserQuestionID BIGINT AUTO_INCREMENT PRIMARY KEY,
    QuestionID BIGINT,
    UserQuestionnaireID BIGINT,
    UserResponseText TEXT,
    UserResponseScore INT,
    ResponseDateTime DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID),
    FOREIGN KEY (UserQuestionnaireID) REFERENCES UserQuestionnaires(UserQuestionnaireID)
);
--Not implemented yet--
--CREATE TABLE PatientPlan ()
--CREATE TABLE PlanMedication ()
--CREATE TABLE AppointmentRecord ()
-----------------------
--Education--
-- CREATE TABLE Articles (
--     ArticleID INT AUTO_INCREMENT PRIMARY KEY,
--     ArticleTitle VARCHAR(255),
--     ArticleCreated DATETIME,
--     ArticleUpdated DATETIME
-- );
-- CREATE TABLE InfoSections (
--     SectionID INT AUTO_INCREMENT PRIMARY KEY,
--     SectionTitle VARCHAR(255),
--     SectionLevel VARCHAR(255),
--     SectionInfo TEXT,
--     SectionCanRemind BOOLEAN,
--     ArticleID INT,
--     FOREIGN KEY (ArticleID) REFERENCES Articles(ArticleID)
-- );
-- CREATE TABLE InfoAssets (
--     AssetID INT AUTO_INCREMENT PRIMARY KEY,
--     AssetType VARCHAR(255),
--     AssetName VARCHAR(255),
--     AssetLink TEXT,
--     AssetSource TEXT,
--     SectionID INT,
--     FOREIGN KEY (SectionID) REFERENCES InfoSections(SectionID)
-- );
--General use--
CREATE TABLE Task (
    TaskID BIGINT AUTO_INCREMENT PRIMARY KEY,
    TaskType VARCHAR(255),
    TaskName VARCHAR(255),
    TaskDesc TEXT,
    TaskRepeatPeriod VARCHAR(100)
);
CREATE TABLE UserTask (
    UserTaskID INT AUTO_INCREMENT PRIMARY KEY,
    TaskID BIGINT,
    UserID BIGINT,
    Bitmask INT,
    FOREIGN KEY (TaskID) REFERENCES Task(TaskID),
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);
CREATE TABLE UserTaskLog (
    UserTaskLogID INT AUTO_INCREMENT PRIMARY KEY,
    UserID BIGINT,
    UserTaskID INT,
    Bitmask INT,
    MonthYear VARCHAR(255),
    CreatedAt DATETIME,
    FOREIGN KEY (UserTaskID) REFERENCES UserTask(UserTaskID),
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);
--Not implemented yet--
--CREATE TABLE Reminders ()
--CREATE TABLE Clinic ()
--CREATE TABLE Log ()
--CREATE TABLE Changes ()
--CREATE TABLE LoginRecords ()
--CREATE TABLE Record ()
-----------------------