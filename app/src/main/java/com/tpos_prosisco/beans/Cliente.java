package com.tpos_prosisco.beans;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "cliente")
public class Cliente {

    @PrimaryKey(autoGenerate = true)
    public int id;


    @SerializedName("co_cli")
    @Expose
    private String coCli;
    @SerializedName("dias_credito")
    @Expose
    private Integer diasCredito;
    @SerializedName("direc1")
    @Expose
    private String direc1;
    @SerializedName("telefonos")
    @Expose
    private String telefonos;
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("respons")
    @Expose
    private String respons;
    @SerializedName("en_ruta")
    @Expose
    private Integer enRuta;
    @SerializedName("saldo")
    @Expose
    private Double saldo;

    public String getCoCli() {
        return coCli;
    }

    public void setCoCli(String coCli) {
        this.coCli = coCli;
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public String getDirec1() {
        return direc1;
    }

    public void setDirec1(String direc1) {
        this.direc1 = direc1;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getRespons() {
        return respons;
    }

    public void setRespons(String respons) {
        this.respons = respons;
    }

    public Integer getEnRuta() {
        return enRuta;
    }

    public void setEnRuta(Integer enRuta) {
        this.enRuta = enRuta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Cliente(String coCli, Integer diasCredito, String direc1, String telefonos, String fax, String respons, Integer enRuta, Double saldo) {
        this.coCli = coCli;
        this.diasCredito = diasCredito;
        this.direc1 = direc1;
        this.telefonos = telefonos;
        this.fax = fax;
        this.respons = respons;
        this.enRuta = enRuta;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
