package com.tpos_prosisco.api.interfaces;

import com.tpos_prosisco.beans.Responses.LogueoResponse;
import com.tpos_prosisco.beans.Sesion;
import com.tpos_prosisco.beans.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LogueoService {

    @Headers({
            "Content-type: application/json"
    })

    @POST("Logueo")
    Call<Integer> getUser(@Body User user);

    @POST("Logueo/Sesion")
   // @GET("Logueo")
    Call<LogueoResponse> setLogueo(@Body Sesion sesion);
}
