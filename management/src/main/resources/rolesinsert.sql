INSERT INTO Roles(name, permission)
VALUES('ROLE_USER', 'READ:USER,READ:CUSTOMER'),
	('ROLE_MANAGER', 'READ:USER,READ:CUSTOMER,UPDATE:USER,UPDATE:CUSTOMER'),
	('ROLE_ADMIN', 'READ:USER,READ:CUSTOMER,CREATE:USER,CREATE:CUSTOMER,UPDATE:USER,UPDATE:CUSTOMER'),
	('ROLE_SYSADMIN', 'READ:USER,READ:CUSTOMER,CREATE:USER,CREATE:CUSTOMER,UPDATE:USER,UPDATE:CUSTOMER,DELETE:USER,DELETE:CUSTOMER');



INSERT INTO management_db.Users (first_name, last_name, email, password, address, phone, title, bio, enabled,
                                 non_locked, using_mfa, created_at, image_url)
VALUES ('João', 'Silva', 'js@gmail.com', '$2a$12$EmOAiUDkwg7ebolb5SXJz.q4faiTDhCXqMTmooHmAttYbHglNa/w6', 'Rua A',
        '99898989898', 'O cara', 'Eu sou o cara, ou não?', 1, 1, 0, DEFAULT, DEFAULT);

INSERT INTO management_db.UserRoles (user_id, role_id)
VALUES (1, 1);
