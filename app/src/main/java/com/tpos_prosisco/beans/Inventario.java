package com.tpos_prosisco.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Creada por Norman el 9/21/2020
 **/
public class Inventario {
    @SerializedName("CODIGO")
    @Expose
    private String cODIGO;
    @SerializedName("DESCRIPCION")
    @Expose
    private String dESCRIPCION;
    @SerializedName("STOCK")
    @Expose
    private Double sTOCK;
    @SerializedName("PRECIO")
    @Expose
    private Double pRECIO;

    public String getCODIGO() {
        return cODIGO;
    }

    public void setCODIGO(String cODIGO) {
        this.cODIGO = cODIGO;
    }

    public String getDESCRIPCION() {
        return dESCRIPCION;
    }

    public void setDESCRIPCION(String dESCRIPCION) {
        this.dESCRIPCION = dESCRIPCION;
    }

    public Double getSTOCK() {
        return sTOCK;
    }

    public void setSTOCK(Double sTOCK) {
        this.sTOCK = sTOCK;
    }

    public Double getPRECIO() {
        return pRECIO;
    }

    public void setPRECIO(Double pRECIO) {
        this.pRECIO = pRECIO;
    }
}
