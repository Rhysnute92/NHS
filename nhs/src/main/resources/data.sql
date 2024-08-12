INSERT INTO UserCredentials (UserName, UserPassword, UserRole)
VALUES 
('admin', '$2y$10$J68pzi1vv7Sjez4Yt5siU.v4FrsXxD58APhQHiXNEldGNRa1um5uK', 'ROLE_ADMIN'),
('testUser', '$2a$10$hznjz2afk0Dlu3ye10eoPuLWDvnFTuI3ZPegsFs1KJ1mbbfH1MVzq', 'ROLE_PATIENT'),
('testProvider', '$2a$10$cibnUtr2BBkAQ2HibZDFWOarJcCtfRg1uj0HcfEd8z2WQyN4EePlC', 'ROLE_PROVIDER');

INSERT INTO UserWidgets (UserID, WidgetName, Position)
VALUES (2, 'task-completion', 1);

INSERT INTO Task (TaskType, TaskName, TaskDesc, TaskRepeatPeriod) VALUES 
('Home Monitoring', 'Arm Swelling Measurements', 'Measure and record the swelling in the affected arm(s) each morning.', 'DAILY'),
('Home Monitoring', 'Leg Swelling Measurements', 'Measure and record the swelling in the affected leg(s) each morning.', 'DAILY'),
('Home Monitoring', 'Symptom Tracking', 'Record any new or worsening symptoms, such as pain, heaviness, or tightness in the limb.', 'DAILY'),
('Physical Therapy', 'Manual Lymphatic Drainage', 'Perform manual lymphatic drainage massage as instructed by the therapist.', 'DAILY'),
('Physical Therapy', 'Compression Therapy', 'Wear prescribed compression garments or bandages to manage swelling.', 'DAILY'),
('Exercise Plans', 'Daily Exercises', 'Perform prescribed daily exercises to improve lymphatic flow and mobility.', 'DAILY'),
('Exercise Plans', 'Daily Steps', 'Perform 5000 daily steps to improve lymphatic flow and mobility.', 'DAILY');

INSERT INTO UserTask (TaskID, UserID, Bitmask) VALUES
(1, 2, 2), -- Arm Swelling Measurements
(2, 2, 3), -- Leg Swelling Measurements
(4, 2, 7), -- Manual Lymphatic Drainage
(5, 2, 6);-- Compression Therapy
