package com.icon_plus.logical_test.soal_2.service;

import com.icon_plus.logical_test.soal_2.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
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

    public List<UnitSummaryResponse> getUnitSummary(Integer month, Integer year) {
        System.out.println("=== DEBUGGING API CALLS ===");
        
        // Debug raw responses
        System.out.println(debugRawBookingData());
        System.out.println("\n" + debugRawConsumptionData());
        
        System.out.println("Filtering for month: " + month + ", year: " + year);

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

            BigDecimal totalNominal = BigDecimal.ZERO; // total global semua booking

            for (BookingListResponse booking : bookings) {
                // Parse booking date
                String[] dateParts = booking.getBookingDate().split("-");
                if (dateParts.length < 3) continue; // Skip invalid dates
                
                int bookingYear = Integer.parseInt(dateParts[0]);
                int bookingMonth = Integer.parseInt(dateParts[1]);
                
                // Apply month and year filter if provided
                if ((month != null && bookingMonth != month) || 
                    (year != null && bookingYear != year)) {
                    continue;
                }
                String unitName = booking.getUnitName();
                Integer participants = booking.getParticipants();
                String roomName = booking.getRoomName();

                String date = booking.getBookingDate();

                BigDecimal totalNominalPerUnit = BigDecimal.ZERO;

                // Handle null participants
                int participantCount = (participants != null) ? participants : 0;

                // Hitung total per unit
                for (JenisKonsumsiResponse konsumsi : masterKonsumsiList) {
                    if (konsumsi != null
                            && konsumsi.getName() != null
                            && konsumsi.getMaxPrice() != null) {

                        BigDecimal konsumsiTotal = BigDecimal.valueOf(participantCount)
                                .multiply(konsumsi.getMaxPrice());

                        totalNominalPerUnit = totalNominalPerUnit.add(konsumsiTotal);
                    }
                }

                // Tambahkan ke total global
                totalNominal = totalNominal.add(totalNominalPerUnit);

                // Hitung persentase (safe division)
                BigDecimal percentageUse = BigDecimal.ZERO;
                if (totalNominalPerUnit.compareTo(BigDecimal.ZERO) > 0) {
                    percentageUse = totalNominalPerUnit
                            .divide(totalNominal, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100));
                }

                System.out.printf("Unit: %s, Room: %s, Total per Unit: %s, Persentase: %s%%%n",
                        unitName, roomName, totalNominalPerUnit, percentageUse);


            System.out.println("Total keseluruhan: " + totalNominal);


            RoomSummaryResponse roomSummary = new RoomSummaryResponse
                    (
                        date,
                        roomName,
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

