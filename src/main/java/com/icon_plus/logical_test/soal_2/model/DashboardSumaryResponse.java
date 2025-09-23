package com.icon_plus.logical_test.soal_2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSumaryResponse {
    private String period;
    private SummaryStatistics statistics;
    private List<RoomUtilization> roomUtilizations;
    private ConsumptionSummary consumptionSummary;
    private OfficeActivity officeActivity;
}
