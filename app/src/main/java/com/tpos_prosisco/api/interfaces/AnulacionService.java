package com.tpos_prosisco.api.interfaces;

import com.tpos_prosisco.beans.Responses.ProductoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Creada por Norman el 9/16/2020
 **/
public interface AnulacionService {

    String API_ROUTE = "Reportes/Anulacion/{imei}/{ruta}/{fact_num}";
    @GET(API_ROUTE)
    Call<String> setAnulacion(
            @Path("imei") String imei,
            @Path("ruta") String ruta,
            @Path("fact_num") int fact_num
    );
}
