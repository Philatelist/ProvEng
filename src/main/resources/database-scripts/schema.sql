/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     7/27/2016 13:04:01                           */
/*==============================================================*/

DROP TABLE IF EXISTS DayBook, Department,
Test, TestCard, TestAnswer, TestCardChoice, TestAttempt, Material,
Event, po_group, LoginHistory, PASSWORD,
Permission, Relationship, Role, po_user,
Location, UserGroup, UserRole CASCADE;

DROP TABLE IF EXISTS Permission CASCADE;
-- po_user_event, po_user_po_group, po_user_role, role_permission, event_location, po_user_daybook, event_daybook CASCADE;

DROP INDEX IF EXISTS AK_ROLE, AK_USER CASCADE;

DROP DOMAIN IF EXISTS E_TIMESTAMP, PRIMARY_ID, STATUS,
TABLENAME, XMLDEFPATH, XMLVERSION;

DROP SEQUENCE IF EXISTS DAYBOOK_SEQ, EVENT_SEQ, RELATIONSHIP_SEQ, S_TEST_SEQ, S_DEV_SEQ,
S_TEST_CARD_SEQ, S_TEST_ANSWER_SEQ, S_TEST_CARD_CHOICE_SEQ, S_TEST_ATTEMPT_SEQ,
S_MATERIAL_SEQ, S_ADDRESS_SEQ, S_GROUP_SEQ, S_LOGIN_HISTORY_SEQ,
S_PERMISSION_SEQ, S_PO_CONTACT_SEQ, S_PO_PASSWORD_SEQ, S_ROLES_SEQ,
S_USER_GROUP_SEQ, S_USER_ROLE_SEQ, LOCATION_SEQ, S_USER_GROUP_SEQ, S_USER_ROLE_SEQ CASCADE;

CREATE SEQUENCE DAYBOOK_SEQ;
CREATE SEQUENCE EVENT_SEQ;
CREATE SEQUENCE RELATIONSHIP_SEQ;
CREATE SEQUENCE S_ADDRESS_SEQ;
CREATE SEQUENCE S_GROUP_SEQ;
CREATE SEQUENCE LOCATION_SEQ;
CREATE SEQUENCE S_LOGIN_HISTORY_SEQ;
CREATE SEQUENCE S_PERMISSION_SEQ;
CREATE SEQUENCE S_PO_CONTACT_SEQ;
CREATE SEQUENCE S_PO_PASSWORD_SEQ;
CREATE SEQUENCE S_ROLES_SEQ;
CREATE SEQUENCE S_USER_GROUP_SEQ;
CREATE SEQUENCE S_USER_ROLE_SEQ;
CREATE SEQUENCE S_TEST_SEQ;
CREATE SEQUENCE S_DEV_SEQ;
CREATE SEQUENCE S_TEST_ANSWER_SEQ;
CREATE SEQUENCE S_TEST_CARD_CHOICE_SEQ;
CREATE SEQUENCE S_TEST_ATTEMPT_SEQ;
CREATE SEQUENCE S_TEST_CARD_SEQ;
CREATE SEQUENCE S_MATERIAL_SEQ;


/*==============================================================*/
/* Domain: E_TIMESTAMP                                          */
/*==============================================================*/
CREATE DOMAIN E_TIMESTAMP AS TIMESTAMP;

/*==============================================================*/
/* Domain: PRIMARY_ID                                           */
/*==============================================================*/
CREATE DOMAIN PRIMARY_ID AS NUMERIC;

/*==============================================================*/
/* Domain: STATUS                                               */
/*==============================================================*/

CREATE DOMAIN STATUS AS NUMERIC(1);

COMMENT ON DOMAIN STATUS IS
'0 - active, -1 - deleted';

/*==============================================================*/
/* Domain: TABLENAME                                            */
/*==============================================================*/
CREATE DOMAIN TABLENAME AS VARCHAR(32);

/*==============================================================*/
/* Domain: XMLDEFPATH                                           */
/*==============================================================*/
CREATE DOMAIN XMLDEFPATH AS VARCHAR(200);

COMMENT ON DOMAIN XMLDEFPATH IS
'Full path nane for XML definishion of object';

/*==============================================================*/
/* Domain: XMLVERSION                                           */
/*==============================================================*/
CREATE DOMAIN XMLVERSION AS NUMERIC(5);

/*==============================================================*/
/* Table: MATERIAL                                               */
/*==============================================================*/
CREATE TABLE Material (
  ID          BIGINT       NOT NULL DEFAULT NEXTVAL('S_MATERIAL_SEQ'),
  NAME        VARCHAR(256) NOT NULL,
  MIN_LEVEL   NUMERIC(2),
  DESCRIPTION VARCHAR(512) NOT NULL,
  LINK        VARCHAR(256) NOT NULL,
  TYPE        VARCHAR(32)  NOT NULL,
  SYS_STATUS  NUMERIC(1)   NOT NULL DEFAULT 0,
  CREATE_DTM  TIMESTAMP    NOT NULL DEFAULT NOW(),
  MODIFY_DTM  TIMESTAMP,
  CONSTRAINT PK_MATERIAL PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: DayBook                                               */
/*==============================================================*/
CREATE TABLE DayBook (
  ID              BIGINT      NOT NULL DEFAULT NEXTVAL('daybook_seq'),
  USER_ID         BIGINT,
  EVENT_ID        BIGINT,
  TEST_ATTEMPT_ID BIGINT,
  MARK            NUMERIC     NOT NULL,
  TYPE            VARCHAR(32) NOT NULL
    CONSTRAINT CKC_TYPE_DAYBOOK CHECK (TYPE IN
                                       ('Lesson', 'Presentation', 'Test', 'StartTest', 'Workshop', 'TotalPoints', 'FinalTest')),
  MARK_DATE       TIMESTAMP   NOT NULL,
  SYS_STATUS      NUMERIC(1)  NOT NULL DEFAULT 0,
  CREATE_DTM      TIMESTAMP   NOT NULL DEFAULT NOW(),
  MODIFY_DTM      TIMESTAMP,
  CONSTRAINT PK_DAYBOOK PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table Test													*/
/*==============================================================*/
CREATE TABLE Test (
  ID               BIGINT      NOT NULL DEFAULT NEXTVAL('S_TEST_SEQ'),
  NAME             VARCHAR(64) NOT NULL,
  VERSION          SMALLINT,
  DURATION         BIGINT,
  WEIGHT           SMALLINT,
  PASS_MARK        SMALLINT,
  ALLOWED_ATTEMPTS SMALLINT,
  MIN_LEVEL        NUMERIC(2),
  TYPE             VARCHAR(32),
  PARENT_ID        BIGINT,
  IGNORE_ORDER     BOOLEAN,
  SYS_STATUS       NUMERIC(1)  NOT NULL DEFAULT 0,
  CREATE_DTM       TIMESTAMP   NOT NULL DEFAULT NOW(),
  MODIFY_DTM       TIMESTAMP,
  CONSTRAINT PK_TEST PRIMARY KEY (ID)
);

/*==============================================================*/
/*  Table:  TestCard                  	                */
/*==============================================================*/
CREATE TABLE TestCard (
  ID           BIGINT       NOT NULL DEFAULT NEXTVAL('S_TEST_CARD_SEQ'),
  NAME         VARCHAR(64)  NOT NULL,
  ORDER_NUMBER SMALLINT,
  QUESTION     VARCHAR(512) NOT NULL,
  TEST_ID      BIGINT,
  ARRAY_INDEX  SMALLINT,
  SYS_STATUS   NUMERIC(1)   NOT NULL DEFAULT 0,
  CREATE_DTM   TIMESTAMP    NOT NULL DEFAULT NOW(),
  MODIFY_DTM   TIMESTAMP,
  CONSTRAINT PK_TEST_CARD PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: TestAnswer                                           */
/*==============================================================*/
CREATE TABLE TestAnswer (
  ID           BIGINT       NOT NULL DEFAULT NEXTVAL('S_TEST_ANSWER_SEQ'),
  NAME         VARCHAR(64)  NOT NULL,
  IS_CORRECT   BOOLEAN,
  TEXT         VARCHAR(256) NOT NULL,
  IS_VISIBLE   BOOLEAN,
  TEST_CARD_ID BIGINT,
  SYS_STATUS   NUMERIC(1)   NOT NULL DEFAULT 0,
  CREATE_DTM   TIMESTAMP    NOT NULL DEFAULT NOW(),
  MODIFY_DTM   TIMESTAMP,
  CONSTRAINT PK_TEST_ANSWER PRIMARY KEY (ID)

);

/*==============================================================*/
/* Table: TestCardChoice                                            */
/*==============================================================*/
CREATE TABLE TestCardChoice (
  ID              BIGINT     NOT NULL DEFAULT NEXTVAL('S_TEST_CARD_CHOICE_SEQ'),
  TEST_ANSWER_ID  BIGINT,
  TEST_CARD_ID    BIGINT,
  TEST_ATTEMPT_ID BIGINT,
  SYS_STATUS      NUMERIC(1) NOT NULL DEFAULT 0,
  CREATE_DTM      TIMESTAMP  NOT NULL DEFAULT NOW(),
  MODIFY_DTM      TIMESTAMP,
  CONSTRAINT PK_TEST_CARD_CHOICE PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: TestAttempt                                           */
/*==============================================================*/
CREATE TABLE TestAttempt (
  ID         BIGINT     NOT NULL DEFAULT NEXTVAL('S_TEST_ATTEMPT_SEQ'),
  IS_PASSED  BOOLEAN,
  NOTES      VARCHAR(256),
  TEST_ID    BIGINT,
  SYS_STATUS NUMERIC(1) NOT NULL DEFAULT 0,
  CREATE_DTM TIMESTAMP  NOT NULL DEFAULT NOW(),
  MODIFY_DTM TIMESTAMP,
  CONSTRAINT PK_TEST_ATTEMPT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Department                                            */
/*==============================================================*/
CREATE TABLE Department (
  ID          BIGINT      NOT NULL DEFAULT NEXTVAL('s_address_seq'),
  NAME        VARCHAR(64) NOT NULL,
  URL         VARCHAR(256),
  DESCRIPTION VARCHAR(2000),
  SYS_STATUS  NUMERIC(1)  NOT NULL DEFAULT 0,
  CREATE_DTM  TIMESTAMP   NOT NULL DEFAULT NOW(),
  MODIFY_DTM  TIMESTAMP,
  CONSTRAINT PK_DEPARTMENT PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Developer                                             */
/*==============================================================*/
CREATE TABLE Developers (
  ID         BIGINT      NOT NULL DEFAULT NEXTVAL('s_dev_seq'),
  EMAIL      VARCHAR(64) NOT NULL,
  SYS_STATUS NUMERIC(1)  NOT NULL DEFAULT 0,
  CREATE_DTM TIMESTAMP   NOT NULL DEFAULT NOW(),
  MODIFY_DTM TIMESTAMP,
  CONSTRAINT PK_DEVELOPER PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Event                                                 */
/*==============================================================*/
CREATE TABLE Event (
  ID            BIGINT      NOT NULL DEFAULT NEXTVAL('event_seq'),
  LEADER_ID     BIGINT      NOT NULL,
  CREATER_ID    BIGINT,
  GROUP_ID      BIGINT,
  SUPEREVENT_ID BIGINT,
  LOCATION_ID   BIGINT,
  NAME          VARCHAR(64),
  TYPE          VARCHAR(32) NOT NULL
    CONSTRAINT CKC_TYPE_EVENT CHECK (TYPE IN ('Lesson', 'Presentation', 'Test', 'Workshop', 'Cancelled', 'Accepted',
                                                        'Visited', 'Schedule', 'Notification', 'Invite', 'Lifetime',
                                              'Denied', 'Challenge', 'Material')),
  DATE_START    TIMESTAMP,
  DATE_END      TIMESTAMP,
  REGULAR       VARCHAR(32)
    CONSTRAINT CKC_REGULAR_EVENT CHECK (REGULAR IN ('Once', 'Weekly', 'Monthly')),
  NOTE          VARCHAR(1000),
  SYS_STATUS    NUMERIC(1)  NOT NULL DEFAULT 0,
  CREATE_DTM    TIMESTAMP   NOT NULL DEFAULT NOW(),
  MODIFY_DTM    TIMESTAMP,
  CONSTRAINT PK_EVENT PRIMARY KEY (ID),
  ITEM_NAME     VARCHAR(1000),
  ITEM_ID       BIGINT
);

/*==============================================================*/
/* Table: Location                                          */
/*==============================================================*/
CREATE TABLE Location (
  ID         BIGINT      NOT NULL DEFAULT NEXTVAL('location_seq'),
  NAME       VARCHAR(64) NOT NULL,
  PLACE      VARCHAR(64) NOT NULL,
  ROOMINESS  NUMERIC(3),
  NOTE       VARCHAR(100),
  SYS_STATUS NUMERIC(1)  NOT NULL DEFAULT 0,
  CREATE_DTM TIMESTAMP   NOT NULL DEFAULT NOW(),
  MODIFY_DTM TIMESTAMP,
  CONSTRAINT PK_LOCATION PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: LoginHistory                                          */
/*==============================================================*/
CREATE TABLE LoginHistory (
  ID          BIGINT       NOT NULL DEFAULT NEXTVAL('s_login_history_seq'),
  USER_ID     BIGINT       NOT NULL,
  JSESSION_ID VARCHAR(200) NULL,
  SYS_STATUS  NUMERIC(1)   NOT NULL DEFAULT 0,
  CREATE_DTM  TIMESTAMP    NOT NULL DEFAULT NOW(),
  MODIFY_DTM  TIMESTAMP    NULL,
  CONSTRAINT PK_LOGINHISTORY PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: PASSWORD                                              */
/*==============================================================*/
CREATE TABLE PASSWORD (
  ID         BIGINT       NOT NULL DEFAULT NEXTVAL('s_po_password_seq'),
  USER_ID    BIGINT,
  PASSWORD   VARCHAR(128) NOT NULL,
  SYS_STATUS NUMERIC(1)   NOT NULL DEFAULT 0,
  CREATE_DTM TIMESTAMP    NOT NULL DEFAULT NOW(),
  MODIFY_DTM TIMESTAMP    NULL,
  CONSTRAINT PK_PASSWORD PRIMARY KEY (ID)
);

COMMENT ON COLUMN PASSWORD.PASSWORD IS
'Password could be local password, Google token or other OAUTH system token.';

/*==============================================================*/
/* Table: Permission                                            */
/*==============================================================*/
CREATE TABLE Permission (
  ID          BIGINT      NOT NULL DEFAULT NEXTVAL('s_permission_seq'),
  ROLE_ID     BIGINT,
  TYPE        NUMERIC(2)  NOT NULL DEFAULT 0,
  NAME        VARCHAR(64) NOT NULL,
  ACCESS_FLAG VARCHAR(1)  NOT NULL,
  OBJECT      VARCHAR(64) NOT NULL
    CONSTRAINT CKC_BOBJECT_DEF_PATH_S_PERMIS CHECK (OBJECT IN
                                                    ('Profile', 'Lesson', 'Challenge', 'Test', 'User', 'Group', 'Event', 'DayBook', 'Department',
                                                                'Schedule', 'Calendar', 'Feed', 'Accept', 'Denied', 'Location', 'Password', 'Permission',
                                                     'Material', 'Relationship', 'Role')),
  SYS_STATUS  NUMERIC(1)  NOT NULL DEFAULT 0,
  CREATE_DTM  TIMESTAMP   NOT NULL DEFAULT NOW(),
  MODIFY_DTM  TIMESTAMP   NULL,
  CONSTRAINT PK_PERMISSION PRIMARY KEY (ID)
);

COMMENT ON COLUMN Permission.TYPE IS
'static(0)/dynamic(1)';

COMMENT ON COLUMN Permission.ACCESS_FLAG IS
'C/R/W/D (Create, Read, Write, Delete)';

/*==============================================================*/
/* Table: po_group                                              */
/*==============================================================*/
CREATE TABLE po_group (
  ID                 BIGINT      NOT NULL DEFAULT NEXTVAL('s_group_seq'),
  LEADER_ID          BIGINT      NOT NULL,
  NAME               VARCHAR(64) NOT NULL,
  LEVEL              VARCHAR(64),
  SYS_STATUS         NUMERIC(1)  NOT NULL DEFAULT 0,
  CREATE_DTM         TIMESTAMP   NOT NULL DEFAULT NOW(),
  PRIMARY_GROUP_FLAG BOOLEAN     NOT NULL DEFAULT FALSE,
  MODIFY_DTM         TIMESTAMP   NULL,
  CONSTRAINT PK_GROUPS PRIMARY KEY (ID)
);

COMMENT ON COLUMN po_group.PRIMARY_GROUP_FLAG IS
't/f, y/n, ...';
COMMENT ON TABLE po_group IS
'Departments of Client hierachical organization structure - linearizied list';

/*==============================================================*/
/* Table: po_user                                               */
/*==============================================================*/
CREATE TABLE po_user (
  ID                       BIGINT      NOT NULL DEFAULT NEXTVAL('s_po_contact_seq'),
  DEPARTMENT_ID            BIGINT,
  LOGIN_NAME               VARCHAR(64) NOT NULL,
  USER_TYPE                VARCHAR(1)  NOT NULL DEFAULT 'U',
  FIRSTNAME                VARCHAR(32) NOT NULL,
  LASTNAME                 VARCHAR(64) NOT NULL,
  EMAIL                    VARCHAR(64) NOT NULL,
  PHONE                    VARCHAR(64),
  SKYPE                    VARCHAR(64),
  LEVEL                    VARCHAR(64),
  URL                      VARCHAR(1000),
  INVITE_DATE              TIMESTAMP   NULL,
  PASSWORD_CAN_EXPIRE_FLAG BOOL        NOT NULL DEFAULT TRUE,
  SYS_STATUS               NUMERIC(1)  NOT NULL DEFAULT 0,
  CREATE_DTM               TIMESTAMP   NOT NULL DEFAULT NOW(),
  MODIFY_DTM               TIMESTAMP,
  CONSTRAINT PK_USER PRIMARY KEY (ID)
);

COMMENT ON COLUMN po_user.USER_TYPE IS
'Type of po_user: ''S'' - System po_user, ''U'' - po_user';

/*==============================================================*/
/* Table: Relationship                                          */
/*==============================================================*/
CREATE TABLE Relationship (
  ID                BIGINT      NOT NULL DEFAULT NEXTVAL('relationship_seq'),
  USER_ID           BIGINT,
  RELATED_PERSON_ID BIGINT      NOT NULL,
  TYPE              VARCHAR(64) NOT NULL DEFAULT 'Friend'
    CONSTRAINT CKC_PRIMARY_GROUP_FLA_S_USER_G CHECK (TYPE IN ('Friend', 'Comate', 'Test')),
  SYS_STATUS        NUMERIC(1)  NOT NULL DEFAULT 0,
  CREATE_DTM        TIMESTAMP   NOT NULL DEFAULT NOW(),
  MODIFY_DTM        TIMESTAMP   NULL,
  CONSTRAINT PK_RELATIONSHIP PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: Role                                                  */
/*==============================================================*/
CREATE TABLE Role (
  ID         BIGINT      NOT NULL DEFAULT NEXTVAL('s_roles_seq'),
  NAME       VARCHAR(64) NOT NULL,
  SYS_STATUS NUMERIC(1)  NOT NULL DEFAULT 0,
  CREATE_DTM TIMESTAMP   NOT NULL DEFAULT NOW(),
  MODIFY_DTM TIMESTAMP   NULL,
  CONSTRAINT PK_ROLE PRIMARY KEY (ID)
);


/*==============================================================*/
/* Table: UserGroup                                             */
/*==============================================================*/
CREATE TABLE UserGroup (
  ID         BIGINT     NOT NULL DEFAULT NEXTVAL('s_user_group_seq'),
  USER_ID    BIGINT     NOT NULL,
  GROUP_ID   BIGINT     NOT NULL,
  SYS_STATUS NUMERIC(1) NOT NULL DEFAULT 0,
  CREATE_DTM TIMESTAMP  NOT NULL DEFAULT NOW(),
  MODIFY_DTM TIMESTAMP  NULL,
  CONSTRAINT PK_USERGROUP PRIMARY KEY (ID)
);

/*==============================================================*/
/* Table: UserRole                                              */
/*==============================================================*/
CREATE TABLE UserRole (
  ID         BIGINT     NOT NULL DEFAULT NEXTVAL('s_user_role_seq'),
  USER_ID    BIGINT     NOT NULL,
  ROLE_ID    BIGINT     NOT NULL,
  SYS_STATUS NUMERIC(1) NOT NULL DEFAULT 0,
  CREATE_DTM TIMESTAMP  NOT NULL DEFAULT NOW(),
  MODIFY_DTM TIMESTAMP  NULL,
  CONSTRAINT PK_USERROLE PRIMARY KEY (ID)
);


/*==============================================================*/
/* Index: AK_ROLE                                               */
/*==============================================================*/
CREATE UNIQUE INDEX AK_ROLE ON Role (
  (CASE WHEN SYS_STATUS = 0
    THEN NAME END)
);

/*==============================================================*/
/* Index: AK_USER                                               */
/*==============================================================*/
CREATE UNIQUE INDEX AK_USER ON po_user (
  (CASE WHEN SYS_STATUS = 0
    THEN LOGIN_NAME END)
);

--ALTER TABLE DayBook
--ADD CONSTRAINT FK_DAYBOOK_EVENT FOREIGN KEY (EVENT_ID)
--REFERENCES event (ID)
-- ON DELETE RESTRICT ON UPDATE RESTRICT;
--ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE DayBook
ADD CONSTRAINT FK_DAYBOOK_USER FOREIGN KEY (USER_ID)
REFERENCES po_user (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

--ALTER TABLE DayBook
--ADD CONSTRAINT FK_DAYBOOK FOREIGN KEY (TEST_ATTEMPT_ID)
--REFERENCES TestAttempt(ID);

ALTER TABLE Event
ADD CONSTRAINT FK_GROUP_EVENT FOREIGN KEY (GROUP_ID)
REFERENCES po_group (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Event
ADD CONSTRAINT FK_USEREVENT FOREIGN KEY (LEADER_ID)
REFERENCES po_user (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Event
ADD CONSTRAINT FK_CREATEREVENT FOREIGN KEY (CREATER_ID)
REFERENCES po_user (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Event
ADD CONSTRAINT FK_LOCATION_EVENT FOREIGN KEY (LOCATION_ID)
REFERENCES Location (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Event
ADD CONSTRAINT FK_SUPEREVENT FOREIGN KEY (SUPEREVENT_ID)
REFERENCES Event (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE po_group
ADD CONSTRAINT FK_LEADER FOREIGN KEY (LEADER_ID)
REFERENCES po_user (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE LoginHistory
ADD CONSTRAINT FK_LOGINHISTORY FOREIGN KEY (USER_ID)
REFERENCES po_user (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE PASSWORD
ADD CONSTRAINT FK_PASSWORD FOREIGN KEY (USER_ID)
REFERENCES po_user (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Relationship
ADD CONSTRAINT FK_RELATIONSHIP01 FOREIGN KEY (USER_ID)
REFERENCES po_user (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Relationship
ADD CONSTRAINT FK_RELATIONSHIP02 FOREIGN KEY (RELATED_PERSON_ID)
REFERENCES po_user (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE po_user
ADD CONSTRAINT FK_USER_DEPARTMENT FOREIGN KEY (DEPARTMENT_ID)
REFERENCES Department (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

-- ALTER TABLE Location
-- ADD CONSTRAINT FK_EVENT_LOCATION FOREIGN KEY (ID)
-- REFERENCES Event (ID)
--   -- ON DELETE RESTRICT ON UPDATE RESTRICT;
-- ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Permission
ADD CONSTRAINT FK_PERMISSION_ROLE FOREIGN KEY (ROLE_ID)
REFERENCES Role (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE UserGroup
ADD CONSTRAINT FK_USERGROUP01 FOREIGN KEY (USER_ID)
REFERENCES po_user (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE UserGroup
ADD CONSTRAINT FK_USERGROUP02 FOREIGN KEY (GROUP_ID)
REFERENCES po_group (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE UserRole
ADD CONSTRAINT FK_USERROLE01 FOREIGN KEY (USER_ID)
REFERENCES po_user (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE UserRole
ADD CONSTRAINT FK_USERROLE02 FOREIGN KEY (ROLE_ID)
REFERENCES Role (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Test
ADD CONSTRAINT FK_PARENT_TEST FOREIGN KEY (PARENT_ID)
REFERENCES Test (ID);
-- ON DELETE RESTRICT ON UPDATE RESTRICT;
--ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE TestCard
ADD CONSTRAINT FK_TEST_CARD1 FOREIGN KEY (TEST_ID)
REFERENCES Test (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE TestAnswer
ADD CONSTRAINT FK_TEST_ANSWER1 FOREIGN KEY (TEST_CARD_ID)
REFERENCES TestCard (ID)
  -- ON DELETE RESTRICT ON UPDATE RESTRICT;
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE TestCardChoice
ADD CONSTRAINT FK_TEST_CARD_CHOICE1 FOREIGN KEY (TEST_ANSWER_ID)
REFERENCES TestAnswer (ID);

ALTER TABLE TestCardChoice
ADD CONSTRAINT FK_TEST_CARD_CHOICE2 FOREIGN KEY (TEST_CARD_ID)
REFERENCES TestCard (ID);

ALTER TABLE TestCardChoice
ADD CONSTRAINT FK_TEST_CARD_CHOICE3 FOREIGN KEY (TEST_ATTEMPT_ID)
REFERENCES TestAttempt (ID);

ALTER TABLE TestAttempt
ADD CONSTRAINT FK_TEST_ATTEMPT1 FOREIGN KEY (TEST_ID)
REFERENCES Test (ID);
