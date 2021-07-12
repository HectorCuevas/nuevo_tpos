package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.ReporteVendedor;

public class ReporteVendedorResponse {

    public ReporteVendedor[] getReporteVendedores() {
        return reporteVendedores;
    }

    public void setReporteVendedores(ReporteVendedor[] reporteVendedores) {
        this.reporteVendedores = reporteVendedores;
    }

    @SerializedName("Ventas")
    @Expose
    private ReporteVendedor[] reporteVendedores;



}
