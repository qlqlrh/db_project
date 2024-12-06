INSERT INTO student_role (student_id, role) VALUES
                                                ('202255514', 'Freshman'),
                                                ('202255515', 'Senior_Student'),
                                                ('202255516', 'Professor'),
                                                ('202255517', 'Student_Council'),
                                                ('202255518', 'Freshman');


INSERT INTO "users" (student_id, name, email, password) VALUES
                                                            ('202255514', '홍길동', 'hong@example.com', 'password123'),
                                                            ('202255515', '김철수', 'kim@example.com', 'password456'),
                                                            ('202255516', '이영희', 'lee@example.com', 'password789'),
                                                            ('202255517', '박민수', 'park@example.com', 'password234'),
                                                            ('202255518', '최수영', 'choi@example.com', 'password567');


INSERT INTO "meeting" (user_id, title, space, date, time, max_participants, participant_count, is_completed, created_for, created_by_role) VALUES
                                                                                                                                               (2, 'Senior Study Group', 'Room 101', '2024-12-10', '14:00', 10, 5, FALSE, 'Freshman', 'Senior_Student'),
                                                                                                                                               (3, 'Professor Meeting', 'Conference Hall', '2024-12-12', '10:00', 20, 10, FALSE, 'Senior_Student', 'Professor'),
                                                                                                                                               (4, 'Council Planning', 'Meeting Room 1', '2024-12-15', '16:00', 8, 3, FALSE, 'Senior_Student', 'Student_Council');


INSERT INTO "enrollment" (user_id, meeting_id, enrollment_date) VALUES
                                                                    (1, 1, '2024-12-08'),
                                                                    (2, 2, '2024-12-09'),
                                                                    (3, 3, '2024-12-10');


INSERT INTO review (meeting_id, user_id, rating, comment) VALUES
                                                              (1, 1, 5, 'Great meeting!'),
                                                              (2, 2, 4, 'Very informative session.'),
                                                              (3, 3, 3, 'Could be better.');


INSERT INTO "timetable" (user_id, day_of_week, start_time, end_time) VALUES
                                                                         (1, 'MONDAY', '09:00', '11:00'),
                                                                         (2, 'WEDNESDAY', '13:00', '15:00'),
                                                                         (3, 'FRIDAY', '10:00', '12:00');
