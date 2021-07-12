package com.tpos_prosisco.api.interfaces;

import com.tpos_prosisco.beans.Cobro;
import com.tpos_prosisco.beans.InfoCobro;
import com.tpos_prosisco.beans.Motivo;
import com.tpos_prosisco.beans.Responses.CobroResponse;
import com.tpos_prosisco.beans.Responses.CodigoMotivoResponse;
import com.tpos_prosisco.beans.Responses.MovimientoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Creada por Norman el 11/18/2020
 **/
public interface CobroService {


    @Headers({
            "Content-type: application/json"
    })
    @POST("Cobro")
    Call<Integer> setCobro(@Body InfoCobro cobro);

    String API_ROUTE = "Cobro/{imei}/{cod}";
    @GET(API_ROUTE)
    Call<CobroResponse> getCobros(
            @Path("imei") String imei,
            @Path("cod") String cod
    );

    String API_ROUTE_v2 = "Cobro/Movimientos/{imei}/{cod}";
    @GET(API_ROUTE_v2)
    Call<MovimientoResponse> getMovimientos(
            @Path("imei") String imei,
            @Path("cod") String cod
    );


}
