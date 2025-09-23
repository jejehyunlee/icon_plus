package com.icon_plus.logical_test.soal_2.service;

import com.icon_plus.logical_test.soal_2.entity.Booking;
import com.icon_plus.logical_test.soal_2.entity.ConsumptionType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);
    private final RestTemplate restTemplate;

    public List<Booking> getAllBookings() {
        logger.info("Mengambil data dari API Booking");
        String url = "https://66876cc30bc7155dc017a662.mockapi.io/api/dummy-data/bookingList";
        Booking[] bookings = restTemplate.getForObject(url, Booking[].class);
        return Arrays.asList(bookings != null ? bookings : new Booking[0]);
    }

    public List<ConsumptionType> getAllConsumptionTypes() {
        logger.info("Mengambil data dari API ConsumptionType");
        String url = "https://6686cb5583c983911b03a7f3.mockapi.io/api/dummy-data/masterJenisKonsumsi";
        ConsumptionType[] consumptions = restTemplate.getForObject(url, ConsumptionType[].class);
        return Arrays.asList(consumptions != null ? consumptions : new ConsumptionType[0]);
    }

    public static void main(String[] args) {
        ApiService apiService = new ApiService(new RestTemplate());
        List<Booking> bookings = apiService.getAllBookings();
        System.out.println(bookings);

        List<ConsumptionType> consumptionTypes = apiService.getAllConsumptionTypes();
        System.out.println(consumptionTypes);
    }
}