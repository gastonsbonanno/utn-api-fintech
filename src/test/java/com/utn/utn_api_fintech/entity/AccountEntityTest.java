package com.utn.utn_api_fintech.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AccountEntity Tests")
class AccountEntityTest {

    private AccountEntity accountEntity;
    private ClientEntity clientEntity;

    @BeforeEach
    void setUp() {
        clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setNombre("Juan");
        clientEntity.setApellido("Pérez");
        clientEntity.setDocumento("20-12345678-9");

        accountEntity = new AccountEntity();
    }

    @Test
    @DisplayName("Should create AccountEntity with default constructor")
    void testAccountEntityDefaultConstructor() {
        // Assert
        assertThat(accountEntity).isNotNull();
        assertThat(accountEntity.getAccountId()).isNull();
        assertThat(accountEntity.getClientEntity()).isNull();
        assertThat(accountEntity.getNumeroCuenta()).isNull();
        assertThat(accountEntity.getMoneda()).isNull();
        assertThat(accountEntity.getSaldo()).isNull();
        assertThat(accountEntity.getActivo()).isNull();
        assertThat(accountEntity.getFechaCreacion()).isNull();
        assertThat(accountEntity.getFechaModificacion()).isNull();
    }

    @Test
    @DisplayName("Should create AccountEntity with full constructor")
    void testAccountEntityFullConstructor() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        AccountEntity account = new AccountEntity(
                1L,
                clientEntity,
                "ACC001",
                "ARS",
                1000.0,
                true,
                now,
                null
        );

        // Assert
        assertThat(account.getAccountId()).isEqualTo(1L);
        assertThat(account.getClientEntity()).isEqualTo(clientEntity);
        assertThat(account.getNumeroCuenta()).isEqualTo("ACC001");
        assertThat(account.getMoneda()).isEqualTo("ARS");
        assertThat(account.getSaldo()).isEqualTo(1000.0);
        assertThat(account.getActivo()).isTrue();
        assertThat(account.getFechaCreacion()).isEqualTo(now);
        assertThat(account.getFechaModificacion()).isNull();
    }

    @Test
    @DisplayName("Should set and get accountId")
    void testSetGetAccountId() {
        // Act
        accountEntity.setAccountId(5L);

        // Assert
        assertThat(accountEntity.getAccountId()).isEqualTo(5L);
    }

    @Test
    @DisplayName("Should set and get clientEntity")
    void testSetGetClientEntity() {
        // Act
        accountEntity.setClientEntity(clientEntity);

        // Assert
        assertThat(accountEntity.getClientEntity()).isEqualTo(clientEntity);
    }

    @Test
    @DisplayName("Should set and get numeroCuenta")
    void testSetGetNumeroCuenta() {
        // Act
        accountEntity.setNumeroCuenta("ACC12345");

        // Assert
        assertThat(accountEntity.getNumeroCuenta()).isEqualTo("ACC12345");
    }

    @Test
    @DisplayName("Should set and get moneda")
    void testSetGetMoneda() {
        // Act
        accountEntity.setMoneda("USD");

        // Assert
        assertThat(accountEntity.getMoneda()).isEqualTo("USD");
    }

    @Test
    @DisplayName("Should set and get saldo")
    void testSetGetSaldo() {
        // Act
        accountEntity.setSaldo(5000.50);

        // Assert
        assertThat(accountEntity.getSaldo()).isEqualTo(5000.50);
    }

    @Test
    @DisplayName("Should set and get activo")
    void testSetGetActivo() {
        // Act
        accountEntity.setActivo(false);

        // Assert
        assertThat(accountEntity.getActivo()).isFalse();
    }

    @Test
    @DisplayName("Should set and get fechaCreacion")
    void testSetGetFechaCreacion() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        accountEntity.setFechaCreacion(now);

        // Assert
        assertThat(accountEntity.getFechaCreacion()).isEqualTo(now);
    }

    @Test
    @DisplayName("Should set and get fechaModificacion")
    void testSetGetFechaModificacion() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        accountEntity.setFechaModificacion(now);

        // Assert
        assertThat(accountEntity.getFechaModificacion()).isEqualTo(now);
    }

    @Test
    @DisplayName("Should handle zero saldo")
    void testZeroSaldo() {
        // Act
        accountEntity.setSaldo(0.0);

        // Assert
        assertThat(accountEntity.getSaldo()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Should handle negative saldo")
    void testNegativeSaldo() {
        // Act
        accountEntity.setSaldo(-500.0);

        // Assert
        assertThat(accountEntity.getSaldo()).isEqualTo(-500.0);
    }

    @Test
    @DisplayName("Should handle large saldo values")
    void testLargeSaldo() {
        // Act
        accountEntity.setSaldo(999999999.99);

        // Assert
        assertThat(accountEntity.getSaldo()).isEqualTo(999999999.99);
    }
}

