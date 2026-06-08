package com.utn.utn_api_fintech.repository;

import com.utn.utn_api_fintech.entity.AccountEntity;
import com.utn.utn_api_fintech.entity.ClientEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("AccountRepository Integration Tests")
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Should create and find account in database")
    void testSaveAndFindAccount() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setNombre("Test Client");
        client.setApellido("Test");
        client.setDocumento("20-12345678-9");
        client.setDireccion("Test Address");
        client.setTelefono("1234567890");
        client.setActivo(true);
        
        ClientEntity savedClient = clientRepository.save(client);

        AccountEntity account = new AccountEntity();
        account.setClientEntity(savedClient);
        account.setNumeroCuenta("TEST001");
        account.setMoneda("ARS");
        account.setSaldo(1000.0);
        account.setActivo(true);
        account.setFechaCreacion(LocalDateTime.now());

        // Act
        AccountEntity savedAccount = accountRepository.save(account);

        // Assert
        assertThat(savedAccount.getAccountId()).isNotNull();
        assertThat(savedAccount.getNumeroCuenta()).isEqualTo("TEST001");
    }

    @Test
    @DisplayName("Should find account by id")
    void testFindAccountById() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setNombre("Client");
        client.setApellido("Lastname");
        client.setDocumento("20-12345678-9");
        client.setActivo(true);
        
        ClientEntity savedClient = clientRepository.save(client);

        AccountEntity account = new AccountEntity();
        account.setClientEntity(savedClient);
        account.setNumeroCuenta("ACC123");
        account.setMoneda("USD");
        account.setSaldo(500.0);
        account.setActivo(true);
        account.setFechaCreacion(LocalDateTime.now());
        
        AccountEntity savedAccount = accountRepository.save(account);

        // Act
        Optional<AccountEntity> foundAccount = accountRepository.findById(savedAccount.getAccountId());

        // Assert
        assertThat(foundAccount).isPresent();
        assertThat(foundAccount.get().getNumeroCuenta()).isEqualTo("ACC123");
    }

    @Test
    @DisplayName("Should return empty when account not found")
    void testFindAccountByIdNotFound() {
        // Act
        Optional<AccountEntity> foundAccount = accountRepository.findById(99999L);

        // Assert
        assertThat(foundAccount).isEmpty();
    }

    @Test
    @DisplayName("Should find all accounts")
    void testFindAllAccounts() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setNombre("Client");
        client.setApellido("Test");
        client.setDocumento("20-12345678-9");
        client.setActivo(true);
        
        ClientEntity savedClient = clientRepository.save(client);

        AccountEntity acc1 = new AccountEntity();
        acc1.setClientEntity(savedClient);
        acc1.setNumeroCuenta("ACC1");
        acc1.setMoneda("ARS");
        acc1.setSaldo(1000.0);
        acc1.setActivo(true);
        acc1.setFechaCreacion(LocalDateTime.now());
        
        AccountEntity acc2 = new AccountEntity();
        acc2.setClientEntity(savedClient);
        acc2.setNumeroCuenta("ACC2");
        acc2.setMoneda("USD");
        acc2.setSaldo(500.0);
        acc2.setActivo(true);
        acc2.setFechaCreacion(LocalDateTime.now());
        
        accountRepository.save(acc1);
        accountRepository.save(acc2);

        // Act
        List<AccountEntity> allAccounts = accountRepository.findAll();

        // Assert
        assertThat(allAccounts).isNotEmpty();
    }

    @Test
    @DisplayName("Should delete account")
    void testDeleteAccount() {
        // Arrange
        ClientEntity client = new ClientEntity();
        client.setNombre("Client");
        client.setApellido("Test");
        client.setDocumento("20-12345678-9");
        client.setActivo(true);
        
        ClientEntity savedClient = clientRepository.save(client);

        AccountEntity account = new AccountEntity();
        account.setClientEntity(savedClient);
        account.setNumeroCuenta("DEL001");
        account.setMoneda("ARS");
        account.setSaldo(100.0);
        account.setActivo(true);
        account.setFechaCreacion(LocalDateTime.now());
        
        AccountEntity savedAccount = accountRepository.save(account);
        Long accountId = savedAccount.getAccountId();

        // Act
        accountRepository.deleteById(accountId);

        // Assert
        Optional<AccountEntity> deletedAccount = accountRepository.findById(accountId);
        assertThat(deletedAccount).isEmpty();
    }
}


