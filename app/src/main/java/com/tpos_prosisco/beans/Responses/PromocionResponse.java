package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Promocion;

public class PromocionResponse {

    public Promocion[] getPromociones() {
        return promociones;
    }

    public void setPromociones(Promocion[] promociones) {
        this.promociones = promociones;
    }

    @SerializedName("Promociones")
    @Expose
    private Promocion[] promociones;


}
