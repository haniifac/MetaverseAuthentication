insert into groups (ID, groupname) values (1,'STUDENT');
insert into groups (ID, groupname) values (2,'TEACHER');
insert into groups (ID, groupname) values (3,'ADMIN');

insert into user_account (ID, username, password, ACCESS_TOKEN, ID_TOKEN, REFRESH_TOKEN, FCM_TOKEN, REG_NUMBER, EMAIL, IMAGE_URL)
values (1, 'dendy', 'qwe123', '', '' , '', '', 'TCR001', 'dendy.prtha@staff.ukdw.ac.id', '');

insert into teacher (ID, TEACHER_ID, EMPLOYMENT_NUMBER, NAME, GENDER, DAY_OF_BIRTH, BIRTH_PLACE, ADDRESS, INPUT_DATE, URL_GOOGLE_SCHOLAR)
values (1, 'TCR001', 'EN001', 'Dendy' , 'Male', '40-12-1091','Earth','Indonesia',CURRENT_TIMESTAMP ,'https://google.scholar.com');

insert into user_account (ID,username, password, ACCESS_TOKEN, ID_TOKEN, REFRESH_TOKEN, FCM_TOKEN, REG_NUMBER, EMAIL, IMAGE_URL)
values (2,'prtha', 'qwe123', '', '' , '', '', 'STD001', 'prtha@student.ukdw.ac.id', '');

insert into student (ID, STUDENT_ID, REGISTER_YEAR, NAME, GENDER, DAY_OF_BIRTH, BIRTH_PLACE, ADDRESS, INPUT_DATE)
values (2, 'STD001', '2024', 'Prtha' , 'Male', '30-12-2887','Indonesia','Earth', CURRENT_TIMESTAMP);

INSERT INTO user_group (user_id, group_id) VALUES (1, 2);
INSERT INTO user_group (user_id, group_id) VALUES (1, 3);

INSERT INTO user_group (user_id, group_id) VALUES (2, 1);