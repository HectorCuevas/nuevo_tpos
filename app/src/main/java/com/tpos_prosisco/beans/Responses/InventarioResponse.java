package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.Inventario;

/**
 * Creada por Norman el 9/21/2020
 **/
public class InventarioResponse {

    public Inventario[] getInventario() {
        return inventario;
    }

    public void setInventario(Inventario[] inventario) {
        this.inventario = inventario;
    }
    @SerializedName("Ventas")
    @Expose
    private Inventario[] inventario;


}
