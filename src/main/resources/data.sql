INSERT INTO app_users (username, password, email, firstname, lastname, timezone, level, xp)
VALUES ('username', '{noop}password', 'username@gmail.com', 'admin', 'admin', 'Asia/Kathmandu', 1, 0),
       ('nousername', '{noop}password', 'username@gmail.com', 'admin', 'admin', 'Asia/Kathmandu', 1, 0);

INSERT INTO user_weekly_xp (uid, week_start, xp)
VALUES (1, '2026-01-19', 1200),
       (2, '2026-01-19', 980);


INSERT INTO todos (description,
                   status,
                   deadline_date,
                   completed_at,
                   uuid,
                   estimated_completion_time_minutes,
                   rating,
                   type,
                   created_by,
                   created_date)
VALUES ('Learn Spring Boot basics', 'NOT_STARTED', '2026-01-05', null,  UUID(), 45 , NULL, 'PURE', '1', CURDATE()),
       ('Understand JPQL joins', 'COMPLETED', CURDATE(),NOW(),  UUID(), 1,  8, 'PURE', '1', CURDATE()),
       ('Implement JWT authentication', 'NOT_STARTED', CURDATE(), null,  UUID(), 1,  NULL, 'PURE', '1', CURDATE()),
       ('Revise Data Communication notes', 'COMPLETED', CURDATE(),NOW(),  UUID(), 60,  8, 'PURE', '1', CURDATE()),
       ('Practice low-level C programming', 'NOT_STARTED', CURDATE(), null,  UUID(), 120,  NULL, 'PURE', '1', CURDATE()),
       ('Explore Linux networking basics', 'NOT_STARTED', CURDATE(), null,  UUID(), 75,  NULL, 'PURE', '1', CURDATE()),
       ('Explore Fedora Linux basics', 'NOT_STARTED', CURDATE(), null,  UUID(), 75,  NULL, 'PURE', '2', CURDATE()),
       ('Prepare internship presentation', 'NOT_STARTED', CURDATE(), null,  UUID(), 50,  NULL, 'PURE', '1', CURDATE());
