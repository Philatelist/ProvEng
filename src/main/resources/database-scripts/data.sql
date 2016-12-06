-- (id, name, url, description, sys_status, create_dtm)
INSERT INTO department
VALUES (nextval('s_address_seq'), 'Admin Department', 'http://provectus-it.com', 'Admin Department', 0, now());
INSERT INTO department
VALUES (nextval('s_address_seq'), 'Administration', 'http://provectus-it.com', 'Administration', 0, now());
INSERT INTO department
VALUES (nextval('s_address_seq'), 'Corporate support', 'http://provectus-it.com', 'Corporate support', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'cPrime', 'http://provectus-it.com', 'cPrime', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'Determine', 'http://provectus-it.com', 'Determine', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'eCommerce', 'http://provectus-it.com', 'eCommerce', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'Education', 'http://provectus-it.com', 'Education', 0, now());
INSERT INTO department
VALUES (nextval('s_address_seq'), 'Finance Department', 'http://provectus-it.com', 'Finance Department', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'HR', 'http://provectus-it.com', 'HR', 0, now());
INSERT INTO department
VALUES (nextval('s_address_seq'), 'IT Department', 'http://provectus-it.com', 'IT Department', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'Kazan', 'http://provectus-it.com', 'Kazan', 0, now());
INSERT INTO department
VALUES (nextval('s_address_seq'), 'Legal Department', 'http://provectus-it.com', 'Legal Department', 0, now());
INSERT INTO department
VALUES (nextval('s_address_seq'), 'LiveNation', 'http://provectus-it.com', 'LiveNation', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'Marketing', 'http://provectus-it.com', 'Marketing', 0, now());
INSERT INTO department
VALUES (nextval('s_address_seq'), 'Men`s Wearhouse', 'http://provectus-it.com', 'Men`s Wearhouse', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'Minted', 'http://provectus-it.com', 'Minted', 0, now());
INSERT INTO department
VALUES (nextval('s_address_seq'), 'Mobile studio', 'http://provectus-it.com', 'Mobile studio', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'ModelN', 'http://provectus-it.com', 'ModelN', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'NOC', 'http://provectus-it.com', 'NOC', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'PR', 'http://provectus-it.com', 'PR', 0, now());
INSERT INTO department VALUES (nextval('s_address_seq'), 'PIX', 'http://provectus-it.com', 'PIX', 0, now());

-- (id, name, sys_status, create_dtm)
INSERT INTO role VALUES (nextval('s_roles_seq'), 'Administrator', 0, now());
INSERT INTO role VALUES (nextval('s_roles_seq'), 'Teacher', 0, now());
INSERT INTO role VALUES (nextval('s_roles_seq'), 'Student', 0, now());
INSERT INTO role VALUES (nextval('s_roles_seq'), 'Guest', 0, now());

-- (id, role_id, type, name, access_flag, object, sys_status, create_dtm)
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ProfileCreate', 'C', 'User', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ProfileRead', 'R', 'User', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ProfileWrite', 'W', 'User', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ProfileDelete', 'D', 'User', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'EventCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'EventRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'EventWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'EventDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'PrimaryGroupCreate', 'C', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'PrimaryGroupRead', 'R', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'PrimaryGroupWrite', 'W', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'PrimaryGroupDelete', 'D', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'WorkshopGroupCreate', 'C', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'WorkshopGroupRead', 'R', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'WorkshopGroupWrite', 'W', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'WorkshopGroupDelete', 'D', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'LessonCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'LessonRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'LessonWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'LessonDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ChallengeCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ChallengeRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ChallengeWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ChallengeDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ScheduleCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ScheduleRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ScheduleWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ScheduleDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'CalendarCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'CalendarRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'CalendarWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'CalendarDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'FeedCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'FeedRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'FeedWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'FeedDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'Accept', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'Denied', 'C', 'Event', 0, now());

INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ProfileCreate', 'C', 'User', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ProfileRead', 'R', 'User', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ProfileWrite', 'W', 'User', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ProfileDelete', 'D', 'User', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'EventCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'EventRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'EventWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'EventDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'PrimaryGroupCreate', 'C', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'PrimaryGroupRead', 'R', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'PrimaryGroupWrite', 'W', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'PrimaryGroupDelete', 'D', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'WorkshopGroupCreate', 'C', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'WorkshopGroupRead', 'R', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'WorkshopGroupWrite', 'W', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'WorkshopGroupDelete', 'D', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'LessonCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'LessonRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'LessonWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'LessonDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ChallengeCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ChallengeRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ChallengeWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ChallengeDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ScheduleCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ScheduleRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ScheduleWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ScheduleDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'CalendarCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'CalendarRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'CalendarWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'CalendarDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'FeedCreate', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'FeedRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'FeedWrite', 'W', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'FeedDelete', 'D', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'Accept', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'Denied', 'C', 'Event', 0, now());

INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'ProfileWrite', 'W', 'User', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'ProfileRead', 'R', 'User', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'EventRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'PrimaryGroupRead', 'R', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'WorkshopGroupRead', 'R', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'LessonRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'ChallengeRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'ScheduleRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'CalendarRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'FeedRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'Accept', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'Denied', 'C', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'ReadTest', 'R', 'Test', 0, now());

INSERT INTO permission VALUES (nextval('s_permission_seq'), 4, 0, 'ProfileRead', 'R', 'User', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 4, 0, 'ProfileWrite', 'W', 'User', 0, now());

-- (id, name, place, roominess, note, sys_status. create_dtm)
INSERT INTO location VALUES (nextval('location_seq'), 'China, Tower', 'China, Tower', 15, 'some note', 0, now());
INSERT INTO location VALUES (nextval('location_seq'), 'San Diego, USA', 'San Diego, USA', 8, 'some note', 0, now());
INSERT INTO location VALUES (nextval('location_seq'), 'Chicago, USA', 'Chicago, USA', 8, 'some note', 0, now());
INSERT INTO location VALUES (nextval('location_seq'), 'Oxford, UK', 'Oxford, UK', 6, 'some note', 0, now());
INSERT INTO location VALUES (nextval('location_seq'), 'Cardiff, UK', 'Cardiff, UK', 6, 'some note', 0, now());
INSERT INTO location VALUES (nextval('location_seq'), 'Cambridge, UK', 'Cambridge, UK', 6, 'some note', 0, now());