package com.utn.utn_api_fintech.controller.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AccountDTOResponse Tests")
class AccountDTOResponseTest {

    @Test
    @DisplayName("Should create AccountDTOResponse with all parameters")
    void testAccountDTOResponseCreation() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        AccountDTOResponse dto = new AccountDTOResponse(
                1L,
                1L,
                "ACC001",
                "ARS",
                1000.0,
                1000.0,
                true,
                now,
                null
        );

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.accountId()).isEqualTo(1L);
        assertThat(dto.clientId()).isEqualTo(1L);
        assertThat(dto.numeroCuenta()).isEqualTo("ACC001");
        assertThat(dto.moneda()).isEqualTo("ARS");
        assertThat(dto.saldo()).isEqualTo(1000.0);
        assertThat(dto.saldoEnPesos()).isEqualTo(1000.0);
        assertThat(dto.activo()).isTrue();
        assertThat(dto.fechaCreacion()).isEqualTo(now);
        assertThat(dto.fechaModificacion()).isNull();
    }

    @Test
    @DisplayName("Should create AccountDTOResponse with null values")
    void testAccountDTOResponseWithNulls() {
        // Act
        AccountDTOResponse dto = new AccountDTOResponse(
                1L,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.accountId()).isEqualTo(1L);
        assertThat(dto.clientId()).isNull();
        assertThat(dto.numeroCuenta()).isNull();
        assertThat(dto.moneda()).isNull();
        assertThat(dto.saldo()).isNull();
        assertThat(dto.saldoEnPesos()).isNull();
        assertThat(dto.activo()).isNull();
        assertThat(dto.fechaCreacion()).isNull();
        assertThat(dto.fechaModificacion()).isNull();
    }

    @Test
    @DisplayName("Should access all fields of AccountDTOResponse")
    void testAccountDTOResponseFields() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 6, 7, 10, 30, 0);
        LocalDateTime modifiedAt = LocalDateTime.of(2026, 6, 7, 15, 45, 0);

        // Act
        AccountDTOResponse dto = new AccountDTOResponse(
                5L,
                2L,
                "ACC123",
                "USD",
                500.0,
                55000.0,
                true,
                createdAt,
                modifiedAt
        );

        // Assert
        assertThat(dto.accountId()).isEqualTo(5L);
        assertThat(dto.clientId()).isEqualTo(2L);
        assertThat(dto.numeroCuenta()).isEqualTo("ACC123");
        assertThat(dto.moneda()).isEqualTo("USD");
        assertThat(dto.saldo()).isEqualTo(500.0);
        assertThat(dto.saldoEnPesos()).isEqualTo(55000.0);
        assertThat(dto.activo()).isTrue();
        assertThat(dto.fechaCreacion()).isEqualTo(createdAt);
        assertThat(dto.fechaModificacion()).isEqualTo(modifiedAt);
    }

    @Test
    @DisplayName("Should calculate correct saldoEnPesos for USD account")
    void testSaldoEnPesosCalculation() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        AccountDTOResponse dto = new AccountDTOResponse(
                1L,
                1L,
                "ACC001",
                "USD",
                100.0,
                11000.0, // 100 * 110 exchange rate
                true,
                now,
                null
        );

        // Assert
        assertThat(dto.saldo()).isEqualTo(100.0);
        assertThat(dto.saldoEnPesos()).isEqualTo(11000.0);
    }

    @Test
    @DisplayName("Should handle record equality")
    void testAccountDTOResponseEquality() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        AccountDTOResponse dto1 = new AccountDTOResponse(1L, 1L, "ACC001", "ARS", 1000.0, 1000.0, true, now, null);
        AccountDTOResponse dto2 = new AccountDTOResponse(1L, 1L, "ACC001", "ARS", 1000.0, 1000.0, true, now, null);

        // Assert
        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    @DisplayName("Should handle record inequality")
    void testAccountDTOResponseInequality() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        AccountDTOResponse dto1 = new AccountDTOResponse(1L, 1L, "ACC001", "ARS", 1000.0, 1000.0, true, now, null);
        AccountDTOResponse dto2 = new AccountDTOResponse(2L, 2L, "ACC002", "USD", 2000.0, 2000.0, false, now, null);

        // Assert
        assertThat(dto1).isNotEqualTo(dto2);
    }

    @Test
    @DisplayName("Should have consistent toString()")
    void testAccountDTOResponseToString() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        AccountDTOResponse dto = new AccountDTOResponse(1L, 1L, "ACC001", "ARS", 1000.0, 1000.0, true, now, null);

        // Assert
        assertThat(dto.toString()).isNotNull();
        assertThat(dto.toString()).contains("ACC001");
    }

    @Test
    @DisplayName("Should handle different dates for fechaCreacion and fechaModificacion")
    void testDifferentDates() {
        // Arrange
        LocalDateTime createdAt = LocalDateTime.of(2026, 6, 1, 10, 0, 0);
        LocalDateTime modifiedAt = LocalDateTime.of(2026, 6, 7, 15, 0, 0);

        // Act
        AccountDTOResponse dto = new AccountDTOResponse(
                1L,
                1L,
                "ACC001",
                "ARS",
                1000.0,
                1000.0,
                true,
                createdAt,
                modifiedAt
        );

        // Assert
        assertThat(dto.fechaCreacion()).isBefore(dto.fechaModificacion());
    }

    @Test
    @DisplayName("Should handle inactive account")
    void testInactiveAccount() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        AccountDTOResponse dto = new AccountDTOResponse(
                1L,
                1L,
                "ACC001",
                "ARS",
                1000.0,
                1000.0,
                false,
                now,
                null
        );

        // Assert
        assertThat(dto.activo()).isFalse();
    }
}

