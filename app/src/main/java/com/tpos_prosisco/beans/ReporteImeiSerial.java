package com.tpos_prosisco.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Creada por Norman el 2/8/2021
 **/
public class ReporteImeiSerial {
    @SerializedName("FACTURA")
    @Expose
    private Integer fACTURA;
    @SerializedName("FECHA")
    @Expose
    private String fECHA;
    @SerializedName("ANULADA")
    @Expose
    private Boolean aNULADA;
    @SerializedName("DATOS")
    @Expose
    private String dATOS;
    @SerializedName("MONTO")
    @Expose
    private Double mONTO;
    @SerializedName("URL_FACTURA")
    @Expose
    private String uRLFACTURA;

    public Integer getFACTURA() {
        return fACTURA;
    }

    public void setFACTURA(Integer fACTURA) {
        this.fACTURA = fACTURA;
    }

    public String getFECHA() {
        return fECHA;
    }

    public void setFECHA(String fECHA) {
        this.fECHA = fECHA;
    }

    public Boolean getANULADA() {
        return aNULADA;
    }

    public void setANULADA(Boolean aNULADA) {
        this.aNULADA = aNULADA;
    }

    public String getDATOS() {
        return dATOS;
    }

    public void setDATOS(String dATOS) {
        this.dATOS = dATOS;
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
