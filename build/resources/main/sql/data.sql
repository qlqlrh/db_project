-- INSERT INTO User
INSERT INTO "users" ("name", "role", "email", "password")
VALUES
    ('Alice', 'Freshman', 'alice@example.com', 'password123'),
    ('Bob', 'Senior Student', 'bob@example.com', 'securepass'),
    ('Charlie', 'Professor', 'charlie@example.com', 'charliepass'),
    ('Dave', 'Student Council', 'dave@example.com', 'adminpass'),
    ('Eve', 'Freshman', 'eve@example.com', 'password456');

-- INSERT INTO Meeting
INSERT INTO "meeting" ("user_id", "title", "space", "date", "time", "max_participants", "participant_count", "created_for", "created_by_role")
VALUES
    (2, 'Freshman Orientation', '6203 강의실', '2024-12-01', '10:00', 4, 2, 'Freshman', 'Senior Student'),
    (3, 'Advanced Research Workshop', '대학본부', '2024-12-05', '15:00', 10, 0, 'Senior Student', 'Professor'),
    (2, 'Team Project Meeting', '정문', '2024-12-03', '14:00', 4, 1, 'Freshman', 'Senior Student');

-- INSERT INTO Enrollment
VALUES
    (1, 1, '2024-11-20'), -- Alice (Freshman) 참여
    (5, 1, '2024-11-21'), -- Eve (Freshman) 참여
    (1, 3, '2024-11-22'); -- Alice (Freshman) 다른 모임 참여

-- INSERT INTO Review
INSERT INTO "review" ("meeting_id", "user_id", "rating", "comment", "created_at")
VALUES
    (1, 1, 5, 'Great orientation program!', '2024-12-02 12:00:00'),
    (1, 5, 4, 'Very informative and helpful.', '2024-12-02 14:00:00'),
    (3, 1, 3, 'The meeting was okay but could be better.', '2024-12-04 16:00:00');

-- INSERT INTO Timetable
INSERT INTO "timetable" ("user_id", "day_of_week", "start_time", "end_time")
VALUES
    (2, 'Monday', '09:00', '12:00'), -- Bob (Senior Student) 수업 시간
    (2, 'Wednesday', '13:00', '15:00'), -- Bob (Senior Student)
    (1, 'Tuesday', '10:00', '12:00'), -- Alice (Freshman) 수업 시간
    (5, 'Friday', '14:00', '16:00'); -- Eve (Freshman)
