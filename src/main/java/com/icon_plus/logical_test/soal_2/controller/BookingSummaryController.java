package com.icon_plus.logical_test.soal_2.controller;

import com.icon_plus.logical_test.soal_2.model.DashboardSumaryResponse;
import com.icon_plus.logical_test.soal_2.service.DashboardSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController
    @RequiredArgsConstructor
    @RequestMapping("/api/booking")
    public class BookingSummaryController {

            private final DashboardSummaryService dashboardSummaryService;

            @GetMapping("/summary")
            public DashboardSumaryResponse getDashboardSummary() {
                return dashboardSummaryService.getDashboardSummary();
            }
    }
