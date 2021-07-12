package com.tpos_prosisco.data;

import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.ReporteService;
import com.tpos_prosisco.beans.Imei;
import com.tpos_prosisco.beans.ReporteCategoria;
import com.tpos_prosisco.beans.ReporteImeiSerial;
import com.tpos_prosisco.beans.ReporteVendedor;
import com.tpos_prosisco.beans.ReporteVenta;
import com.tpos_prosisco.beans.Responses.ReporteCategoriaResponse;
import com.tpos_prosisco.beans.Responses.ReporteFactura;
import com.tpos_prosisco.beans.Responses.ReporteFacturaResponse;
import com.tpos_prosisco.beans.Responses.ReporteImeiResponse;
import com.tpos_prosisco.beans.Responses.ReporteImeiSerialResponse;
import com.tpos_prosisco.beans.Responses.ReporteVendedorResponse;
import com.tpos_prosisco.beans.Responses.ReporteVentaDiariaResponse;
import com.tpos_prosisco.beans.Responses.ReporteVentaResponse;
import com.tpos_prosisco.beans.VentaDiaria;

import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tpos_prosisco.ApplicationTpos.IMEI;

public class ReporteVentaRepository {
    private AppClient appClient;
    private ReporteService reporteService;

    public ReporteVentaRepository() {
        appClient = AppClient.getInstance();
        reporteService = appClient.getReporteService();
    }

    public MutableLiveData<List<ReporteVenta>> getReporte(String ruta, String mes){
        final MutableLiveData<List<ReporteVenta>> reporteVentas = new MutableLiveData<List<ReporteVenta>>();
        Call<ReporteVentaResponse> call = reporteService.getReporteVenta(IMEI, ruta, mes);
        call.enqueue(new Callback<ReporteVentaResponse>() {
            @Override
            public void onResponse(Call<ReporteVentaResponse> call, Response<ReporteVentaResponse> response) {
                if(response.isSuccessful()){
                    List<ReporteVenta> reporte = Arrays.asList(response.body().getReporteVentas());
                    reporteVentas.setValue(reporte);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReporteVentaResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return reporteVentas;
    }

    public MutableLiveData<List<ReporteCategoria>> getReporteCategoria(String ruta, String mes){
        final MutableLiveData<List<ReporteCategoria>> reporteVentas = new MutableLiveData<List<ReporteCategoria>>();
        Call<ReporteCategoriaResponse> call = reporteService.getReporteCategoria(IMEI, ruta, mes);
        call.enqueue(new Callback<ReporteCategoriaResponse>() {
            @Override
            public void onResponse(Call<ReporteCategoriaResponse> call, Response<ReporteCategoriaResponse> response) {
                if(response.isSuccessful()){
                    List<ReporteCategoria> reporte = Arrays.asList(response.body().getReporteVentas());
                    reporteVentas.setValue(reporte);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReporteCategoriaResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return reporteVentas;
    }

    public MutableLiveData<List<ReporteFactura>> getReporteFactura(String ruta, String dia){
        final MutableLiveData<List<ReporteFactura>> reporteFact = new MutableLiveData<List<ReporteFactura>>();
        Call<ReporteFacturaResponse> call = reporteService.getReporteFactura(IMEI, ruta, dia);
        call.enqueue(new Callback<ReporteFacturaResponse>() {
            @Override
            public void onResponse(Call<ReporteFacturaResponse> call, Response<ReporteFacturaResponse> response) {
                if(response.isSuccessful()){
                    List<ReporteFactura> reporte = Arrays.asList(response.body().getReporteFacturas());
                    reporteFact.setValue(reporte);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReporteFacturaResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return reporteFact;
    }

    public MutableLiveData<List<ReporteVendedor>> getReporteVendedor(String ruta, String dia){
        final MutableLiveData<List<ReporteVendedor>> reporteFact = new MutableLiveData<List<ReporteVendedor>>();
        Call<ReporteVendedorResponse> call = reporteService.getReporteVendedor(IMEI, ruta, dia);
        call.enqueue(new Callback<ReporteVendedorResponse>() {
            @Override
            public void onResponse(Call<ReporteVendedorResponse> call, Response<ReporteVendedorResponse> response) {
                if(response.isSuccessful()){
                    List<ReporteVendedor> reporte = Arrays.asList(response.body().getReporteVendedores());
                    reporteFact.setValue(reporte);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReporteVendedorResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return reporteFact;
    }

    public MutableLiveData<List<Imei>> getReporteImei(String ruta, String dia){
        final MutableLiveData<List<Imei>> reporteFact = new MutableLiveData<List<Imei>>();
        Call<ReporteImeiResponse> call = reporteService.getReporteIMEI(IMEI, ruta, dia);
        call.enqueue(new Callback<ReporteImeiResponse>() {
            @Override
            public void onResponse(Call<ReporteImeiResponse> call, Response<ReporteImeiResponse> response) {
                if(response.isSuccessful()){
                    List<Imei> reporte = Arrays.asList(response.body().getImeis());
                    reporteFact.setValue(reporte);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReporteImeiResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return reporteFact;
    }
    public MutableLiveData<List<VentaDiaria>> getReporteVentaDiaria(String ruta, String dia){
        final MutableLiveData<List<VentaDiaria>> reporteFact = new MutableLiveData<List<VentaDiaria>>();
        Call<ReporteVentaDiariaResponse> call = reporteService.getReporteVentaDiaria(IMEI, ruta, dia);
        call.enqueue(new Callback<ReporteVentaDiariaResponse>() {
            @Override
            public void onResponse(Call<ReporteVentaDiariaResponse> call, Response<ReporteVentaDiariaResponse> response) {
                if(response.isSuccessful()){
                    List<VentaDiaria> reporte = Arrays.asList(response.body().getVentaDiaria());
                    reporteFact.setValue(reporte);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReporteVentaDiariaResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return reporteFact;
    }

    public MutableLiveData<String> setTraslado(String imei, String ruta, String fact_num) {

        final MutableLiveData<String> resultMutable = new MutableLiveData<String>();
        Call<String> call = reporteService.getReporteTraslado(imei, ruta, fact_num);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Object value = response.body();
                if (response.isSuccessful()) {
                    String result = value.toString().toString();
                    resultMutable.setValue(result);
                } else {
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha ido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return resultMutable;
    }
    public MutableLiveData<List<ReporteImeiSerial>> getReporteVentaImeiSerial(String imei, String serial){
        final MutableLiveData<List<ReporteImeiSerial>> reporteFact = new MutableLiveData<List<ReporteImeiSerial>>();
        Call<ReporteImeiSerialResponse> call = reporteService.getReporteImeiSerial(imei, serial);
        call.enqueue(new Callback<ReporteImeiSerialResponse>() {
            @Override
            public void onResponse(Call<ReporteImeiSerialResponse> call, Response<ReporteImeiSerialResponse> response) {
                if(response.isSuccessful()){
                    List<ReporteImeiSerial> reporte = Arrays.asList(response.body().getReporteImeiSerialResponse());
                    reporteFact.setValue(reporte);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReporteImeiSerialResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return reporteFact;
    }

}
