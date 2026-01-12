CREATE TABLE app_users
(
    uid       INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(50),
    lastname  VARCHAR(50),
    username  VARCHAR(50) UNIQUE,
    email     VARCHAR(100),
    password  VARCHAR(100) NOT NULL,
    timezone  VARCHAR(64)  NOT NULL,
    level     INT          NOT NULL,
    xp        INT          NOT NULL
);

CREATE TABLE user_weekly_xp
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    uid        INT  NOT NULL,
    week_start DATE NOT NULL,
    xp         INT  NOT NULL,
    UNIQUE KEY uq_user_week_start (uid, week_start),
    CONSTRAINT fk_user_weekly_xp_user FOREIGN KEY (uid)
        REFERENCES app_users (uid)
        ON DELETE CASCADE
);

CREATE TABLE habits
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255),
    status        VARCHAR(50),
    start_date    DATE,
    end_date      DATE,
    cheat_days    INT,
    uuid          VARCHAR(36)  NOT NULL UNIQUE,
    streak        INT,
    created_by    VARCHAR(255) NOT NULL,
    created_date  DATE         NOT NULL,
    modified_by   VARCHAR(255) NULL,
    modified_date DATE NULL,
    CONSTRAINT uq_habits_uuid UNIQUE (uuid)
);

CREATE TABLE habit_frequency
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    habit_id         INT         NOT NULL,
    day_of_week      VARCHAR(20) NOT NULL,
    duration_minutes INT,
    CONSTRAINT uq_habit_day UNIQUE (habit_id, day_of_week),
    CONSTRAINT fk_habit_frequency_habit
        FOREIGN KEY (habit_id)
            REFERENCES habits (id)
            ON DELETE CASCADE
);

CREATE TABLE todos
(
    id                                INT AUTO_INCREMENT PRIMARY KEY,
    uuid                              VARCHAR(36)  NOT NULL UNIQUE,
    description                       TEXT         NOT NULL,
    status                            VARCHAR(50)  NOT NULL,
    deadline_date                     DATE,
    estimated_completion_time_minutes INT,
    user_id                           INT          NOT NULL,
    todo_rating                       INT NULL,
    total_elapsed_seconds             INT DEFAULT 0,
    last_resumed_at                   TIMESTAMP NULL,
    habit_id                          INT NULL,
    type                              VARCHAR(50)  NOT NULL,
    created_by                        VARCHAR(255) NOT NULL,
    created_date                      DATE         NOT NULL,
    modified_by                       VARCHAR(255) NULL,
    modified_date                     DATE NULL,

    CONSTRAINT fk_todos_user
        FOREIGN KEY (user_id)
            REFERENCES app_users (uid),

    CONSTRAINT fk_todos_habit
        FOREIGN KEY (habit_id)
            REFERENCES habits (id)
);
