CREATE TABLE IF NOT EXISTS "user" (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    registration_date DATE NOT NULL DEFAULT CURRENT_DATE,
    email VARCHAR(30) NOT NULL,
    phone VARCHAR(30) NOT NULL
    );

CREATE TABLE IF NOT EXISTS authority (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS user_authority (
    user_id BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, authority_id),
    CONSTRAINT fk_user_authority_user
        FOREIGN KEY (user_id)
        REFERENCES "user"(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_authority_authority
        FOREIGN KEY (authority_id)
        REFERENCES authority(id)
        ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS mood_entry (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    mood VARCHAR(20) NOT NULL,
    energy_level INT,
    stress_level INT,
    notes VARCHAR(1000) NOT NULL,
    logged_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
        CONSTRAINT fk_mood_entry_user
        FOREIGN KEY (user_id)
        REFERENCES "user"(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reminder (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    message VARCHAR(2000) NOT NULL,
    cron_expression VARCHAR(100) NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at TIMESTAMP,
    mood_entry_id BIGINT NOT NULL,
    CONSTRAINT fk_reminder_mood_entry
        FOREIGN KEY (mood_entry_id)
        REFERENCES mood_entry(id)
        ON DELETE CASCADE
);


