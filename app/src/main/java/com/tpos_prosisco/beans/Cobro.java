package com.tpos_prosisco.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Creada por Norman el 11/18/2020
 **/
public class Cobro {

    @SerializedName("FACTURA")
    @Expose
    private Integer fACTURA;
    @SerializedName("SALDO")
    @Expose
    private Double sALDO;
    @SerializedName("FECHA")
    @Expose
    private String fECHA;
    @SerializedName("VENCIMIENTO")
    @Expose
    private String vENCIMIENTO;
    @SerializedName("COMENTARIO")
    @Expose
    private String cOMENTARIO;

    public Integer getFACTURA() {
        return fACTURA;
    }

    public void setFACTURA(Integer fACTURA) {
        this.fACTURA = fACTURA;
    }

    public Double getSALDO() {
        return sALDO;
    }

    public void setSALDO(Double sALDO) {
        this.sALDO = sALDO;
    }

    public String getFECHA() {
        return fECHA;
    }

    public void setFECHA(String fECHA) {
        this.fECHA = fECHA;
    }

    public String getVENCIMIENTO() {
        return vENCIMIENTO;
    }

    public void setVENCIMIENTO(String vENCIMIENTO) {
        this.vENCIMIENTO = vENCIMIENTO;
    }

    public String getCOMENTARIO() {
        return cOMENTARIO;
    }

    public void setCOMENTARIO(String cOMENTARIO) {
        this.cOMENTARIO = cOMENTARIO;
    }
}
