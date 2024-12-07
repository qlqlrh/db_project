# 모여봄

# 프로젝트 개요 (Project Topic)

이 웹 애플리케이션은 **학생 커뮤니티 관리 시스템**으로, 학생들이 참여하는 다양한 모임을 관리하고, 각 모임에 대한 후기를 작성하거나 조회할 수 있는 기능을 제공합니다. 주요 기능으로는 모임 등록, 시간표 관리, 후기 게시판 등이 포함되어 있으며, **학생회**와 **일반 사용자**의 역할을 분리하여 기능에 따라 다르게 표시됩니다. 또한, **학생회**는 특정 권한을 가진 관리자로서 모임의 후기를 삭제할 수 있는 기능을 제공합니다.

---

# 2. 사용자 (Roles)

### 사용자 역할

- **일반 사용자 (Student)**:
  - 모임에 참여하거나, 본인이 참여한 모임에 대해 후기를 작성할 수 있습니다.
  - 후기는 모임 제목, 날짜, 모임장 이름, 별점, 한 줄 후기 등의 정보를 포함합니다.
  - 후기를 작성하고 조회할 수 있습니다.
- **학생회 (Student_Council)**:
  - 학생회는 모임에 대한 후기를 **삭제**할 수 있는 권한을 갖습니다.
  - 학생회는 모든 사용자가 작성한 후기를 확인할 수 있으며, 특정 후기에 대한 관리 권한을 가집니다.
- **교수 (Professor)**:
  - 교수는 모임을 생성할 수 있으며, 학생들이 참여한 모임에 대해 후기를 작성할 수 있습니다.
- **신입생 (Freshman)**:
  - 신입생은 모임에 참여하고, 후기를 작성할 수 있지만, 관리 권한은 없습니다.
- **졸업생 (Senior_Student)**:
  - 졸업생은 신입생과 유사한 권한을 가지며, 후기를 작성할 수 있습니다.

---

# 3. 기능 (Functions)

### 1. 모임 등록 (Meeting Registration)

- **사용자**: 학생, 교수
- **기능**: 사용자는 모임을 등록할 수 있으며, 모임에 대한 제목, 시간, 장소 등의 정보를 기입합니다.
- **SQL Features**:
  - **DML**: `INSERT` 문을 통해 모임 정보를 `meeting` 테이블에 삽입합니다.
  - **Foreign Key Constraints**: `user_id`와 `created_by_role`을 `users` 테이블과 연결합니다.

```sql
INSERT INTO meeting (user_id, title, space, date, time, max_participants, created_for, created_by_role)
VALUES (?, ?, ?, ?, ?, ?, ?, ?);
```

### 2. 후기 게시판 (Review Board)

- **사용자**: 학생회, 일반 사용자
- **기능**: 사용자는 후기 게시판에서 자신이 참여한 모임에 대해 후기를 작성할 수 있으며, 학생회는 후기를 삭제할 수 있는 권한을 가집니다.
- **SQL Features**:
  - **DML**: `INSERT`, `SELECT`, `DELETE` 등
  - **SFW** (Select From Where): 후기를 작성하고 조회하는 데 사용됩니다. 학생회는 특정 조건 하에 삭제 기능을 사용할 수 있습니다.
  - **ORDER BY**: 후기는 작성 날짜 또는 별점 기준으로 정렬할 수 있습니다.
  - **Subquery**: 특정 조건을 만족하는 후기를 조회할 때, 서브쿼리를 사용할 수 있습니다.

```sql
-- 후기 삽입
INSERT INTO review (meeting_id, user_id, rating, comment, created_at)
VALUES (?, ?, ?, ?, ?);

-- 후기를 조회하는 쿼리
SELECT * FROM review_view
WHERE meeting_id = ? ORDER BY created_at DESC;

-- 후기 삭제 (학생회 권한)
DELETE FROM review WHERE review_id = ?;
```

### 3. 역할 기반 보기 (Role-based Views)

- **사용자**: 학생회, 일반 사용자
- **기능**: 학생회는 모든 후기를 볼 수 있고, 그 후기를 삭제할 수 있습니다. 일반 사용자는 `review_view`에서 일부 정보를 제한적으로 조회합니다.
- **SQL Features**:
  - **View**: 학생회가 아닌 사용자는 `review_view`에서 데이터를 조회합니다. 이 `view`는 학생회가 아닌 사용자에게 `created_by_name`을 숨깁니다.
  - **Subquery**: 사용자가 작성한 후기 데이터를 조회할 때 서브쿼리를 사용할 수 있습니다.

```sql
-- View 생성
CREATE VIEW review_view AS
SELECT
    r.review_id,
    m.title AS meeting_title,
    m.date AS meeting_date,
    u.name AS created_by_name,
    r.rating,
    r.comment
FROM review r
JOIN meeting m ON r.meeting_id = m.meeting_id
JOIN users u ON r.user_id = u.user_id;
```

### 4. 회원가입 (User Registration)

- **사용자**: 모든 사용자
- **기능**: 사용자는 `signup` 페이지에서 정보를 입력하여 회원가입을 할 수 있습니다. 회원가입 시, 학번과 비밀번호를 입력하고, 해당 학번에 맞는 역할을 자동으로 할당받습니다.
- **SQL Features**:
  - **DML**: `INSERT` 문을 사용하여 새로운 사용자 정보를 `users` 테이블에 추가합니다.
  - **Subquery**: `student_role` 테이블에서 학번에 해당하는 역할을 조회한 후, `users` 테이블에 해당 역할을 설정합니다.

```sql
-- student_role에서 역할 조회
SELECT role FROM student_role WHERE student_id = ?;

-- users 테이블에 신규 사용자 추가
INSERT INTO users (name, email, password, student_id)
VALUES (?, ?, ?, ?);
```

### 5. 시간표 관리 (Timetable Management)

- **사용자**: 학생
- **기능**: 사용자는 자신의 시간표를 관리할 수 있으며, 시간표에서 수업 시간, 과목 등을 확인하고 수정할 수 있습니다.
- **SQL Features**:
  - **DML**: `INSERT`, `UPDATE` 문을 통해 시간표 정보를 삽입하거나 수정합니다.
  - **Foreign Key Constraints**: `user_id`와 `users` 테이블을 연결하여 각 사용자의 시간표를 관리합니다.

```sql
-- 시간표 삽입
INSERT INTO timetable (user_id, day_of_week, start_time, end_time)
VALUES (?, ?, ?, ?);

-- 시간표 업데이트
UPDATE timetable
SET start_time = ?, end_time = ?
WHERE user_id = ? AND day_of_week = ?;
```

### **6. 모임 등록 (Meeting Registration)**

- **대상 사용자:** 학생회 및 교수를 제외한 사용자 (교수는 예외적으로 모든 모임을 등록할 수 있음)
- **기능 설명:** 사용자는 모임 제목, 장소, 날짜, 시간, 최대 인원 등의 정보를 입력하여 모임을 등록할 수 있습니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **INSERT:** `meeting` 테이블에 모임 정보를 삽입.

      ```sql
      INSERT INTO meeting (user_id, title, space, date, time, max_participants, created_for, created_by_role)
      VALUES (?, ?, ?, ?, ?, ?, ?, ?);
      ```

  - **Constraints:**
    - `FOREIGN KEY`: `user_id`는 `users` 테이블과 연결되어 있습니다.
    - `CHECK`: `created_for`와 `created_by_role` 필드는 가능한 값을 제한하는 제약 조건을 두고 있습니다.
    - **SFW (Select From Where):**
      - 모임 정보를 조회할 때 사용된 `SELECT` 쿼리에서 `WHERE` 조건을 사용하여 날짜, 생성자, 역할 등을 필터링할 수 있습니다.

### **7. 모임 신청 (Meeting Enrollment)**

- **대상 사용자:** 사용자는 본인이 참가할 모임을 신청할 수 있습니다. 단, 모임장 자신은 신청할 수 없습니다.
- **기능 설명:** 사용자는 모임의 신청이 가능한지 여부를 확인하고, 신청 조건을 만족하면 모임에 참여할 수 있습니다. 최대 인원 초과, 모임의 날짜와 시간 충돌 여부도 확인합니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **INSERT:** `enrollment` 테이블에 모임 신청 데이터를 삽입.
    - **UPDATE:** 모임 신청 후 `meeting` 테이블의 참가자 수(`participant_count`)를 업데이트.

      ```sql
      INSERT INTO enrollment (user_id, meeting_id, enrollment_date) VALUES (?, ?, ?);
      
      UPDATE meeting SET participant_count = participant_count + 1
      WHERE meeting_id = ?;
      ```

  - **Constraints:**
    - `FOREIGN KEY`: `user_id`, `meeting_id`는 각각 `users`와 `meeting` 테이블과 연결되어 있습니다.
    - **Subquery:** 사용자가 이미 모임에 신청한 상태인지 확인하는 서브쿼리.

        ```sql
        SELECT COUNT(*)
        FROM enrollment
        WHERE user_id = ? AND meeting_id = ?
        ```

  - **SFW (Select From Where):**
    - `SELECT` 쿼리로 사용자의 시간표와 모임 시간의 충돌을 체크합니다.

      ```sql
      SELECT * FROM timetable
      WHERE user_id = ? AND day_of_week = ?
          AND
          ((meeting_start_time BETWEEN ? AND ?)
          OR
          (meeting_end_time BETWEEN ? AND ?));
      ```

  - **Group By:** 모임의 참여 인원을 집계할 때 사용될 수 있습니다.

      ```sql
      sql
      코드 복사
      SELECT meeting_id, COUNT(*) AS participants FROM enrollment GROUP BY meeting_id;
      
      ```


### **8. 모임 완료 처리 (Complete Meeting)**

- **대상 사용자:** 모임을 생성한 사람(주로 교수나 학생회)
- **기능 설명:** 모임이 완료되었음을 표시하며, 모임의 상태를 변경합니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **UPDATE:** 모임의 완료 여부를 업데이트.

      ```sql
      UPDATE meeting SET is_completed = TRUE
      WHERE meeting_id = ?;
      ```


### **9. 모임 개설 취소 (Cancel Created Meeting)**

- **대상 사용자:** 모임을 생성한 사람(주로 교수나 학생회)
- **기능 설명:** 모임이 개설된 후, 취소할 수 있습니다. 취소 시 모든 신청자가 삭제됩니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **DELETE:** `enrollment` 테이블에서 해당 모임의 신청자를 삭제.
    - **DELETE:** `meeting` 테이블에서 모임을 삭제.

      ```sql
      DELETE FROM enrollment
      WHERE meeting_id = ?;
      
      DELETE FROM meeting
      WHERE meeting_id = ?;
      ```

  - **Constraints:**
    - `ON DELETE CASCADE`: 모임이 삭제되면 해당 모임에 대한 모든 신청자가 자동으로 삭제됩니다.

### **10. 모임 참여 취소 (Cancel Enrolled Meeting)**

- **대상 사용자:** 모임에 신청한 사용자
- **기능 설명:** 사용자는 모임 신청을 취소할 수 있습니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **DELETE:** `enrollment` 테이블에서 신청한 모임을 삭제.
    - **UPDATE:** 모임의 참가자 수를 감소시킴.

      ```sql
      DELETE FROM enrollment WHERE user_id = ? AND meeting_id = ?;
      UPDATE meeting SET participant_count = participant_count - 1
      WHERE meeting_id = ?;
      ```


### **11. 시간표 등록 (Timetable Registration)**

- **대상 사용자:** 모든 사용자 (학생)
- **기능 설명:** 사용자는 자신의 시간표를 등록할 수 있으며, 각 시간표 항목은 요일, 시작 시간, 종료 시간을 포함합니다. 사용자는 시간표를 중복 없이 등록할 수 있습니다. 만약 중복된 시간이 있다면 오류 메시지가 표시됩니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **INSERT:** 새로운 시간표를 `timetable` 테이블에 삽입합니다.

      ```sql
      INSERT INTO timetable (user_id, day_of_week, start_time, end_time)
      VALUES (?, ?, ?, ?);
      ```

  - **Constraints:**
    - **UNIQUE:** `user_id`, `day_of_week`, `start_time`, `end_time`에 대해 유니크 제약 조건이 설정되어 있어, 동일한 사용자가 같은 시간대에 여러 시간표를 등록할 수 없습니다.
    - **FOREIGN KEY:** `user_id`는 `users` 테이블과 연결되어 있습니다.
    - **CHECK:** `start_time`이 `end_time`보다 늦을 수 없도록 검증합니다.
  - **SFW (Select From Where):**
    - `SELECT`를 사용하여 시간표와 충돌이 있는지 확인합니다.

      ```sql
      SELECT *
      FROM timetable
      WHERE user_id = ? AND day_of_week = ? AND
      ((start_time BETWEEN ? AND ?) OR (end_time BETWEEN ? AND ?));
      ```

  - **DML (Data Manipulation Language) - SELECT 쿼리 사용 예:**
    - 충돌하는 시간대가 있는지 확인하기 위해 `SFW` 쿼리를 사용합니다.

### **12. 시간표 중복 확인 (Timetable Conflict Check)**

- **대상 사용자:** 모든 사용자 (학생)
- **기능 설명:** 사용자가 등록하려는 시간표가 기존 시간표와 충돌하는지 확인합니다. 충돌이 있으면 `true`를 반환하고, 없으면 `false`를 반환합니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **SELECT:** 사용자의 기존 시간표와 비교하여 시간대 충돌을 확인하는 `SELECT` 쿼리.

      ```sql
      SELECT *
      FROM timetablWHERE user_id = ? AND day_of_week = ? AND
      ((start_time BETWEEN ? AND ?) OR (end_time BETWEEN ? AND ?));
      ```

  - **Subquery (서브쿼리):**
    - `SELECT` 쿼리 내에서 서브쿼리를 사용하여 충돌 여부를 체크합니다.

### **13. 시간표 등록 시 충돌 체크 (Conflict Check When Registering Timetable)**

- **대상 사용자:** 모든 사용자 (학생)
- **기능 설명:** 새로운 시간표를 등록할 때, 이미 등록된 시간표와 충돌이 있는지 체크합니다. 충돌 시 예외를 발생시키고, 충돌이 없으면 시간표를 저장합니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **SELECT:** `isConflictWithTimetable` 메소드에서 사용자의 기존 시간표와 비교하여 충돌이 있는지 확인하는 `SELECT` 쿼리 사용.
    - **INSERT:** 시간이 겹치지 않으면 새로운 시간표를 `timetable` 테이블에 삽입합니다.

      ```sql
      INSERT INTO timetable (user_id, day_of_week, start_time, end_time)
      VALUES (?, ?, ?, ?);
      ```

  - **DML (Data Manipulation Language):**
    - **SELECT:** `SELECT` 쿼리를 사용하여 해당 사용자의 시간표에서 겹치는 시간대를 확인합니다.
    - **DML (Data Manipulation Language):**
    - **INSERT:** 사용자가 시간표를 등록할 때, 중복이 없으면 `timetable` 테이블에 데이터를 삽입합니다.

### **14. 시간표 보기 (View Timetable)**

- **대상 사용자:** 모든 사용자 (학생)
- **기능 설명:** 사용자는 자신의 시간표를 볼 수 있습니다. 시간표는 요일별로 정렬되어 표시됩니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **SELECT:** 사용자의 시간표를 조회하는 `SELECT` 쿼리.

      ```sql
      SELECT *
      FROM timetable
      WHERE user_id = ?
      ORDER BY day_of_week, start_time;
      ```

  - **Order By:**
    - 시간표는 요일별로, 시작 시간에 따라 정렬됩니다.

      ```sql
      SELECT *
      FROM timetable
      WHERE user_id = ?
      ORDER BY day_of_week, start_time;
      ```


### **15. 모임 신청 시 시간표 충돌 체크 (Meeting Enrollment Conflict Check)**

- **대상 사용자:** 모임에 신청하려는 사용자
- **기능 설명:** 사용자가 모임에 신청할 때, 해당 모임의 시간과 시간표가 겹치는지 확인합니다. 시간표와 모임 시간이 겹친다면 신청이 불가능하도록 처리합니다.
- **SQL 기능:**
  - **SFW (Select From Where):**
    - `SELECT`를 사용하여 사용자의 시간표와 모임 시간이 충돌하는지 확인합니다.

      ```sql
      SELECT *
      FROM timetable
      WHERE user_id = ? AND day_of_week = ? AND
      ((start_time BETWEEN ? AND ?) OR (end_time BETWEEN ? AND ?));
      ```

  - **DML (Data Manipulation Language):**
    - **INSERT:** 충돌이 없다면 `enrollment` 테이블에 데이터를 삽입하여 모임 신청을 처리합니다.

      ```sql
      INSERT INTO enrollment (user_id, meeting_id, enrollment_date)
      VALUES (?, ?, ?);
      ```


### **16. 시간표 수정 (Update Timetable)**

- **대상 사용자:** 시간표를 수정하려는 사용자
- **기능 설명:** 사용자는 기존에 등록된 시간표를 수정할 수 있으며, 수정 시 기존 시간표와의 충돌 여부를 확인합니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **UPDATE:** 기존 시간표를 수정하는 `UPDATE` 쿼리.

      ```sql
      UPDATE timetable SET start_time = ?, end_time = ?
      WHERE timetable_id = ?;
      ```

  - **SFW (Select From Where):**
    - **SELECT:** 시간표 수정 전, 해당 시간대에 충돌하는 시간표가 있는지 확인하는 `SELECT` 쿼리.

      ```sql
      SELECT *
      FROM timetable
      WHERE user_id = ? AND day_of_week = ?
          AND ((start_time BETWEEN ? AND ?) OR (end_time BETWEEN ? AND ?));
      ```


### **17. 후기 게시판 조회 (Review Board)**

- **대상 사용자:** 모든 사용자
- **기능 설명:** 사용자는 후기 게시판에서 후기를 확인할 수 있습니다. 후기는 기본적으로 작성 순서대로 보여주며, 정렬 방식은 별점 순, 날짜 순으로 선택 가능합니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **SELECT:** 후기 데이터를 조회하기 위한 `SELECT` 쿼리.

      ```sql
      SELECT *
      FROM review
      JOIN meeting ON review.meeting_id = meeting.meeting_id
      JOIN users ON review.user_id = users.user_id
      ORDER BY created_at DESC; -- 기본은 날짜 순으로 정
      ```

  - **SFW (Select From Where):**
    - 후기를 조회할 때 `WHERE` 조건을 사용하여 특정 데이터를 필터링합니다.

      ```sql
      SELECT *
      FROM review
      JOIN meeting ON review.meeting_id = meeting.meeting_id
      JOIN users ON review.user_id = users.user_id
      WHERE review.created_at >= ?; -- 예: 특정 날짜 이후의 후기를 조회
      ```

  - **Order By:**
    - **ORDER BY**를 사용하여 후기를 정렬합니다. 기본적으로는 날짜 순으로 정렬되며, `sort` 파라미터에 따라 별점 순으로도 정렬할 수 있습니다.

      ```sql
      SELECT * FROM review
      ORDER BY rating DESC; -- 별점 순으로 정렬
      ```

  - **Keys:**
    - **JOIN:** `review` 테이블, `meeting` 테이블, `users` 테이블을 `JOIN`하여 후기에 대한 상세 정보를 가져옵니다.

---

### **18. 후기 작성 (Write Review)**

- **대상 사용자:** 모임에 참여한 사용자
- **기능 설명:** 사용자는 참여한 모임에 대해 후기를 작성할 수 있습니다. 후기에는 별점과 한 줄 코멘트가 포함됩니다. 사용자가 이미 후기를 작성한 경우, 같은 모임에 대해 다시 후기를 작성할 수 없습니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **INSERT:** 사용자가 작성한 후기를 `review` 테이블에 저장합니다.

      ```sql
      INSERT INTO review (user_id, meeting_id, rating, comment, created_at)
      VALUES (?, ?, ?, ?, ?);
      ```

  - **Constraints:**
    - **UNIQUE:** `user_id`와 `meeting_id` 조합에 대해 유니크 제약을 걸어, 사용자가 이미 작성한 후기를 중복 작성하지 않도록 합니다.

---

### **19. 후기 삭제 (Delete Review)**

- **대상 사용자:** 학생회 역할을 가진 사용자
- **기능 설명:** 학생회는 다른 사용자가 작성한 후기를 삭제할 수 있습니다. 삭제 권한이 없는 사용자가 시도하면 권한 오류가 발생합니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **DELETE:** 후기를 삭제하기 위한 `DELETE` 쿼리.

      ```sql
      DELETE FROM review WHERE review_id = ?;
      ```

  - **SFW (Select From Where):**
    - **SELECT**를 사용하여 해당 리뷰가 존재하는지 확인 후 삭제합니다.

      ```sql
      SELECT * FROM review WHERE review_id = ?
      ```


---

### **20. 후기 작성 여부 확인 (Check if User Has Reviewed)**

- **대상 사용자:** 모임에 참여한 사용자
- **기능 설명:** 사용자가 이미 해당 모임에 후기를 작성했는지 확인할 수 있습니다. 이미 작성한 경우 후기를 작성할 수 없도록 제한됩니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **SELECT:** `SELECT` 쿼리로 사용자가 해당 모임에 대해 후기를 작성했는지 확인합니다.

      ```sql
      SELECT 1 FROM review WHERE user_id = ? AND meeting_id = ?;
      ```

  - **SFW (Select From Where):**
    - 사용자가 특정 모임에 대해 후기를 작성했는지 `WHERE` 조건을 통해 필터링하여 확인합니다.

      ```sql
      SELECT *
      FROM review
      WHERE user_id = ? AND meeting_id = ?;
      ```


---

### **21. 별점 순으로 후기 정렬 (Sort Reviews by Rating)**

- **대상 사용자:** 모든 사용자
- **기능 설명:** 사용자는 후기를 별점 순으로 정렬해서 볼 수 있습니다.
- **SQL 기능:**
  - **DML (Data Manipulation Language):**
    - **SELECT:** 후기 데이터를 조회하며 별점 순으로 정렬합니다.

      ```sql
      SELECT *
      FROM review
      JOIN meeting ON review.meeting_id = meeting.meeting_id
      JOIN users ON review.user_id = users.user_id
      ORDER BY rating DESC; -- 별점 순으로 정렬
      ```

  - **Order By:**
    - **ORDER BY**를 사용하여 후기를 별점 순으로 정렬합니다.

# 4. **데이터베이스 스키마 및 다이어그램**

PK : 진하게, FK : 밑줄

| 테이블명 | 컬럼명 |
| --- | --- |
| student_role | **role_id** serial, student_id varchar(20), role varchar(50) |
| users | **user_id** serial, student_id varchar(15), name varchar(50), email varchar(100), password varchar(30) |
| meeting | **meeting_id** serial, user_id int, title varchar(100), space varchar(100), date date, time time, max_participants int, participant_count int, is_completed boolean, created_for varchar(50), created_by_role varchar(50) |
| enrollment | **enrollment_id** serial, user_id int, meeting_id int, enrollment_date date |
| review | **review_id** serial, meeting_id int, user_id int, rating int, comment text, created_at timestamp |
| timetable | **timetable_id** serial, user_id int, day_of_week varchar(50), start_time time, end_time time |

![image.png](%E1%84%86%E1%85%A9%E1%84%8B%E1%85%A7%E1%84%87%E1%85%A9%E1%86%B7%20155f6efd3739809a8fabe75b416fbf1c/image.png)

# 5.  **팀원의 역할 배분**

- 김동인
  - 모임 신청
  - 시간표 등록
  - 후기 게시판
  - 메인 화면
- 김진우
  - 모임 등록
  - 마이페이지
  - 회원관리
  - 메인 화면
