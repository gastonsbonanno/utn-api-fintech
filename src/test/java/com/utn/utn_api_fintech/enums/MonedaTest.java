package com.utn.utn_api_fintech.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Moneda Enum Tests")
class MonedaTest {

    @Test
    @DisplayName("Should have USD value")
    void testUSDValue() {
        // Assert
        assertThat(Moneda.USD)
                .isNotNull()
                .isEqualTo(Moneda.USD);
    }

    @Test
    @DisplayName("Should have ARS value")
    void testARSValue() {
        // Assert
        assertThat(Moneda.ARS)
                .isNotNull()
                .isEqualTo(Moneda.ARS);
    }

    @Test
    @DisplayName("Should have correct description for USD")
    void testUSDDescription() {
        // Assert
        assertThat(Moneda.USD.getDescripcion()).isEqualTo("Dolar");
    }

    @Test
    @DisplayName("Should have correct description for ARS")
    void testARSDescription() {
        // Assert
        assertThat(Moneda.ARS.getDescripcion()).isEqualTo("Peso Argentino");
    }

    @Test
    @DisplayName("Should return all currency values")
    void testValues() {
        // Act
        Moneda[] currencies = Moneda.values();

        // Assert
        assertThat(currencies).hasSize(2);
        assertThat(currencies).contains(Moneda.USD, Moneda.ARS);
    }

    @Test
    @DisplayName("Should parse USD from string")
    void testValueOfUSD() {
        // Act
        Moneda moneda = Moneda.valueOf("USD");

        // Assert
        assertThat(moneda).isEqualTo(Moneda.USD);
        assertThat(moneda.getDescripcion()).isEqualTo("Dolar");
    }

    @Test
    @DisplayName("Should parse ARS from string")
    void testValueOfARS() {
        // Act
        Moneda moneda = Moneda.valueOf("ARS");

        // Assert
        assertThat(moneda).isEqualTo(Moneda.ARS);
        assertThat(moneda.getDescripcion()).isEqualTo("Peso Argentino");
    }

    @Test
    @DisplayName("Should throw exception for invalid currency")
    void testValueOfInvalid() {
        // Act & Assert
        assertThatThrownBy(() -> Moneda.valueOf("INVALID"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Should compare currencies correctly")
    void testCurrencyComparison() {
        // Assert
        assertThat(Moneda.USD).isNotEqualTo(Moneda.ARS);
        assertThat(Moneda.USD).isEqualTo(Moneda.USD);
        assertThat(Moneda.ARS).isEqualTo(Moneda.ARS);
    }

    @Test
    @DisplayName("Should return correct name for USD")
    void testUSDName() {
        // Assert
        assertThat(Moneda.USD.name()).isEqualTo("USD");
    }

    @Test
    @DisplayName("Should return correct name for ARS")
    void testARSName() {
        // Assert
        assertThat(Moneda.ARS.name()).isEqualTo("ARS");
    }

    @Test
    @DisplayName("Should return correct ordinal values")
    void testOrdinal() {
        // Assert
        assertThat(Moneda.USD.ordinal()).isEqualTo(0);
        assertThat(Moneda.ARS.ordinal()).isEqualTo(1);
    }
}

