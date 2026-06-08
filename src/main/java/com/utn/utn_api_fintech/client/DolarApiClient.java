package com.utn.utn_api_fintech.client;

import com.utn.utn_api_fintech.client.model.DolarOficialModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DolarApiClient {

    @Value("${dolar-client.api-url}") private String url;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(DolarApiClient.class);

    public DolarApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public DolarOficialModel obtenerDolarOficial() {
        logger.debug("Calling external dolar API: {}", url);
        try {
            var res = restTemplate.getForObject(url, DolarOficialModel.class);
            logger.debug("Dolar API response: {}", res);
            return res;
        } catch (Exception e) {
            logger.warn("Error calling dolar API: {}", e.getMessage());
            throw e;
        }
    }

}
