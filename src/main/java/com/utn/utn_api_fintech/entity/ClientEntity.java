package com.utn.utn_api_fintech.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "documento")
    private String documento;

    private String cuit;

    private String direccion;
    private String telefono;
    private String email;

    @Column(name = "tipo_cliente")
    private String tipoCliente;

    private boolean activo;

    public ClientEntity() {
    }

    public ClientEntity(Long id, String nombre, String apellido, String razonSocial, String documento, String cuit, String direccion, String telefono, String email, String tipoCliente, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.razonSocial = razonSocial;
        this.documento = documento;
        this.cuit = cuit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.tipoCliente = tipoCliente;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
