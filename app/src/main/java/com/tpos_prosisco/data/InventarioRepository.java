package com.tpos_prosisco.data;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.ReporteService;
import com.tpos_prosisco.beans.Inventario;
import com.tpos_prosisco.beans.ReporteVenta;
import com.tpos_prosisco.beans.Responses.InventarioResponse;
import com.tpos_prosisco.beans.Responses.ReporteVentaResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tpos_prosisco.ApplicationTpos.IMEI;

/**
 * Creada por Norman el 9/21/2020
 **/
public class InventarioRepository {
    private AppClient appClient;
    private ReporteService reporteService;

    public InventarioRepository() {
        this.appClient = AppClient.getInstance();
        reporteService =  appClient.getReporteService();
    }
    public MutableLiveData<List<Inventario>> getReporte(String ruta){
        final MutableLiveData<List<Inventario>> reporteVentas = new MutableLiveData<List<Inventario>>();
        Call<InventarioResponse> call = reporteService.getReporteInventario(IMEI, ruta);
        call.enqueue(new Callback<InventarioResponse>() {
            @Override
            public void onResponse(Call<InventarioResponse> call, Response<InventarioResponse> response) {
                if(response.isSuccessful()){
                    List<Inventario> reporte = Arrays.asList(response.body().getInventario());
                    reporteVentas.setValue(reporte);
                }else{
                    String str = response.errorBody().toString();
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<InventarioResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return reporteVentas;
    }


}
