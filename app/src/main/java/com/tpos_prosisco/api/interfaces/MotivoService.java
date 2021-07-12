package com.tpos_prosisco.api.interfaces;

import com.tpos_prosisco.beans.Motivo;
import com.tpos_prosisco.beans.Responses.CodigoMotivoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Creada por Norman el 11/9/2020
 **/
public interface MotivoService {

    @Headers({
            "Content-type: application/json"
    })
    @POST("Compra")
    Call<Integer> setMotivo(@Body Motivo motivo);

    String API_ROUTE = "Compra/Motivo/{imei}";
    @GET(API_ROUTE)
    Call<CodigoMotivoResponse> getMotivos(
            @Path("imei") String imei
    );
}
