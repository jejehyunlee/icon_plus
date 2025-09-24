package com.icon_plus.logical_test.soal_2.model;

import com.icon_plus.logical_test.soal_2.entity.Consumption;
import lombok.Data;
import java.util.Collections;
import java.util.List;

@Data
public class BookingListResponse {
    private String id;                    // String, bukan Long
    private String bookingDate;
    private String officeName;
    private String roomName;
    private String startTime;
    private String endTime;
    private Integer participants;
    private List<Consumption> listConsumption;  // Sesuai dengan Booking.java

    // Helper methods
    public List<Consumption> getListConsumptionSafe() {
        return listConsumption != null ? listConsumption : Collections.emptyList();
    }
    
    public String getUnitName() {
        return officeName; // Use officeName as unitName
    }
    
    public Integer getCapacity() {
        return participants; // Use participants as capacity
    }
}
