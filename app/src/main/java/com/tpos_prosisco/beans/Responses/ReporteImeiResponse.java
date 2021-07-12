package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.Imei;

/**
 * Creada por Norman el 9/22/2020
 **/
public class ReporteImeiResponse {
    @SerializedName("Ventas")
    @Expose
    private Imei[] imeis;

    public Imei[] getImeis() {
        return imeis;
    }

    public void setImeis(Imei[] imeis) {
        this.imeis = imeis;
    }
}
