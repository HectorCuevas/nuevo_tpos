package com.tpos_prosisco.beans;


import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "factura")
public class Factura {


    @PrimaryKey(autoGenerate = true)
    public int id;
    private String tipo_doc;
    private String imei;
    private String ruta;
    private String fact_num;
    private String co_cli;
    private String co_ven;
    private String nit;
    private String dpi;
    private String nombre;
    private String direccion;
    private String telefono;
    @SerializedName("forma_pag")
    private String forma_pago;
    private String tipo_venta;
    private double total;
    private double cobro;
    private String comentario;
    private String fe_us_in;
    private double nro_doc;
    private double latitud;
    private double longitud;
    private String departamento;
    private String municipio;
    private int zona;
    @SerializedName("imagen1")
    @Nullable
    private String imagen1;
    @SerializedName("imagen2")
    @Nullable
    private String imagen2;

    @SerializedName("pedidos")
    @Ignore
    private List<DetalleFactura> items;


    public Factura() {
    }

    public int getId() {
        return id;
    }
    public String getImagen2() {
        return imagen2;
    }

    public void setImagen2(String imagen) {
        this.imagen2 = imagen;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRuta() {
        return ruta;
    }
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getImagen1() {
        return imagen1;
    }

    public void setImagen1(String imagen) {
        this.imagen1 = imagen;
    }


    public String getTipo_venta() {
        return tipo_venta;
    }

    public void setTipo_venta(String tipo_venta) {
        this.tipo_venta = tipo_venta;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc = tipo_doc;
    }


    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public String getFact_num() {
        return fact_num;
    }

    public void setFact_num(String fact_num) {
        this.fact_num = fact_num;
    }

    public String getCo_cli() {
        return co_cli;
    }

    public void setCo_cli(String co_cli) {
        this.co_cli = co_cli;
    }

    public String getCo_ven() {
        return co_ven;
    }

    public void setCo_ven(String co_ven) {
        this.co_ven = co_ven;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getForma_pag() {
        return forma_pago;
    }

    public void setForma_pag(String forma_pag) {
        this.forma_pago = forma_pag;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getCobro() {
        return cobro;
    }

    public void setCobro(double cobro) {
        this.cobro = cobro;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFe_us_in() {
        return fe_us_in;
    }

    public void setFe_us_in(String fe_us_in) {
        this.fe_us_in = fe_us_in;
    }

    public double getNro_doc() {
        return nro_doc;
    }

    public void setNro_doc(double nro_doc) {
        this.nro_doc = nro_doc;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public List<DetalleFactura> getItems() {
        return items;
    }

    public void setItems(List<DetalleFactura> items) {
        this.items = items;
    }


}
