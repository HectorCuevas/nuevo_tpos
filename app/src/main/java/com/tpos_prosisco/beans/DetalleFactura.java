package com.tpos_prosisco.beans;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "DetalleFactura")
public class DetalleFactura {

    @PrimaryKey(autoGenerate = true)
    private int id_det;
    @ForeignKey
            (entity = Factura.class,
                    parentColumns = "id",
                    childColumns = "id_factura",
                    onDelete = CASCADE
            )
    public long id_factura;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    private String imei;
    private String tipo_doc;
    private int fact_num;
    private String comentario_renglon;
    private String co_art;
    private double prec_vta;
    private String ruta;
    private double total_art;
    private double descuento;
    private double reng_neto;
    private int aux1;
    private String aux2;


    public DetalleFactura() {
    }

    public long getId_factura() {
        return id_factura;
    }

    public void setId_factura(long id_factura) {
        this.id_factura = id_factura;
    }
    public int getId_det() {
        return id_det;
    }

    public void setId_det(int id_det) {
        this.id_det = id_det;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public int getFact_num() {
        return fact_num;
    }

    public void setFact_num(int fact_num) {
        this.fact_num = fact_num;
    }

    public String getComentario_renglon() {
        return comentario_renglon;
    }

    public void setComentario_renglon(String comentario_renglon) {
        this.comentario_renglon = comentario_renglon;
    }

    public String getCo_art() {
        return co_art;
    }

    public void setCo_art(String co_art) {
        this.co_art = co_art;
    }

    public double getPrec_vta() {
        return prec_vta;
    }

    public void setPrec_vta(double prec_vta) {
        this.prec_vta = prec_vta;
    }

    public double getTotal_art() {
        return total_art;
    }

    public void setTotal_art(double total_art) {
        this.total_art = total_art;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getReng_neto() {
        return reng_neto;
    }

    public void setReng_neto(double reng_neto) {
        this.reng_neto = reng_neto;
    }

    public int getAux1() {
        return aux1;
    }

    public void setAux1(int aux1) {
        this.aux1 = aux1;
    }

    public String getAux2() {
        return aux2;
    }

    public void setAux2(String aux2) {
        this.aux2 = aux2;
    }


}
