package com.tpos_prosisco.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.ReporteFacturaAdapter;
import com.tpos_prosisco.adapters.ReporteImeiAdapter;
import com.tpos_prosisco.beans.Imei;
import com.tpos_prosisco.beans.Responses.ReporteFactura;
import com.tpos_prosisco.data.ReporteVentaViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class ReporteImeiActivity extends AppCompatActivity {
    private ListView lstReporte;
    private ReporteVentaViewModel reporteVentaViewModel;
    private ReporteImeiAdapter reporteVentaAdapter;
    private LottieAnimationView lottieAnimationView;
    private TextView lblMensaje;
    List<Imei> facturas = new ArrayList<Imei>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_imei);

        lstReporte = findViewById(R.id.lstReporte);
        lblMensaje = findViewById(R.id.lblReporteMensaje);
        reporteVentaViewModel = ViewModelProviders.of(this)
                .get(ReporteVentaViewModel.class);

        lottieAnimationView = findViewById(R.id.animationView);
        getReporte(logueoInfo.getCoSucu().trim(), "Hoy");

        lstReporte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = facturas.get(position).getURLFACTURA().trim();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
    private void getReporte(String ruta, String dia) {

        reporteVentaViewModel.getReporteImei(ruta, dia).observe(ReporteImeiActivity.this, new Observer<List<Imei>>() {
            @Override
            public void onChanged(List<Imei> reporteFacturas) {
                reporteVentaAdapter = new ReporteImeiAdapter(getApplicationContext(), reporteFacturas);
                lstReporte.setAdapter(reporteVentaAdapter);
                facturas = reporteFacturas;
                if(reporteFacturas.size() == 0){
                    lstReporte.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lblMensaje.setVisibility(View.VISIBLE);
                }
                reporteVentaViewModel.getReporteImei(ruta, dia).removeObserver(this);
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