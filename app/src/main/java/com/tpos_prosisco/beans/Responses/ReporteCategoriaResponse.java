package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.ReporteCategoria;
import com.tpos_prosisco.beans.ReporteVenta;

public class ReporteCategoriaResponse {

    @SerializedName("Ventas")
    @Expose
    private ReporteCategoria[] reporteVentas;

    public ReporteCategoria[] getReporteVentas() {
        return reporteVentas;
    }

    public void setReporteVentas(ReporteCategoria[] reporteVentas) {
        this.reporteVentas = reporteVentas;
    }
}
