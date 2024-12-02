package com.example.lightning.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long meetingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user; // Meeting 생성자 (참조)

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "space", nullable = false, length = 100)
    private String space;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "max_participants", nullable = false)
    private int maxParticipants;

    @Column(name = "participant_count", nullable = false)
    private int participantCount = 0;

    @Column(name = "created_for", nullable = false, length = 50)
    private String createdFor;

    @Column(name = "created_by_role", nullable = false, length = 50)
    private String createdByRole;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
<<<<<<< HEAD

//    @Override
//    public String toString() {
//        return "Meeting{" +
//                "location='" + location + '\'' +
//                ", meetingDate='" + meetingDate + '\'' +
//                ", startHour=" + startHour +
//                ", startMinute=" + startMinute +
//                ", minParticipants=" + minParticipants +
//                ", maxParticipants=" + maxParticipants +
//                ", organizer='" + organizer + '\'' +
//                '}';
//    }
=======
>>>>>>> bf0cdb8f096eec57bd2aa15fc86838d290fecb52
}
