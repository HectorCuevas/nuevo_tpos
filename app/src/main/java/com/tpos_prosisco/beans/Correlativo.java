package com.tpos_prosisco.beans;

public class Correlativo {

    private String imei;
    private String tipo_doc;
    private String co_sucu;
    private int accion;

    public Correlativo(String imei, String tipo_doc, String co_sucu, int accion) {
        this.imei = imei;
        this.tipo_doc = tipo_doc;
        this.co_sucu = co_sucu;
        this.accion = accion;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public String getCo_sucu() {
        return co_sucu;
    }

    public void setCo_sucu(String co_sucu) {
        this.co_sucu = co_sucu;
    }

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

}
