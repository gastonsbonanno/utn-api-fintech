package com.utn.utn_api_fintech.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ClientEntity Tests")
class ClientEntityTest {

    private ClientEntity clientEntity;

    @BeforeEach
    void setUp() {
        clientEntity = new ClientEntity();
    }

    @Test
    @DisplayName("Should create ClientEntity with default constructor")
    void testClientEntityDefaultConstructor() {
        // Assert
        assertThat(clientEntity).isNotNull();
        assertThat(clientEntity.getId()).isNull();
        assertThat(clientEntity.getNombre()).isNull();
        assertThat(clientEntity.getApellido()).isNull();
        assertThat(clientEntity.getDocumento()).isNull();
        assertThat(clientEntity.getDireccion()).isNull();
        assertThat(clientEntity.getTelefono()).isNull();
        assertThat(clientEntity.getRazonSocial()).isNull();
        assertThat(clientEntity.getCuit()).isNull();
        assertThat(clientEntity.getEmail()).isNull();
        assertThat(clientEntity.getTipoCliente()).isNull();
    }

    @Test
    @DisplayName("Should create ClientEntity with full constructor")
    void testClientEntityFullConstructor() {
        // Act
        ClientEntity client = new ClientEntity(
                1L,
                "Juan",
                "Pérez",
                "Mi Empresa",
                "20-12345678-9",
                "ABC123",
                "Av. Test 123",
                "1123456789",
                "juan@example.com",
                "PERSONA_FISICA",
                true
        );

        // Assert
        assertThat(client.getId()).isEqualTo(1L);
        assertThat(client.getNombre()).isEqualTo("Juan");
        assertThat(client.getApellido()).isEqualTo("Pérez");
        assertThat(client.getRazonSocial()).isEqualTo("Mi Empresa");
        assertThat(client.getDocumento()).isEqualTo("20-12345678-9");
        assertThat(client.getCuit()).isEqualTo("ABC123");
        assertThat(client.getDireccion()).isEqualTo("Av. Test 123");
        assertThat(client.getTelefono()).isEqualTo("1123456789");
        assertThat(client.getEmail()).isEqualTo("juan@example.com");
        assertThat(client.getTipoCliente()).isEqualTo("PERSONA_FISICA");
        assertThat(client.isActivo()).isTrue();
    }

    @Test
    @DisplayName("Should inherit id from UserEntity")
    void testInheritIdFromUserEntity() {
        // Act
        clientEntity.setId(10L);

        // Assert
        assertThat(clientEntity.getId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("Should inherit nombre from UserEntity")
    void testInheritNombreFromUserEntity() {
        // Act
        clientEntity.setNombre("Carlos");

        // Assert
        assertThat(clientEntity.getNombre()).isEqualTo("Carlos");
    }

    @Test
    @DisplayName("Should inherit apellido from UserEntity")
    void testInheritApellidoFromUserEntity() {
        // Act
        clientEntity.setApellido("García");

        // Assert
        assertThat(clientEntity.getApellido()).isEqualTo("García");
    }

    @Test
    @DisplayName("Should inherit documento from UserEntity")
    void testInheritDocumentoFromUserEntity() {
        // Act
        clientEntity.setDocumento("20-12345678-9");

        // Assert
        assertThat(clientEntity.getDocumento()).isEqualTo("20-12345678-9");
    }

    @Test
    @DisplayName("Should inherit direccion from UserEntity")
    void testInheritDireccionFromUserEntity() {
        // Act
        clientEntity.setDireccion("Calle Principal 456");

        // Assert
        assertThat(clientEntity.getDireccion()).isEqualTo("Calle Principal 456");
    }

    @Test
    @DisplayName("Should inherit telefono from UserEntity")
    void testInheritTelefonoFromUserEntity() {
        // Act
        clientEntity.setTelefono("1198765432");

        // Assert
        assertThat(clientEntity.getTelefono()).isEqualTo("1198765432");
    }

    @Test
    @DisplayName("Should set and get razonSocial")
    void testSetGetRazonSocial() {
        // Act
        clientEntity.setRazonSocial("MiEmpresa S.A.");

        // Assert
        assertThat(clientEntity.getRazonSocial()).isEqualTo("MiEmpresa S.A.");
    }

    @Test
    @DisplayName("Should set and get cuit")
    void testSetGetCuit() {
        // Act
        clientEntity.setCuit("20-12345678-9");

        // Assert
        assertThat(clientEntity.getCuit()).isEqualTo("20-12345678-9");
    }

    @Test
    @DisplayName("Should set and get email")
    void testSetGetEmail() {
        // Act
        clientEntity.setEmail("cliente@empresa.com");

        // Assert
        assertThat(clientEntity.getEmail()).isEqualTo("cliente@empresa.com");
    }

    @Test
    @DisplayName("Should set and get tipoCliente")
    void testSetGetTipoCliente() {
        // Act
        clientEntity.setTipoCliente("PERSONA_JURIDICA");

        // Assert
        assertThat(clientEntity.getTipoCliente()).isEqualTo("PERSONA_JURIDICA");
    }

    @Test
    @DisplayName("Should set and get activo")
    void testSetGetActivo() {
        // Act
        clientEntity.setActivo(false);

        // Assert
        assertThat(clientEntity.isActivo()).isFalse();
    }

    @Test
    @DisplayName("Should support all inherited and own properties together")
    void testAllPropertiesTogether() {
        // Arrange
        ClientEntity client = new ClientEntity();

        // Act - Set all inherited properties
        client.setId(5L);
        client.setNombre("María");
        client.setApellido("López");
        client.setDocumento("20-98765432-1");
        client.setDireccion("Av. Corrientes 1234");
        client.setTelefono("1145678900");

        // Act - Set own properties
        client.setRazonSocial("María López S.A.");
        client.setCuit("20-98765432-1");
        client.setEmail("maria@example.com");
        client.setTipoCliente("EMPRESA");
        client.setActivo(true);

        // Assert
        assertThat(client.getId()).isEqualTo(5L);
        assertThat(client.getNombre()).isEqualTo("María");
        assertThat(client.getApellido()).isEqualTo("López");
        assertThat(client.getDocumento()).isEqualTo("20-98765432-1");
        assertThat(client.getDireccion()).isEqualTo("Av. Corrientes 1234");
        assertThat(client.getTelefono()).isEqualTo("1145678900");
        assertThat(client.getRazonSocial()).isEqualTo("María López S.A.");
        assertThat(client.getCuit()).isEqualTo("20-98765432-1");
        assertThat(client.getEmail()).isEqualTo("maria@example.com");
        assertThat(client.getTipoCliente()).isEqualTo("EMPRESA");
        assertThat(client.isActivo()).isTrue();
    }

    @Test
    @DisplayName("Should handle empty razonSocial")
    void testEmptyRazonSocial() {
        // Act
        clientEntity.setRazonSocial("");

        // Assert
        assertThat(clientEntity.getRazonSocial()).isEmpty();
    }
}

