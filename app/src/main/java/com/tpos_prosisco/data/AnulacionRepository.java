package com.tpos_prosisco.data;

import android.app.Application;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.AnulacionService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Creada por Norman el 9/16/2020
 **/
public class AnulacionRepository {

    private AppClient appClient;
    private AnulacionService anulacionService;

    public AnulacionRepository(Application application){
        appClient = AppClient.getInstance();
        anulacionService = appClient.getAnulacionService();
    }

    public MutableLiveData<String> setAnulacion(String imei, String ruta, int fact_num){

        final MutableLiveData<String> resultMutable = new MutableLiveData<String>();
        Call<String> call = anulacionService.setAnulacion(imei, ruta, fact_num);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Object value = response.body();
                if(response.isSuccessful()) {
                    String result = value.toString().toString();
                    resultMutable.setValue(result);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(),"Algo ha ido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return resultMutable;
    }


}
