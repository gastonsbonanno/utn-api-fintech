package com.utn.utn_api_fintech.controller.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AccountDTORequest Tests")
class AccountDTORequestTest {

    @Test
    @DisplayName("Should create AccountDTORequest with all parameters")
    void testAccountDTORequestCreation() {
        // Act
        AccountDTORequest dto = new AccountDTORequest(
                1L,
                "ACC001",
                "ARS",
                1000.0,
                true
        );

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.clientId()).isEqualTo(1L);
        assertThat(dto.numeroCuenta()).isEqualTo("ACC001");
        assertThat(dto.moneda()).isEqualTo("ARS");
        assertThat(dto.saldo()).isEqualTo(1000.0);
        assertThat(dto.activo()).isTrue();
    }

    @Test
    @DisplayName("Should create AccountDTORequest with null values")
    void testAccountDTORequestWithNulls() {
        // Act
        AccountDTORequest dto = new AccountDTORequest(
                1L,
                null,
                null,
                null,
                null
        );

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.clientId()).isEqualTo(1L);
        assertThat(dto.numeroCuenta()).isNull();
        assertThat(dto.moneda()).isNull();
        assertThat(dto.saldo()).isNull();
        assertThat(dto.activo()).isNull();
    }

    @Test
    @DisplayName("Should access all fields of AccountDTORequest")
    void testAccountDTORequestFields() {
        // Act
        AccountDTORequest dto = new AccountDTORequest(5L, "ACC123", "USD", 500.0, false);

        // Assert
        assertThat(dto.clientId()).isEqualTo(5L);
        assertThat(dto.numeroCuenta()).isEqualTo("ACC123");
        assertThat(dto.moneda()).isEqualTo("USD");
        assertThat(dto.saldo()).isEqualTo(500.0);
        assertThat(dto.activo()).isFalse();
    }

    @Test
    @DisplayName("Should handle record equality")
    void testAccountDTORequestEquality() {
        // Arrange
        AccountDTORequest dto1 = new AccountDTORequest(1L, "ACC001", "ARS", 1000.0, true);
        AccountDTORequest dto2 = new AccountDTORequest(1L, "ACC001", "ARS", 1000.0, true);

        // Assert
        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    @DisplayName("Should handle record inequality")
    void testAccountDTORequestInequality() {
        // Arrange
        AccountDTORequest dto1 = new AccountDTORequest(1L, "ACC001", "ARS", 1000.0, true);
        AccountDTORequest dto2 = new AccountDTORequest(2L, "ACC002", "USD", 2000.0, false);

        // Assert
        assertThat(dto1).isNotEqualTo(dto2);
    }

    @Test
    @DisplayName("Should have consistent toString()")
    void testAccountDTORequestToString() {
        // Act
        AccountDTORequest dto = new AccountDTORequest(1L, "ACC001", "ARS", 1000.0, true);

        // Assert
        assertThat(dto.toString()).isNotNull();
        assertThat(dto.toString()).contains("ACC001");
    }
}

