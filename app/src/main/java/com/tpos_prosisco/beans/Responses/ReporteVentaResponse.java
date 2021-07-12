package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.ReporteVenta;

public class ReporteVentaResponse {

    @SerializedName("Ventas")
    @Expose
    private ReporteVenta[] reporteVentas;

    public ReporteVenta[] getReporteVentas() {
        return reporteVentas;
    }

    public void setReporteVentas(ReporteVenta[] reporteVentas) {
        this.reporteVentas = reporteVentas;
    }

}
