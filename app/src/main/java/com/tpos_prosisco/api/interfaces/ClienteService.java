package com.tpos_prosisco.api.interfaces;

import com.tpos_prosisco.beans.Responses.ClienteResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ClienteService {

    String API_ROUTE = "Clientes/{imei}/{ruta}/{canal}";
    @GET(API_ROUTE)
    Call<ClienteResponse> getClientes(
            @Path("imei") String imei,
            @Path("ruta") String ruta,
            @Path("canal") String canal
    );
}
