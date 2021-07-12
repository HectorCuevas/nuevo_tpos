package com.tpos_prosisco.beans.Responses;

import com.google.gson.annotations.SerializedName;
import com.tpos_prosisco.beans.Cliente;

public class ClienteResponse {

    public Cliente[] getClientes() {
        return clientes;
    }

    public void setClientes(Cliente[] clientes) {
        this.clientes = clientes;
    }

    @SerializedName("Clientes")
    private Cliente[] clientes;
}
