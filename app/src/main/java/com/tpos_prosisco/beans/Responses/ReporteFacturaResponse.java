package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReporteFacturaResponse {

    public ReporteFactura[] getReporteFacturas() {
        return reporteFacturas;
    }

    public void setReporteFacturas(ReporteFactura[] reporteFacturas) {
        this.reporteFacturas = reporteFacturas;
    }

    @SerializedName("Ventas")
    @Expose
    private ReporteFactura[] reporteFacturas;

}
