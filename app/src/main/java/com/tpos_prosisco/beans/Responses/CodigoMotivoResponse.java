package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.CodigoMotivo;
import com.tpos_prosisco.beans.ReporteVenta;

/**
 * Creada por Norman el 11/12/2020
 **/
public class CodigoMotivoResponse {

    @SerializedName("Motivo")
    @Expose
    private CodigoMotivo[] codigoMotivos;

    public CodigoMotivo[] getCodigoMotivos() {
        return codigoMotivos;
    }

    public void setCodigoMotivos(CodigoMotivo[] codigoMotivos) {
        this.codigoMotivos = codigoMotivos;
    }
}
