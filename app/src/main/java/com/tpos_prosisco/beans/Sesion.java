package com.tpos_prosisco.beans;

import androidx.room.PrimaryKey;

public class Sesion {


    private String tipo_sesion;
    private String imei;

    public Sesion(String tipo_sesion, String imei, double latitud, double longitud) {
        this.tipo_sesion = tipo_sesion;
        this.imei = imei;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    private double latitud;
    private double longitud;

    public String getTipo_sesion() {
        return tipo_sesion;
    }

    public void setTipo_sesion(String tipo_sesion) {
        this.tipo_sesion = tipo_sesion;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }



}
