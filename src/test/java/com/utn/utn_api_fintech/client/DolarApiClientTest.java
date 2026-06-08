package com.utn.utn_api_fintech.client;

import com.utn.utn_api_fintech.client.model.DolarOficialModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DolarApiClient Tests")
class DolarApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DolarApiClient dolarApiClient;

    private DolarOficialModel mockResponse;

    @BeforeEach
    void setUp() {
        mockResponse = new DolarOficialModel(
                "Dolar",
                "oficial",
                "Dólar Oficial",
                100.0,
                110.0,
                "2026-06-07"
        );
    }

    @Test
    @DisplayName("Should return dolar oficial data when API call succeeds")
    void testObtenerDolarOficialSuccess() {
        // Arrange
        String url = "https://dolarapi.com/v1/dolares/oficial";
        when(restTemplate.getForObject(anyString(), eq(DolarOficialModel.class)))
                .thenReturn(mockResponse);

        // Use reflection to set the URL since it's private
        try {
            var field = DolarApiClient.class.getDeclaredField("url");
            field.setAccessible(true);
            field.set(dolarApiClient, url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Act
        DolarOficialModel result = dolarApiClient.obtenerDolarOficial();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.moneda()).isEqualTo("Dolar");
        assertThat(result.compra()).isEqualTo(100.0);
        assertThat(result.venta()).isEqualTo(110.0);
        assertThat(result.casa()).isEqualTo("oficial");
        verify(restTemplate, times(1)).getForObject(anyString(), eq(DolarOficialModel.class));
    }

    @Test
    @DisplayName("Should throw exception when API call fails")
    void testObtenerDolarOficialFailure() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(DolarOficialModel.class)))
                .thenThrow(new RestClientException("Connection timeout"));

        // Use reflection to set the URL
        try {
            var field = DolarApiClient.class.getDeclaredField("url");
            field.setAccessible(true);
            field.set(dolarApiClient, "https://invalid-url.com");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Act & Assert
        assertThatThrownBy(() -> dolarApiClient.obtenerDolarOficial())
                .isInstanceOf(RestClientException.class)
                .hasMessageContaining("Connection timeout");

        verify(restTemplate, times(1)).getForObject(anyString(), eq(DolarOficialModel.class));
    }

    @Test
    @DisplayName("Should handle null response from API")
    void testObtenerDolarOficialNullResponse() {
        // Arrange
        when(restTemplate.getForObject(anyString(), eq(DolarOficialModel.class)))
                .thenReturn(null);

        // Use reflection to set the URL
        try {
            var field = DolarApiClient.class.getDeclaredField("url");
            field.setAccessible(true);
            field.set(dolarApiClient, "https://dolarapi.com/v1/dolares/oficial");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Act
        DolarOficialModel result = dolarApiClient.obtenerDolarOficial();

        // Assert
        assertThat(result).isNull();
        verify(restTemplate, times(1)).getForObject(anyString(), eq(DolarOficialModel.class));
    }

    @Test
    @DisplayName("Should correctly parse dolar response with all fields")
    void testObtenerDolarOficialCompleteData() {
        // Arrange
        DolarOficialModel fullResponse = new DolarOficialModel(
                "Dolar",
                "oficial",
                "Dólar Oficial",
                105.50,
                115.75,
                "2026-06-07T10:30:00"
        );
        when(restTemplate.getForObject(anyString(), eq(DolarOficialModel.class)))
                .thenReturn(fullResponse);

        // Use reflection to set the URL
        try {
            var field = DolarApiClient.class.getDeclaredField("url");
            field.setAccessible(true);
            field.set(dolarApiClient, "https://dolarapi.com/v1/dolares/oficial");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Act
        DolarOficialModel result = dolarApiClient.obtenerDolarOficial();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.moneda()).isEqualTo("Dolar");
        assertThat(result.casa()).isEqualTo("oficial");
        assertThat(result.nombre()).isEqualTo("Dólar Oficial");
        assertThat(result.compra()).isEqualTo(105.50);
        assertThat(result.venta()).isEqualTo(115.75);
        assertThat(result.fechaActualizacion()).isEqualTo("2026-06-07T10:30:00");
    }
}

