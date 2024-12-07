package com.example.lightning.repository;

import com.example.lightning.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByUser_UserId(Long userId);
    //date열을 기준으로 정렬된 모임을 가져오기
    List<Meeting> findAllByOrderByDateAsc();

}
