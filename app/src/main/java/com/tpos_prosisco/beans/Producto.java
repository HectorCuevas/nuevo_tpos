package com.tpos_prosisco.beans;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "producto")
public class Producto {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("co_art")
    @Expose
    private String coArt;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("serial")
    @Expose
    private String serial;
    @SerializedName("Column1")
    @Expose
    private String column1;
    @SerializedName("co_alma")
    @Expose
    private String coAlma;
    @SerializedName("stock")
    @Expose
    private Double stock;
    @SerializedName("prec_vta1")
    @Expose
    private Double precVta1;
    @SerializedName("prec_vta2")
    @Expose
    private Double precVta2;
    @SerializedName("prec_vta3")
    @Expose
    private Double precVta3;
    @SerializedName("prec_vta31")
    @Expose
    private Double precVta31;
    @SerializedName("prec_vta5")
    @Expose
    private Double precVta5;
    private int tel;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    private String comentario;

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }


    @Ignore
    public Producto(String coArt, String tipo, String serial, String column1, String coAlma, Double stock, Double precVta1, Double precVta2, Double precVta3, Double precVta31, Double precVta5) {
        super();
        this.coArt = coArt;
        this.tipo = tipo;
        this.serial = serial;
        this.column1 = column1;
        this.coAlma = coAlma;
        this.stock = stock;
        this.precVta1 = precVta1;
        this.precVta2 = precVta2;
        this.precVta3 = precVta3;
        this.precVta31 = precVta31;
        this.precVta5 = precVta5;
    }
     @Ignore
    public Producto(String column1,  String coAlma, Double precVta1,  String tipo,String coArt,  String serial, Double stock) {
        super();
        this.coArt = coArt;
        this.tipo = tipo;
        this.serial = serial;
        this.column1 = column1;
        this.coAlma = coAlma;
        this.stock = stock;
        this.precVta1 = precVta1;
    }
    //  @Ignore
    public Producto(String coArt, String column1, String serial, Double precVta1, String tipo) {
        super();
        this.coArt = coArt;
        this.column1 = column1;
        this.serial = serial;
        this.precVta1 = precVta1;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoArt() {
        return coArt;
    }

    public void setCoArt(String coArt) {
        this.coArt = coArt;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getCoAlma() {
        return coAlma;
    }

    public void setCoAlma(String coAlma) {
        this.coAlma = coAlma;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getPrecVta1() {
        return precVta1;
    }

    public void setPrecVta1(Double precVta1) {
        this.precVta1 = precVta1;
    }

    public Double getPrecVta2() {
        return precVta2;
    }

    public void setPrecVta2(Double precVta2) {
        this.precVta2 = precVta2;
    }

    public Double getPrecVta3() {
        return precVta3;
    }

    public void setPrecVta3(Double precVta3) {
        this.precVta3 = precVta3;
    }

    public Double getPrecVta31() {
        return precVta31;
    }

    public void setPrecVta31(Double precVta31) {
        this.precVta31 = precVta31;
    }

    public Double getPrecVta5() {
        return precVta5;
    }

    public void setPrecVta5(Double precVta5) {
        this.precVta5 = precVta5;
    }

}