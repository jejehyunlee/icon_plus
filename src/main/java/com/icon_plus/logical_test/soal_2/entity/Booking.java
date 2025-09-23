package com.icon_plus.logical_test.soal_2.entity;

import lombok.Data;
import java.util.List;

@Data
public class Booking {
    private String id;
    private String bookingDate;
    private String officeName;
    private String startTime;
    private String endTime;
    private List<Consumption> listConsumption;
    private Integer participants;
    private String roomName;

    // Getter untuk participants dengan default value
    public Integer getParticipants() {
        return participants != null ? participants : 0;
    }
}