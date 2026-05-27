INSERT INTO authority (name) VALUES ('ROLE_ADMIN');
INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_GUEST');

-- Admin user: username=admin, password=admin123 (hashed)
-- User: username=user, password=user123 (hashed)
INSERT INTO "user" (username, password, first_name, last_name, registration_date, email, phone)
VALUES
    ('admin', '$2a$12$nnavZuxs7zMd4CRvLXT6Ze/eyedcpFaxQDKMevtHyuqIcgXKiQtfW',
     'Admin', 'User', CURRENT_DATE, 'admin@mindcheck.com',
     '+385911111111'),

    ('user', '$2a$12$Z4a5pFqhv.h6nR94jeppPO04.yAvYh.CBjMQchlndLhYRdQfMRI9e',
     'Regular', 'User', CURRENT_DATE, 'user@mindcheck.com',
     '+385922222222'),

    ('guest', '$2a$12$GKMveIp8I/cD6nHf1I8ZxOqYasUSchet3hDTekmINJhO7JoJOy1I2',
        'Guest', 'User', CURRENT_DATE, 'guest@mindcheck.com',
        '+3859333333333');


INSERT INTO user_authority (user_id, authority_id)
VALUES
    (1, 1),
    (2, 2),
    (3,3);

INSERT INTO mood_entry (mood, energy_level, stress_level, notes, logged_at, user_id)
VALUES
    ('GOOD', 7, 3, 'Felt productive and focused', CURRENT_TIMESTAMP, 1),

    ('BAD', 3, 8, 'Stressful day at work', CURRENT_TIMESTAMP, 2),

    ('GREAT', 9, 2, 'Had a great workout and relaxed evening', CURRENT_TIMESTAMP, 1),

    ('OKAY', 5, 5, 'Average day, nothing special', CURRENT_TIMESTAMP, 3),

    ('TERRIBLE', 2, 9, 'Very anxious and overwhelmed', CURRENT_TIMESTAMP, 1),

    ('GOOD', 8, 4, 'Good mood after meeting friends', CURRENT_TIMESTAMP, 2),

    ('BAD', 4, 7, 'Low energy and poor sleep', CURRENT_TIMESTAMP, 2),

    ('GREAT', 10, 1, 'Best day this week, everything went well', CURRENT_TIMESTAMP, 3),

    ('OKAY', 6, 5, 'Managed tasks but felt a bit tired', CURRENT_TIMESTAMP, 2),

    ('GOOD', 7, 3, 'Calm and steady day', CURRENT_TIMESTAMP, 3);

INSERT INTO reminder (title, message, cron_expression, is_active, created_at, mood_entry_id)
VALUES
    ('Morning Check-in', 'Take 2 minutes to log your morning mood.', '0 0 8 * * *', TRUE, NOW(), 1),
    ('Hydration Reminder', 'Drink a glass of water and stretch briefly.', '0 30 10 * * *', TRUE, NOW(), 3),
    ('Midday Breathing', 'Pause for a 3-minute breathing exercise.', '0 0 12 * * *', TRUE, NOW(), 2),
    ('Lunch Mood Note', 'Write one sentence about how lunch affected your mood.', '0 15 13 * * *', TRUE, NOW(), 5),
    ('Afternoon Reset', 'Take a short walk to reset your energy.', '0 0 15 * * *', TRUE, NOW(), 4),
    ('Focus Break', 'Step away from screen for 5 minutes.', '0 45 16 * * *', TRUE, NOW(), 7),
    ('Evening Reflection', 'Log your mood and one positive event from today.', '0 0 20 * * *', TRUE, NOW(), 6),
    ('Sleep Prep', 'Start wind-down routine: no screens, low lights.', '0 30 22 * * *', TRUE, NOW(), 8),
    ('Weekly Review', 'Review mood trends for the week.', '0 0 9 * * MON', TRUE, NOW(), 9),
    ('Weekend Planning', 'Plan one relaxing weekend activity.', '0 0 11 * * SAT', TRUE, NOW(), 10),
    ('Stress Scan', 'Rate current stress and note one trigger.', '0 */20 9-18 * * *', FALSE, NOW(), 2),
    ('Gratitude Prompt', 'Write down one thing you are grateful for.', '0 30 21 * * *', TRUE, NOW(), 1),
    ('Movement Reminder', 'Do a quick mobility routine.', '0 0 17 * * *', TRUE, NOW(), 4),
    ('Social Check-in', 'Message a friend or family member.', '0 0 19 * * WED', FALSE, NOW(), 7),
    ('Night Mood Log', 'Final mood log before sleep.', '0 45 23 * * *', TRUE, NOW(), 5);