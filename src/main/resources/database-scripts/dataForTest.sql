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
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ProfileCreate', 'C', 'Profile', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ProfileRead', 'R', 'Profile', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ProfileWrite', 'W', 'Profile', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ProfileDelete', 'D', 'Profile', 0, now());
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
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'LessonCreate', 'C', 'Lesson', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'LessonRead', 'R', 'Lesson', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'LessonWrite', 'W', 'Lesson', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'LessonDelete', 'D', 'Lesson', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ChallengeCreate', 'C', 'Challenge', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ChallengeRead', 'R', 'Challenge', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ChallengeWrite', 'W', 'Challenge', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ChallengeDelete', 'D', 'Challenge', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ScheduleCreate', 'C', 'Schedule', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ScheduleRead', 'R', 'Schedule', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ScheduleWrite', 'W', 'Schedule', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'ScheduleDelete', 'D', 'Schedule', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'CalendarCreate', 'C', 'Calendar', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'CalendarRead', 'R', 'Calendar', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'CalendarWrite', 'W', 'Calendar', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'CalendarDelete', 'D', 'Calendar', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'FeedCreate', 'C', 'Feed', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'FeedRead', 'R', 'Feed', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'FeedWrite', 'W', 'Feed', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'FeedDelete', 'D', 'Feed', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'Accept', 'C', 'Accept', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'Denied', 'C', 'Denied', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'Material', 'R', 'Material', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 1, 0, 'Test', 'R', 'Test', 0, now());

INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ProfileCreate', 'C', 'Profile', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ProfileRead', 'R', 'Profile', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ProfileWrite', 'W', 'Profile', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ProfileDelete', 'D', 'Profile', 0, now());
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
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'LessonCreate', 'C', 'Lesson', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'LessonRead', 'R', 'Lesson', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'LessonWrite', 'W', 'Lesson', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'LessonDelete', 'D', 'Lesson', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ChallengeCreate', 'C', 'Challenge', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ChallengeRead', 'R', 'Challenge', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ChallengeWrite', 'W', 'Challenge', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ChallengeDelete', 'D', 'Challenge', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ScheduleCreate', 'C', 'Schedule', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ScheduleRead', 'R', 'Schedule', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ScheduleWrite', 'W', 'Schedule', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'ScheduleDelete', 'D', 'Schedule', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'CalendarCreate', 'C', 'Calendar', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'CalendarRead', 'R', 'Calendar', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'CalendarWrite', 'W', 'Calendar', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'CalendarDelete', 'D', 'Calendar', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'FeedCreate', 'C', 'Feed', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'FeedRead', 'R', 'Feed', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'FeedWrite', 'W', 'Feed', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'FeedDelete', 'D', 'Feed', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'Accept', 'C', 'Accept', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'Denied', 'C', 'Denied', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'Material', 'R', 'Material', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 2, 0, 'Test', 'R', 'Test', 0, now());

INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'ProfileWrite', 'W', 'Profile', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'ProfileRead', 'R', 'Profile', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'EventRead', 'R', 'Event', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'PrimaryGroupRead', 'R', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'WorkshopGroupRead', 'R', 'Group', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'LessonRead', 'R', 'Lesson', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'ChallengeRead', 'R', 'Challenge', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'ScheduleRead', 'R', 'Schedule', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'CalendarRead', 'R', 'Calendar', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'FeedRead', 'R', 'Feed', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'Accept', 'C', 'Accept', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'Denied', 'C', 'Denied', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'ReadTest', 'R', 'Test', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'Material', 'R', 'Material', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 3, 0, 'Test', 'R', 'Test', 0, now());

INSERT INTO permission VALUES (nextval('s_permission_seq'), 4, 0, 'ProfileRead', 'R', 'Profile', 0, now());
INSERT INTO permission VALUES (nextval('s_permission_seq'), 4, 0, 'ProfileWrite', 'W', 'Profile', 0, now());

-- (id, department_id, login_name, user_type, firstname, lastname, email, phone, skype, level, url, invite_date, password_can_expire, sys_status, create_dtm)
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 1, 'teacher', 'U', 'Maria', 'Ivanovna', 'proveng.teacher@gmail.com', NULL, NULL, NULL,
                                'https://lh5.googleusercontent.com/-XHK0R0DK2P4/AAAAAAAAAAI/AAAAAAAAABE/qpxkPhiOyUs/photo.jpg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 1, 'teacher2', 'U', 'Alexander', 'Vasnecov', 'email@mail.ru', '+380671234567',
                                'a.v.skype', NULL,
                                'http://s5o.ru/storage/simple/ru/edt/24/54/30/96/rued2a38fdff1.jpeg', now(), TRUE, 0,
   now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 1, 'loggggin', 'U', 'Viktor', 'Schenderovich', 'email@gmail.com', '+380501234567',
                                'v.skype', 'Elementary',
                                'http://s5o.ru/storage/simple/ru/edt/48/44/49/52/rue2c22d09554.jpeg', now(),
   TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 1, 'logDim', 'U', 'Dmitry', 'Ilyaschenko', 'email@yahoo.net', '+380481234567',
                                'DDD.skype', 'Elementary',
                                'http://s5o.ru/storage/simple/ru/edt/38/71/29/58/ruee4c8940cbf.jpeg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 2, 'Carevich', 'U', 'Ivan', 'Ivanuschenko', 'Ivanuschenko@yahoo.net', '+380481234567',
                                'Ivan.skype', 'Elementary',
                                'http://s5o.ru/storage/simple/ru/edt/22/79/46/41/rue574330eecc.jpeg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 2, 'Dlinniy', 'U', 'Sergey', 'Korovainiy', 'Korovainiy@gmail.net', '+380481234567',
                                'Sergey.skype', 'Elementary',
                                'http://s5o.ru/storage/simple/ru/edt/51/80/63/42/rue60fede1c1b.jpeg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 2, 'vsesilniy', 'U', 'Andrey', 'Koloborod`ko', 'Koloborod@mail.net', '+380481234567',
                                'Andrey.skype', 'Elementary',
                                'http://s5o.ru/storage/simple/ru/edt/80/15/17/61/rue36e45e2a76.jpeg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 3, 'VadLis', 'U', 'Vadim', 'Lisenkov', 'Lisenkov@yahoo.net', '+380481234567',
                                'Vadim.skype', 'Elementary',
                                'http://s5o.ru/storage/simple/ru/edt/50/87/35/78/ruee111f4d951.jpeg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 3, 'frankenstain', 'U', 'Igor', 'Formazon', 'Formazon@gmail.net', '+380481234567',
                                'Igor.skype', 'Pre-intermediate',
                                'http://s5o.ru/storage/simple/ru/edt/79/19///rue4ed823dd60.jpg', now(),
   TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 3, 'bulba', 'U', 'Taras', 'Schevchenko', 'Schevchenko@mail.net', '+380481234567',
                                'Taras.skype', 'Pre-intermediate',
                                'http://s5o.ru/storage/simple/ru/edt/10/20/78/23/rued24e422950.jpeg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 4, 'guslyar', 'U', 'Nikodim', 'Bezfamilniy', 'Bezfamilniy@yahoo.net', '+380481234567',
                                'Nikodim.skype', 'Pre-intermediate',
                                'http://s5o.ru/storage/simple/ru/edt/34/70/03/55/rue335673316d.jpeg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 4, 'my_login', 'U', 'Iliya', 'Muromets', 'Muromets@gmail.net', '+380481234567',
                                'Iliya.skype', 'Intermediate',
                                'http://s5o.ru/storage/simple/ru/edt/74/34/21/89/rueb8cc5776ae.jpeg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 5, 'popovich', 'U', 'Alexey', 'Popovich', 'Popovich@yahoo.net', '+380481234567',
                                'Alexey.skype', 'Intermediate',
                                'http://s5o.ru/storage/simple/ru/edt/40/35/31/51/rue700d8ba70d.jpeg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 5, 'veider', 'U', 'Dart', 'Temnosilovich', 'Temnosilovich@gmail.net', '+380481234567',
                                'Dart.skype', 'Intermediate',
                                'http://s5o.ru/storage/simple/ru/edt/15/36/31/28/rue543cbcb0ba.jpeg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 6, 'gorshok', 'U', 'Ivasik', 'Telesik', 'Telesik@mail.net', '+380481234567',
                                'Ivasik.skype', 'Upper-intermediate',
                                'http://s5o.ru/storage/simple/ru/edt/59/01/06/63/rue6340c86894.jpg',
   now(), TRUE, 0, now());
INSERT INTO po_user VALUES
  (nextval('s_po_contact_seq'), 6, 'PuPu', 'U', 'Vladimir', 'Kucherenko', 'Kucherenko@gmail.net', '+380481234567',
                                'Vladimir.skype', 'Upper-intermediate',
                                'http://s5o.ru/storage/simple/ru/edt/58/76/05/18/ruec8dd9441cf.jpeg',
   now(), TRUE, 0, now());

-- (id, name, place, roominess, note, sys_status, create_dtm)
INSERT INTO location VALUES (nextval('location_seq'), 'China, Tower', 'China, Tower', 15, 'some note', 0, now());
INSERT INTO location VALUES (nextval('location_seq'), 'San Diego, USA', 'San Diego, USA', 8, 'some note', 0, now());
INSERT INTO location VALUES (nextval('location_seq'), 'Chicago, USA', 'Chicago, USA', 8, 'some note', 0, now());
INSERT INTO location VALUES (nextval('location_seq'), 'Oxford, UK', 'Oxford, UK', 6, 'some note', 0, now());
INSERT INTO location VALUES (nextval('location_seq'), 'Cardiff, UK', 'Cardiff, UK', 6, 'some note', 0, now());
INSERT INTO location VALUES (nextval('location_seq'), 'Cambridge, UK', 'Cambridge, UK', 6, 'some note', 0, now());

-- (id, leaderId, name, level, sys_status, create_dtm, primary_group_flag)
INSERT INTO po_group VALUES (nextval('s_group_seq'), 1, 'Newbies', 'Beginner', 0, now(), 'Y');
INSERT INTO po_group VALUES (nextval('s_group_seq'), 1, 'Beginners', 'Elementary', 0, now(), 'Y');
INSERT INTO po_group VALUES (nextval('s_group_seq'), 1, 'Middle-1', 'Pre-intermediate', 0, now(), 'Y');
INSERT INTO po_group VALUES (nextval('s_group_seq'), 2, 'Middle-2', 'Intermediate', 0, now(), 'Y');
INSERT INTO po_group VALUES (nextval('s_group_seq'), 2, 'Middle-3', 'Upper-intermediate', 0, now(), 'Y');
INSERT INTO po_group VALUES (nextval('s_group_seq'), 3, 'HighLevel-1', 'Advanced', 0, now(), 'Y');
INSERT INTO po_group VALUES (nextval('s_group_seq'), 3, 'Highlevel-2', 'Proficient', 0, now(), 'Y');

-- (id, leaderId, creater_id, groupId, superevent_id, location_id, name, type, date_start, date_end, regular, note, sys_status, create_dtm)
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, NULL, 1, 'ParentEvent1', 'Lifetime', '2016-08-18', '2016-11-19', 'Once', 'Notes',
   0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 2, NULL, 1, 'ParentEvent2', 'Lifetime', '2016-09-01', '2016-12-02', 'Once', 'Notes',
   0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 2, NULL, 3, NULL, 1, 'ParentEvent3', 'Lifetime', '2016-09-01', '2016-12-02', 'Once', 'Notes',
   0, now());

INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, 1, 1, 'Monday Lesson', 'Schedule', '2016-08-22', '2016-08-23', 'Weekly',
   'first note', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, 1, 2, 'Wednesday Lesson', 'Schedule', '2016-08-24', '2016-08-25', 'Weekly',
   'second note', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 2, 2, 3, 'Tuesday Lesson', 'Schedule', '2016-09-06', '2016-09-07', 'Weekly',
   'first note', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 2, NULL, 3, 3, 3, 'Tuesday Lesson', 'Schedule', '2016-09-06', '2016-09-07', 'Weekly',
   'first note', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 2, 2, 4, 'Thursday Lesson', 'Schedule', '2016-09-01', '2016-09-02', 'Weekly',
   'second note', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 2, NULL, 3, 3, 4, 'Thursday Lesson', 'Schedule', '2016-09-01', '2016-09-02', 'Weekly',
   'second note', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, 4, 1, 'Introductory lesson.', 'Lesson', '2016-08-22 16:30', '2016-08-22 17:30',
                         'Once', 'This first lesson for beginners group Elementary level', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, 5, 2, 'New lesson.', 'Lesson', '2016-09-01 14:30', '2016-09-01 15:30', 'Once',
   'This second lesson for beginners group Elementary level', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, 4, 1, 'Second lesson.', 'Lesson', '2016-08-24 17:30', '2016-08-24 18:30', 'Once',
   'This third lesson for beginners group Elementary level', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, 6, 1, 'Intro lesson.', 'Lesson', '2016-09-06 16:45', '2016-09-06 17:45', 'Once',
   'This third lesson for beginners group Elementary level', 0, now());

INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, NULL, 3, 'Workshop for beginners', 'Workshop', '2016-08-27 13:15',
                         '2016-08-27 14:00', 'Once', 'This workshop for beginners', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, NULL, 4, 'Workshop for elementary', 'Workshop', '2016-09-07 16:00',
                         '2016-09-07 16:45', 'Once', 'This workshop for beginners', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, NULL, 5, 'Workshop for pre-intermediate', 'Workshop', '2016-10-13 18:15',
                         '2016-10-13 19:00', 'Once', 'This workshop for beginners', 0, now());

INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, NULL, 1, 'Challenge for intermediate', 'Challenge', '2016-09-30 19:00',
                         '2016-09-30 21:00', 'Once', 'This challenge for users who passed all FinalTests', 0, now());
INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, NULL, 2, 'Challenge for guests', 'Challenge', '2016-10-31 18:45',
                         '2016-10-31 20:45', 'Once', 'This challenge for users who passed start test', 0, now());

INSERT INTO event VALUES
  (nextval('event_seq'), 1, NULL, 1, NULL, NULL, 'Notification for user', 'Notification', NULL, NULL, 'Once',
   'Arnold shared a link', 0, now());

INSERT INTO event
VALUES (nextval('event_seq'), 1, 3, 1, 14, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 4, 1, 14, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 5, 1, 14, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 6, 1, 14, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 7, 1, 14, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 8, 1, 14, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 9, 1, 14, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 10, 1, 14, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 11, 1, 14, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 12, 1, 14, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 13, 1, 14, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 14, 1, 14, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 15, 1, 14, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 16, 1, 14, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());

INSERT INTO event
VALUES (nextval('event_seq'), 1, 3, 1, 15, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 4, 1, 15, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 5, 1, 15, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 6, 1, 15, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 7, 1, 15, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 8, 1, 15, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 9, 1, 15, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 10, 1, 15, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 11, 1, 15, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 12, 1, 15, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 13, 1, 15, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 14, 1, 15, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 15, 1, 15, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 16, 1, 15, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());

INSERT INTO event
VALUES (nextval('event_seq'), 1, 3, 1, 16, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 4, 1, 16, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 5, 1, 16, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 6, 1, 16, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 7, 1, 16, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 8, 1, 16, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 9, 1, 16, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 10, 1, 16, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 11, 1, 16, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 12, 1, 16, NULL, 'Denied by user', 'Denied', NULL, NULL, NULL, 'I can"t', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 13, 1, 16, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 14, 1, 16, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 15, 1, 16, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());
INSERT INTO event
VALUES (nextval('event_seq'), 1, 16, 1, 16, NULL, 'Accepted by user', 'Accepted', NULL, NULL, NULL, 'I can', 0, now());

INSERT INTO event VALUES
  (nextval('event_seq'), 1, 1, 1, 15, NULL, 'Cancelled by teacher', 'Cancelled', NULL, NULL, NULL, 'We cant', 0,
   now());

-- (id, user_id, event_id, mark, type, mark_date, sys_status, create_dtm)
INSERT INTO daybook (id, user_id, event_id, mark, type, mark_date, sys_status, create_dtm)
VALUES (nextval('daybook_seq'), 1, 5, 5, 'Lesson', now(), 0, now());
INSERT INTO daybook (id, user_id, event_id, mark, type, mark_date, sys_status, create_dtm)
VALUES (nextval('daybook_seq'), 2, 5, 12, 'Test', now(), 0, now());
INSERT INTO daybook (id, user_id, event_id, mark, type, mark_date, sys_status, create_dtm)
VALUES (nextval('daybook_seq'), 3, 5, 55, 'Presentation', now(), 0, now());
INSERT INTO daybook (id, user_id, event_id, mark, type, mark_date, sys_status, create_dtm)
VALUES (nextval('daybook_seq'), 4, 5, 0, 'StartTest', now(), 0, now());
INSERT INTO daybook (id, user_id, event_id, mark, type, mark_date, sys_status, create_dtm)
VALUES (nextval('daybook_seq'), 6, 5, 55, 'StartTest', now(), 0, now());
INSERT INTO daybook (id, user_id, event_id, mark, type, mark_date, sys_status, create_dtm)
VALUES (nextval('daybook_seq'), 9, 5, 55, 'StartTest', now(), 0, now());
INSERT INTO daybook (id, user_id, event_id, mark, type, mark_date, sys_status, create_dtm)
VALUES (nextval('daybook_seq'), 12, 5, 55, 'StartTest', now(), 0, now());
INSERT INTO daybook (id, user_id, event_id, mark, type, mark_date, sys_status, create_dtm)
VALUES (nextval('daybook_seq'), 15, 5, 55, 'StartTest', now(), 0, now());

-- (id, user_id, related_person_id, type, sys_status, create_dtm)
INSERT INTO relationship VALUES (nextval('relationship_seq'), 1, 2, 'Comate', 0, now());
INSERT INTO relationship VALUES (nextval('relationship_seq'), 1, 3, 'Test', 0, now());
INSERT INTO relationship VALUES (nextval('relationship_seq'), 3, 2, 'Comate', 0, now());

-- (id, user_id, password, sys_status, create_dtm)
INSERT INTO password VALUES (nextval('s_po_password_seq'), 1, 'password111', 0, now());
INSERT INTO password VALUES (nextval('s_po_password_seq'), 2, 'password2222', 0, now());
INSERT INTO password VALUES (nextval('s_po_password_seq'), 3, 'password33333', 0, now());

-- (id, user_id, groupId, primary_group_flag, sys_status, create_dtm)
INSERT INTO usergroup VALUES (nextval('s_user_group_seq'), 1, 1, 0, now());
INSERT INTO usergroup VALUES (nextval('s_user_group_seq'), 2, 2, 0, now());

-- (id, user_id, role_id, sys_status, create_dtm)
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 1, 2, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 2, 2, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 3, 1, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 4, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 5, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 6, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 7, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 8, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 9, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 10, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 11, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 12, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 13, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 14, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 15, 3, 0, now());
INSERT INTO userrole VALUES (nextval('s_user_role_seq'), 16, 3, 0, now());

-- (id, user_id, jsession, sys_status, create_dtm)
INSERT INTO loginhistory VALUES (nextval('s_login_history_seq'), 1, 'jsession1', 0, now());

--
-- Data for Name: test; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO test (id, name, version, duration, weight, pass_mark, allowed_attempts, min_level, type, parent_id, ignore_order, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_SEQ'), 'Start Test', 0, 3600000, 5, 0, 1, 0, 'START', NULL, FALSE, 0, '2016-10-05 21:53:50.018',
        NULL);
INSERT INTO public.test (id, name, version, duration, weight, pass_mark, allowed_attempts, min_level, type, parent_id, ignore_order, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_SEQ'), 'Test A', 0, 60000, 5, 1, 3, 1, 'REGULAR', NULL, FALSE, 0, '2016-10-15 14:30:46.430000',
        NULL);
INSERT INTO public.test (id, name, version, duration, weight, pass_mark, allowed_attempts, min_level, type, parent_id, ignore_order, sys_status, create_dtm, modify_dtm)
VALUES
  (nextval('S_TEST_SEQ'), 'Test B', 0, 0, 5, 1, 3, 2, 'REGULAR', NULL, FALSE, 0, '2016-10-15 14:30:50.328000', NULL);

--
-- Data for Name: testcard; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard1', NULL, '..... Caviar in the fridge.', 1, 0, 0, '2016-10-18 23:20:54.194',
   NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard2', NULL, 'George..... fly to Stockholm tomorrow.', 1, 1, 0,
   '2016-10-18 23:20:54.194', NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard3', NULL, 'They laughed a lot last night. The film ..... very funny.', 1, 2, 0,
   '2016-10-18 23:20:54.194', NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard4', NULL, 'I wish I ..... a car, I’m tired of catching the bus.', 1, 3, 0,
   '2016-10-18 23:20:54.194', NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard5', NULL, 'Let’s go for a walk in the mountains, ……', 1, 4, 0,
   '2016-10-18 23:20:54.2', NULL);

INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard1', NULL, 'My parents have got blue eyes but my … hair is black.', 2, 0, 0,
   '2016-10-18 23:21:06.19', NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard2', NULL, 'I’ll buy … milk if I go to the supermarket.', 2, 1, 0,
   '2016-10-18 23:21:06.19', NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard3', NULL, 'I’m not sure if I’ll go to the party. I … stay at home.', 2, 2, 0,
   '2016-10-18 23:21:06.194', NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard4', NULL, 'I … to Warsaw last week.', 2, 3, 0, '2016-10-18 23:21:06.195', NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard5', NULL, 'They come from Rome. They … be Italian.', 2, 4, 0,
   '2016-10-18 23:21:06.195', NULL);

INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard1', NULL, '... up at 7 o’clock.', 3, 0, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard2', NULL, 'She … the piano very well.', 3, 1, 0, '2016-10-18 23:22:43.845',
   NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard3', NULL, 'She said she … at 9 o’ clock.', 3, 2, 0, '2016-10-18 23:22:43.845',
   NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard4', NULL, '...you got any apples?', 3, 3, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testcard (id, name, order_number, question, test_id, array_index, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_TEST_CARD_SEQ'), 'testCard5', NULL, 'They’ve never … to a rock concert.', 3, 4, 0,
   '2016-10-18 23:22:43.845', NULL);
--
-- Data for Name: testanswer; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'There isn’t no', NULL, 1, 0, '2016-10-18 23:20:54.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'There is any', NULL, 1, 0, '2016-10-18 23:20:54.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', TRUE, 'There isn’t any', NULL, 1, 0, '2016-10-18 23:20:54.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'There aren’t no', NULL, 1, 0, '2016-10-18 23:20:54.194', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'to going', NULL, 2, 0, '2016-10-18 23:20:54.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'goes to', NULL, 2, 0, '2016-10-18 23:20:54.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', TRUE, 'is going to', NULL, 2, 0, '2016-10-18 23:20:54.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'go to', NULL, 2, 0, '2016-10-18 23:20:54.194', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'should have been', NULL, 3, 0, '2016-10-18 23:20:54.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', TRUE, 'must have been', NULL, 3, 0, '2016-10-18 23:20:54.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', NULL, 'was to be', NULL, 3, 0, '2016-10-18 23:20:54.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'should be', NULL, 3, 0, '2016-10-18 23:20:54.194', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, ' have', NULL, 4, 0, '2016-10-18 23:20:54.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'would have', NULL, 4, 0, '2016-10-18 23:20:54.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', TRUE, 'had', NULL, 4, 0, '2016-10-18 23:20:54.195', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'had had', NULL, 4, 0, '2016-10-18 23:20:54.195', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'don’t we?', NULL, 5, 0, '2016-10-18 23:20:54.2', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'let us?', NULL, 5, 0, '2016-10-18 23:20:54.2', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', TRUE, 'shall we?', NULL, 5, 0, '2016-10-18 23:20:54.2', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'will we?', NULL, 5, 0, '2016-10-18 23:20:54.2', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'fathers’', NULL, 6, 0, '2016-10-18 23:21:06.19', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'fathers', NULL, 6, 0, '2016-10-18 23:21:06.19', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', TRUE, 'father’s', NULL, 6, 0, '2016-10-18 23:21:06.19', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'father', NULL, 6, 0, '2016-10-18 23:21:06.19', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', TRUE, 'some', NULL, 7, 0, '2016-10-18 23:21:06.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'a', NULL, 7, 0, '2016-10-18 23:21:06.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', NULL, 'any', NULL, 7, 0, '2016-10-18 23:21:06.194', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'an', NULL, 7, 0, '2016-10-18 23:21:06.194', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'must', NULL, 8, 0, '2016-10-18 23:21:06.195', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'mustn’t', NULL, 8, 0, '2016-10-18 23:21:06.195', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', TRUE, 'might', NULL, 8, 0, '2016-10-18 23:21:06.195', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'has to', NULL, 8, 0, '2016-10-18 23:21:06.195', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'am not going', NULL, 9, 0, '2016-10-18 23:21:06.195', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', TRUE, 'went', NULL, 9, 0, '2016-10-18 23:21:06.195', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', NULL, 'go', NULL, 9, 0, '2016-10-18 23:21:06.195', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'was', NULL, 9, 0, '2016-10-18 23:21:06.195', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'can', NULL, 10, 0, '2016-10-18 23:21:06.195', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'can’t', NULL, 10, 0, '2016-10-18 23:21:06.195', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', NULL, 'could', NULL, 10, 0, '2016-10-18 23:21:06.195', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', TRUE, 'must', NULL, 10, 0, '2016-10-18 23:21:06.195', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'get usually', NULL, 11, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'get often', NULL, 11, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', TRUE, 'usually get', NULL, 11, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'get sometimes', NULL, 11, 0, '2016-10-18 23:22:43.845', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', TRUE, 'can play', NULL, 12, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'can', NULL, 12, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', NULL, 'does', NULL, 12, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'play', NULL, 12, 0, '2016-10-18 23:22:43.845', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'has come', NULL, 13, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'coming', NULL, 13, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', TRUE, 'was coming', NULL, 13, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'come', NULL, 13, 0, '2016-10-18 23:22:43.845', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'Has', NULL, 14, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', NULL, 'Do', NULL, 14, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', NULL, 'Is', NULL, 14, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', TRUE, 'Have', NULL, 14, 0, '2016-10-18 23:22:43.845', NULL);

INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'A', NULL, 'saw', NULL, 15, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'B', TRUE, 'been', NULL, 15, 0, '2016-10-18 23:22:43.845', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'C', NULL, 'gone', NULL, 15, 0, '2016-10-18 23:22:43.846', NULL);
INSERT INTO testanswer (id, name, is_correct, text, is_visible, test_card_id, sys_status, create_dtm, modify_dtm)
VALUES (nextval('S_TEST_ANSWER_SEQ'), 'D', NULL, 'seen', NULL, 15, 0, '2016-10-18 23:22:43.846', NULL);

--
-- Data for Name: material; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO material (id, name, min_level, description, link, type, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_MATERIAL_SEQ'), 'The Wanderer of the North', 1,
   'In this story from To Build a Fire and Other Stories, a mysterious stranger tells a few mushers about how he followed his wife''s kidnapper from his home in the Aleutian Islands to Tokyo Bay and finally to the Yukon Territory in Canada. Author: Jack London .',
   'https://americanenglish.state.gov/files/ae/resource_files/the-wanderer-ofthe-north.mp3', 'AUDIO', 0,
   '2016-10-19 23:51:15.404', NULL);
INSERT INTO material (id, name, min_level, description, link, type, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_MATERIAL_SEQ'), 'The Wanderer of the North', 2,
   'In this story from To Build a Fire and Other Stories, a mysterious stranger tells a few mushers about how he followed his wife''s kidnapper from his home in the Aleutian Islands to Tokyo Bay and finally to the Yukon Territory in Canada. Author: Jack London .',
   'https://americanenglish.state.gov/files/ae/resource_files/the-wanderer-of-the-north.pdf', 'LINK', 0,
   '2016-10-19 23:51:30.045', NULL);
INSERT INTO material (id, name, min_level, description, link, type, sys_status, create_dtm, modify_dtm) VALUES
  (nextval('S_MATERIAL_SEQ'), 'Pronunciation', 3,
   'Use this video to learn about pronouncing "than" in sentences with comparative adjectives.',
   'http://av.voanews.com/Videoroot/Pangeavideo/2016/10/3/33/334705e4-cf26-44d9-aa82-0f4f11a37074.mp4', 'VIDEO', 0,
   '2016-10-20 00:47:16.938', NULL);