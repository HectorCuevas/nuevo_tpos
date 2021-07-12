package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.ReporteImeiSerial;

/**
 * Creada por Norman el 2/8/2021
 **/
public class ReporteImeiSerialResponse {

    @SerializedName("Ventas")
    @Expose
    ReporteImeiSerial[] ReporteImeiSerialResponse;

    public ReporteImeiSerial[] getReporteImeiSerialResponse() {
        return ReporteImeiSerialResponse;
    }

    public void setReporteImeiSerialResponse(ReporteImeiSerial[] reporteImeiSerialResponse) {
        ReporteImeiSerialResponse = reporteImeiSerialResponse;
    }
}
