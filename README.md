# 모여봄🌸
<img width="400" alt="Image" src="https://github.com/user-attachments/assets/ff9f9d22-d6f6-4099-bbeb-284245c4737d" />
<img width="400" alt="Image" src="https://github.com/user-attachments/assets/8516b6a8-6754-4884-afde-a1ca9c3c5064" />
<img width="400" alt="Image" src="https://github.com/user-attachments/assets/447c9a86-5f13-45a5-840d-7c4a24cf2741" />
<img width="400" alt="Image" src="https://github.com/user-attachments/assets/d5d6185f-708d-464b-b7a3-8be793cdb975" />

# 1️⃣ 프로젝트 개요

**프로젝트 주제**

이 웹 애플리케이션은 **부산대학교 정보컴퓨터공학부 학부생들을 위한 번개 모임 시스템**으로, 학생들이 참여하고 관리할 수 있는 다양한 모임을 지원합니다. 학생들은 자신의 시간표와 일정을 고려하여 효율적으로 모임에 신청하고, 후기를 작성하거나 조회하는 등의 기능을 이용할 수 있습니다.

**프로젝트 목표**

이 웹 애플리케이션은 소극적인 성격의 학생들이 많은 **부산대학교 컴퓨터공학부 학생들의 교류 활성화**를 목표로 설계되었습니다. 학생들이 다양한 모임에 참여하고, 후기를 통해 그들의 경험을 공유함으로써 보다 신입생과 재학생, 재학생과 재학생, 교수님과 학부생 간의 활발한 소통과 교류를 촉진하고자 합니다.

**차별성 및 장점**

1. **시간표 기반 모임 신청 기능:** 기존의 모임 관리 시스템과 차별화되는 점은 **시간표와의 충돌 여부를 자동으로 확인**하여 사용자가 자신과 맞는 시간에만 모임에 신청할 수 있도록 돕는 기능입니다. 이로 인해 학생들이 시간 관리에 있어 더 효율적으로 활동할 수 있습니다.
2. **역할에 따른 기능 제한:** **학생회, 재학생, 새내기, 교수님** 등 사용자의 역할에 맞춰 기능을 제공하여, 각 역할에 적합한 서비스를 제공합니다. 예를 들어, 학생회는 다른 사용자의 정보를 관리할 수 있는 기능이 제공되고, 새내기는 모임에 신청만 할 수 있는 제한된 기능을 가집니다.
3. **후기 시스템:** 모임에 참여한 후 **후기를 작성하고 조회할 수 있는 시스템**을 통해, 다른 사용자가 모임의 유용성이나 참여 경험을 기반으로 선택할 수 있도록 돕습니다. 이 시스템은 **모임 선택을 보다 투명하게 만들고**, 사용자 간 정보 공유를 촉진합니다.
4. **사용자 친화적인 UI/UX:** 직관적인 인터페이스와 모임 신청 및 관리의 단순화를 통해 사용자는 복잡한 절차 없이 손쉽게 모임을 찾고 참여할 수 있습니다. 또한, **역할에 맞는 맞춤형 디자인**을 제공하여 각 역할에 맞는 기능을 보다 쉽게 이용할 수 있습니다.

**사용 언어 및 라이브러리** : Java, SpringBoot, JPA, Postgresql

---

# 2️⃣ 사용자(Role)

- **신입생 (Freshman)**:
  - 시스템에 등록된 모임에 신청하고 참여하는 사용자.
  - 새내기들은 재학생이 개설한 모임을 보고, 자신의 일정에 맞춰 신청할 수 있습니다.
  - 모임에 참여하거나, 본인이 참여한 모임에 대해 후기를 작성할 수 있습니다.
  - 후기를 조회할 수 있습니다.
- **재학생 (Senior Student):**
  - 날짜와 시간, 장소를 설정해서 시스템에 모임을 등록하는 사용자.
  - 재학생들은 다른 재학생이나 교수님이 개설한 모임을 보고, 자신의 일정에 맞춰 신청할 수 있습니다.
  - 신입생이 할 수 있는 모든 기능을 사용할 수 있습니다.
- **교수 (Professor)**:
  - 모임에 인원 제한 없이 참여할 수 있으며, 참가 대상이 아니어도 참여할 수 있는 권한을 가진 사용자.
  - 재학생이 할 수 있는 모든 기능을 사용할 수 있습니다.
- **학생회 (Student_Council)**:
  - 시스템의 관리자로서 전체 프로그램을 운영하고 관리하는 사용자.
  - 시스템에 가입된 모든 사용자의 정보를 열람 및 변경할 수 있습니다.
  - 학생회는 부적절한 후기를 삭제할 수 있습니다.

---

# 3️⃣ 기능

## 1. 회원가입

- **사용자**: 모든 사용자
- **기능**: 사용자는 `signup` 페이지에서 정보를 입력하여 회원가입을 할 수 있습니다. 회원가입 시, 학번과 비밀번호를 입력하고, 해당 학번에 맞는 역할을 자동으로 할당받습니다. 단, 부산대학교 정보컴퓨터공학부 소속인 학생과 교수님만 가입 가능합니다.
- **주요 SQL :** `INSERT` 문을 사용하여 새로운 사용자 정보를 `users` 테이블에 추가합니다.

    ```sql
    -- student_role에서 역할 조회
    SELECT role FROM student_role WHERE student_id = ?;
    
    -- users 테이블에 신규 사용자 추가
    INSERT INTO users (name, email, password, student_id)
    VALUES (?, ?, ?, ?);
    ```


## 2. 모임 등록

- **사용자**: 재학생, 교수, 학생회
- **기능**: 사용자는 모임을 등록할 수 있으며, 모임에 대한 제목, 시간, 장소 등의 정보를 기입합니다.
- **주요 SQL :** `INSERT` 문을 통해 모임 정보를 `meeting` 테이블에 삽입합니다.
- **Foreign Key Constraints**: `user_id`와 `created_by_role`을 `users` 테이블과 연결합니다.

    ```sql
    INSERT INTO meeting (user_id, title, space, date, time, max_participants, created_for, created_by_role)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?);
    ```


### **2-1. 모임 완료 처리**

- **사용자:** 모임을 생성한 사람
- **기능 설명:** 모임이 완료되었음을 표시하며, 모임의 상태를 변경합니다.
- **주요 SQL :** `UPDATE` 문을 통해 특정 모임의 완료 여부를 업데이트합니다.

    ```sql
    UPDATE meeting SET is_completed = TRUE
    WHERE meeting_id = ?;
    ```


### **2-2. 모임 개설 취소**

- **대상 사용자:** 모임을 생성한 사람
- **기능 설명:** 모임이 개설된 후, 취소할 수 있습니다. 취소 시 모든 신청자가 삭제됩니다.
- **주요 SQL :** `DELETE` 문을 통해 `meeting` 테이블에서 모임을 삭제합니다.
- **Constraints:** `ON DELETE CASCADE`를 통해 모임이 삭제되면 해당 모임에 대한 모든 신청자가 자동으로 삭제됩니다.

    ```sql
    DELETE FROM meeting
    WHERE meeting_id = ?;
    ```


---

## **3. 모임 신청**

- **사용자:** 모임 신청 대상자에 해당하는 역할을 가진 사용자
- **기능 설명:** 사용자는 모임의 신청이 가능한지 여부를 확인하고, 신청 조건을 만족하면 모임에 참여할 수 있습니다. 신청 대상, 최대 인원 초과, 모임의 날짜와 시간표 충돌 여부도 확인합니다.
- **주요 SQL :**
  - `INSERT` 문을 통해 `enrollment` 테이블에 모임 신청 데이터를 삽입하고, `UPDATE` 문을 통해 모임 신청 후 `meeting` 테이블의 참가자 수(`participant_count`)를 업데이트합니다.
  - `Group By` 문을 통해 모임의 참여 인원을 집계합니다.
- **Foreign Key Constraints:** `user_id`, `meeting_id`는 각각 `users`와 `meeting` 테이블과 연결되어 있습니다.

    ```sql
    -- 모임 신청 등록
    INSERT INTO enrollment (user_id, meeting_id, enrollment_date) VALUES (?, ?, ?);
    
    -- 모임 참여자 수 증가
    UPDATE meeting SET participant_count = participant_count + 1
    WHERE meeting_id = ?;
    
    -- 최대 참가 인원 초과 검증
    SELECT 
        m.meeting_id,
        m.max_participants,
        (SELECT COUNT(*) FROM enrollment e WHERE e.meeting_id = m.meeting_id) AS participant_count
    FROM meeting m
    WHERE m.meeting_id = ? 
      AND (SELECT COUNT(*) FROM enrollment e WHERE e.meeting_id = m.meeting_id) < m.max_participants;
    
    -- 모임의 참여 인원을 집계
    SELECT meeting_id, COUNT(*) AS participants
    FROM enrollment
    GROUP BY meeting_id;
    ```


### **3-1. 모임 참여 취소**

- **대상 사용자:** 모임에 신청한 사용자
- **기능 설명:** 사용자는 모임 신청을 취소할 수 있습니다.
- **주요 SQL**
  - `DELETE` 문을 통해 `enrollment` 테이블에서 신청한 모임을 삭제하고, `UPDATE` 문을 통해 모임의 참가자 수를 감소시킵니다.

      ```sql
      DELETE FROM enrollment WHERE user_id = ? AND meeting_id = ?;
      
      UPDATE meeting SET participant_count = participant_count - 1
      WHERE meeting_id = ?;
      ```


### **3-2. 모임 신청 시 시간표 충돌 체크**

- **대상 사용자:** 모임에 신청하려는 사용자
- **기능 설명:** 사용자가 모임에 신청할 때, 해당 모임의 시간과 시간표가 겹치는지 확인합니다. 시간표와 모임 시간이 겹친다면 신청이 불가능하도록 처리합니다.
- **주요 SQL**
  - `SELECT`를 사용하여 사용자의 시간표와 모임 시간이 충돌하는지 확인합니다.

      ```sql
      SELECT t.*
      FROM timetable t
      JOIN meeting m ON t.day_of_week = TO_CHAR(m.date, 'Day') -- 요일 비교
      WHERE 
          t.user_id = :userId -- 사용자 ID (변수 바인딩)
          AND (
              (m.start_time BETWEEN t.start_time AND t.end_time)  -- 모임의 시작 시간이 시간표 사이에 있는지
              OR
              (m.end_time BETWEEN t.start_time AND t.end_time)    -- 모임의 종료 시간이 시간표 사이에 있는지
              OR
              (m.start_time <= t.start_time AND m.end_time >= t.end_time) -- 모임이 시간표를 완전히 포함하는 경우
          );
      ```

  - 충돌이 없다면, `INSERT` 문을 통해 `enrollment` 테이블에 데이터를 삽입하여 모임 신청을 처리합니다.

      ```sql
      INSERT INTO enrollment (user_id, meeting_id, enrollment_date)
      VALUES (?, ?, ?);
      ```


---

## **4. 후기 게시판**

### **4-1. 후기 조회**

- **대상 사용자:** 모든 사용자
- **기능 설명:** 사용자는 후기 게시판에서 후기를 확인할 수 있습니다. 후기는 기본적으로 작성 순서대로 보여주며, 정렬 방식은 별점 순, 최신순으로 선택 가능합니다.
- **주요 SQL :**
  - 후기 데이터를 별점높은 순으로 조회하기 위해 `SELECT` 문과 `ORDER BY` 문을 사용합니다.
  - `JOIN` 을 사용해 `review` 테이블, `meeting` 테이블, `users` 테이블을 `JOIN`하여 후기에 대한 상세 정보를 가져옵니다.

      ```sql
      SELECT *
      FROM review
      JOIN meeting ON review.meeting_id = meeting.meeting_id
      JOIN users ON review.user_id = users.user_id
      ORDER BY rating DESC;
      ```


### **4-2. 후기 작성**

- **대상 사용자:** 모임에 참여한 사용자
- **기능 설명:** 사용자는 참여한 모임에 대해 후기를 작성할 수 있습니다. 후기에는 모임 날짜, 모임 제목, 모임장, 별점과 한 줄 코멘트가 포함됩니다. 사용자가 이미 후기를 작성한 경우, 같은 모임에 대해 다시 후기를 작성할 수 없습니다.
- **주요 SQL :**  사용자가 작성한 후기를 `INSERT` 문을 사용해서 `review` 테이블에 저장합니다.
- **Constraints:** `user_id`와 `meeting_id` 조합에 대해 `UNIQUE` 제약을 걸어, 사용자가 이미 작성한 후기를 중복 작성하지 않도록 합니다.

    ```sql
    INSERT INTO review (user_id, meeting_id, rating, comment, created_at)
    VALUES (?, ?, ?, ?, ?);
    ```


### **4-3. 후기 삭제**

- **대상 사용자:** 학생회
- **기능 설명:** 학생회는 다른 사용자가 작성한 후기를 삭제할 수 있습니다. 삭제 권한이 없는 사용자에게는 삭제 버튼이 보이지 않습니다.
- **주요 SQL :**  후기를 삭제하기 위해서 `DELETE` 문을 사용합니다.

    ```sql
    DELETE FROM review WHERE review_id = ?;
    ```


### **4-4. 별점 순으로 후기 정렬**

- **사용자:** 모든 사용자
- **기능 설명:** 사용자는 후기를 별점 순으로 정렬해서 볼 수 있습니다.
- **주요 SQL :** `ORDER BY`를 사용하여 후기를 별점 순으로 정렬합니다. 내부적으로 `idx_meeting_date`라는 `index`를 사용해서 더욱 효율적으로 데이터를 조회합니다.

    ```sql
    SELECT *
    FROM review
    JOIN meeting ON review.meeting_id = meeting.meeting_id
    JOIN users ON review.user_id = users.user_id
    ORDER BY rating DESC; -- 별점 순으로 정렬
    ```


### **4-5. 별점 5점만 보기**

- **사용자:** 모든 사용자
- **기능 설명:** 사용자가 후기 게시판에서 별점이 5점인 후기만 필터링하여 볼 수 있는 기능을 제공합니다. "전체 보기"를 클릭하면 모든 후기를 다시 볼 수 있도록 합니다.
- **주요 SQL :**  `HAVING` 절을 사용해서 별점 5점인 후기를 모임별로 그룹화한 뒤, 5점인 후기에 대해서만 후기를 보여줍니다.

    ```sql
    SELECT m.meeting_id, m.title, AVG(r.rating) as average_rating
    FROM review r
    JOIN meeting m ON r.meeting_id = m.meeting_id
    GROUP BY m.meeting_id, m.title
    HAVING AVG(r.rating) = 5;
    ```


---

## 5. 시간표

### 5-1. 시간표 등록

- **사용자**: 모든 사용자
- **기능**: 사용자는 자신의 수업에 맞게 요일, 수업 시작 시간, 종료 시간을 등록할 수 있고, 마이페이지에서 등록한 확인할 수 있습니다.
- **주요 SQL :**  `INSERT`, `UPDATE` 문을 통해 시간표 정보를 삽입합니다.
- **Constraints:**
  - **FOREIGN KEY:** `user_id`는 `users` 테이블과 연결되어 있습니다.
  - **UNIQUE:** `user_id`, `day_of_week`, `start_time`, `end_time`에 대해 유니크 제약 조건이 설정되어 있어, 동일한 사용자가 같은 시간대에 여러 시간표를 등록할 수 없습니다.
  - **CHECK:** `start_time`이 `end_time`보다 늦을 수 없도록 검증합니다.

    ```sql
    	-- 시간표 삽입
    INSERT INTO timetable (user_id, day_of_week, start_time, end_time)
    VALUES (?, ?, ?, ?);
    ```


### **5-2. 시간표 등록 시 충돌 체크**

- **대상 사용자:** 모든 사용자
- **기능 설명:** 새로운 시간표를 등록할 때, 이미 등록된 시간표와 충돌이 있는지 체크합니다. 충돌 시 예외를 발생시키고, 충돌이 없으면 시간표를 저장합니다.
- **주요 SQL :**  두 시간표가 겹치는지 확인하는 쿼리

    ```sql
    -- 두 시간표가 겹치는지 확인하는 쿼리
    SELECT t1.timetable_id AS timetable1_id, t2.timetable_id AS timetable2_id
    FROM timetable t1
    JOIN timetable t2 ON t1.user_id = t2.user_id  -- 같은 사용자끼리 비교
    WHERE t1.timetable_id != t2.timetable_id  -- 자신과 비교하지 않도록
      AND t1.day_of_week = t2.day_of_week  -- 같은 요일에서 비교
      AND (
          (t1.start_time < t2.end_time AND t1.end_time > t2.start_time)  -- 시간표1의 시간이 시간표2의 시간에 겹치는 경우
          OR
          (t2.start_time < t1.end_time AND t2.end_time > t1.start_time)  -- 시간표2의 시간이 시간표1의 시간에 겹치는 경우
      );
    ```


### **5-3. 시간표 보기 (View Timetable)**

- **대상 사용자:** 모든 사용자 (학생)
- **기능 설명:** 사용자는 자신의 시간표를 볼 수 있습니다. 로그인을 해야만 볼 수 있습니다.
- **주요 SQL :**  사용자의 시간표를 조회하는 `SELECT` 문을 사용하여 자신의 시간표를 조회합니다.

    ```sql
    SELECT *
    FROM timetable
    WHERE user_id = ?;
    ```


---

## 6. 회원 정보

### **6-1. 회원 관리 기능**

- **사용자:** 학생회
- **기능 설명:** 사용자는 다른 사용자의 정보를 조회하고, 수정할 수 있습니다. 수정 가능한 항목은 사용자의 이름, 역할, 이메일, 학번입니다. 이 기능은 주로 관리자 권한을 가진 사용자가 사용자 정보를 관리하는 데 사용됩니다.
- **주요 SQL :**  `UPDATE` , `BEGIN` `COMMIT`문을 사용해서 `users` 테이블에 있는 기존 사용자의 정보를 atomic 하게 수정합니다. 또한, 트랜잭션을 사용하여 서로 다른 테이블의 정보들을 한 번에 처리할 수 있습니다.

    ```sql
    -- 트랜잭션 시작
    BEGIN;
    
    -- 1. 사용자 정보 수정 (users 테이블)
    UPDATE users
    SET name = ?, role = ?, email = ?, student_id = ?
    WHERE user_id = ?;
    
    -- 2. 역할 정보 수정 (student_role 테이블)
    UPDATE student_role
    SET role = ?
    WHERE student_id = ?;
    
    -- 트랜잭션 커밋
    COMMIT;
    ```


### **6-2. 비밀번호 재설정**

- **대상 사용자:** 모든 사용자
- **기능 설명:** 사용자는 자신의 비밀번호를 수정할 수 있습니다.
- **주요 SQL :**  `UPDATE` , `BEGIN` `COMMIT`문을 사용해서 자신의 비밀번호를 수정합니다.

    ```sql
    -- 트랜잭션 시작
    BEGIN;
    
    -- 1. 비밀번호 수정 (users 테이블)
    UPDATE users
    SET password = ?
    WHERE user_id = ?;
    
    -- 트랜잭션 커밋
    COMMIT;
    ```


# **4️⃣ 데이터베이스 스키마 및 다이어그램**

**PK : 진하게**, <u>FK : 밑줄</u>

| 테이블명 | 컬럼명                                                                                                                                                                                                                       |
| --- |---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| student_role | **role_id** serial, <u>student_id</u> varchar(20), role varchar(50)                                                                                                                                                       |
| users | **user_id** serial, <u>student_id</u> varchar(15), name varchar(50), email varchar(100), password varchar(30)                                                                                                                    |
| meeting | **meeting_id** serial, <u>user_id</u> int, title varchar(100), space varchar(100), date date, time time, max_participants int, participant_count int, is_completed boolean, created_for varchar(50), created_by_role varchar(50) |
| enrollment | **enrollment_id** serial, <u>user_id</u> int, <u>meeting_id</u> int, enrollment_date date                                                                                                                                               |
| review | **review_id** serial, <u>meeting_id</u> int, <u>user_id</u> int, rating int, comment text, created_at timestamp                                                                                                                         |
| timetable | **timetable_id** serial, <u>user_id</u> int, day_of_week varchar(50), start_time time, end_time time                                                                                                                             |

![ERD.png](https://abaft-chocolate-f90.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F94628612-4999-4a9e-bd19-428a991f4eb4%2F2559b269-73bb-40df-a76a-3f6dadb51767%2Fd669e99c-292d-495d-9d15-f3ae2ab88d71.png?table=block&id=155f6efd-3739-80d8-b577-ebf37b335a5d&spaceId=94628612-4999-4a9e-bd19-428a991f4eb4&width=1420&userId=&cache=v2)

# **5️⃣ 팀원의 역할 배분**

- 김동인 🙉
  - 모임 신청
  - 시간표 등록
  - 후기 게시판
  - 메인 화면
  - 보고서 작성
- 김진우 😼
  - 모임 등록
  - 마이페이지
  - 회원관리
  - 메인 화면
  - 데모 영상 시나리오 작성 및 촬영

# 6️⃣ 프로젝트 실행 방법

1. src/main/resources/application.properties 파일에 들어가서 아래 부분을 본인의 db에 알맞게 설정합니다.

    ```sql
    # Datasource Configuration
    spring.datasource.hikari.maximum-pool-size=4
    spring.datasource.url=${DB_PATH}
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PASSWORD}
    ```

2. dbbeaver에서 아래 DDL sql문을 실행합니다.

    ```sql
    -- DROP TABLES IF EXISTS
    DROP TABLE IF EXISTS "users" CASCADE;
    DROP TABLE IF EXISTS "meeting" CASCADE;
    DROP TABLE IF EXISTS "enrollment" CASCADE;
    DROP TABLE IF EXISTS "review" CASCADE;
    DROP TABLE IF EXISTS "timetable" CASCADE;
    DROP TABLE IF EXISTS "student_role" CASCADE;
    
    -- CREATE TABLE student_role
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
   
   -- index 사용
   CREATE INDEX idx_meeting_date
   ON meeting (date);
    ```
3. dbbeaver에서 아래의 더미데이터를 추가하는 sql문을 실행합니다.

```sql
INSERT INTO student_role (student_id, role) VALUES
  ('202255513', 'Student_Council'),
  ('202255514', 'Freshman'),
  ('202255537', 'Freshman'),
  ('202255535', 'Senior_Student'),
  ('202155501', 'Freshman'),
  ('202155502', 'Senior_Student'),
  ('202155503', 'Senior_Student'),
  ('202155504', 'Senior_Student'),
  ('202255517', 'Senior_Student'),
  ('6515201', 'Professor');


INSERT INTO "users" (student_id, name, email, password) VALUES
  ('202255513', '김대욱', 'kdw@pusan.ac.kr', '333'),
  ('202255514', '김동인', 'kdi@pusan.ac.kr', '444'),
  ('202255537', '김한솔', 'khs@pusan.ac.kr', '777'),
  ('202155501', '김민경', 'kmk@pusan.ac.kr', '111'),
  ('202155502', '김예슬', 'kys@pusan.ac.kr', '222'),
  ('202155503', '김문경', 'kmg@pusan.ac.kr', '333'),
  ('202155504', '김채현', 'kch@pusan.ac.kr', '444'),
  ('202255517', '김동현', 'kdh@pusan.ac.kr', '777'),
  ('6515201', '조준수', 'cjs@pusan.ac.kr', '111');


INSERT INTO "meeting" (user_id, title, space, date, time, max_participants, participant_count, is_completed, created_for, created_by_role) VALUES
  (1, '롤 5인팟', 'OX PX', '2024-11-13', '20:30:00', 5, 3, true, 'Senior_Student', 'Student_Council'),
  (5, '새준단!', '보드게임방', '2024-12-10', '18:00:00', 2, 2, false, 'Freshman', 'Senior_Student'),
  (6, '헬스장', '워너짐', '2024-12-23', '14:00:00', 3, 0, false, 'Freshman', 'Senior_Student'),
  (9, '교수님과의바뱍', '칠칠켄터키', '2024-12-07', '17:30:00', 5, 2, true, 'Senior_Student', 'Professor'),
  (1, '카공', '카페그라운드', '2024-12-15', '15:00:00', 3, 0, false, 'Senior_Student', 'Student_Council'),
  (7, '정컴노래방', '깐느', '2024-12-19', '20:00:00', 4, 0, false, 'Freshman', 'Senior_Student');


INSERT INTO "enrollment" (user_id, meeting_id, enrollment_date) VALUES
  (5, 1, '2024-12-08'),
  (6, 1, '2024-12-08'),
  (7, 1, '2024-12-08'),
  (3, 2, '2024-12-08'),
  (4, 2, '2024-12-08'),
  (5, 3, '2024-12-08'),
  (8, 3, '2024-12-08');
--
--
INSERT INTO review (meeting_id, user_id, rating, comment) VALUES
  (1, 7, 2, '사람들이 롤을 너무 못해요 으악!'),
  (1, 6, 3, '재밌었어요!'),
  (1, 5, 5, '짱이에요!'),
  (3, 8, 5, '조준수 교수님 최고♡'),
  (3, 5, 4, '너무 재밌었어요');
--
--
INSERT INTO "timetable" (user_id, day_of_week, start_time, end_time) VALUES
  (2, 'MONDAY', '13:30:00', '17:30:00'),
  (3, 'TUESDAY', '09:00:00', '12:00:00'),
  (4, 'WEDNESDAY', '14:00:00', '16:00:00');
```
4. intelliJ IDEA에서 위 프로젝트를 열고, src/main/java/com/example/lightning/LightningApplication.java 파일을 실행합니다.
5. [http://localhost:8080](http://localhost:8080/) 에 접속합니다.
