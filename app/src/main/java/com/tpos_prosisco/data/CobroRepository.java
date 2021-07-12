package com.tpos_prosisco.data;

import android.app.Application;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.CobroService;
import com.tpos_prosisco.beans.Cobro;
import com.tpos_prosisco.beans.InfoCobro;
import com.tpos_prosisco.beans.Motivo;
import com.tpos_prosisco.beans.Movimiento;
import com.tpos_prosisco.beans.Responses.CobroResponse;
import com.tpos_prosisco.beans.Responses.MovimientoResponse;

import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.tpos_prosisco.ApplicationTpos.IMEI;

/**
 * Creada por Norman el 11/18/2020
 **/
public class CobroRepository {
    private AppClient appClient;
    private CobroService cobroService;

    public CobroRepository(Application application){
        appClient = AppClient.getInstance();
        cobroService = appClient.getcobroService();
    }

    public MutableLiveData<List<Cobro>> getCobros(String cod){
        final MutableLiveData<List<Cobro>> cobros = new MutableLiveData<List<Cobro>>();
        Call<CobroResponse> call = cobroService.getCobros(IMEI, cod);
        call.enqueue(new Callback<CobroResponse>() {
            @Override
            public void onResponse(Call<CobroResponse> call, Response<CobroResponse> response) {
                if(response.isSuccessful()){
                    List<Cobro> reporte = Arrays.asList(response.body().getCobros());
                    cobros.setValue(reporte);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CobroResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return cobros ;
    }
    public MutableLiveData<List<Movimiento>> getMovimientos(String cod){
        final MutableLiveData<List<Movimiento>> cobros = new MutableLiveData<List<Movimiento>>();
        Call<MovimientoResponse> call = cobroService.getMovimientos(IMEI, cod);
        call.enqueue(new Callback<MovimientoResponse>() {
            @Override
            public void onResponse(Call<MovimientoResponse> call, Response<MovimientoResponse> response) {
                if(response.isSuccessful()){
                    List<Movimiento> reporte = Arrays.asList(response.body().getMovimientos());
                    cobros.setValue(reporte);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MovimientoResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return cobros;
    }


    public MutableLiveData<Integer> setCobro(InfoCobro cobro) {
        final MutableLiveData<Integer> resMut = new MutableLiveData<Integer>();
        Call<Integer> call = cobroService.setCobro(cobro);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Object value = response.body();
                if(response.isSuccessful()) {
                    if(Integer.parseInt(value.toString()) > 0){
                        Toast.makeText(ApplicationTpos.getInstance(), "Enviado exitosamente", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ApplicationTpos.getInstance(), "Algo ha ido mal: No se ha enviado el cobro", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha ido mal: No se ha enviado el cobro", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return resMut;
    }

}
