-- Drop all tables for testing purposes
DROP DATABASE nhs;
CREATE DATABASE nhs;
USE nhs;

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
--Diary--
CREATE TABLE Photos (
    PhotoID BIGINT AUTO_INCREMENT PRIMARY KEY,
    PhotoURL TEXT,
    PhotoDate DATETIME,
    PhotoBodypart VARCHAR(255),
    UserID BIGINT,
    FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);
CREATE TABLE Measurements (
  MeasurementID BIGINT AUTO_INCREMENT PRIMARY KEY,
  MeasurementType VARCHAR(255),
  MeasurementValue FLOAT,
  MeasurementUnit VARCHAR(100),
  UserID BIGINT,
  FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);

CREATE TABLE Symptoms (
      SymptomID BIGINT AUTO_INCREMENT PRIMARY KEY,
      SymptomName VARCHAR(255),
      SymptomSeverity INT,
      SymptomStartDate DATETIME,
      SymptomIsActive BOOLEAN,
      UserID BIGINT,
      FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);

CREATE TABLE DiaryEntries (
  EntryID BIGINT AUTO_INCREMENT PRIMARY KEY,
  EntryDate DATE NOT NULL,
  EntryMood TINYINT,
  EntryNotes TEXT,
  UserID BIGINT NOT NULL,
  FOREIGN KEY (UserID) REFERENCES UserCredentials(UserID)
);

CREATE TABLE DiaryPhotos (
  DiaryPhotoID BIGINT AUTO_INCREMENT PRIMARY KEY,
  EntryID BIGINT,
  PhotoID BIGINT,
  FOREIGN KEY (EntryID) REFERENCES DiaryEntries(EntryID),
  FOREIGN KEY (PhotoID) REFERENCES Photos(PhotoID)
);

CREATE TABLE DiaryMeasurements (
    DiaryMeasurementID BIGINT AUTO_INCREMENT PRIMARY KEY,
    EntryID BIGINT,
    MeasurementID BIGINT,
    FOREIGN KEY (EntryID) REFERENCES DiaryEntries(EntryID),
    FOREIGN KEY (MeasurementID) REFERENCES Measurements(MeasurementID)
);

CREATE TABLE DiarySymptoms (
       DiarySymptomID BIGINT AUTO_INCREMENT PRIMARY KEY,
       EntryID BIGINT NOT NULL,
       SymptomID BIGINT NOT NULL,
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