INSERT INTO Admin (AdminName, AdminPassword)
VALUES ('admin', '$2y$10$J68pzi1vv7Sjez4Yt5siU.v4FrsXxD58APhQHiXNEldGNRa1um5uK', 'ADMIN');
INSERT INTO PatientCredentials (UserName, UserPassword)
VALUES ('testUser', '$2y$10$zxSfL8MeOavhI1So9E2M.ecsGVPQK/TCLy28bhpw/Z8aXWlAjCDZ6', 'PATIENT');
INSERT INTO ProviderCredentials (UserName, UserPassword)
VALUES ('testProvider', '$2y$10$AXhw3DL/jSk3xfUEx5JXbezFBfw2lSiAg63ale77wSXBxdKwiwUPi', 'PROVIDER');
INSERT INTO UserWidgets (UserID, WidgetName, Position)
VALUES (1, 'task-completion', 1);