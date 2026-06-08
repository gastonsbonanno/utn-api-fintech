package com.utn.utn_api_fintech.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class ClientEntity extends UserEntity {

    @Column(name = "razon_social")
    private String razonSocial;

    private String cuit;

    private String email;

    @Column(name = "tipo_cliente")
    private String tipoCliente;

    private boolean activo;

    public ClientEntity() {
    }

    public ClientEntity(Long id, String nombre, String apellido, String razonSocial, String documento, String cuit, String direccion, String telefono, String email, String tipoCliente, boolean activo) {
        super(id, nombre, apellido, documento, direccion, telefono);
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.email = email;
        this.tipoCliente = tipoCliente;
        this.activo = activo;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
