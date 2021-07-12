package com.tpos_prosisco.beans;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "vendedor")
public class Vendedor {
    public Vendedor(String cOVEN, String vENDES) {
        this.cOVEN = cOVEN;
        this.vENDES = vENDES;
    }

    @SerializedName("CO_VEN")
    @Expose
    private String cOVEN;
    @SerializedName("VEN_DES")
    @Expose
    private String vENDES;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getCOVEN() {
        return cOVEN;
    }

    public void setCOVEN(String cOVEN) {
        this.cOVEN = cOVEN;
    }

    public String getVENDES() {
        return vENDES;
    }

    public void setVENDES(String vENDES) {
        this.vENDES = vENDES;
    }
}
