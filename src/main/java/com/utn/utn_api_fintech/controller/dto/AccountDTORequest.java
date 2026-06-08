package com.utn.utn_api_fintech.controller.dto;

import java.time.LocalDateTime;

public record AccountDTORequest(
    Long clientId,
    String numeroCuenta,
    String moneda,
    Double saldo,
    Boolean activo
) {}

