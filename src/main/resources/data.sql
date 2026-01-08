INSERT INTO app_users (username, password, email, firstname, lastname)
VALUES ('username', '{noop}password', 'username@gmail.com', 'admin', 'admin'),
       ('not_username', '{noop}password', 'username@gmail.com', 'admin', 'admin');

INSERT INTO todos (description,
                   status,
                   deadline_date,
                   uuid,
                   estimated_completion_time_minutes,
                   user_id,
                   task_rating,
                   type)
VALUES ('Learn Spring Boot basics', 'NOT_STARTED', CURDATE(), UUID(), 45, 1, NULL,'PURE'),
       ('Understand JPQL joins', 'NOT_STARTED', CURDATE(), UUID(), 30, 1, NULL, 'PURE'),
       ('Implement JWT authentication', 'NOT_STARTED', CURDATE(), UUID(), 60, 1, NULL, 'PURE'),
       ('Revise Data Communication notes', 'NOT_STARTED', CURDATE(), UUID(), 90, 1, NULL, 'PURE'),
       ('Practice low-level C programming', 'NOT_STARTED', CURDATE(), UUID(), 120, 1, NULL, 'PURE'),
       ('Explore Linux networking basics', 'NOT_STARTED', CURDATE(), UUID(), 75, 1, NULL, 'PURE'),
       ('Prepare internship presentation', 'NOT_STARTED', CURDATE(), UUID(), 50, 1, NULL, 'PURE');
