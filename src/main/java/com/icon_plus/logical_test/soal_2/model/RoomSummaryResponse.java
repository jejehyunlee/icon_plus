package com.icon_plus.logical_test.soal_2.model;

import com.icon_plus.logical_test.soal_2.entity.Consumption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomSummaryResponse {
    private String bookingDate;
    private String roomName;
    private Integer participants;
    private BigDecimal totalNominal;
    private BigDecimal precentageConsumption;
    private List<JenisKonsumsiResponse> consumptionSummary;
}
