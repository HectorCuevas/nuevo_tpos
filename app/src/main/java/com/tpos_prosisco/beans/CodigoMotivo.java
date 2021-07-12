package com.tpos_prosisco.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Creada por Norman el 11/12/2020
 **/
public class CodigoMotivo {
    @SerializedName("co_motivo")
    @Expose
    private int co_motivo;
    @SerializedName("motivo")
    @Expose
    private String motivo;
    @SerializedName("require_comentario")
    @Expose
    private boolean require_comentario;

    public int getCo_motivo() {
        return co_motivo;
    }

    public void setCo_motivo(int co_motivo) {
        this.co_motivo = co_motivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean getRequire_comentario() {
        return require_comentario;
    }

    public void setRequire_comentario(boolean require_comentario) {
        this.require_comentario = require_comentario;
    }


}
