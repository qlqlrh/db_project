package com.example.lightning.domain;

public class Meeting {
    private String location;
    private String meetingDate;
    private int startHour;
    private int startMinute;
    private int minParticipants;
    private int maxParticipants;
    private String organizer;

    // Getters and Setters

    @Override
    public String toString() {
        return "Meeting{" +
                "location='" + location + '\'' +
                ", meetingDate='" + meetingDate + '\'' +
                ", startHour=" + startHour +
                ", startMinute=" + startMinute +
                ", minParticipants=" + minParticipants +
                ", maxParticipants=" + maxParticipants +
                ", organizer='" + organizer + '\'' +
                '}';
    }
}
