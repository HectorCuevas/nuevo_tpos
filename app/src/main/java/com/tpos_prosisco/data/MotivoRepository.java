package com.tpos_prosisco.data;

import android.app.Application;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.MotivoService;
import com.tpos_prosisco.beans.CodigoMotivo;
import com.tpos_prosisco.beans.Motivo;
import com.tpos_prosisco.beans.Responses.CodigoMotivoResponse;
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tpos_prosisco.ApplicationTpos.IMEI;

/**
 * Creada por Norman el 11/10/2020
 **/
public class MotivoRepository {
    private AppClient appClient;
    private MotivoService motivoService;

    public MotivoRepository(Application application){
        appClient = AppClient.getInstance();
        motivoService = appClient.getMotivoService();
    }
    public MutableLiveData<Integer> setMotivo(Motivo motivo) {
        final MutableLiveData<Integer> resMut = new MutableLiveData<Integer>();
        Call<Integer> call = motivoService.setMotivo(motivo);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Object value = response.body();
                if(response.isSuccessful()) {
                    Toast.makeText(ApplicationTpos.getInstance(), "Se ha enviado exitosamente", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha ido mal", Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });
        return resMut;
    }

    public MutableLiveData<List<CodigoMotivo>> getMotivos(){
        final MutableLiveData<List<CodigoMotivo>> motivos = new MutableLiveData<List<CodigoMotivo>>();
        Call<CodigoMotivoResponse> call = motivoService.getMotivos(IMEI);
        call.enqueue(new Callback<CodigoMotivoResponse>() {
            @Override
            public void onResponse(Call<CodigoMotivoResponse> call, Response<CodigoMotivoResponse> response) {
                if(response.isSuccessful()){
                    List<CodigoMotivo> reporte = Arrays.asList(response.body().getCodigoMotivos());
                    motivos.setValue(reporte);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CodigoMotivoResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return motivos;
    }

}
