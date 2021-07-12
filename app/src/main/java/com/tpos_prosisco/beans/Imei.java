package com.tpos_prosisco.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Creada por Norman el 9/22/2020
 **/
public class Imei {
    @SerializedName("SERIAL")
    @Expose
    private String sERIAL;
    @SerializedName("ARTICULO")
    @Expose
    private String aRTICULO;
    @SerializedName("FACTURA")
    @Expose
    private Integer fACTURA;
    @SerializedName("URL_FACTURA")
    @Expose
    private String uRLFACTURA;

    public String getSERIAL() {
        return sERIAL;
    }

    public void setSERIAL(String sERIAL) {
        this.sERIAL = sERIAL;
    }

    public String getARTICULO() {
        return aRTICULO;
    }

    public void setARTICULO(String aRTICULO) {
        this.aRTICULO = aRTICULO;
    }

    public Integer getFACTURA() {
        return fACTURA;
    }

    public void setFACTURA(Integer fACTURA) {
        this.fACTURA = fACTURA;
    }

    public String getURLFACTURA() {
        return uRLFACTURA;
    }

    public void setURLFACTURA(String uRLFACTURA) {
        this.uRLFACTURA = uRLFACTURA;
    }
}
