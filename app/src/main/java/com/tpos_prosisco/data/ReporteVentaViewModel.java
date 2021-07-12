package com.tpos_prosisco.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tpos_prosisco.beans.Imei;
import com.tpos_prosisco.beans.ReporteCategoria;
import com.tpos_prosisco.beans.ReporteImeiSerial;
import com.tpos_prosisco.beans.ReporteVendedor;
import com.tpos_prosisco.beans.ReporteVenta;
import com.tpos_prosisco.beans.Responses.ReporteFactura;
import com.tpos_prosisco.beans.VentaDiaria;

import java.util.List;

public class ReporteVentaViewModel extends AndroidViewModel {

    private ReporteVentaRepository reporteVentaRepository;

    public ReporteVentaViewModel(@NonNull Application application) {
        super(application);
        reporteVentaRepository = new ReporteVentaRepository();
    }

    public LiveData<List<ReporteVenta>> getReporte(String ruta, String mes){
        return reporteVentaRepository.getReporte(ruta, mes);
    }
    public LiveData<List<ReporteCategoria>> getReporteCategoria(String ruta, String mes){
        return reporteVentaRepository.getReporteCategoria(ruta, mes);
    }

    public LiveData<List<ReporteFactura>> getReporteFactura(String ruta, String dia){
        return reporteVentaRepository.getReporteFactura(ruta, dia);
    }
    public LiveData<List<ReporteVendedor>> getReporteVendedor(String ruta, String dia){
        return  reporteVentaRepository.getReporteVendedor(ruta, dia);
    }

    public LiveData<List<Imei>> getReporteImei(String ruta, String dia){
        return  reporteVentaRepository.getReporteImei(ruta, dia);
    }

    public LiveData<List<VentaDiaria>> getReporteVentaDiaria(String ruta, String dia){
        return  reporteVentaRepository.getReporteVentaDiaria(ruta, dia);
    }

    public LiveData<String> setTraslado(String imei, String ruta, String fact_num){
        return reporteVentaRepository.setTraslado(imei, ruta, fact_num);
    }

    public LiveData<List<ReporteImeiSerial>> getReporteSerialImei(String ruta, String dia){
        return  reporteVentaRepository.getReporteVentaImeiSerial(ruta, dia);
    }

}
