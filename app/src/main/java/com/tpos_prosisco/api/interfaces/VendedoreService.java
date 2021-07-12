package com.tpos_prosisco.api.interfaces;

import com.tpos_prosisco.beans.Responses.VendedorResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VendedoreService {

    String API_ROUTE = "Vendedores/{imei}/{ruta}";
    @GET(API_ROUTE)
    Call<VendedorResponse> getVendedores(
            @Path("imei") String imei,
            @Path("ruta") String ruta
    );

}
