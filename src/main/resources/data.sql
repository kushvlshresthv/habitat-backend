INSERT INTO app_users (username, password, email, firstname, lastname, timezone)
VALUES ('username', '{noop}password', 'username@gmail.com', 'admin', 'admin', 'Asia/Kathmandu'),
       ('nousername', '{noop}password', 'username@gmail.com', 'admin', 'admin', 'Asia/Kathmandu');


INSERT INTO todos (description,
                   status,
                   deadline_date,
                   uuid,
                   estimated_completion_time_minutes,
                   user_id,
                   todo_rating,
                   type,
                   created_by,
                   created_date)
VALUES ('Learn Spring Boot basics', 'NOT_STARTED','2026-01-10', UUID(), 45, 1, NULL, 'PURE', '1', CURDATE()),
       ('Understand JPQL joins', 'NOT_STARTED', CURDATE(), UUID(), 30, 1, NULL, 'PURE', '1', CURDATE()),
       ('Implement JWT authentication', 'NOT_STARTED', CURDATE(), UUID(), 60, 1, NULL, 'PURE', '1', CURDATE()),
       ('Revise Data Communication notes', 'NOT_STARTED', CURDATE(), UUID(), 90, 1, NULL, 'PURE', '1', CURDATE()),
       ('Practice low-level C programming', 'NOT_STARTED', CURDATE(), UUID(), 120, 1, NULL, 'PURE', '1', CURDATE()),
       ('Explore Linux networking basics', 'NOT_STARTED', CURDATE(), UUID(), 75, 1, NULL, 'PURE', '1', CURDATE()),
       ('Explore Fedora Linux basics', 'NOT_STARTED', CURDATE(), UUID(), 75, 2, NULL, 'PURE', '2', CURDATE()),
       ('Prepare internship presentation', 'NOT_STARTED', CURDATE(), UUID(), 50, 1, NULL, 'PURE', '1', CURDATE());
