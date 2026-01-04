INSERT INTO app_users (username, password, email, firstname, lastname)
VALUES ('username', '{noop}password', 'username@gmail.com', 'admin', 'admin'),
       ('not_username', '{noop}password', 'username@gmail.com', 'admin', 'admin');

INSERT INTO todos (description,
                   is_completed,
                   deadline_date,
                   todo_uuid,
                   estimated_completion_time_minutes,
                   user_id,
                   task_rating)
VALUES ('Learn Spring Boot basics', FALSE, NULL, UUID(), 45, 1, NULL),
       ('Understand JPQL joins', FALSE, NULL, UUID(), 30, 1, NULL),
       ('Implement JWT authentication', FALSE, NULL, UUID(), 60, 1, NULL),
       ('Revise Data Communication notes', FALSE, NULL, UUID(), 90, 1, NULL),
       ('Practice low-level C programming', FALSE, NULL, UUID(), 120, 1, NULL),
       ('Explore Linux networking basics', FALSE, NULL, UUID(), 75, 1, NULL),
       ('Prepare internship presentation', FALSE, NULL, UUID(), 50, 1, NULL);
