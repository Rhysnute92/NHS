-- Drop all tables for testing purposes
DROP TABLE IF EXISTS UserTasks;
DROP TABLE IF EXISTS Tasks;
DROP TABLE IF EXISTS InfoAssets;
DROP TABLE IF EXISTS InfoSections;
DROP TABLE IF EXISTS Articles;
DROP TABLE IF EXISTS UserResponse;
DROP TABLE IF EXISTS UserQuestionnaire;
DROP TABLE IF EXISTS Questions;
DROP TABLE IF EXISTS Questionnaires;
DROP TABLE IF EXISTS DiaryEntry;
DROP TABLE IF EXISTS Symptoms;
DROP TABLE IF EXISTS Measurments;
DROP TABLE IF EXISTS Photos;
DROP TABLE IF EXISTS Appointments;
DROP TABLE IF EXISTS UserWidget;
DROP TABLE IF EXISTS Widgets;
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
    Title VARCHAR(100),
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES PatientCredentials (UserID)
);
CREATE TABLE PatientDiagnosis (
    UserID INT,
    PrimaryDiagnosis TEXT,
    DiagnosisDate DATETIME,
    IssueLocation VARCHAR(255),
    Severity VARCHAR(255),
    Details TEXT,
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
    Title VARCHAR(255),
    Occupation VARCHAR(100),
    ProviderID INT,
    FOREIGN KEY (ProviderID) REFERENCES ProviderCredentials (ProviderID)
);
--Dashboard--
CREATE TABLE Widgets (
    WidgetID INT AUTO_INCREMENT PRIMARY KEY,
    WidgetName VARCHAR(255),
    WidgetDesc VARCHAR(255),
    WidgetIcon VARCHAR(255)
);
CREATE TABLE UserWidget (
    UserID INT,
    WidgetID INT,
    WidgetLocation INT,
    FOREIGN KEY (UserID) REFERENCES PatientCredentials(UserID),
    FOREIGN KEY (WidgetID) REFERENCES Widgets(WidgetID)
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
    DateTaken DATETIME,
    Bodypart VARCHAR(255)
);
CREATE TABLE Measurments (
    MeasurmentID INT AUTO_INCREMENT PRIMARY KEY,
    MeasurmentType VARCHAR(255),
    MeasurmentValue FLOAT,
    Unit VARCHAR(100)
);
CREATE TABLE Symptoms (
    SymptomID INT AUTO_INCREMENT PRIMARY KEY,
    SymptomName VARCHAR(255),
    Severity VARCHAR(255),
    StartDate DATETIME,
    IsActive BOOLEAN
);
CREATE TABLE DiaryEntry (
    EntryID INT AUTO_INCREMENT PRIMARY KEY,
    EntryType VARCHAR(255),
    Photo INT,
    Measurment INT,
    Symptom INT,
    Notes TEXT,
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES PatientCredentials(UserID),
    FOREIGN KEY (Photo) REFERENCES Photos(PhotoID),
    FOREIGN KEY (Measurment) REFERENCES Measurments(MeasurmentID),
    FOREIGN KEY (Symptom) REFERENCES Symptoms(SymptomID)
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
    Category VARCHAR(255),
    ResponseType VARCHAR(255)
    QuestionnaireID INT,
    FOREIGN KEY (QuestionnaireID) REFERENCES Questionnaires(QuestionnaireID)
);
CREATE TABLE UserQuestionnaire (
    UserQuestionnaireID INT AUTO_INCREMENT PRIMARY KEY,
    QuestionnaireID INT,
    UserID INT,
    StartDate DATETIME,
    IsCompleted BOOLEAN,
    CompletionDate DATETIME,
    FOREIGN KEY (UserID) REFERENCES PatientCredentials(UserID),
    FOREIGN KEY (QuestionnaireID) REFERENCES Questionnaires(QuestionnaireID)
);
CREATE TABLE UserResponse (
    UserQuestionnaireID INT,
    QuestionID INT,
    Response TEXT,
    FOREIGN KEY (QuestionID) REFERENCES Questions(QuestionID),
    FOREIGN KEY (UserQuestionnaireID) REFERENCES UserQuestionnaire(UserQuestionnaireID)
);
--Not implemented yet--
--CREATE TABLE PatientPlan ()
--CREATE TABLE PlanMedication ()
--CREATE TABLE AppointmentRecord ()
-----------------------
--Education--
CREATE TABLE Articles (
    ArticleID INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255),
    Created DATETIME,
    Updated DATETIME,
);
CREATE TABLE InfoSections (
    SectionID INT AUTO_INCREMENT PRIMARY KEY,
    Title VARCHAR(255),
    SectionLevel VARCHAR(255),
    Info TEXT,
    CanRemind BOOLEAN,
    ArticleID INT,
    FOREIGN KEY (ArticleID) REFERENCES Articles(ArticleID)
);
CREATE TABLE InfoAssets (
    AssetID INT AUTO_INCREMENT PRIMARY KEY,
    AssetType VARCHAR(255),
    AssetName VARCHAR(255),
    Link TEXT,
    Source TEXT,
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
    IsCompleted BOOLEAN,
    Duedate DATETIME,
    IsRepeatable BOOLEAN,
    RepeatPeriod TIMESTAMP,
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

