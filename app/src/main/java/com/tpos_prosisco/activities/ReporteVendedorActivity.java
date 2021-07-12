package com.tpos_prosisco.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.ReporteVendedorAdapter;
import com.tpos_prosisco.beans.ReporteVendedor;
import com.tpos_prosisco.data.ReporteVentaViewModel;

import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class ReporteVendedorActivity extends AppCompatActivity {

    private ListView lstReporte;
    private ReporteVendedorAdapter reporteVendedorAdapter;
    LottieAnimationView lottieAnimationView;
    private ReporteVentaViewModel reporteVentaViewModel;
    TextView lblMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_vendedor);
        lstReporte = findViewById(R.id.lstReporte);
        lblMensaje = findViewById(R.id.lblReporteMensaje);

        reporteVentaViewModel = ViewModelProviders.of(this)
                .get(ReporteVentaViewModel.class);

        lottieAnimationView = findViewById(R.id.animationView);

        getReporte(logueoInfo.getCoSucu().trim(), "Hoy");

    }
    private void getReporte(String ruta, String mes){
        reporteVentaViewModel.getReporteVendedor(ruta, mes ).observe(ReporteVendedorActivity.this, new Observer<List<ReporteVendedor>>() {
            @Override
            public void onChanged(List<ReporteVendedor> reporteVendedors) {
                reporteVendedorAdapter = new ReporteVendedorAdapter(getApplicationContext(), reporteVendedors);
                lstReporte.setAdapter(reporteVendedorAdapter);

                reporteVendedorAdapter.notifyDataSetChanged();

                if(reporteVendedors.size() ==0){
                    lstReporte.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lblMensaje.setVisibility(View.VISIBLE);
                }

            }
        });
    }

}

