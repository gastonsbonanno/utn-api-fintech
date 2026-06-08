package com.utn.utn_api_fintech.controller.dto;

public record AccountDTORequest(
    Long clientId,
    String numeroCuenta,
    String moneda,
    Double saldo,
    Boolean activo
) {}

