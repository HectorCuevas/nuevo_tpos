package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.VentaDiaria;

/**
 * Creada por Norman el 9/23/2020
 **/
public class ReporteVentaDiariaResponse {
    @SerializedName("Ventas")
    @Expose
    private VentaDiaria[] ventaDiaria;

    public VentaDiaria[] getVentaDiaria() {
        return ventaDiaria;
    }

    public void setVentaDiaria(VentaDiaria[] ventaDiaria) {
        this.ventaDiaria = ventaDiaria;
    }
}
