package com.tpos_prosisco.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReporteVenta {
    @SerializedName("COD_VENDEDOR")
    @Expose
    private String cODVENDEDOR;
    @SerializedName("VENDEDOR")
    @Expose
    private String vENDEDOR;
    @SerializedName("COD_ARTICULO")
    @Expose
    private String cODARTICULO;
    @SerializedName("ARTICULO")
    @Expose
    private String aRTICULO;
    @SerializedName("MONTO")
    @Expose
    private Double mONTO;

    public String getCODVENDEDOR() {
        return cODVENDEDOR;
    }

    public void setCODVENDEDOR(String cODVENDEDOR) {
        this.cODVENDEDOR = cODVENDEDOR;
    }

    public String getVENDEDOR() {
        return vENDEDOR;
    }

    public void setVENDEDOR(String vENDEDOR) {
        this.vENDEDOR = vENDEDOR;
    }

    public String getCODARTICULO() {
        return cODARTICULO;
    }

    public void setCODARTICULO(String cODARTICULO) {
        this.cODARTICULO = cODARTICULO;
    }

    public String getARTICULO() {
        return aRTICULO;
    }

    public void setARTICULO(String aRTICULO) {
        this.aRTICULO = aRTICULO;
    }

    public Double getMONTO() {
        return mONTO;
    }

    public void setMONTO(Double mONTO) {
        this.mONTO = mONTO;
    }
}
