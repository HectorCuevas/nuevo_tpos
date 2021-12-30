package com.tpos_prosisco.beans;

public class Item {

    public Item() {
    }

    public Item(Producto producto, int cantidad, double precio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.esPaneles = false;

    }
    public Item(Producto producto, int cantidad, Double precVta1, boolean b) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precVta1;
        this.esPaneles = b;
    }

    private Producto producto;
    private int cantidad;
    private double precio;
    private boolean esPaneles;



    public boolean isEsPaneles() {
        return esPaneles;
    }

    public void setEsPaneles(boolean esPaneles) {
        this.esPaneles = esPaneles;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }



}
