package com.tpos_prosisco.beans;

public class Recarga {
    private int monto;

    public Recarga() {

    }


    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    private String cliente;
    public Recarga(String numero, int monto, String cliente) {
        this.numero = numero;
        this.monto = monto;
        this.cliente  =cliente;
    }

    public String numero;



    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }


}
