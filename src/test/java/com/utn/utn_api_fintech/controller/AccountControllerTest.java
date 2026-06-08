
package com.utn.utn_api_fintech.controller;

import com.utn.utn_api_fintech.controller.dto.AccountDTORequest;
import com.utn.utn_api_fintech.controller.dto.AccountDTOResponse;
import com.utn.utn_api_fintech.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccountController Unit Tests")
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private AccountDTOResponse testAccount;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        testAccount = new AccountDTOResponse(
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
    }

    @Test
    @DisplayName("Should return all accounts")
    void testGetAllAccounts() {
        // Arrange
        List<AccountDTOResponse> accounts = List.of(testAccount);
        when(accountService.getAll()).thenReturn(accounts);

        // Act
        ResponseEntity<List<AccountDTOResponse>> response = accountController.getAllAccounts();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).accountId()).isEqualTo(1L);
        verify(accountService, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return empty list when no accounts exist")
    void testGetAllAccountsEmpty() {
        // Arrange
        when(accountService.getAll()).thenReturn(List.of());

        // Act
        ResponseEntity<List<AccountDTOResponse>> response = accountController.getAllAccounts();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
        verify(accountService, times(1)).getAll();
    }

    @Test
    @DisplayName("Should return account by ID")
    void testGetAccountById() {
        // Arrange
        when(accountService.getById(1L)).thenReturn(testAccount);

        // Act
        ResponseEntity<AccountDTOResponse> response = accountController.getAccountById(1L);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().accountId()).isEqualTo(1L);
        assertThat(response.getBody().numeroCuenta()).isEqualTo("ACC001");
        verify(accountService, times(1)).getById(1L);
    }

    @Test
    @DisplayName("Should return 404 when account not found")
    void testGetAccountByIdNotFound() {
        // Arrange
        when(accountService.getById(999L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        // Act & Assert
        assertThatThrownBy(() -> accountController.getAccountById(999L))
                .isInstanceOf(ResponseStatusException.class);
        verify(accountService, times(1)).getById(999L);
    }

    @Test
    @DisplayName("Should delete account successfully")
    void testDeleteAccount() {
        // Arrange
        doNothing().when(accountService).deleteById(1L);

        // Act
        ResponseEntity<Void> response = accountController.deleteAccount(1L);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(accountService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should return 404 when deleting non-existent account")
    void testDeleteAccountNotFound() {
        // Arrange
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"))
                .when(accountService).deleteById(999L);

        // Act & Assert
        assertThatThrownBy(() -> accountController.deleteAccount(999L))
                .isInstanceOf(ResponseStatusException.class);
        verify(accountService, times(1)).deleteById(999L);
    }

    @Test
    @DisplayName("Should create new account")
    void testCreateAccount() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(1L, "NewACC", "ARS", 5000.0, null);
        when(accountService.createAccountForClient(any(AccountDTORequest.class))).thenReturn(testAccount);

        // Act
        ResponseEntity<AccountDTOResponse> response = accountController.createAccount(request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().accountId()).isEqualTo(1L);
        verify(accountService, times(1)).createAccountForClient(any(AccountDTORequest.class));
    }

    @Test
    @DisplayName("Should return 400 when creating account with invalid request")
    void testCreateAccountInvalidRequest() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(1L, "ACC", "INVALID", 1000.0, null);
        when(accountService.createAccountForClient(any(AccountDTORequest.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency"));

        // Act & Assert
        assertThatThrownBy(() -> accountController.createAccount(request))
                .isInstanceOf(ResponseStatusException.class);
        verify(accountService, times(1)).createAccountForClient(any(AccountDTORequest.class));
    }

    @Test
    @DisplayName("Should return 404 when creating account for non-existent client")
    void testCreateAccountClientNotFound() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(999L, "ACC", "ARS", 1000.0, null);
        when(accountService.createAccountForClient(any(AccountDTORequest.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        // Act & Assert
        assertThatThrownBy(() -> accountController.createAccount(request))
                .isInstanceOf(ResponseStatusException.class);
        verify(accountService, times(1)).createAccountForClient(any(AccountDTORequest.class));
    }

    @Test
    @DisplayName("Should update account successfully")
    void testUpdateAccount() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(null, "UpdatedACC", "USD", 5000.0, true);
        when(accountService.updateAccount(eq(1L), any(AccountDTORequest.class))).thenReturn(testAccount);

        // Act
        ResponseEntity<AccountDTOResponse> response = accountController.updateAccount(1L, request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().accountId()).isEqualTo(1L);
        verify(accountService, times(1)).updateAccount(eq(1L), any(AccountDTORequest.class));
    }

    @Test
    @DisplayName("Should return 404 when updating non-existent account")
    void testUpdateAccountNotFound() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(null, "ACC", "ARS", 1000.0, null);
        when(accountService.updateAccount(eq(999L), any(AccountDTORequest.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        // Act & Assert
        assertThatThrownBy(() -> accountController.updateAccount(999L, request))
                .isInstanceOf(ResponseStatusException.class);
        verify(accountService, times(1)).updateAccount(eq(999L), any(AccountDTORequest.class));
    }

    @Test
    @DisplayName("Should return 400 when updating with invalid currency")
    void testUpdateAccountInvalidCurrency() {
        // Arrange
        AccountDTORequest request = new AccountDTORequest(null, null, "INVALID", null, null);
        when(accountService.updateAccount(eq(1L), any(AccountDTORequest.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency"));

        // Act & Assert
        assertThatThrownBy(() -> accountController.updateAccount(1L, request))
                .isInstanceOf(ResponseStatusException.class);
        verify(accountService, times(1)).updateAccount(eq(1L), any(AccountDTORequest.class));
    }
}




