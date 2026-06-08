package com.utn.utn_api_fintech.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DolarOficialModel(
    @JsonProperty("moneda") String moneda,
    @JsonProperty("casa") String casa,
    @JsonProperty("nombre") String nombre,
    @JsonProperty("compra") Double compra,
    @JsonProperty("venta") Double venta,
    @JsonProperty("fechaActualizacion") String fechaActualizacion
) {}

