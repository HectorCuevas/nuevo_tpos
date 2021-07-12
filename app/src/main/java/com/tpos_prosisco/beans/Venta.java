package com.tpos_prosisco.beans;

import androidx.annotation.Nullable;

public class Venta {

    private String nombre;
    private int numero;
    private String dpi;
    private String nit;
    private int zona;
    private String municipio;
    private String depto;
    private String condicionPago;
    private String direccionRecarga;
    private Cliente cliente;
    private Vendedor vendedor;
    @Nullable
    private byte[] imagen;
    @Nullable
    private byte[] imagen2;

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }


    public byte[] getImagen2() {
        return imagen2;
    }

    public void setImagen2(byte[] imagen) {
        this.imagen2 = imagen;
    }
    public String getDireccionRecarga() {
        return direccionRecarga;
    }

    public void setDireccionRecarga(String direccionRecarga) {
        this.direccionRecarga = direccionRecarga;
    }



    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }


    public Venta() {
    }




    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getCondicionPago() {
        return condicionPago;
    }

    public void setCondicionPago(String condicionPago) {
        this.condicionPago = condicionPago;
    }



}
