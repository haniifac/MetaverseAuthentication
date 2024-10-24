-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

insert into groups (ID, groupname, permission) values (NEXTVAL('GROUP_SEQ'),'STUDENT', 1);
insert into groups (ID, groupname, permission) values (NEXTVAL('GROUP_SEQ'),'TEACHER',3);
insert into groups (ID, groupname, permission) values (NEXTVAL('GROUP_SEQ'),'ADMIN',7);

insert into user_account (ID, username, password, ACCESS_TOKEN, ID_TOKEN, REFRESH_TOKEN, FCM_TOKEN, REG_NUMBER, EMAIL, IMAGE_URL)
values (NEXTVAL('USER_SEQ'), 'dendy', 'qwe123', '', '' , '', '', 'TCR001', 'dendy.prtha@staff.ukdw.ac.id', '');

insert into teacher (ID, TEACHER_ID, EMPLOYMENT_NUMBER, NAME, GENDER, DAY_OF_BIRTH, BIRTH_PLACE, ADDRESS, INPUT_DATE, URL_GOOGLE_SCHOLAR)
values (CURRVAL('USER_SEQ'), 'TCR001', 'EN001', 'Dendy' , 'Male', '40-12-1091','Earth','Indonesia',CURRENT_TIMESTAMP ,'https://google.scholar.com');

INSERT INTO user_group (user_id, group_id) VALUES (CURRVAL('USER_SEQ'), 2);
INSERT INTO user_group (user_id, group_id) VALUES (CURRVAL('USER_SEQ'), 3);

insert into user_account (ID, username, password, ACCESS_TOKEN, ID_TOKEN, REFRESH_TOKEN, FCM_TOKEN, REG_NUMBER, EMAIL, IMAGE_URL)
values (NEXTVAL('USER_SEQ'),'prtha', 'qwe123', '', '' , '', '', 'STD001', 'prtha@student.ukdw.ac.id', '');

insert into student (ID, STUDENT_ID, REGISTER_YEAR, NAME, GENDER, DAY_OF_BIRTH, BIRTH_PLACE, ADDRESS, INPUT_DATE)
values (CURRVAL('USER_SEQ'), 'STD001', '2024', 'Prtha' , 'Male', '30-12-2887','Indonesia','Earth', CURRENT_TIMESTAMP);

INSERT INTO user_group (user_id, group_id) VALUES (CURRVAL('USER_SEQ'), 1);