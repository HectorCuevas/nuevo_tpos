package com.tpos_prosisco.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.airbnb.lottie.LottieAnimationView;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.FacturaAdapter;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.CorrelativoService;
import com.tpos_prosisco.beans.Correlativo;
import com.tpos_prosisco.beans.DetalleFactura;
import com.tpos_prosisco.beans.Factura;
import com.tpos_prosisco.beans.FacturasConDetalles;
import com.tpos_prosisco.data.CorrelativoViewModel;
import com.tpos_prosisco.data.FacturaViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class FacturaActivity extends AppCompatActivity {

    private ListView lstFacturas;
    private FacturaAdapter facturaAdapter;
    private FacturaViewModel facturaViewModel;
    List<FacturasConDetalles> facturas = new ArrayList<>();
    List<Factura> facturasParaEnviar = new ArrayList<>();;
    private CorrelativoViewModel correlativoViewModel;
    private ProgressDialog pdialog;
    private LottieAnimationView lottieAnimationView;
    private TextView lblMensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_factura);
        lottieAnimationView = findViewById(R.id.animationView);
        lblMensaje = findViewById(R.id.lblReporteMensaje);
        lstFacturas = findViewById(R.id.lstFcaturas);

        facturaViewModel = ViewModelProviders.of(this)
                .get(FacturaViewModel.class);
            correlativoViewModel = ViewModelProviders.of(this)
                    .get(CorrelativoViewModel.class);

        getFacturas();

        lstFacturas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isNetworkConnected()) {
                    createSimpleDialog(position);

                }else{
                    AestheticDialog.showConnectify(FacturaActivity.this, "No cuenta con conexión a internet", AestheticDialog.ERROR);
                }
            }
        });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    void getFacturas(){
        //Toast.makeText(getApplicationContext(), "Aqui", Toast.LENGTH_LONG).show();
        facturaViewModel.getFacturas().observe(FacturaActivity.this, new Observer<List<FacturasConDetalles>>() {
            @Override
            public void onChanged(List<FacturasConDetalles> facturasConDetalles) {
                facturas = facturasConDetalles;
                facturaAdapter = new FacturaAdapter(FacturaActivity.this, facturasConDetalles);
                lstFacturas.setAdapter(facturaAdapter);
                facturaAdapter.notifyDataSetChanged();
                sendFactura();
                if(facturasConDetalles.size() ==0){
                    lstFacturas.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lblMensaje.setVisibility(View.VISIBLE);
                }
              //  Toast.makeText(getApplicationContext(), String.valueOf(facturas.size()), Toast.LENGTH_SHORT).show();
                facturaViewModel.getFacturas().removeObserver(this);
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




    private void sendFactura() {

        for(FacturasConDetalles factura :  facturas){
            List<DetalleFactura> detalleFacturas =  new ArrayList<>();
            for(DetalleFactura detalle :  factura.detalleFacturaList){
                if( factura.factura.getId() == detalle.getId_factura())
                    detalleFacturas.add(detalle);
            }
            factura.factura.setItems(detalleFacturas);
            facturasParaEnviar.add(factura.factura);
        }
       // Toast.makeText(getApplicationContext(), String.valueOf(facturasParaEnviar.size()), Toast.LENGTH_SHORT).show();
    }

    public void createSimpleDialog(int pos) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Desea enviar la factura?");
        builder1.setCancelable(true);

        FacturasConDetalles factura =  (FacturasConDetalles) facturaAdapter.getItem(pos);// .get(position);
        builder1.setPositiveButton(
                "Enviar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        sendFactura2(factura.factura, pos);
                    }
                });

        builder1.setNegativeButton(
                "Eliminar factura",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        facturaViewModel.deleteById(factura.factura);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void sendFactura2(Factura factura, int pos) {
        pdialog = new ProgressDialog(FacturaActivity.this);
        pdialog.setMessage("Iniciando ...");
        pdialog.show();
        final Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer id) {
                if (id != null) {
                    //Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                    factura.setFact_num(String.valueOf(id));
                    for (DetalleFactura detalle : factura.getItems()){
                        detalle.setFact_num(id);
                    }
                    facturaViewModel.sendFactura(factura);
                    facturas.remove(pos);
                    facturaAdapter.notifyDataSetChanged();
                   // facturaViewModel.deleteById(factura);
                }else{
                    Toast.makeText(getApplicationContext(), "Hubo un problema", Toast.LENGTH_LONG).show();
                }
                pdialog.dismiss();
                pdialog = null;
            }
        };
        String cod = logueoInfo.getCoSucu().trim();
        correlativoViewModel.getID(new Correlativo(IMEI,"FACTURA", cod, 0)).observe(this, observer);
    }

}
