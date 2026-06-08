package com.utn.utn_api_fintech.service;

import com.utn.utn_api_fintech.client.DolarApiClient;
import com.utn.utn_api_fintech.client.model.DolarOficialModel;
import com.utn.utn_api_fintech.controller.dto.AccountDTOResponse;
import com.utn.utn_api_fintech.entity.AccountEntity;
import com.utn.utn_api_fintech.entity.ClientEntity;
import com.utn.utn_api_fintech.controller.dto.AccountDTORequest;
import com.utn.utn_api_fintech.repository.AccountRepository;
import com.utn.utn_api_fintech.repository.ClientRepository;
import com.utn.utn_api_fintech.enums.Moneda;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final DolarApiClient dolarApiClient;
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository, DolarApiClient dolarApiClient) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.dolarApiClient = dolarApiClient;
    }

    public List<AccountDTOResponse> getAll() {
        logger.info("Fetching all accounts");
        var list = accountRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
        logger.info("Returning {} accounts", list.size());
        return list;
    }

    public AccountDTOResponse getById(Long id) {
        logger.info("Fetching account by id={}", id);
        var response = accountRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Account with id={} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
                });
        var dto = toDTO(response);
        logger.debug("getById result: {}", dto);
        return dto;
    }

    public void deleteById(Long id) {
        logger.info("Deleting account id={}", id);
        AccountEntity account = accountRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Account with id={} not found for delete", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
                });
        accountRepository.delete(account);
        logger.info("Account id={} deleted", id);
    }

    public AccountDTOResponse createAccountForClient(AccountDTORequest request) {
        logger.info("Creating account for clientId={}", request.clientId());
        ClientEntity client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> {
                    logger.warn("Client with id={} not found", request.clientId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
                });
        String moneda = request.moneda() != null ? request.moneda() : "ARS";
        try {
            Moneda.valueOf(moneda);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid currency provided: {}", moneda);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency. Allowed values: " + getAllowedCurrencies());
        }

        AccountEntity account = new AccountEntity();
        account.setClientEntity(client);
        account.setNumeroCuenta(request.numeroCuenta());
        account.setMoneda(moneda);
        account.setSaldo(request.saldo());
        account.setActivo(true);
        account.setFechaCreacion(LocalDateTime.now());

        var entity = accountRepository.save(account);
        logger.info("Account created with id={} for clientId={}", entity.getAccountId(), request.clientId());
        return toDTO(entity);
    }

    public AccountDTOResponse updateAccount(Long id, AccountDTORequest request) {
        logger.info("Updating account id={}", id);
        AccountEntity account = accountRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Account with id={} not found for update", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
                });

        if (request.numeroCuenta() != null) {
            account.setNumeroCuenta(request.numeroCuenta());
        }
        if (request.moneda() != null) {
            try {
                Moneda.valueOf(request.moneda());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency. Allowed values: " + getAllowedCurrencies());
            }
            account.setMoneda(request.moneda());
        }
        if (request.saldo() != null) {
            account.setSaldo(request.saldo());
        }
        if (request.activo() != null) {
            account.setActivo(request.activo());
        }

        account.setFechaModificacion(LocalDateTime.now());
        var saved = accountRepository.save(account);
        logger.info("Account id={} updated", id);
        return toDTO(saved);
    }

    private String getAllowedCurrencies() {
        StringBuilder sb = new StringBuilder();
        for (Moneda m : Moneda.values()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(m.name());
        }
        return sb.toString();
    }

    public AccountDTOResponse toDTO(AccountEntity account) {
        DolarOficialModel dolarOficialModel = null;
        try {
            dolarOficialModel = dolarApiClient.obtenerDolarOficial();
        } catch (Exception e) {
            logger.warn("Failed to fetch dolar oficial: {}", e.getMessage());
        }
        Double saldoEnPesos = account.getSaldo();
        if ("USD".equals(account.getMoneda()) && dolarOficialModel != null) {
            saldoEnPesos = account.getSaldo() * dolarOficialModel.venta();
        }
        logger.debug("Mapping account id={} to DTO", account.getAccountId());
        return new AccountDTOResponse(
                account.getAccountId(),
                account.getClientEntity() != null ? account.getClientEntity().getId() : null,
                account.getNumeroCuenta(),
                account.getMoneda(),
                account.getSaldo(),
                saldoEnPesos,
                account.getActivo(),
                account.getFechaCreacion(),
                account.getFechaModificacion()
        );
    }

}

