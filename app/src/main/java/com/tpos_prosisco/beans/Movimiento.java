package com.tpos_prosisco.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Creada por Norman el 11/30/2020
 **/
public class Movimiento {
    @SerializedName("TIPO")
    @Expose
    private String tIPO;
    @SerializedName("FECHA")
    @Expose
    private String fECHA;
    @SerializedName("NUMERO")
    @Expose
    private Integer nUMERO;
    @SerializedName("OBSERVACIONES")
    @Expose
    private String oBSERVACIONES;
    @SerializedName("MONTO")
    @Expose
    private Double mONTO;
    @SerializedName("URL_FACTURA")
    @Expose
    private String uRLFACTURA;

    public String getTIPO() {
        return tIPO;
    }

    public void setTIPO(String tIPO) {
        this.tIPO = tIPO;
    }

    public String getFECHA() {
        return fECHA;
    }

    public void setFECHA(String fECHA) {
        this.fECHA = fECHA;
    }

    public Integer getNUMERO() {
        return nUMERO;
    }

    public void setNUMERO(Integer nUMERO) {
        this.nUMERO = nUMERO;
    }

    public String getOBSERVACIONES() {
        return oBSERVACIONES;
    }

    public void setOBSERVACIONES(String oBSERVACIONES) {
        this.oBSERVACIONES = oBSERVACIONES;
    }

    public Double getMONTO() {
        return mONTO;
    }

    public void setMONTO(Double mONTO) {
        this.mONTO = mONTO;
    }

    public String getURLFACTURA() {
        return uRLFACTURA;
    }

    public void setURLFACTURA(String uRLFACTURA) {
        this.uRLFACTURA = uRLFACTURA;
    }

}
