package com.icon_plus.logical_test.soal_2.service;

import com.icon_plus.logical_test.soal_2.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UnitSummaryService {

    private final RestTemplate restTemplate;

    @Value("${api.booking.url}")
    private String bookingListUrl;

    @Value("${api.consumption.url}")
    private String konsumsiUrl;

    public String debugRawBookingData() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(bookingListUrl, String.class);
            return "Booking API Response:\n" + response.getBody();
        } catch (Exception e) {
            return "Error calling booking API: " + e.getMessage();
        }
    }

    public String debugRawConsumptionData() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(konsumsiUrl, String.class);
            return "Consumption API Response:\n" + response.getBody();
        } catch (Exception e) {
            return "Error calling consumption API: " + e.getMessage();
        }
    }

    public List<UnitSummaryResponse> getUnitSummary() {
        System.out.println("=== DEBUGGING API CALLS ===");
        
        // Debug raw responses
        System.out.println(debugRawBookingData());
        System.out.println("\n" + debugRawConsumptionData());

        try {
            // Ambil data booking
            ResponseEntity<BookingListResponse[]> bookingResp =
                    restTemplate.getForEntity(bookingListUrl, BookingListResponse[].class);
            
            if (bookingResp.getBody() == null) {
                System.out.println("ERROR: Booking response is null");
                return Collections.emptyList();
            }
            
            List<BookingListResponse> bookings = Arrays.asList(bookingResp.getBody());
            System.out.println("Successfully parsed " + bookings.size() + " bookings");

            // Ambil data master konsumsi
            ResponseEntity<JenisKonsumsiResponse[]> konsumsiResp =
                    restTemplate.getForEntity(konsumsiUrl, JenisKonsumsiResponse[].class);
                    
            if (konsumsiResp.getBody() == null) {
                System.out.println("ERROR: Konsumsi response is null");
                return Collections.emptyList();
            }
            
            List<JenisKonsumsiResponse> masterKonsumsiList = Arrays.asList(konsumsiResp.getBody());
            System.out.println("Successfully parsed " + masterKonsumsiList.size() + " master konsumsi");


            // Grouping per unit
            Map<String, List<RoomSummaryResponse>> unitMap = new LinkedHashMap<>();

            for (BookingListResponse booking : bookings) {

                String unitName = booking.getUnitName();

                Integer participants = booking.getParticipants();

                String getName = booking.getRoomName();
                BigDecimal totalNominal = BigDecimal.ZERO;
                BigDecimal totalNominalPerUnit = BigDecimal.ZERO;

                // Handle null participants
                int participantCount = (participants != null) ? participants : 0;
                
                // Calculate both totals in single loop
                for (JenisKonsumsiResponse konsumsi : masterKonsumsiList) {
                    if (konsumsi.getName() != null && konsumsi.getMaxPrice() != null) {
                        BigDecimal konsumsiTotal = BigDecimal.valueOf(participantCount).multiply(konsumsi.getMaxPrice());
                        totalNominalPerUnit = totalNominalPerUnit.add(konsumsiTotal);
                        totalNominal = totalNominal.add(konsumsiTotal);
                    }
                }

                // Calculate percentage with division by zero protection
                BigDecimal percentageUse = BigDecimal.ZERO;
                if (totalNominalPerUnit.compareTo(BigDecimal.ZERO) > 0) {
                    percentageUse = totalNominal.divide(totalNominalPerUnit, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                }

                RoomSummaryResponse roomSummary = new RoomSummaryResponse
                    (
                        getName,
                        participants,
                        totalNominal,
                        percentageUse,
                        masterKonsumsiList
                    );

                unitMap.computeIfAbsent(unitName, k -> new ArrayList<>()).add(roomSummary);
            }

            // Convert ke response
            return unitMap.entrySet().stream()
                    .map(e -> new UnitSummaryResponse(e.getKey(), e.getValue()))
                    .toList();

        } catch (Exception e) {
            System.out.println("ERROR in getUnitSummary: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

