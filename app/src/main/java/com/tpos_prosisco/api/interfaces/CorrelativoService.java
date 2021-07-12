package com.tpos_prosisco.api.interfaces;

import com.tpos_prosisco.beans.Correlativo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CorrelativoService {

    @Headers({
            "Content-type: application/json"
    })
    @POST("Correlativo")
    Call<Integer> getID(@Body Correlativo correlativo);
}