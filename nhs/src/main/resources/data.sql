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