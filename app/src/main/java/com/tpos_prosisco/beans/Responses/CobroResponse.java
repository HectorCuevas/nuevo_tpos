package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.Cobro;

/**
 * Creada por Norman el 11/18/2020
 **/
public class CobroResponse {

    @SerializedName("Cobros")
    @Expose
    private Cobro[] cobros;

    public Cobro[] getCobros() {
        return cobros;
    }

    public void setCobros(Cobro[] cobros) {
        this.cobros = cobros;
    }
}
