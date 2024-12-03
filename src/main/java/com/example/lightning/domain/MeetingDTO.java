package com.example.lightning.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class MeetingDTO {

        private Long meetingId;
        private String title;
        private String space;
        private String date;
        private String time;
        private int maxParticipants;
        private int participantCount;
        private String createdFor;
        private String createdByRole;
        private String createdByName;

        public MeetingDTO(Meeting meeting) {
            this.meetingId = meeting.getMeetingId();
            this.title = meeting.getTitle();
            this.space = meeting.getSpace();
            // LocalDate, LocalTime을 String으로 변환
            this.date = meeting.getDate().toString();
            this.time = meeting.getTime().toString();
            this.maxParticipants = meeting.getMaxParticipants();
            this.participantCount = meeting.getParticipantCount();
            this.createdFor = meeting.getCreatedFor();
            this.createdByRole = meeting.getCreatedByRole();
            this.createdByName = meeting.getUser().getName();
        }

}
