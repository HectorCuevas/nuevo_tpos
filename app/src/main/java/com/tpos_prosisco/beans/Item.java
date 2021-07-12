package com.tpos_prosisco.beans;

public class Item {

    public Item() {
    }

    public Item(Producto producto, int cantidad, double precio) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    private Producto producto;
    private int cantidad;
    private double precio;


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
