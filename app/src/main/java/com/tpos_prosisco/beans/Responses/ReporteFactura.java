package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReporteFactura {

    @SerializedName("FACTURA")
    @Expose
    private Integer fACTURA;
    @SerializedName("ANULADA")
    @Expose
    private Boolean aNULADA;
    @SerializedName("VENDEDOR")
    @Expose
    private String vENDEDOR;
    @SerializedName("MONTO")
    @Expose
    private Double mONTO;
    @SerializedName("COBRO")
    @Expose
    private Integer cOBRO;

    public String getURL_FACTURA() {
        return URL_FACTURA;
    }

    public void setURL_FACTURA(String URL_FACTURA) {
        this.URL_FACTURA = URL_FACTURA;
    }

    @SerializedName("URL_FACTURA")
    @Expose
    private String URL_FACTURA;

    public Integer getFACTURA() {
        return fACTURA;
    }

    public void setFACTURA(Integer fACTURA) {
        this.fACTURA = fACTURA;
    }

    public Boolean getANULADA() {
        return aNULADA;
    }

    public void setANULADA(Boolean aNULADA) {
        this.aNULADA = aNULADA;
    }

    public String getVENDEDOR() {
        return vENDEDOR;
    }

    public void setVENDEDOR(String vENDEDOR) {
        this.vENDEDOR = vENDEDOR;
    }

    public Double getMONTO() {
        return mONTO;
    }

    public void setMONTO(Double mONTO) {
        this.mONTO = mONTO;
    }

    public Integer getCOBRO() {
        return cOBRO;
    }

    public void setCOBRO(Integer cOBRO) {
        this.cOBRO = cOBRO;
    }
}
