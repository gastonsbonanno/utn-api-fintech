package com.utn.utn_api_fintech.controller;

import com.utn.utn_api_fintech.controller.dto.AccountDTORequest;
import com.utn.utn_api_fintech.controller.dto.AccountDTOResponse;
import com.utn.utn_api_fintech.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public ResponseEntity<List<AccountDTOResponse>> getAllAccounts() {
		logger.info("GET /accounts - getAllAccounts called");
		var response = accountService.getAll();
		logger.info("getAllAccounts executed, returning {} accounts", response.size());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountDTOResponse> getAccountById(@PathVariable Long id) {
		logger.info("GET /accounts/{} - getAccountById called", id);
		var dto = accountService.getById(id);
		logger.debug("getAccountById result: {}", dto);
		return ResponseEntity.ok(dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
		logger.info("DELETE /accounts/{} - deleteAccount called", id);
		accountService.deleteById(id);
		logger.info("Account id={} deleted", id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<AccountDTOResponse> updateAccount(@PathVariable Long id, @RequestBody AccountDTORequest request) {
		logger.info("PUT /accounts/{} - updateAccount called", id);
		var dto = accountService.updateAccount(id, request);
		logger.info("Account id={} updated", id);
		return ResponseEntity.ok(dto);
	}

	@PostMapping
	public ResponseEntity<AccountDTOResponse> createAccount(@RequestBody AccountDTORequest request) {
		logger.info("POST /accounts - createAccount called for clientId={}", request.clientId());
		var dto = accountService.createAccountForClient(request);
		logger.info("Account created id={}", dto.accountId());
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

}


