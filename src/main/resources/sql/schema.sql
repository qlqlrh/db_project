-- DROP TABLES IF EXISTS
DROP TABLE IF EXISTS "users" CASCADE;
DROP TABLE IF EXISTS "meeting" CASCADE;
DROP TABLE IF EXISTS "enrollment" CASCADE;
DROP TABLE IF EXISTS "review" CASCADE;
DROP TABLE IF EXISTS "timetable" CASCADE;
DROP TABLE IF EXISTS "student_role" CASCADE;

-- 학번과 역할을 매칭하는 테이블
CREATE TABLE student_role (
                              role_id SERIAL PRIMARY KEY,
                              student_id VARCHAR(20) UNIQUE NOT NULL,
                              role VARCHAR(50) CHECK (role IN ('Freshman', 'Senior_Student', 'Professor', 'Student_Council')) NOT NULL
);

-- CREATE TABLE Users
CREATE TABLE "users" (
                         "user_id" SERIAL PRIMARY KEY,                                          -- 사용자 ID (고유, 자동 증가)
                         "student_id" VARCHAR(15) UNIQUE NOT NULL,                              -- 학번 (FK로 연결)
                         "name" VARCHAR(50) NOT NULL,                                           -- 사용자 이름
                         "email" VARCHAR(100) UNIQUE NOT NULL,                                  -- 이메일 (고유, 필수)
                         "password" VARCHAR(30) NOT NULL,                                       -- 비밀번호
                         CONSTRAINT "FK_USERS_STUDENT_ROLE" FOREIGN KEY ("student_id") REFERENCES "student_role" ("student_id") ON DELETE CASCADE
);



-- 변경된 meeting 테이블 생성
CREATE TABLE "meeting" (
                           "meeting_id" SERIAL PRIMARY KEY,
                           "user_id" INT NOT NULL,
                           "title" VARCHAR(100) NOT NULL,
                           "space" VARCHAR(100) NOT NULL,
                           "date" DATE NOT NULL,
                           "time" TIME NOT NULL,
                           "max_participants" INT NOT NULL,
                           "participant_count" INT DEFAULT 0 NOT NULL,
                           "is_completed" BOOLEAN DEFAULT FALSE,         -- 모임 완료 여부
                           "created_for" VARCHAR(50) CHECK ("created_for" IN ('Freshman', 'Senior_Student', 'Student_Council', 'Professor')) NOT NULL,
                           "created_by_role" VARCHAR(50) CHECK ("created_by_role" IN ('Senior_Student', 'Student_Council', 'Professor')) NOT NULL,
                           CONSTRAINT "FK_MEETING_USER" FOREIGN KEY ("user_id") REFERENCES "users" ("user_id") ON DELETE CASCADE
);

-- CREATE TABLE enrollment
CREATE TABLE "enrollment" (
                              "enrollment_id" SERIAL PRIMARY KEY,
                              "user_id" INT NOT NULL,
                              "meeting_id" INT NOT NULL,
                              "enrollment_date" DATE NOT NULL,
                              CONSTRAINT "FK_ENROLLMENT_USER" FOREIGN KEY ("user_id") REFERENCES "users" ("user_id") ON DELETE CASCADE,
                              CONSTRAINT "FK_ENROLLMENT_MEETING" FOREIGN KEY ("meeting_id") REFERENCES "meeting" ("meeting_id") ON DELETE CASCADE
);

-- CREATE TABLE review
CREATE TABLE review (
                        review_id SERIAL PRIMARY KEY,                -- 후기 ID
                        meeting_id INT NOT NULL,                    -- 모임 ID (FK)
                        user_id INT NOT NULL,                       -- 후기 작성자 ID (FK)
                        rating INT CHECK (rating BETWEEN 1 AND 5),  -- 별점 (1~5)
                        comment TEXT NOT NULL,                      -- 한줄 후기
                        created_at TIMESTAMP DEFAULT NOW(),         -- 후기 작성 시간
                        CONSTRAINT fk_review_meeting FOREIGN KEY (meeting_id) REFERENCES meeting (meeting_id) ON DELETE CASCADE,
                        CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

-- CREATE TABLE timetable
CREATE TABLE "timetable" (
                             "timetable_id" SERIAL PRIMARY KEY,
                             "user_id" INT NOT NULL,
                             "day_of_week" VARCHAR(50) CHECK ("day_of_week" IN ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')) NULL,
                             "start_time" TIME NULL,
                             "end_time" TIME NULL,
                             CONSTRAINT "FK_TIMETABLE_USER" FOREIGN KEY ("user_id") REFERENCES "users" ("user_id") ON DELETE CASCADE
);


