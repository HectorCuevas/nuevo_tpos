package com.tpos_prosisco.beans;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Creada por Norman el 11/9/2020
 **/
public class Motivo {

    @SerializedName("Imei")
    private String imei;
    @SerializedName("pRUTA")
    private String ruta;
    @SerializedName("pCO_CLI")
    private String co_cli;
    @SerializedName("pCO_VEN")
    private String co_ven;
    @SerializedName("pFE_US_IN")
    private String datetime;
    @SerializedName("pCO_MOTIVO")
    private int co_motivo;
    @SerializedName("pCOMENTARIO")
    private String motivo;
    @SerializedName("pLATITUD")
    private double latitud;
    @SerializedName("pLONGITUD")
    private double longitud;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getCo_ven() {
        return co_ven;
    }

    public void setCo_ven(String co_ven) {
        this.co_ven = co_ven;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getCo_motivo() {
        return co_motivo;
    }

    public void setCo_motivo(int co_motivo) {
        this.co_motivo = co_motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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
    public String getCo_cli() {
        return co_cli;
    }

    public void setCo_cli(String co_cli) {
        this.co_cli = co_cli;
    }
}
