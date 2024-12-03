-- INSERT INTO User
INSERT INTO users (name, role, email, password)
VALUES
    ('Alice', 'Freshman', 'alice@university.edu', 'password1'),
    ('Bob', 'Senior_Student', 'bob@university.edu', 'password2'),
    ('Charlie', 'Professor', 'charlie@university.edu', 'password3'),
    ('David', 'Student_Council', 'david@university.edu', 'password4'),
    ('asdf', 'Freshman', 'asdf@pusan.ac.kr', 'asdf');


-- INSERT INTO Meeting
INSERT INTO "meeting" ("user_id", "title", "space", "date", "time", "max_participants", "participant_count", "is_completed", "created_for", "created_by_role")
VALUES
    (2, 'Freshman Orientation', '6203 강의실', '2024-12-01', '10:00', 4, 2, true,'Freshman', 'Senior_Student'),
    (3, 'Advanced Research Workshop', '대학본부', '2024-12-05', '15:00', 10, 0, false, 'Senior_Student', 'Professor'),
    (2, 'Team Project Meeting', '정문', '2024-12-03', '14:00', 4, 1, false, 'Freshman', 'Senior_Student');

-- INSERT INTO Enrollment
INSERT INTO "enrollment" ("user_id", "meeting_id", "enrollment_date")
VALUES
    (1, 1, '2024-11-20'), -- Alice (Freshman) 참여
    (3, 1, '2024-11-21'), -- Eve (Freshman) 참여
    (1, 3, '2024-11-22'); -- Alice (Freshman) 다른 모임 참여

-- INSERT INTO Review
INSERT INTO "review" ("meeting_id", "user_id", "rating", "comment", "created_at")
VALUES
    (1, 1, 5, 'Great orientation program!', '2024-12-02 12:00:00'),
    (1, 3, 4, 'Very informative and helpful.', '2024-12-02 14:00:00'),
    (3, 1, 3, 'The meeting was okay but could be better.', '2024-12-04 16:00:00');

-- INSERT INTO Timetable
INSERT INTO "timetable" ("user_id", "day_of_week", "start_time", "end_time")
VALUES
    (2, 'MONDAY', '09:00', '12:00'), -- Bob (Senior Student) 수업 시간
    (2, 'WEDNESDAY', '13:00', '15:00'), -- Bob (Senior Student)
    (1, 'THURSDAY', '10:00', '12:00'), -- Alice (Freshman) 수업 시간
    (3, 'FRIDAY', '14:00', '16:00'); -- Eve (Freshman)
