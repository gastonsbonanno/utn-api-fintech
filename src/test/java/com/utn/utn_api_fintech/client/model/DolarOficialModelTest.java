package com.utn.utn_api_fintech.client.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("DolarOficialModel Tests")
class DolarOficialModelTest {

    @Test
    @DisplayName("Should create DolarOficialModel with all parameters")
    void testDolarOficialModelCreation() {
        // Act
        DolarOficialModel model = new DolarOficialModel(
                "Dolar",
                "oficial",
                "Dólar Oficial",
                100.0,
                110.0,
                "2026-06-07"
        );

        // Assert
        assertThat(model).isNotNull();
        assertThat(model.moneda()).isEqualTo("Dolar");
        assertThat(model.casa()).isEqualTo("oficial");
        assertThat(model.nombre()).isEqualTo("Dólar Oficial");
        assertThat(model.compra()).isEqualTo(100.0);
        assertThat(model.venta()).isEqualTo(110.0);
        assertThat(model.fechaActualizacion()).isEqualTo("2026-06-07");
    }

    @Test
    @DisplayName("Should create DolarOficialModel with null values")
    void testDolarOficialModelWithNulls() {
        // Act
        DolarOficialModel model = new DolarOficialModel(
                null,
                null,
                null,
                null,
                null,
                null
        );

        // Assert
        assertThat(model).isNotNull();
        assertThat(model.moneda()).isNull();
        assertThat(model.casa()).isNull();
        assertThat(model.nombre()).isNull();
        assertThat(model.compra()).isNull();
        assertThat(model.venta()).isNull();
        assertThat(model.fechaActualizacion()).isNull();
    }

    @Test
    @DisplayName("Should access all fields of DolarOficialModel")
    void testDolarOficialModelFields() {
        // Act
        DolarOficialModel model = new DolarOficialModel(
                "Dolar",
                "oficial",
                "Dólar Oficial",
                105.50,
                115.75,
                "2026-06-07T10:30:00"
        );

        // Assert
        assertThat(model.moneda()).isEqualTo("Dolar");
        assertThat(model.casa()).isEqualTo("oficial");
        assertThat(model.nombre()).isEqualTo("Dólar Oficial");
        assertThat(model.compra()).isEqualTo(105.50);
        assertThat(model.venta()).isEqualTo(115.75);
        assertThat(model.fechaActualizacion()).isEqualTo("2026-06-07T10:30:00");
    }

    @Test
    @DisplayName("Should handle large decimal values")
    void testLargeDecimalValues() {
        // Act
        DolarOficialModel model = new DolarOficialModel(
                "Dolar",
                "oficial",
                "Dólar",
                999999.99,
                1000000.00,
                "2026-06-07"
        );

        // Assert
        assertThat(model.compra()).isEqualTo(999999.99);
        assertThat(model.venta()).isEqualTo(1000000.00);
    }

    @Test
    @DisplayName("Should handle zero values")
    void testZeroValues() {
        // Act
        DolarOficialModel model = new DolarOficialModel(
                "Dolar",
                "oficial",
                "Dólar",
                0.0,
                0.0,
                "2026-06-07"
        );

        // Assert
        assertThat(model.compra()).isEqualTo(0.0);
        assertThat(model.venta()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Should handle negative values")
    void testNegativeValues() {
        // Act
        DolarOficialModel model = new DolarOficialModel(
                "Dolar",
                "oficial",
                "Dólar",
                -100.0,
                -110.0,
                "2026-06-07"
        );

        // Assert
        assertThat(model.compra()).isEqualTo(-100.0);
        assertThat(model.venta()).isEqualTo(-110.0);
    }

    @Test
    @DisplayName("Should handle record equality")
    void testDolarOficialModelEquality() {
        // Arrange
        DolarOficialModel model1 = new DolarOficialModel("Dolar", "oficial", "Dólar Oficial", 100.0, 110.0, "2026-06-07");
        DolarOficialModel model2 = new DolarOficialModel("Dolar", "oficial", "Dólar Oficial", 100.0, 110.0, "2026-06-07");

        // Assert
        assertThat(model1).isEqualTo(model2);
    }

    @Test
    @DisplayName("Should handle record inequality")
    void testDolarOficialModelInequality() {
        // Arrange
        DolarOficialModel model1 = new DolarOficialModel("Dolar", "oficial", "Dólar Oficial", 100.0, 110.0, "2026-06-07");
        DolarOficialModel model2 = new DolarOficialModel("Dolar", "oficial", "Dólar Oficial", 105.0, 115.0, "2026-06-07");

        // Assert
        assertThat(model1).isNotEqualTo(model2);
    }

    @Test
    @DisplayName("Should have consistent toString()")
    void testDolarOficialModelToString() {
        // Act
        DolarOficialModel model = new DolarOficialModel("Dolar", "oficial", "Dólar Oficial", 100.0, 110.0, "2026-06-07");

        // Assert
        assertThat(model.toString()).isNotNull();
        assertThat(model.toString()).contains("Dolar");
    }

    @Test
    @DisplayName("Should calculate spread between compra and venta")
    void testSpreadCalculation() {
        // Arrange
        DolarOficialModel model = new DolarOficialModel("Dolar", "oficial", "Dólar Oficial", 100.0, 110.0, "2026-06-07");

        // Act
        double spread = model.venta() - model.compra();

        // Assert
        assertThat(spread).isEqualTo(10.0);
    }

    @Test
    @DisplayName("Should handle same values for compra and venta")
    void testSameCompraVentaValues() {
        // Act
        DolarOficialModel model = new DolarOficialModel("Dolar", "oficial", "Dólar Oficial", 100.0, 100.0, "2026-06-07");

        // Assert
        assertThat(model.compra()).isEqualTo(model.venta());
        assertThat(model.compra()).isEqualTo(100.0);
    }
}

