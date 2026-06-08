package com.utn.utn_api_fintech.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("UserEntity Tests")
class UserEntityTest {

    // Since UserEntity is abstract, we'll use a concrete implementation for testing
    private static class ConcreteUserEntity extends UserEntity {
        public ConcreteUserEntity() {
            super();
        }

        public ConcreteUserEntity(Long id, String nombre, String apellido, String documento, String direccion, String telefono) {
            super(id, nombre, apellido, documento, direccion, telefono);
        }
    }

    @Test
    @DisplayName("Should create UserEntity with default constructor")
    void testUserEntityDefaultConstructor() {
        // Act
        ConcreteUserEntity userEntity = new ConcreteUserEntity();

        // Assert
        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getId()).isNull();
        assertThat(userEntity.getNombre()).isNull();
        assertThat(userEntity.getApellido()).isNull();
        assertThat(userEntity.getDocumento()).isNull();
        assertThat(userEntity.getDireccion()).isNull();
        assertThat(userEntity.getTelefono()).isNull();
    }

    @Test
    @DisplayName("Should create UserEntity with full constructor")
    void testUserEntityFullConstructor() {
        // Act
        ConcreteUserEntity userEntity = new ConcreteUserEntity(
                1L,
                "Juan",
                "Pérez",
                "20-12345678-9",
                "Av. Test 123",
                "1123456789"
        );

        // Assert
        assertThat(userEntity.getId()).isEqualTo(1L);
        assertThat(userEntity.getNombre()).isEqualTo("Juan");
        assertThat(userEntity.getApellido()).isEqualTo("Pérez");
        assertThat(userEntity.getDocumento()).isEqualTo("20-12345678-9");
        assertThat(userEntity.getDireccion()).isEqualTo("Av. Test 123");
        assertThat(userEntity.getTelefono()).isEqualTo("1123456789");
    }

    @Test
    @DisplayName("Should set and get id")
    void testSetGetId() {
        // Arrange
        ConcreteUserEntity userEntity = new ConcreteUserEntity();

        // Act
        userEntity.setId(5L);

        // Assert
        assertThat(userEntity.getId()).isEqualTo(5L);
    }

    @Test
    @DisplayName("Should set and get nombre")
    void testSetGetNombre() {
        // Arrange
        ConcreteUserEntity userEntity = new ConcreteUserEntity();

        // Act
        userEntity.setNombre("Carlos");

        // Assert
        assertThat(userEntity.getNombre()).isEqualTo("Carlos");
    }

    @Test
    @DisplayName("Should set and get apellido")
    void testSetGetApellido() {
        // Arrange
        ConcreteUserEntity userEntity = new ConcreteUserEntity();

        // Act
        userEntity.setApellido("García");

        // Assert
        assertThat(userEntity.getApellido()).isEqualTo("García");
    }

    @Test
    @DisplayName("Should set and get documento")
    void testSetGetDocumento() {
        // Arrange
        ConcreteUserEntity userEntity = new ConcreteUserEntity();

        // Act
        userEntity.setDocumento("23-45678901-2");

        // Assert
        assertThat(userEntity.getDocumento()).isEqualTo("23-45678901-2");
    }

    @Test
    @DisplayName("Should set and get direccion")
    void testSetGetDireccion() {
        // Arrange
        ConcreteUserEntity userEntity = new ConcreteUserEntity();

        // Act
        userEntity.setDireccion("Calle Principal 456");

        // Assert
        assertThat(userEntity.getDireccion()).isEqualTo("Calle Principal 456");
    }

    @Test
    @DisplayName("Should set and get telefono")
    void testSetGetTelefono() {
        // Arrange
        ConcreteUserEntity userEntity = new ConcreteUserEntity();

        // Act
        userEntity.setTelefono("1198765432");

        // Assert
        assertThat(userEntity.getTelefono()).isEqualTo("1198765432");
    }

    @Test
    @DisplayName("Should update all properties after initialization")
    void testUpdateAllProperties() {
        // Arrange
        ConcreteUserEntity userEntity = new ConcreteUserEntity();

        // Act - Initial set
        userEntity.setId(1L);
        userEntity.setNombre("Juan");
        userEntity.setApellido("Pérez");
        userEntity.setDocumento("20-12345678-9");
        userEntity.setDireccion("Av. Original 123");
        userEntity.setTelefono("1123456789");

        // Assert initial state
        assertThat(userEntity.getId()).isEqualTo(1L);
        assertThat(userEntity.getNombre()).isEqualTo("Juan");
        assertThat(userEntity.getApellido()).isEqualTo("Pérez");
        assertThat(userEntity.getDocumento()).isEqualTo("20-12345678-9");
        assertThat(userEntity.getDireccion()).isEqualTo("Av. Original 123");
        assertThat(userEntity.getTelefono()).isEqualTo("1123456789");

        // Act - Update properties
        userEntity.setNombre("Carlos");
        userEntity.setApellido("García");
        userEntity.setDireccion("Calle Nueva 456");
        userEntity.setTelefono("1198765432");

        // Assert updated state
        assertThat(userEntity.getId()).isEqualTo(1L); // Should remain same
        assertThat(userEntity.getNombre()).isEqualTo("Carlos");
        assertThat(userEntity.getApellido()).isEqualTo("García");
        assertThat(userEntity.getDocumento()).isEqualTo("20-12345678-9"); // Should remain same
        assertThat(userEntity.getDireccion()).isEqualTo("Calle Nueva 456");
        assertThat(userEntity.getTelefono()).isEqualTo("1198765432");
    }

    @Test
    @DisplayName("Should handle empty strings")
    void testEmptyStrings() {
        // Arrange
        ConcreteUserEntity userEntity = new ConcreteUserEntity();

        // Act
        userEntity.setNombre("");
        userEntity.setApellido("");
        userEntity.setDocumento("");
        userEntity.setDireccion("");
        userEntity.setTelefono("");

        // Assert
        assertThat(userEntity.getNombre()).isEmpty();
        assertThat(userEntity.getApellido()).isEmpty();
        assertThat(userEntity.getDocumento()).isEmpty();
        assertThat(userEntity.getDireccion()).isEmpty();
        assertThat(userEntity.getTelefono()).isEmpty();
    }

    @Test
    @DisplayName("Should handle null values")
    void testNullValues() {
        // Arrange
        ConcreteUserEntity userEntity = new ConcreteUserEntity();
        userEntity.setNombre("Juan");

        // Act
        userEntity.setNombre(null);

        // Assert
        assertThat(userEntity.getNombre()).isNull();
    }

    @Test
    @DisplayName("Should handle special characters in strings")
    void testSpecialCharacters() {
        // Arrange
        ConcreteUserEntity userEntity = new ConcreteUserEntity();

        // Act
        userEntity.setNombre("José");
        userEntity.setApellido("Martínez");
        userEntity.setDireccion("Av. Jossé María Paz 1234");

        // Assert
        assertThat(userEntity.getNombre()).isEqualTo("José");
        assertThat(userEntity.getApellido()).isEqualTo("Martínez");
        assertThat(userEntity.getDireccion()).isEqualTo("Av. Jossé María Paz 1234");
    }

    @Test
    @DisplayName("Should handle long strings")
    void testLongStrings() {
        // Arrange
        ConcreteUserEntity userEntity = new ConcreteUserEntity();
        String longString = "A".repeat(255);

        // Act
        userEntity.setDireccion(longString);

        // Assert
        assertThat(userEntity.getDireccion()).isEqualTo(longString);
        assertThat(userEntity.getDireccion()).hasSize(255);
    }
}


