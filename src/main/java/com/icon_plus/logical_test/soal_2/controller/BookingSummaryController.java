package com.icon_plus.logical_test.soal_2.controller;

import com.icon_plus.logical_test.soal_2.model.UnitSummaryResponse;
import com.icon_plus.logical_test.soal_2.service.UnitSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/booking")
    public class BookingSummaryController {

//          private final DashboardSummaryService dashboardSummaryService;

            private final UnitSummaryService unitSummaryService;

//            @GetMapping("/summary")
//            public DashboardSumaryResponse getDashboardSummary(
//                    @RequestParam(required = false) Integer year,
//                    @RequestParam(required = false) Integer month
//                )
//
//            {
//                try {
//                    return dashboardSummaryService.getDashboardSummary(year, month);
//                } catch (Exception e) {
//                    throw new RuntimeException("Failed to generate dashboard summary: " + e.getMessage());
//                }
//            }

            @GetMapping("/summary")
            public List<UnitSummaryResponse> getDashboardSummaryNew() {

                try {
                    return unitSummaryService.getUnitSummary();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to generate unit summary: " + e.getMessage(), e);
                }

            }
    }
