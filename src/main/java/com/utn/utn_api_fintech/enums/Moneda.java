package com.utn.utn_api_fintech.enums;

public enum Moneda {
    USD("Dolar"),
    ARS("Peso Argentino");

    private final String descripcion;

    Moneda(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }


}
