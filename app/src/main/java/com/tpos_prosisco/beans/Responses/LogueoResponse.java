package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.Logueo;

public class LogueoResponse {

    public Logueo[] getLogueos() {
        return logueos;
    }

    public void setLogueos(Logueo[] logueos) {
        this.logueos = logueos;
    }
    @SerializedName("Logueo")
    @Expose
    private Logueo[] logueos;
}
