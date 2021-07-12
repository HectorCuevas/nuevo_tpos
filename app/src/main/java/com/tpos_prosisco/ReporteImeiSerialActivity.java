package com.tpos_prosisco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tpos_prosisco.activities.PromocionesActivity;
import com.tpos_prosisco.activities.ReporteVendedorActivity;
import com.tpos_prosisco.adapters.ReporteImeiSerialAdapter;
import com.tpos_prosisco.adapters.ReporteVendedorAdapter;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.ReporteImeiSerial;
import com.tpos_prosisco.beans.ReporteVendedor;
import com.tpos_prosisco.data.ReporteVentaViewModel;

import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class ReporteImeiSerialActivity extends AppCompatActivity {

    private ListView lstReporte;
    private ReporteImeiSerialAdapter reporteVendedorAdapter;
    LottieAnimationView lottieAnimationView;
    private ReporteVentaViewModel reporteVentaViewModel;
    TextView lblMensaje;
    private View modal;
    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_imei_serial);

        lstReporte = findViewById(R.id.lstReporte);
        lblMensaje = findViewById(R.id.lblReporteMensaje);

        reporteVentaViewModel = ViewModelProviders.of(this)
                .get(ReporteVentaViewModel.class);

        lottieAnimationView = findViewById(R.id.animationView);
        modal = getLayoutInflater().inflate(R.layout.bottom_sheet_serial, null);
        setWindow(modal);

        lstReporte.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReporteImeiSerial reporte = (ReporteImeiSerial) reporteVendedorAdapter.getItem(position);
                String url = reporte.getURLFACTURA().trim();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    private void getReporte(String imei, String serial){
        reporteVentaViewModel.getReporteSerialImei(imei, serial).observe(ReporteImeiSerialActivity.this, new Observer<List<ReporteImeiSerial>>() {
            @Override
            public void onChanged(List<ReporteImeiSerial> reporteVendedors) {
                reporteVendedorAdapter = new ReporteImeiSerialAdapter(getApplicationContext(), reporteVendedors);
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

    private void setWindow(View modal) {
        mBottomSheetDialog = new BottomSheetDialog(ReporteImeiSerialActivity.this);
        mBottomSheetDialog.setContentView(modal);
        mBottomSheetDialog.show();
        modal.findViewById(R.id.button_okDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtserial = modal.findViewById(R.id.txtComentario);
                String serial = txtserial.getText().toString();
               // 358300063954186/18379
                if(serial.length() > 5){
                    //getReporte("358300063954186","18379");
                    getReporte(IMEI,serial);
                    mBottomSheetDialog.dismiss();
                }else
                    Toast.makeText(getApplicationContext(), "El serial debe ser mayor a 5 digitos", Toast.LENGTH_SHORT).show();
            }
        });
        modal.findViewById(R.id.button_closeDialog).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();

            }
        });
    }

}