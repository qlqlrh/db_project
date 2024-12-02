-- DROP TABLES IF EXISTS
DROP TABLE IF EXISTS "users" CASCADE;
DROP TABLE IF EXISTS "meeting" CASCADE;
DROP TABLE IF EXISTS "enrollment" CASCADE;
DROP TABLE IF EXISTS "review" CASCADE;
DROP TABLE IF EXISTS "timetable" CASCADE;

-- CREATE TABLE Users
CREATE TABLE "users" (
                         "user_id" SERIAL PRIMARY KEY,
                         "name" VARCHAR(50) NOT NULL,
                         "role" VARCHAR(50) CHECK ("role" IN ('Freshman', 'Senior_Student', 'Professor', 'Student_Council')) NOT NULL,
                         "email" VARCHAR(100) UNIQUE NOT NULL,
                         "password" VARCHAR(30) NOT NULL
);

-- CREATE TABLE meeting
CREATE TABLE "meeting" (
                           "meeting_id" SERIAL PRIMARY KEY,
                           "user_id" INT NOT NULL,
                           "title" VARCHAR(100) NOT NULL,
                           "space" VARCHAR(100) NOT NULL,
                           "date" DATE NOT NULL,
                           "time" TIME NOT NULL,
                           "max_participants" INT NOT NULL,
                           "participant_count" INT DEFAULT 0 NOT NULL,
                           "created_for" VARCHAR(50) CHECK ("created_for" IN ('Freshman', 'Senior_Student')) NOT NULL,
                           "created_by_role" VARCHAR(50) CHECK ("created_by_role" IN ('Senior_Student', 'Professor')) NOT NULL,
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
CREATE TABLE "review" (
                          "review_id" SERIAL PRIMARY KEY,
                          "meeting_id" INT NOT NULL,
                          "user_id" INT NOT NULL,
                          "rating" INT CHECK ("rating" BETWEEN 1 AND 5) NOT NULL,
                          "comment" TEXT NOT NULL,
                          "created_at" TIMESTAMP NOT NULL,
                          CONSTRAINT "FK_REVIEW_MEETING" FOREIGN KEY ("meeting_id") REFERENCES "meeting" ("meeting_id") ON DELETE CASCADE,
                          CONSTRAINT "FK_REVIEW_USER" FOREIGN KEY ("user_id") REFERENCES "users" ("user_id") ON DELETE CASCADE
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
