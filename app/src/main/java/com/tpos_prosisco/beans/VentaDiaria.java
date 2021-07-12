package com.tpos_prosisco.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Creada por Norman el 9/23/2020
 **/
public class VentaDiaria {
    @SerializedName("DESCRIPCION")
    @Expose
    private String dESCRIPCION;
    @SerializedName("VALOR")
    @Expose
    private double vALOR;

    public String getDESCRIPCION() {
        return dESCRIPCION;
    }

    public void setDESCRIPCION(String dESCRIPCION) {
        this.dESCRIPCION = dESCRIPCION;
    }

    public double getVALOR() {
        return vALOR;
    }

    public void setVALOR(double vALOR) {
        this.vALOR = vALOR;
    }

}
