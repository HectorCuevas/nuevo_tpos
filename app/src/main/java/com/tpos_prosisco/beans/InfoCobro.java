package com.tpos_prosisco.beans;

/**
 * Creada por Norman el 11/19/2020
 **/
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class InfoCobro {
    @SerializedName("pImei")
    @Expose
    private String pImei;
    @SerializedName("pFACT_NUM")
    @Expose
    private Integer pFACTNUM;
    @SerializedName("pFORMA_PAG")
    @Expose
    private String pFORMAPAG;
    @SerializedName("pCOBRO")
    @Expose
    private Double pCOBRO;
    @SerializedName("pFE_US_IN")
    @Expose
    private String pFEUSIN;
    @SerializedName("pLATITUD")
    @Expose
    private Double pLATITUD;
    @SerializedName("pLONGITUD")
    @Expose
    private Double pLONGITUD;

    public String getPImei() {
        return pImei;
    }

    public void setPImei(String pImei) {
        this.pImei = pImei;
    }

    public Integer getPFACTNUM() {
        return pFACTNUM;
    }

    public void setPFACTNUM(Integer pFACTNUM) {
        this.pFACTNUM = pFACTNUM;
    }

    public String getPFORMAPAG() {
        return pFORMAPAG;
    }

    public void setPFORMAPAG(String pFORMAPAG) {
        this.pFORMAPAG = pFORMAPAG;
    }

    public Double getPCOBRO() {
        return pCOBRO;
    }

    public void setPCOBRO(Double pCOBRO) {
        this.pCOBRO = pCOBRO;
    }

    public String getPFEUSIN() {
        return pFEUSIN;
    }

    public void setPFEUSIN(String pFEUSIN) {
        this.pFEUSIN = pFEUSIN;
    }

    public Double getPLATITUD() {
        return pLATITUD;
    }

    public void setPLATITUD(Double pLATITUD) {
        this.pLATITUD = pLATITUD;
    }

    public Double getPLONGITUD() {
        return pLONGITUD;
    }

    public void setPLONGITUD(Double pLONGITUD) {
        this.pLONGITUD = pLONGITUD;
    }
}
