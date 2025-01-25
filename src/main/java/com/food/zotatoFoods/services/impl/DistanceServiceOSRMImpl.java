package com.food.zotatoFoods.services.impl;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.food.zotatoFoods.services.DistanceService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_API_BASE_URL = "https://router.project-osrm.org/route/v1/driving/";

    private final RestTemplate restTemplate; // Use RestTemplate for making requests

    @Override
    public double CalculateDistance(Point src, Point dest) {
        try {
            String uri = UriComponentsBuilder.fromHttpUrl(OSRM_API_BASE_URL)
                    .path(src.getX() + "," + src.getY() + ";" + dest.getX() + "," + dest.getY())
                    .build()
                    .toUriString();

            log.info("OSRM API Request URI: {}", uri);

            // Fetch the response using RestTemplate and log it for debugging
            String rawJsonResponse = restTemplate.getForObject(uri, String.class);
            log.info("Raw OSRM Response: {}", rawJsonResponse);

            // Deserialize the JSON response into the DTO
            OSRMResponseDto responseDto = restTemplate.getForObject(uri, OSRMResponseDto.class);

            if (responseDto == null || responseDto.getRoutes().isEmpty()) {
                throw new RuntimeException("No routes found in OSRM response.");
            }

            return responseDto.getRoutes().get(0).getDistance() / 1000.0; // Convert meters to kilometers
        } catch (Exception e) {
            throw new RuntimeException("Error getting data from OSRM: " + e.getMessage(), e);
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true) // Ignore unexpected fields
    public static class OSRMResponseDto {
        private List<OSRMRoute> routes; // List of routes
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OSRMRoute {
        private Double distance; // Distance in meters
    }
}
