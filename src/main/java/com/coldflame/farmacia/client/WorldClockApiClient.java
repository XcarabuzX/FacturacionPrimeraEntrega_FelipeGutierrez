package com.coldflame.farmacia.client;

import lombok.Data;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class WorldClockApiClient {

    private static final String API_URL = "http://worldclockapi.com/api/json/utc/now";
    private static final int TIMEOUT = 3000;

    public String obtenerFechaActual() {
        try {
            RestTemplate restTemplate = new RestTemplate();

            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(TIMEOUT);
            factory.setReadTimeout(TIMEOUT);
            restTemplate.setRequestFactory(factory);

            WorldClockResponse response = restTemplate.getForObject(API_URL, WorldClockResponse.class);

            if (response != null && response.getCurrentDateTime() != null) {
                return response.getCurrentDateTime();
            }

            return obtenerFechaFallback();

        } catch (Exception e) {
            System.err.println("Error al obtener fecha de API externa: " + e.getMessage());
            return obtenerFechaFallback();
        }
    }

    private String obtenerFechaFallback() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }

    @Data
    private static class WorldClockResponse {
        private String currentDateTime;
    }
}
