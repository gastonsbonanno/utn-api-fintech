package com.utn.utn_api_fintech.repository;

import com.utn.utn_api_fintech.entity.ClientEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("ClientRepository Integration Tests")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Should save and find client")
    void testSaveAndFindClient() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setNombre("Juan");
        client.setApellido("Pérez");
        client.setDocumento("20-12345678-9");
        client.setDireccion("Av. Test 123");
        client.setTelefono("1123456789");
        client.setActivo(true);

        // Act
        ClientEntity savedClient = clientRepository.save(client);

        // Assert
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getNombre()).isEqualTo("Juan");
        assertThat(savedClient.getApellido()).isEqualTo("Pérez");
    }

    @Test
    @DisplayName("Should find client by id")
    void testFindClientById() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setNombre("María");
        client.setApellido("García");
        client.setDocumento("23-45678901-2");
        client.setDireccion("Calle Principal");
        client.setTelefono("1198765432");
        client.setActivo(true);
        
        ClientEntity savedClient = clientRepository.save(client);

        // Act
        Optional<ClientEntity> foundClient = clientRepository.findById(savedClient.getId());

        // Assert
        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getNombre()).isEqualTo("María");
    }

    @Test
    @DisplayName("Should return empty when client not found")
    void testFindClientByIdNotFound() {
        // Act
        Optional<ClientEntity> foundClient = clientRepository.findById(99999L);

        // Assert
        assertThat(foundClient).isEmpty();
    }

    @Test
    @DisplayName("Should find all clients")
    void testFindAllClients() {
        // Arrange
        ClientEntity client1 = new ClientEntity();
        client1.setNombre("Client1");
        client1.setApellido("Test");
        client1.setDocumento("20-11111111-1");
        client1.setActivo(true);
        
        ClientEntity client2 = new ClientEntity();
        client2.setNombre("Client2");
        client2.setApellido("Test");
        client2.setDocumento("20-22222222-2");
        client2.setActivo(true);
        
        clientRepository.save(client1);
        clientRepository.save(client2);

        // Act
        List<ClientEntity> allClients = clientRepository.findAll();

        // Assert
        assertThat(allClients).isNotEmpty();
    }

    @Test
    @DisplayName("Should update client")
    void testUpdateClient() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setNombre("Original");
        client.setApellido("Name");
        client.setDocumento("20-12345678-9");
        client.setActivo(true);
        
        ClientEntity savedClient = clientRepository.save(client);

        // Act
        savedClient.setNombre("Updated");
        ClientEntity updatedClient = clientRepository.save(savedClient);

        // Assert
        assertThat(updatedClient.getNombre()).isEqualTo("Updated");
    }

    @Test
    @DisplayName("Should delete client")
    void testDeleteClient() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setNombre("ToDelete");
        client.setApellido("Client");
        client.setDocumento("20-12345678-9");
        client.setActivo(true);
        
        ClientEntity savedClient = clientRepository.save(client);
        Long clientId = savedClient.getId();

        // Act
        clientRepository.deleteById(clientId);

        // Assert
        Optional<ClientEntity> deletedClient = clientRepository.findById(clientId);
        assertThat(deletedClient).isEmpty();
    }

    @Test
    @DisplayName("Should save and retrieve client with all properties")
    void testClientWithAllProperties() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setNombre("Juan");
        client.setApellido("Pérez");
        client.setDocumento("20-12345678-9");
        client.setDireccion("Av. Test 123");
        client.setTelefono("1123456789");
        client.setRazonSocial("Mi Empresa");
        client.setCuit("20-12345678-9");
        client.setEmail("juan@example.com");
        client.setTipoCliente("PERSONA_FISICA");
        client.setActivo(true);

        // Act
        ClientEntity savedClient = clientRepository.save(client);
        Optional<ClientEntity> foundClient = clientRepository.findById(savedClient.getId());

        // Assert
        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getRazonSocial()).isEqualTo("Mi Empresa");
        assertThat(foundClient.get().getEmail()).isEqualTo("juan@example.com");
    }

    @Test
    @DisplayName("Should save inactive client")
    void testInactiveClient() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setNombre("Inactive");
        client.setApellido("Client");
        client.setDocumento("20-12345678-9");
        client.setActivo(false);

        // Act
        ClientEntity savedClient = clientRepository.save(client);
        Optional<ClientEntity> foundClient = clientRepository.findById(savedClient.getId());

        // Assert
        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().isActivo()).isFalse();
    }
}


