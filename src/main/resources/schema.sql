CREATE TABLE app_users
(
    uid       INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(50),
    lastname  VARCHAR(50),
    username  VARCHAR(50) UNIQUE,
    email     VARCHAR(100),
    password  VARCHAR(100)
);

CREATE TABLE todos
(
    id                                INT AUTO_INCREMENT PRIMARY KEY,
    description                       TEXT        NOT NULL,
    status                            VARCHAR(50) NOT NULL,
    deadline_date                     DATE,
    todo_uuid                         VARCHAR(36) NOT NULL UNIQUE,
    estimated_completion_time_minutes INT,
    user_id                           INT         NOT NULL,
    task_rating                       INT NULL,
    total_elapsed_seconds             INT DEFAULT 0,
    last_resumed_at                   TIMESTAMP NULL,
    CONSTRAINT fk_todos_user
        FOREIGN KEY (user_id)
            REFERENCES app_users (uid)
);
