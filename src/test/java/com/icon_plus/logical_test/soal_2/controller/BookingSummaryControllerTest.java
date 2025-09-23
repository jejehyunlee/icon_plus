package com.icon_plus.logical_test.soal_2.controller;

import com.icon_plus.logical_test.soal_2.model.DashboardSumaryResponse;
import com.icon_plus.logical_test.soal_2.service.DashboardSummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookingSummaryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DashboardSummaryService dashboardSummaryService;

    @InjectMocks
    private BookingSummaryController bookingSummaryController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingSummaryController)
                .setControllerAdvice()
                .build();
    }

    @Test
    void getDashboardSummary_WithYearAndMonth_ShouldReturnSuccess() throws Exception {
        // Arrange
        int year = 2023;
        int month = 5;
        DashboardSumaryResponse mockResponse = createMockResponse(year, month);

        when(dashboardSummaryService.getDashboardSummary(year, month))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/booking/summary")
                .param("year", String.valueOf(year))
                .param("month", String.valueOf(month))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.period").value("MAY 2023"));
    }

    private DashboardSumaryResponse createMockResponse(int year, int month) {
        DashboardSumaryResponse response = new DashboardSumaryResponse();
        response.setPeriod(String.format("%s %d",
            LocalDate.of(year, month, 1).getMonth().toString(),
            year));
        // Add more mock data as needed for your response object
        return response;
    }
}
