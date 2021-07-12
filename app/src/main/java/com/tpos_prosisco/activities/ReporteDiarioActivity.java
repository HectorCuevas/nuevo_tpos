package com.tpos_prosisco.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.ReporteDiarioAdapter;
import com.tpos_prosisco.adapters.ReporteImeiAdapter;
import com.tpos_prosisco.beans.Imei;
import com.tpos_prosisco.beans.VentaDiaria;
import com.tpos_prosisco.data.ReporteVentaViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class ReporteDiarioActivity extends AppCompatActivity {
    private ListView lstReporte;
    private ReporteVentaViewModel reporteVentaViewModel;
    private ReporteDiarioAdapter reporteVentaAdapter;
    private LottieAnimationView lottieAnimationView;
    private TextView lblMensaje;
    List<VentaDiaria> facturas = new ArrayList<VentaDiaria>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_diario);

        lstReporte = findViewById(R.id.lstReporte);
        lblMensaje = findViewById(R.id.lblReporteMensaje);
        reporteVentaViewModel = ViewModelProviders.of(this)
                .get(ReporteVentaViewModel.class);
        lottieAnimationView = findViewById(R.id.animationView);
        getReporte(logueoInfo.getCoSucu().trim(), "Hoy");

    }

    private void getReporte(String ruta, String dia) {

        reporteVentaViewModel.getReporteVentaDiaria(ruta, dia).observe(ReporteDiarioActivity.this, new Observer<List<VentaDiaria>>() {
            @Override
            public void onChanged(List<VentaDiaria> reporteFacturas) {
                reporteVentaAdapter = new ReporteDiarioAdapter(getApplicationContext(), reporteFacturas);
                lstReporte.setAdapter(reporteVentaAdapter);
                facturas = reporteFacturas;
                if(reporteFacturas.size() == 0){
                    lstReporte.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lblMensaje.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent changePrefs = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        startActivity(changePrefs);
        if (changePrefs.resolveActivity(getPackageManager()) != null) {
            startActivity(changePrefs);
        }
        return super.onKeyDown(keyCode, event);
    }
}