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
DROP TABLE IF EXISTS DiarySymptoms;
DROP TABLE IF EXISTS DiaryMeasurements;
DROP TABLE IF EXISTS DiaryPhotos;
DROP TABLE IF EXISTS DiaryEntries;
DROP TABLE IF EXISTS Symptoms;
DROP TABLE IF EXISTS Measurements;
DROP TABLE IF EXISTS Photos;
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
    UserRole VARCHAR(255)
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
--Diary--
CREATE TABLE Photos (
    PhotoID INT AUTO_INCREMENT PRIMARY KEY,
    PhotoURL TEXT,
    PhotoDate DATETIME,
    PhotoBodypart VARCHAR(255),
    UserID BIGINT,
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);
CREATE TABLE Measurements (
  MeasurementID INT AUTO_INCREMENT PRIMARY KEY,
  MeasurementType VARCHAR(255),
  MeasurementValue FLOAT,
  MeasurementUnit VARCHAR(100),
  UserID BIGINT,
  FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);

CREATE TABLE Symptoms (
      SymptomID INT AUTO_INCREMENT PRIMARY KEY,
      SymptomName VARCHAR(255),
      SymptomSeverity INT,
      SymptomStartDate DATETIME,
      SymptomIsActive BOOLEAN,
      UserID BIGINT,
      FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);

CREATE TABLE DiaryEntries (
  EntryID INT AUTO_INCREMENT PRIMARY KEY,
  EntryDate DATE NOT NULL,
  EntryMood VARCHAR(255),
  EntryNotes TEXT,
  UserID BIGINT NOT NULL,
  CONSTRAINT fk_user FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);


CREATE TABLE DiaryPhotos (
    DiaryPhotoID INT AUTO_INCREMENT PRIMARY KEY,
    EntryID INT,
    PhotoID INT,
    FOREIGN KEY (EntryID) REFERENCES DiaryEntries(EntryID),
    FOREIGN KEY (PhotoID) REFERENCES Photos(PhotoID)
);

CREATE TABLE DiaryMeasurements (
    DiaryMeasurementID INT AUTO_INCREMENT PRIMARY KEY,
    EntryID INT,
    MeasurementID INT,
    FOREIGN KEY (EntryID) REFERENCES DiaryEntries(EntryID),
    FOREIGN KEY (MeasurementID) REFERENCES Measurements(MeasurementID)
);

CREATE TABLE DiarySymptoms (
       DiarySymptomID INT AUTO_INCREMENT PRIMARY KEY,
       EntryID INT NOT NULL,
       SymptomID INT NOT NULL,
       FOREIGN KEY (EntryID) REFERENCES DiaryEntries(EntryID),
       FOREIGN KEY (SymptomID) REFERENCES Symptoms(SymptomID)
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
    QuestionnaireDesc TEXT
);
CREATE TABLE Questions (
    QuestionID BIGINT AUTO_INCREMENT PRIMARY KEY,
    Question TEXT,
    QuestionCategory VARCHAR(255),
    QuestionResponseType VARCHAR(255),
    QuestionnaireID BIGINT,
    FOREIGN KEY (QuestionnaireID) REFERENCES Questionnaires(QuestionnaireID)
);
CREATE TABLE UserQuestionnaires (
    UserQuestionnaireID BIGINT AUTO_INCREMENT PRIMARY KEY,
    QuestionnaireID BIGINT,
    UserID BIGINT,
    QUestionnaireStartDate DATETIME,
    QuestionnaireIsCompleted BOOLEAN,
    QuestionnaireCompletionDate DATETIME,
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID),
    FOREIGN KEY (QuestionnaireID) REFERENCES Questionnaires(QuestionnaireID)
);
CREATE TABLE UserResponses (
    UserQuestionnaireID BIGINT,
    QuestionID BIGINT,
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
    TaskID BIGINT AUTO_INCREMENT PRIMARY KEY,
    TaskType VARCHAR(255),
    TaskName VARCHAR(255),
    TaskDesc TEXT
);
CREATE TABLE UserTasks (
    UserTaskID BIGINT AUTO_INCREMENT PRIMARY KEY,
    TaskIsCompleted BOOLEAN,
    TaskDuedate DATETIME,
    TaskIsRepeatable BOOLEAN,
    TaskRepeatPeriod TIMESTAMP,
    TaskID BIGINT,
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