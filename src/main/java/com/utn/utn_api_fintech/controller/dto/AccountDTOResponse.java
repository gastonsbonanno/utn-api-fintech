package com.utn.utn_api_fintech.controller.dto;

import java.time.LocalDateTime;

public record AccountDTOResponse(
    Long accountId,
    Long clientId,
    String numeroCuenta,
    String moneda,
    Double saldo,
    Double saldoEnPesos,
    Boolean activo,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaModificacion
) {}

