package com.tpos_prosisco.api.interfaces;

import com.tpos_prosisco.beans.Responses.PromocionResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PromocionService {

    String API_ROUTE = "Promociones/{imei}/{canal}";
    @GET(API_ROUTE)
    Call<PromocionResponse> getPromociones(
            @Path("imei") String imei,
            @Path("canal") String canal
    );
}
