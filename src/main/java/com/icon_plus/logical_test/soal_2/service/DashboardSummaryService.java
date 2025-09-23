package com.icon_plus.logical_test.soal_2.service;

import com.icon_plus.logical_test.soal_2.entity.Booking;
import com.icon_plus.logical_test.soal_2.entity.Consumption;
import com.icon_plus.logical_test.soal_2.entity.ConsumptionType;
import com.icon_plus.logical_test.soal_2.model.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardSummaryService {

    private final ApiService externalApiService;

    private static final Logger logger = LoggerFactory.getLogger(DashboardSummaryService.class);


    public DashboardSumaryResponse getDashboardSummary() {
        try {
            // Fetch data from external APIs
            logger.info("Processing fetching data from external APIs...");
            List<Booking> bookings = externalApiService.getAllBookings();
            List<ConsumptionType> consumptionTypes = externalApiService.getAllConsumptionTypes();

            // Filter valid bookings (with consumption data)
            List<Booking> validBookings = bookings.stream()
                    .filter(booking -> booking != null &&
                            booking.getListConsumption() != null &&
                            !booking.getListConsumption().isEmpty())
                    .collect(Collectors.toList());

            DashboardSumaryResponse response = new DashboardSumaryResponse();

            // Set period (current month)
            logger.info("Processing setting period to current month...");
            response.setPeriod(getCurrentMonthPeriod());

            // Calculate statistics
            logger.info("Processing Calculating statistics...");
            response.setStatistics(calculateStatistics(validBookings, consumptionTypes));

            // Calculate room utilizations
            logger.info("Processing Calculating room utilizations...");
            response.setRoomUtilizations(calculateRoomUtilizations(validBookings, consumptionTypes));

            // Calculate consumption summary
            logger.info("Processing Calculating consumption summary...");
            response.setConsumptionSummary(calculateConsumptionSummary(validBookings, consumptionTypes));

            // Calculate office activity
            logger.info("Processing Calculating office activity...");
            response.setOfficeActivity(calculateOfficeActivity(validBookings));

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate dashboard summary: " + e.getMessage());
        }
    }

    private String getCurrentMonthPeriod() {
//        LocalDate now = LocalDate.now();
//        return now.format(DateTimeFormatter.ofPattern("MMMM yyyy"));

        LocalDate januari2024 = LocalDate.of(2024, 1, 1);
        return januari2024.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
    }

    private SummaryStatistics calculateStatistics(List<Booking> bookings, List<ConsumptionType> consumptionTypes) {
        SummaryStatistics stats = new SummaryStatistics();

        stats.setTotalBookings(bookings.size());
        stats.setTotalParticipants(bookings.stream()
                .mapToInt(booking -> booking.getParticipants() != null ? booking.getParticipants() : 0)
                .sum());

        // Simplified utilization calculation
        stats.setAverageUtilization(calculateAverageUtilization(bookings));
        stats.setTotalConsumptionCost(calculateTotalConsumptionCost(bookings, consumptionTypes));

        return stats;
    }

    private List<RoomUtilization> calculateRoomUtilizations(List<Booking> bookings, List<ConsumptionType> consumptionTypes) {
        Map<String, List<Booking>> bookingsByRoom = bookings.stream()
                .filter(booking -> booking.getRoomName() != null)
                .collect(Collectors.groupingBy(Booking::getRoomName));

        return bookingsByRoom.entrySet().stream()
                .map(entry -> {
                    RoomUtilization roomUtil = new RoomUtilization();
                    roomUtil.setRoomName(entry.getKey());
                    roomUtil.setBookingCount(entry.getValue().size());

                    // Utilization rate based on booking count
                    roomUtil.setUtilizationRate(calculateRoomUtilizationRate(entry.getValue()));
                    roomUtil.setTotalConsumption(calculateRoomConsumption(entry.getValue(), consumptionTypes));

                    return roomUtil;
                })
                .sorted((r1, r2) -> Integer.compare(r2.getBookingCount(), r1.getBookingCount()))
                .collect(Collectors.toList());
    }

    private ConsumptionSummary calculateConsumptionSummary(List<Booking> bookings, List<ConsumptionType> consumptionTypes) {
        ConsumptionSummary summary = new ConsumptionSummary();
        Map<String, Integer> priceMap = createPriceMap(consumptionTypes);

        for (Booking booking : bookings) {
            if (booking.getListConsumption() != null && booking.getParticipants() != null) {
                int participants = booking.getParticipants();

                for (Consumption consumption : booking.getListConsumption()) {
                    if (consumption.getName() != null) {
                        Integer price = priceMap.get(consumption.getName());
                        if (price != null) {
                            long cost = participants * price;

                            switch (consumption.getName().toLowerCase()) {
                                case "snack pagi":
                                    summary.setSnackPagi(summary.getSnackPagi() + cost);
                                    break;
                                case "makan siang":
                                    summary.setMakanSiang(summary.getMakanSiang() + cost);
                                    break;
                                case  "snack siang":
                                    summary.setSnacksiang(summary.getSnacksiang() + cost);
                                    break;
                                case "snack sore":
                                    summary.setSnackSore(summary.getSnackSore() + cost);
                                    break;
                                default:
                                    summary.setOtherConsumptions(summary.getOtherConsumptions() + cost);
                                    break;
                            }
                        }
                    }
                }
            }
        }

        return summary;
    }

    private OfficeActivity calculateOfficeActivity(List<Booking> bookings) {
        OfficeActivity activity = new OfficeActivity();

        // Most active office
        Map<String, Long> officeCounts = bookings.stream()
                .filter(booking -> booking.getOfficeName() != null)
                .collect(Collectors.groupingBy(Booking::getOfficeName, Collectors.counting()));

        Optional<Map.Entry<String, Long>> mostActiveOffice = officeCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        if (mostActiveOffice.isPresent()) {
            activity.setMostActiveOffice(mostActiveOffice.get().getKey());
            activity.setOfficeBookingCount(mostActiveOffice.get().getValue().intValue());
        }

        // Most used room
        Map<String, Long> roomCounts = bookings.stream()
                .filter(booking -> booking.getRoomName() != null)
                .collect(Collectors.groupingBy(Booking::getRoomName, Collectors.counting()));

        Optional<Map.Entry<String, Long>> mostUsedRoom = roomCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        if (mostUsedRoom.isPresent()) {
            activity.setMostUsedRoom(mostUsedRoom.get().getKey());
            activity.setRoomUsageCount(mostUsedRoom.get().getValue().intValue());
        }

        return activity;
    }

    private Map<String, Integer> createPriceMap(List<ConsumptionType> consumptionTypes) {
        return consumptionTypes.stream()
                .filter(type -> type.getName() != null && type.getMaxPrice() != null)
                .collect(Collectors.toMap(
                        ConsumptionType::getName,
                        ConsumptionType::getMaxPrice
                ));
    }

    private double calculateAverageUtilization(List<Booking> bookings) {
        if (bookings.isEmpty()) return 0.0;

        // Simplified: assume each booking contributes to utilization
        return Math.min((bookings.size() / 30.0) * 100, 100.0);
    }

    private long calculateTotalConsumptionCost(List<Booking> bookings, List<ConsumptionType> consumptionTypes) {
        Map<String, Integer> priceMap = createPriceMap(consumptionTypes);
        long totalCost = 0L;

        for (Booking booking : bookings) {
            if (booking.getListConsumption() != null && booking.getParticipants() != null) {
                for (Consumption consumption : booking.getListConsumption()) {
                    if (consumption.getName() != null) {
                        Integer price = priceMap.get(consumption.getName());
                        if (price != null) {
                            totalCost += booking.getParticipants() * price;
                        }
                    }
                }
            }
        }

        return totalCost;
    }

    private double calculateRoomUtilizationRate(List<Booking> roomBookings) {
        // Simplified: based on number of bookings
        return Math.min((roomBookings.size() / 10.0) * 100, 100.0);
    }

    private long calculateRoomConsumption(List<Booking> roomBookings, List<ConsumptionType> consumptionTypes) {
        Map<String, Integer> priceMap = createPriceMap(consumptionTypes);
        long totalCost = 0L;

        for (Booking booking : roomBookings) {
            if (booking.getListConsumption() != null && booking.getParticipants() != null) {
                for (Consumption consumption : booking.getListConsumption()) {
                    if (consumption.getName() != null) {
                        Integer price = priceMap.get(consumption.getName());
                        if (price != null) {
                            totalCost += booking.getParticipants() * price;
                        }
                    }
                }
            }
        }

        return totalCost;
    }
}
