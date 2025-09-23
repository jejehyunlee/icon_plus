package com.icon_plus.logical_test.soal_2.model;

import lombok.Data;

@Data
public class RoomUtilization {
    private String roomName;
    private int bookingCount;
    private double utilizationRate;
    private long totalConsumption;
}
