package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.Movimiento;

/**
 * Creada por Norman el 11/30/2020
 **/
public class MovimientoResponse {

    @SerializedName("Cobros")
    @Expose
    private Movimiento[] movimientos;


    public Movimiento[] getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Movimiento[] movimientos) {
        this.movimientos = movimientos;
    }
}
