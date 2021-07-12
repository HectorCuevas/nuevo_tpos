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
import com.tpos_prosisco.beans.Responses.ReporteFactura;
import com.tpos_prosisco.data.ReporteVentaViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class ReporteFacturaActivity extends AppCompatActivity {

    private ListView lstReporte;
    private ReporteVentaViewModel reporteVentaViewModel;
    private ReporteFacturaAdapter reporteVentaAdapter;
    private LottieAnimationView lottieAnimationView;
    private TextView lblMensaje;
    List<ReporteFactura> facturas = new ArrayList<ReporteFactura>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_factura);
        lstReporte = findViewById(R.id.lstReporte);
        lblMensaje = findViewById(R.id.lblReporteMensaje);
        reporteVentaViewModel = ViewModelProviders.of(this)
                .get(ReporteVentaViewModel.class);

        lottieAnimationView = findViewById(R.id.animationView);

        getReporte(logueoInfo.getCoSucu().trim(), "Hoy");

        lstReporte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = facturas.get(position).getURL_FACTURA().trim();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void getReporte(String ruta, String dia) {

        reporteVentaViewModel.getReporteFactura(ruta, dia).observe(ReporteFacturaActivity.this, new Observer<List<ReporteFactura>>() {
            @Override
            public void onChanged(List<ReporteFactura> reporteFacturas) {
                reporteVentaAdapter = new ReporteFacturaAdapter(getApplicationContext(), reporteFacturas);
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
