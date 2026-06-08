package com.utn.utn_api_fintech.service;

import com.utn.utn_api_fintech.client.DolarApiClient;
import com.utn.utn_api_fintech.client.model.DolarOficialModel;
import com.utn.utn_api_fintech.controller.dto.AccountDTORequest;
import com.utn.utn_api_fintech.controller.dto.AccountDTOResponse;
import com.utn.utn_api_fintech.entity.AccountEntity;
import com.utn.utn_api_fintech.entity.ClientEntity;
import com.utn.utn_api_fintech.enums.Moneda;
import com.utn.utn_api_fintech.repository.AccountRepository;
import com.utn.utn_api_fintech.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccountService Tests")
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DolarApiClient dolarApiClient;

    @InjectMocks
    private AccountService accountService;

    private ClientEntity testClient;
    private AccountEntity testAccount;
    private DolarOficialModel testDolarRate;

    @BeforeEach
    void setUp() {
        testClient = new ClientEntity();
        testClient.setId(1L);
        testClient.setNombre("Juan");
        testClient.setApellido("Pérez");
        testClient.setDocumento("20-12345678-9");
        testClient.setDireccion("Av. Test 123");
        testClient.setTelefono("1123456789");
        testClient.setActivo(true);

        testAccount = new AccountEntity();
        testAccount.setAccountId(1L);
        testAccount.setClientEntity(testClient);
        testAccount.setNumeroCuenta("ACC001");
        testAccount.setMoneda("ARS");
        testAccount.setSaldo(1000.0);
        testAccount.setActivo(true);
        testAccount.setFechaCreacion(LocalDateTime.now());

        testDolarRate = new DolarOficialModel("Dolar", "oficial", "Dólar Oficial", 100.0, 110.0, "2026-06-07");
    }

    @Test
    @DisplayName("Should return all accounts")
    void testGetAll() {
        // Arrange
        List<AccountEntity> accountList = List.of(testAccount);
        when(accountRepository.findAll()).thenReturn(accountList);
        when(dolarApiClient.obtenerDolarOficial()).thenReturn(testDolarRate);

        // Act
        List<AccountDTOResponse> result = accountService.getAll();

        // Assert
        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).accountId()).isEqualTo(1L);
        assertThat(result.get(0).numeroCuenta()).isEqualTo("ACC001");
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no accounts exist")
    void testGetAllEmpty() {
        // Arrange
        when(accountRepository.findAll()).thenReturn(List.of());

        // Act
        List<AccountDTOResponse> result = accountService.getAll();

        // Assert
        assertThat(result).isNotNull().isEmpty();
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return account by ID")
    void testGetById() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(dolarApiClient.obtenerDolarOficial()).thenReturn(testDolarRate);

        // Act
        AccountDTOResponse result = accountService.getById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.accountId()).isEqualTo(1L);
        assertThat(result.numeroCuenta()).isEqualTo("ACC001");
        assertThat(result.saldo()).isEqualTo(1000.0);
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw ResponseStatusException when account not found by ID")
    void testGetByIdNotFound() {
        // Arrange
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> accountService.getById(999L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Account not found");
        verify(accountRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should delete account by ID")
    void testDeleteById() {
        // Arrange
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // Act
        accountService.deleteById(1L);

        // Assert
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).delete(testAccount);
    }

    @Test
    @DisplayName("Should throw ResponseStatusException when deleting non-existent account")
    void testDeleteByIdNotFound() {
        // Arrange
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> accountService.deleteById(999L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Account not found");
        verify(accountRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should create account for client with default ARS currency")
    void testCreateAccountForClient() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(1L, "NewACC", null, 5000.0, null);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(testAccount);
        when(dolarApiClient.obtenerDolarOficial()).thenReturn(testDolarRate);

        // Act
        AccountDTOResponse result = accountService.createAccountForClient(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.accountId()).isEqualTo(1L);
        assertThat(result.moneda()).isEqualTo("ARS");
        verify(clientRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    @DisplayName("Should create account with USD currency")
    void testCreateAccountForClientWithUSD() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(1L, "USD_ACC", "USD", 100.0, null);
        testAccount.setMoneda("USD");
        testAccount.setSaldo(100.0);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(testAccount);
        when(dolarApiClient.obtenerDolarOficial()).thenReturn(testDolarRate);

        // Act
        AccountDTOResponse result = accountService.createAccountForClient(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.moneda()).isEqualTo("USD");
        // saldoEnPesos should be calculated: 100 * 110 = 11000
        assertThat(result.saldoEnPesos()).isEqualTo(11000.0);
    }

    @Test
    @DisplayName("Should throw ResponseStatusException when client not found")
    void testCreateAccountForClientNotFound() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(999L, "ACC", "ARS", 1000.0, null);
        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> accountService.createAccountForClient(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Client not found");
        verify(accountRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw ResponseStatusException with invalid currency")
    void testCreateAccountWithInvalidCurrency() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(1L, "ACC", "INVALID", 1000.0, null);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));

        // Act & Assert
        assertThatThrownBy(() -> accountService.createAccountForClient(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Invalid currency");
        verify(accountRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update account numeroCuenta")
    void testUpdateAccountNumeroCuenta() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(null, "UpdatedACC", null, null, null);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(testAccount);
        when(dolarApiClient.obtenerDolarOficial()).thenReturn(testDolarRate);

        // Act
        AccountDTOResponse result = accountService.updateAccount(1L, request);

        // Assert
        assertThat(result).isNotNull();
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    @DisplayName("Should update account moneda")
    void testUpdateAccountMoneda() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(null, null, "USD", null, null);
        testAccount.setMoneda("USD");

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(testAccount);
        when(dolarApiClient.obtenerDolarOficial()).thenReturn(testDolarRate);

        // Act
        AccountDTOResponse result = accountService.updateAccount(1L, request);

        // Assert
        assertThat(result).isNotNull();
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    @DisplayName("Should update account saldo")
    void testUpdateAccountSaldo() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(null, null, null, 5000.0, null);
        testAccount.setSaldo(5000.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(testAccount);
        when(dolarApiClient.obtenerDolarOficial()).thenReturn(testDolarRate);

        // Act
        AccountDTOResponse result = accountService.updateAccount(1L, request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.saldo()).isEqualTo(5000.0);
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    @DisplayName("Should update account activo status")
    void testUpdateAccountActivo() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(null, null, null, null, false);
        testAccount.setActivo(false);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(AccountEntity.class))).thenReturn(testAccount);
        when(dolarApiClient.obtenerDolarOficial()).thenReturn(testDolarRate);

        // Act
        AccountDTOResponse result = accountService.updateAccount(1L, request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.activo()).isFalse();
        verify(accountRepository, times(1)).save(any(AccountEntity.class));
    }

    @Test
    @DisplayName("Should throw ResponseStatusException when updating non-existent account")
    void testUpdateAccountNotFound() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(null, "ACC", null, null, null);
        when(accountRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> accountService.updateAccount(999L, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Account not found");
        verify(accountRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when updating with invalid currency")
    void testUpdateAccountWithInvalidCurrency() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(null, null, "INVALID", null, null);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        // Act & Assert
        assertThatThrownBy(() -> accountService.updateAccount(1L, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Invalid currency");
        verify(accountRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should convert account to DTO with ARS currency")
    void testToDTOWithARS() {
        // Arrange
        when(dolarApiClient.obtenerDolarOficial()).thenReturn(testDolarRate);

        // Act
        AccountDTOResponse result = accountService.toDTO(testAccount);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.accountId()).isEqualTo(1L);
        assertThat(result.numeroCuenta()).isEqualTo("ACC001");
        assertThat(result.moneda()).isEqualTo("ARS");
        assertThat(result.saldo()).isEqualTo(1000.0);
        assertThat(result.saldoEnPesos()).isEqualTo(1000.0); // Same as saldo when ARS
    }

    @Test
    @DisplayName("Should convert account to DTO with USD currency and apply dolar rate")
    void testToDTOWithUSD() {
        // Arrange
        testAccount.setMoneda("USD");
        testAccount.setSaldo(100.0);
        when(dolarApiClient.obtenerDolarOficial()).thenReturn(testDolarRate);

        // Act
        AccountDTOResponse result = accountService.toDTO(testAccount);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.moneda()).isEqualTo("USD");
        assertThat(result.saldo()).isEqualTo(100.0);
        assertThat(result.saldoEnPesos()).isEqualTo(11000.0); // 100 * 110
    }

    @Test
    @DisplayName("Should handle toDTO when dolar API fails")
    void testToDTOWhenDolarApiFails() {
        // Arrange
        testAccount.setMoneda("USD");
        when(dolarApiClient.obtenerDolarOficial()).thenThrow(new RuntimeException("API Error"));

        // Act
        AccountDTOResponse result = accountService.toDTO(testAccount);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.moneda()).isEqualTo("USD");
        assertThat(result.saldoEnPesos()).isEqualTo(testAccount.getSaldo()); // Should remain same when API fails
    }

    @Test
    @DisplayName("Should have null client ID in DTO when account has no client")
    void testToDTOWithNullClient() {
        // Arrange
        AccountEntity accountWithoutClient = new AccountEntity();
        accountWithoutClient.setAccountId(1L);
        accountWithoutClient.setClientEntity(null);
        accountWithoutClient.setNumeroCuenta("ACC001");
        accountWithoutClient.setMoneda("ARS");
        accountWithoutClient.setSaldo(100.0);
        when(dolarApiClient.obtenerDolarOficial()).thenReturn(testDolarRate);

        // Act
        AccountDTOResponse result = accountService.toDTO(accountWithoutClient);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.clientId()).isNull();
    }
}

