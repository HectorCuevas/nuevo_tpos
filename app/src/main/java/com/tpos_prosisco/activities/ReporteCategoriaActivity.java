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
import com.tpos_prosisco.adapters.ReporteCategoriaAdapter;
import com.tpos_prosisco.beans.ReporteCategoria;
import com.tpos_prosisco.data.ReporteVentaViewModel;

import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class ReporteCategoriaActivity extends AppCompatActivity {


    private ListView lstReporte;
    private ReporteCategoriaAdapter reporteVentaAdapter;
    private ReporteVentaViewModel reporteVentaViewModel;
    LottieAnimationView lottieAnimationView;
    TextView lblMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_categoria);
        lstReporte = findViewById(R.id.lstReporte);
        lblMensaje = findViewById(R.id.lblReporteMensaje);
        reporteVentaViewModel = ViewModelProviders.of(this)
                .get(ReporteVentaViewModel.class);

        lottieAnimationView = findViewById(R.id.animationView);

        getReporte(logueoInfo.getCoSucu().trim(), "Mes Actual");
    }

    private void getReporte(String ruta, String mes){
        reporteVentaViewModel.getReporteCategoria(ruta, mes).observe(ReporteCategoriaActivity.this, new Observer<List<ReporteCategoria>>() {
            @Override
            public void onChanged(List<ReporteCategoria> reporteCategorias) {
                reporteVentaAdapter = new ReporteCategoriaAdapter(getApplicationContext(), reporteCategorias);
                lstReporte.setAdapter(reporteVentaAdapter);
                reporteVentaAdapter.notifyDataSetChanged();

                if(reporteCategorias.size() == 0){
                    lstReporte.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lblMensaje.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
