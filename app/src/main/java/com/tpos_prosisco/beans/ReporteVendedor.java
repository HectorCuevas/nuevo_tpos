package com.tpos_prosisco.beans;

public class ReporteVendedor {
    private String CODIGO_VENDEDOR;
    private String VENDEDOR;
    private String RUTA;
    private float MONTO;


    public String getCODIGO_VENDEDOR() {
        return CODIGO_VENDEDOR;
    }

    public String getVENDEDOR() {
        return VENDEDOR;
    }

    public String getRUTA() {
        return RUTA;
    }

    public float getMONTO() {
        return MONTO;
    }

    // Setter Methods

    public void setCODIGO_VENDEDOR(String CODIGO_VENDEDOR) {
        this.CODIGO_VENDEDOR = CODIGO_VENDEDOR;
    }

    public void setVENDEDOR(String VENDEDOR) {
        this.VENDEDOR = VENDEDOR;
    }

    public void setRUTA(String RUTA) {
        this.RUTA = RUTA;
    }

    public void setMONTO(float MONTO) {
        this.MONTO = MONTO;
    }
}
