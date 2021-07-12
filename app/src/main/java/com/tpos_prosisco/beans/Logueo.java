package com.tpos_prosisco.beans;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "logueo")
public class Logueo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("codigo_empresa")
    @Expose
    private String codigoEmpresa;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("codigo_pais")
    @Expose
    private String codigoPais;
    @SerializedName("ussd_recarga")
    @Expose
    private String ussdRecarga;
    @SerializedName("ussd_saldo")
    @Expose
    private String ussdSaldo;
    @SerializedName("ussd_Saldo_Cliente")
    @Expose
    private String ussdSaldoCliente;
    @SerializedName("Simbolo_moneda")
    @Expose
    private String simboloMoneda;
    @SerializedName("Nombre_Ruta")
    @Expose
    private String nombreRuta;
    @SerializedName("co_sucu")
    @Expose
    private String coSucu;
    @SerializedName("id_canal_venta")
    @Expose
    private Integer idCanalVenta;
    @SerializedName("nombre_canal")
    @Expose
    private String nombreCanal;
    @SerializedName("Codigo_TA")
    @Expose
    private String codigoTA;
    @SerializedName("Tipo_Venta")
    @Expose
    private String tipoVenta;
    @SerializedName("Tipo_Contado")
    @Expose
    private String tipoContado;
    @SerializedName("Tipo_Credito")
    @Expose
    private String tipoCredito;

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getUssdRecarga() {
        return ussdRecarga;
    }

    public void setUssdRecarga(String ussdRecarga) {
        this.ussdRecarga = ussdRecarga;
    }

    public String getUssdSaldo() {
        return ussdSaldo;
    }

    public void setUssdSaldo(String ussdSaldo) {
        this.ussdSaldo = ussdSaldo;
    }

    public String getUssdSaldoCliente() {
        return ussdSaldoCliente;
    }

    public void setUssdSaldoCliente(String ussdSaldoCliente) {
        this.ussdSaldoCliente = ussdSaldoCliente;
    }

    public String getSimboloMoneda() {
        return simboloMoneda;
    }

    public void setSimboloMoneda(String simboloMoneda) {
        this.simboloMoneda = simboloMoneda;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public String getCoSucu() {
        return coSucu;
    }

    public void setCoSucu(String coSucu) {
        this.coSucu = coSucu;
    }

    public Integer getIdCanalVenta() {
        return idCanalVenta;
    }

    public void setIdCanalVenta(Integer idCanalVenta) {
        this.idCanalVenta = idCanalVenta;
    }

    public String getNombreCanal() {
        return nombreCanal;
    }

    public void setNombreCanal(String nombreCanal) {
        this.nombreCanal = nombreCanal;
    }

    public String getCodigoTA() {
        return codigoTA;
    }

    public void setCodigoTA(String codigoTA) {
        this.codigoTA = codigoTA;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getTipoContado() {
        return tipoContado;
    }

    public void setTipoContado(String tipoContado) {
        this.tipoContado = tipoContado;
    }

    public String getTipoCredito() {
        return tipoCredito;
    }

    public void setTipoCredito(String tipoCredito) {
        this.tipoCredito = tipoCredito;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}