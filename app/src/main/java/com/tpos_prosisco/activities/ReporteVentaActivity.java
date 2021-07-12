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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.ReporteVentaAdapter;
import com.tpos_prosisco.beans.ReporteVenta;
import com.tpos_prosisco.data.ReporteVentaViewModel;

import java.util.List;

public class ReporteVentaActivity extends AppCompatActivity {

    private ListView lstReporte;
    private ReporteVentaAdapter reporteVentaAdapter;
    private ReporteVentaViewModel reporteVentaViewModel;
    LottieAnimationView lottieAnimationView;
    TextView lblMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_venta);
        lstReporte = findViewById(R.id.lstReporte);
        lblMensaje = findViewById(R.id.lblReporteMensaje);
        reporteVentaViewModel = ViewModelProviders.of(this)
                .get(ReporteVentaViewModel.class);

        lottieAnimationView = findViewById(R.id.animationView);

       // Toast.makeText(getApplicationContext(), "String.valueOf(reporteVentas.size())", Toast.LENGTH_LONG).show();

       // getReporte(ApplicationTpos.logueoInfo.getCoSucu().trim(), "Mes Achdfdftual");
        getReporte(ApplicationTpos.logueoInfo.getCoSucu().trim(), "Hoy");
    }

    private void getReporte(String ruta, String mes){
        reporteVentaViewModel.getReporte(ruta, mes).observe(ReporteVentaActivity.this, new Observer<List<ReporteVenta>>() {
            @Override
            public void onChanged(List<ReporteVenta> reporteVentas) {
                reporteVentaAdapter = new ReporteVentaAdapter(getApplicationContext(), reporteVentas);
                lstReporte.setAdapter(reporteVentaAdapter);
                reporteVentaAdapter.notifyDataSetChanged();

                if(reporteVentas.size() == 0){
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
