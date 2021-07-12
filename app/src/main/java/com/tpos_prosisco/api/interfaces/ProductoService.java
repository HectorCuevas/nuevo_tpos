package com.tpos_prosisco.api.interfaces;

import com.tpos_prosisco.beans.Responses.ProductoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductoService {

    String API_ROUTE = "Productos/{imei}/{ruta}";
    @GET(API_ROUTE)
    Call<ProductoResponse> getProductos(
            @Path("imei") String imei,
            @Path("ruta") String ruta
    );

}
