package com.tpos_prosisco.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.ReporteFacturaAdapter;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Responses.ReporteFactura;
import com.tpos_prosisco.data.AnulacionViewModel;
import com.tpos_prosisco.data.ReporteVentaViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.carrito;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class AnulacionActivity extends AppCompatActivity {
    private ListView lstReporte;
    private ReporteVentaViewModel reporteVentaViewModel;
    private AnulacionViewModel anulacionViewModel;
    private ReporteFacturaAdapter reporteVentaAdapter;
    private LottieAnimationView lottieAnimationView;
    private TextView lblMensaje;
    private BottomSheetDialog mBottomSheetDialog;
    List<ReporteFactura> facturas = new ArrayList<ReporteFactura>();
    String ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anulacion);
        lstReporte = findViewById(R.id.lstReporte);
        lblMensaje = findViewById(R.id.lblReporteMensaje);
        reporteVentaViewModel = ViewModelProviders.of(this)
                .get(ReporteVentaViewModel.class);

        anulacionViewModel =  ViewModelProviders.of(this)
                .get(AnulacionViewModel.class);

        lottieAnimationView = findViewById(R.id.animationView);

        ruta = logueoInfo.getCoSucu().trim();

        getReporte(ruta, "Hoy");

        lstReporte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int fact_num = facturas.get(position).getFACTURA();
                getConfirmacion(fact_num);
            }
        });
    }

    private void getReporte(String ruta, String dia) {

        reporteVentaViewModel.getReporteFactura(ruta, dia).observe(AnulacionActivity.this, new Observer<List<ReporteFactura>>() {
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

    @NonNull
    private void getConfirmacion(int fact_num){
        View modal_confirmacion = getLayoutInflater().inflate(R.layout.bottom_sheet_confirmacion, null);
        mBottomSheetDialog = new BottomSheetDialog(AnulacionActivity.this);
        mBottomSheetDialog.setContentView(modal_confirmacion);
        mBottomSheetDialog.show();
        modal_confirmacion.findViewById(R.id.button_closeDialog1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        modal_confirmacion.findViewById(R.id.button_okDialog1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText txtcantidad = modal_confirmacion.findViewById(R.id.txtConfirmacion);
                boolean confirmacion = txtcantidad.getText().toString().trim().toLowerCase().equals("s");
                if(confirmacion){

                    final Observer<String> observer = new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable final String str) {
                            Toast.makeText(getApplicationContext(), " "+str, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    };

                    anulacionViewModel.setAnulacion(IMEI, ruta, fact_num).observe(AnulacionActivity.this, observer);
                }else
                    Toast.makeText(getApplicationContext(), "No Confirmacion", Toast.LENGTH_SHORT).show();

                mBottomSheetDialog.dismiss();
            }
        });
    }
}