INSERT INTO UserCredentials (UserName, UserPassword, UserRole)
VALUES (
        'admin',
        '$2y$10$J68pzi1vv7Sjez4Yt5siU.v4FrsXxD58APhQHiXNEldGNRa1um5uK',
        'ROLE_ADMIN'
    ),
    (
        'testUser',
        '$2a$10$hznjz2afk0Dlu3ye10eoPuLWDvnFTuI3ZPegsFs1KJ1mbbfH1MVzq',
        'ROLE_PATIENT'
    ),
    (
        'testProvider',
        '$2a$10$cibnUtr2BBkAQ2HibZDFWOarJcCtfRg1uj0HcfEd8z2WQyN4EePlC',
        'ROLE_PROVIDER'
    ),
    (
        'ms@testmail.ua',
        '$2a$10$VCmoUTlG5pfm0T8KuEFyZ.64mk0Y6xhpuoUjbUiEZqm4JyAOErnpG',
        'ROLE_PATIENT'
    ),
        (
        'db@testmail.com',
        '$2a$10$dV3B.pHh4trd.xu3O0zw5eAendv3pRHbpBG4llYOxywUHg89d542S',
        'ROLE_PATIENT'
    ),
        (
        'es@testmail.uk',
        '$2a$10$l93GjjS.lMd7z3cjFIJLqugkTOqoh4Ly5e7HIM88s.mrTlSz4UHkO',
        'ROLE_PATIENT'
    ),
    (
        'sa@testEmail.ua',
        '$2a$10$WJQ0iV7zpX07m.rKSPakr.6Yyyuorvcbh0ROHpoWuNyf7Q.OR9oSm',
        'ROLE_PATIENT'
    ),
    (
        'am@testEmail.uk',
        '$2a$10$Nv/oBsP/AARjTesWOeShI.l.aS30cqDqrTCz6/Lq8CvkBGnb2RCwu',
        'ROLE_PATIENT'
    );
INSERT INTO Questionnaires (
        QuestionnaireType,
        QuestionnaireName,
        QuestionnaireDesc
    )
VALUES (
        'QOL-ARM',
        'Lymphoedema Quality of Life Questionnaire - Arm',
        'This questionnaire measures the quality of life score for patients with chronic oedema/lymphoedema of one or both arms.'
    ),
    (
        'QOL-Breast',
        'Lymphoedema Quality of Life Questionnaire - Breast',
        'This questionnaire measures the quality of life for patients with chronic oedema/lymphoedema of the breast or chest.'
    ),
    (
        'QOL-LEG',
        'Lymphoedema Quality of Life Questionnaire - Leg',
        'This questionnaire measures the quality of life score for patients with chronic oedema/lymphoedema of one or both legs.'
    );
INSERT INTO Question (
        QuestionText,
        QuestionCategory,
        QuestionResponseType,
        QuestionnaireID
    )
VALUES -- Function (Daily Activities)
    (
        'How much does your swollen arm affect your daily activities in your occupation?',
        'Function',
        'Scale',
        1
    ),
    (
        'How much does your swollen arm affect your daily activities in doing your housework?',
        'Function',
        'Scale',
        1
    ),
    (
        'How much does your swollen arm affect your daily activities such as combing your hair?',
        'Function',
        'Scale',
        1
    ),
    (
        'How much does your swollen arm affect your daily activities of getting dressed?',
        'Function',
        'Scale',
        1
    ),
    (
        'How much does your swollen arm affect your daily activities of writing?',
        'Function',
        'Scale',
        1
    ),
    (
        'How much does your swollen arm affect your daily activities of eating?',
        'Function',
        'Scale',
        1
    ),
    (
        'How much does your swollen arm affect your daily activities of washing?',
        'Function',
        'Scale',
        1
    ),
    (
        'How much does your swollen arm affect your daily activities in cleaning teeth?',
        'Function',
        'Scale',
        1
    ),
    (
        'How much does your swollen arm affect your leisure activities/social life?',
        'Function',
        'Scale',
        1
    ),
    (
        'Please give examples of how your swollen arm affects your leisure activities/social life.',
        'Function',
        'Text',
        1
    ),
    (
        'How much do you have to depend on other people?',
        'Function',
        'Scale',
        1
    ),
    -- Appearance
    (
        'How much do you feel the swelling affects your appearance?',
        'Appearance',
        'Scale',
        1
    ),
    (
        'How much difficulty do you have finding clothes to fit?',
        'Appearance',
        'Scale',
        1
    ),
    (
        'How much difficulty do you have finding clothes you would like to wear?',
        'Appearance',
        'Scale',
        1
    ),
    (
        'Does the swelling affect how you feel about yourself?',
        'Appearance',
        'Scale',
        1
    ),
    (
        'Does it affect your relationships with other people?',
        'Appearance',
        'Scale',
        1
    ),
    -- Symptoms
    (
        'Does your lymphoedema cause you pain?',
        'Symptoms',
        'Scale',
        1
    ),
    (
        'Do you have any numbness in your swollen arm?',
        'Symptoms',
        'Scale',
        1
    ),
    (
        'Do you have any feelings of "pins & needles" or tingling in your swollen arm?',
        'Symptoms',
        'Scale',
        1
    ),
    (
        'Does your swollen arm feel weak?',
        'Symptoms',
        'Scale',
        1
    ),
    (
        'Does your swollen arm feel heavy?',
        'Symptoms',
        'Scale',
        1
    ),
    ('Do you feel tired?', 'Symptoms', 'Scale', 1),
    -- Emotion
    (
        'In the past week have you had trouble sleeping?',
        'Emotion',
        'Scale',
        1
    ),
    (
        'In the past week have you had difficulty concentrating on things like reading?',
        'Emotion',
        'Scale',
        1
    ),
    (
        'In the past week have you felt tense?',
        'Emotion',
        'Scale',
        1
    ),
    (
        'In the past week have you felt worried?',
        'Emotion',
        'Scale',
        1
    ),
    (
        'In the past week have you felt irritable?',
        'Emotion',
        'Scale',
        1
    ),
    (
        'In the past week have you felt depressed?',
        'Emotion',
        'Scale',
        1
    ),
    -- Overall Quality of Life
    (
        'Overall, how would you rate your quality of life at present?',
        'Quality of Life',
        'Scale',
        1
    ),
    -- Function (Daily Activities)
    (
        'How much does your swollen breast or chest affect the following daily activities? a) occupation',
        'Function',
        'Scale',
        2
    ),
    (
        'How much does your swollen breast or chest affect the following daily activities? b) housework',
        'Function',
        'Scale',
        2
    ),
    (
        'How much does your swollen breast or chest affect the following daily activities? c) dressing',
        'Function',
        'Scale',
        2
    ),
    (
        'How much does your swollen breast or chest affect the following daily activities? d) washing',
        'Function',
        'Scale',
        2
    ),
    (
        'How much does it affect your leisure activities/social life?',
        'Function',
        'Scale',
        2
    ),
    (
        'Please give examples of how it affects your leisure activities/social life.',
        'Function',
        'Text',
        2
    ),
    (
        'How much do you have to depend on other people?',
        'Function',
        'Scale',
        2
    ),
    -- Appearance
    (
        'How much do you feel the swelling affects your appearance?',
        'Appearance',
        'Scale',
        2
    ),
    (
        'How much difficulty do you have finding clothes including bras to fit?',
        'Appearance',
        'Scale',
        2
    ),
    (
        'How much difficulty do you have finding clothes including bras you would like to wear?',
        'Appearance',
        'Scale',
        2
    ),
    (
        'Does the swelling affect how you feel about yourself?',
        'Appearance',
        'Scale',
        2
    ),
    (
        'Does it affect your relationships with other people?',
        'Appearance',
        'Scale',
        2
    ),
    -- Symptoms
    (
        'Does your swollen breast or chest cause you pain?',
        'Symptoms',
        'Scale',
        2
    ),
    (
        'Do you have any numbness in your swollen breast or chest?',
        'Symptoms',
        'Scale',
        2
    ),
    (
        'Do you have any feelings of "pins & needles" or tingling in your swollen breast or chest?',
        'Symptoms',
        'Scale',
        2
    ),
    (
        'Do you have any feeling of tightness in your swollen breast or chest?',
        'Symptoms',
        'Scale',
        2
    ),
    (
        'Does your swollen breast feel heavy?',
        'Symptoms',
        'Scale',
        2
    ),
    -- Emotion
    (
        'In the past week, have you had trouble sleeping?',
        'Emotion',
        'Scale',
        2
    ),
    (
        'In the past week, have you had difficulty concentrating on things like reading?',
        'Emotion',
        'Scale',
        2
    ),
    (
        'In the past week, have you felt worried?',
        'Emotion',
        'Scale',
        2
    ),
    (
        'In the past week, have you felt irritable?',
        'Emotion',
        'Scale',
        2
    ),
    (
        'In the past week, have you felt depressed?',
        'Emotion',
        'Scale',
        2
    ),
    -- Overall Quality of Life
    (
        'Overall, how would you rate your quality of life at present?',
        'Quality of Life',
        'Scale',
        2
    ),
    (
        'How much does your swollen leg affect your walking?',
        'Function',
        'Scale',
        3
    ),
    (
        'How much does your swollen leg affect your ability to bend, e.g., to tie shoelaces or cut toenails?',
        'Function',
        'Scale',
        3
    ),
    (
        'How much does your swollen leg affect your ability to stand?',
        'Function',
        'Scale',
        3
    ),
    (
        'How much does your swollen leg affect your ability to get up from a chair?',
        'Function',
        'Scale',
        3
    ),
    (
        'How much does your swollen leg affect your occupation?',
        'Function',
        'Scale',
        3
    ),
    (
        'How much does your swollen leg affect your ability to do housework?',
        'Function',
        'Scale',
        3
    ),
    (
        'Does the swelling affect your leisure activities/social life?',
        'Function',
        'Scale',
        3
    ),
    (
        'Please give examples of how your swollen leg affects your leisure activities/social life.',
        'Function',
        'Text',
        3
    ),
    (
        'How much do you have to depend on other people?',
        'Function',
        'Scale',
        3
    ),
    (
        'How much do you feel the swelling affects your appearance?',
        'Appearance',
        'Scale',
        3
    ),
    (
        'How much difficulty do you have finding clothes to fit?',
        'Appearance',
        'Scale',
        3
    ),
    (
        'How much difficulty do you have finding clothes you would like to wear?',
        'Appearance',
        'Scale',
        3
    ),
    (
        'Do you have difficulty finding shoes to fit?',
        'Appearance',
        'Scale',
        3
    ),
    (
        'Do you have difficulty finding socks/tights/stockings to fit?',
        'Appearance',
        'Scale',
        3
    ),
    (
        'Does the swelling affect how you feel about yourself?',
        'Appearance',
        'Scale',
        3
    ),
    (
        'Does it affect your relationships with other people?',
        'Appearance',
        'Scale',
        3
    ),
    (
        'Does your lymphoedema cause you pain?',
        'Symptoms',
        'Scale',
        3
    ),
    (
        'Do you have any numbness in your swollen leg(s)?',
        'Symptoms',
        'Scale',
        3
    ),
    (
        'Do you have any feelings of "pins & needles" or tingling in your swollen leg(s)?',
        'Symptoms',
        'Scale',
        3
    ),
    (
        'Do your swollen leg(s) feel weak?',
        'Symptoms',
        'Scale',
        3
    ),
    (
        'Do your swollen leg(s) feel heavy?',
        'Symptoms',
        'Scale',
        3
    ),
    (
        'In the past week, have you had trouble sleeping?',
        'Emotion',
        'Scale',
        3
    ),
    (
        'In the past week, have you had difficulty concentrating on things, e.g., reading?',
        'Emotion',
        'Scale',
        3
    ),
    (
        'In the past week, have you felt tense?',
        'Emotion',
        'Scale',
        3
    ),
    (
        'In the past week, have you felt worried?',
        'Emotion',
        'Scale',
        3
    ),
    (
        'In the past week, have you felt irritable?',
        'Emotion',
        'Scale',
        3
    ),
    (
        'In the past week, have you felt depressed?',
        'Emotion',
        'Scale',
        3
    ),
    (
        'Overall, how would you rate your quality of life at present?',
        'Quality of Life',
        'Scale',
        3
    );
/* INSERT INTO UserQuestionnaires (
 QuestionnaireID,
 UserID,
 QuestionnaireCreatedDate,
 QuestionnaireDueDate
 )
 VALUES (1, 2, NOW(), '2024-12-31'); */
INSERT INTO UserQuestionnaires (
        QuestionnaireID,
        UserID,
        QuestionnaireCreatedDate,
        QuestionnaireDueDate,
        QuestionnaireStartDate,
        QuestionnaireInProgress,
        QuestionnaireIsCompleted,
        QuestionnaireCompletionDate
    )
VALUES (
        1,
        2,
        '2024-01-01 09:00:00',
        '2024-01-31 23:59:59',
        '2024-01-02 09:00:00',
        FALSE,
        TRUE,
        '2024-02-02 15:00:00' -- The date when the questionnaire was completed
    );
-- Insert corresponding UserQuestion entries for each question in the questionnaire
INSERT INTO UserQuestion (
        QuestionID,
        UserQuestionnaireID,
        UserResponseText,
        UserResponseScore,
        ResponseDateTime
    )
VALUES -- Function (Daily Activities)
    (1, 1, NULL, 3, '2024-01-02 15:00:00'),
    (2, 1, NULL, 4, '2024-01-02 15:05:00'),
    (3, 1, NULL, 2, '2024-01-02 15:10:00'),
    (4, 1, NULL, 3, '2024-01-02 15:15:00'),
    (5, 1, NULL, 4, '2024-01-02 15:20:00'),
    (6, 1, NULL, 2, '2024-01-02 15:25:00'),
    (7, 1, NULL, 4, '2024-01-02 15:30:00'),
    (8, 1, NULL, 3, '2024-01-02 15:35:00'),
    (9, 1, NULL, 4, '2024-01-02 15:40:00'),
    (
        10,
        1,
        'Struggles with some activities, but manageable.',
        NULL,
        '2024-01-02 15:45:00'
    ),
    (11, 1, NULL, 2, '2024-01-02 15:50:00'),
    -- Appearance
    (12, 1, NULL, 3, '2024-01-02 15:55:00'),
    (13, 1, NULL, 4, '2024-01-02 16:00:00'),
    (14, 1, NULL, 2, '2024-01-02 16:05:00'),
    (15, 1, NULL, 3, '2024-01-02 16:10:00'),
    (16, 1, NULL, 4, '2024-01-02 16:15:00'),
    -- Symptoms
    (17, 1, NULL, 2, '2024-01-02 16:20:00'),
    (18, 1, NULL, 3, '2024-01-02 16:25:00'),
    (19, 1, NULL, 4, '2024-01-02 16:30:00'),
    (20, 1, NULL, 2, '2024-01-02 16:35:00'),
    (21, 1, NULL, 3, '2024-01-02 16:40:00'),
    (22, 1, NULL, 4, '2024-01-02 16:45:00'),
    -- Emotion
    (23, 1, NULL, 2, '2024-01-02 16:50:00'),
    (24, 1, NULL, 3, '2024-01-02 16:55:00'),
    (25, 1, NULL, 4, '2024-01-02 17:00:00'),
    (26, 1, NULL, 2, '2024-01-02 17:05:00'),
    (27, 1, NULL, 3, '2024-01-02 17:10:00'),
    (28, 1, NULL, 4, '2024-01-02 17:15:00'),
    -- Overall Quality of Life
    (29, 1, NULL, 3, '2024-01-02 17:20:00');
INSERT INTO Patients (
        PatientEmail,
        PatientMobile,
        NHSNumber,
        PatientDOB,
        PatientName,
        PatientLastName,
        PatientTitle,
        PatientClinic,
        UserID,
        EncryptionKey
    )
VALUES (
        'tUR2SzL4c4rM5vYyYV+qsw==',
        'RaAr3fuWzhPBFnKFxkkcew==',
        '12345678',
        '1969-06-09',
        '4xLXfYk4oPqqdLVhpNfkpQ==',
        '/hnIldHqwWhUyQshrXH1bw==',
        'Lord',
        'Sherwood Forrest Hospitals',
        '2',
        'xlHXWQORPy97Go4sJsrY+XNNEx59m/NaDZ7K7OOuEVY='
    ),
    (
        'N1ZK5L3yQ43exR/4hJ5koA==',
        'm6ryU6G2k7uQBLfqiNLhSA==',
        '101',
        '1970-04-12',
        'X1Qv/v6uCfuQHsy2k5zMlQ==',
        'd/LvY1D9MoEa5a2DmahA/g==',
        'Ms',
        'Florence Nightingale Community Hospital',
        '4',
        'xlHXWQORPy97Go4sJsrY+XNNEx59m/NaDZ7K7OOuEVY='
    ),
    (
        'ArEH0sLs2dK7C4JTBrWPnw==',
        '7cMlSea03MaohInkTlpGuQ==',
        '102',
        '1950-12-01',
        'ssTusH2CtyDvzEL7LnD+6A==',
        '2TxbufnHhP0zsxQQRDSV9g==',
        'Mr',
        'Long Eaton Health Centre',
        '5',
        'xlHXWQORPy97Go4sJsrY+XNNEx59m/NaDZ7K7OOuEVY='
    ),
    (
        '4aQU1iCNtsoPM0h2Lu8Kwg==',
        '2br52wR3xuVMW6s4qJz9VA==',
        '103',
        '1982-11-23',
        '4xLXfYk4oPqqdLVhpNfkpQ==',
        'wYy+w30qVGDFm7ZGEBxA6A==',
        'Mr',
        'Sherwood Forrest Hospitals',
        '6',
        'xlHXWQORPy97Go4sJsrY+XNNEx59m/NaDZ7K7OOuEVY='
    ),
    (
        'hXeSZSd6zEhUQ5ODMUCZzA==',
        'KZIjJAtJgfrwUBVIadfXjA==',
        '104',
        '1967-07-14',
        'FNB+J0TDCFfMThNld3Y8Gw==',
        'dIHO2i8uS0Ygvcyh5ejH0Q==',
        'Mrs',
        'Long Eaton Health Centre',
        '7',
        'xlHXWQORPy97Go4sJsrY+XNNEx59m/NaDZ7K7OOuEVY='
    ),
    (
        's+N8esUPyIobptdk9W1jjg==',
        'bJm0QtoRIWGWTYH8BIjVbQ==',
        '105',
        '1956-10-30',
        'IR1fDczIUfdx3oBce43KWA==',
        'foP0i+sfwoaebgZBoQHIuw==',
        'Dr',
        'Florence Nightingale Community Hospital',
        '8',
        'xlHXWQORPy97Go4sJsrY+XNNEx59m/NaDZ7K7OOuEVY='
    );
INSERT INTO Providers (
        ProviderFirstName,
        ProviderLastName,
        ProviderTitle,
        ProviderOccupation,
        UserID
    )
VALUES ('Jane', 'Doe', 'Mrs', 'Nurse', 3);
INSERT INTO UserWidgets (UserID, WidgetName, Position)
VALUES (2, 'task-completion', 1);
INSERT INTO Task (TaskType, TaskName, TaskDesc, TaskRepeatPeriod)
VALUES (
        'Home Monitoring',
        'Arm Swelling Measurements',
        'Measure and record the swelling in the affected arm(s).',
        'DAILY'
    ),
    (
        'Home Monitoring',
        'Leg Swelling Measurements',
        'Measure and record the swelling in the affected leg(s).',
        'DAILY'
    ),
    (
        'Home Monitoring',
        'Symptom Tracking',
        'Record any new or worsening symptoms, such as pain, heaviness, or tightness in the limb.',
        'DAILY'
    ),
    (
        'Physical Therapy',
        'Manual Lymphatic Drainage',
        'Perform manual lymphatic drainage massage as instructed by the therapist.',
        'DAILY'
    ),
    (
        'Physical Therapy',
        'Compression Therapy',
        'Wear prescribed compression garments or bandages to manage swelling.',
        'DAILY'
    ),
    (
        'Exercise Plans',
        'Daily Exercises',
        'Perform prescribed daily exercises to improve lymphatic flow and mobility.',
        'DAILY'
    ),
    (
        'Exercise Plans',
        'Daily Steps',
        'Perform 5000 daily steps to improve lymphatic flow and mobility.',
        'DAILY'
    );
INSERT INTO UserTask (TaskID, UserID, Bitmask)
VALUES (1, 2, 2),
    -- Arm Swelling Measurements
    (2, 2, 3),
    -- Leg Swelling Measurements
    (4, 2, 7),
    -- Manual Lymphatic Drainage
    (5, 2, 6),
    -- Compression Therapy
    (6, 2, 1);
-- Daily Exercises