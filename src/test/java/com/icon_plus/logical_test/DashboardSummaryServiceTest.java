package com.icon_plus.logical_test;

import com.icon_plus.logical_test.soal_2.entity.Booking;
import com.icon_plus.logical_test.soal_2.entity.Consumption;
import com.icon_plus.logical_test.soal_2.entity.ConsumptionType;
import com.icon_plus.logical_test.soal_2.model.DashboardSumaryResponse;
import com.icon_plus.logical_test.soal_2.service.ApiService;
import com.icon_plus.logical_test.soal_2.service.DashboardSummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DashboardSummaryServiceTest {

    @Mock
    private ApiService apiService;

    @InjectMocks
    private DashboardSummaryService dashboardSummaryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        setupMocks();
    }

    private void setupMocks() {
        // Create test data
        ConsumptionType consumptionType = new ConsumptionType();
        consumptionType.setId("1");
        consumptionType.setName("Test Type");

        Consumption consumption = new Consumption();
        consumption.setName(consumptionType.getName());

        Booking booking = new Booking();
        booking.setId("1");
        booking.setParticipants(10);
        booking.setListConsumption(Collections.singletonList(consumption));

        // Mock API responses
        when(apiService.getAllBookings()).thenReturn(Collections.singletonList(booking));
        when(apiService.getAllConsumptionTypes()).thenReturn(Collections.singletonList(consumptionType));
    }

    @Test
    public void testGetDashboardSummary() {
        // Arrange
        Integer year = 2022;
        Integer month = 12;

        // Act
        DashboardSumaryResponse response = dashboardSummaryService.getDashboardSummary(year, month);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getPeriod());
        assertNotNull(response.getStatistics());
        assertNotNull(response.getRoomUtilizations());
        assertNotNull(response.getConsumptionSummary());
        assertNotNull(response.getOfficeActivity());
        
        // Verify statistics
        assertTrue(response.getStatistics().getTotalBookings() > 0);
        assertTrue(response.getStatistics().getTotalParticipants() > 0);
    }
}
