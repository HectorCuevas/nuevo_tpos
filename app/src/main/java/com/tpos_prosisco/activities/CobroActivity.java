package com.tpos_prosisco.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.tpos_prosisco.adapters.CobroAdapter;
import com.tpos_prosisco.beans.Cobro;
import com.tpos_prosisco.beans.InfoCobro;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.data.CobroViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.carrito;
import static com.tpos_prosisco.ApplicationTpos.latitud;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;
import static com.tpos_prosisco.ApplicationTpos.longitud;
import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;
import static com.tpos_prosisco.ApplicationTpos.promocion;

public class CobroActivity extends AppCompatActivity {


    private ListView lstCobros;
    LottieAnimationView lottieAnimationView;
    TextView lblMensaje;
    private CobroViewModel cobroViewModel;
    private CobroAdapter cobroAdapter;
    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobro);
        lstCobros = findViewById(R.id.lstCobro);
        lottieAnimationView = findViewById(R.id.animationView);
        lblMensaje = findViewById(R.id.lblReporteMensaje);
       cobroViewModel= ViewModelProviders.of(this)
               .get(CobroViewModel.class);



        getCobros(logueoInfo.getCoSucu().trim());

        lstCobros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getCantidad((Cobro)cobroAdapter.getItem(i));
            }
        });


    }


    private void getCobros(String cod){
        //nuevaVenta.getCliente().getCoCli()
        cobroViewModel.getCobros(cod).observe(CobroActivity.this, new Observer<List<Cobro>>() {
            @Override
            public void onChanged(List<Cobro> cobros) {
                cobroAdapter = new CobroAdapter(getApplicationContext(), cobros);
               // Toast.makeText(getApplicationContext(), String.valueOf(nuevaVenta.getCliente().getCoCli()), Toast.LENGTH_SHORT).show();
                lstCobros.setAdapter(cobroAdapter);
                cobroAdapter.notifyDataSetChanged();
                if(cobros.size() ==0){
                    lstCobros.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lblMensaje.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @NonNull
    private void getCantidad(Cobro cobro){
        View modal_cantidad = getLayoutInflater().inflate(R.layout.bottom_sheet_cobro, null);
        mBottomSheetDialog = new BottomSheetDialog(CobroActivity.this);
        mBottomSheetDialog.setContentView(modal_cantidad);
        mBottomSheetDialog.show();
        modal_cantidad.findViewById(R.id.button_closeDialog1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        modal_cantidad.findViewById(R.id.button_okDialog1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText txtcantidad = modal_cantidad.findViewById(R.id.txtCantidadDialog);

                try{
                    double cantidad = Double.parseDouble(txtcantidad.getText().toString());
                    dialogFinish(cobro, cantidad);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Cantidad es requerida", Toast.LENGTH_SHORT).show();
                }
                mBottomSheetDialog.dismiss();
            }
        });
    }

    private void dialogFinish(Cobro xcobro, double cantidad) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                CobroActivity.this);

        // set title
        alertDialogBuilder.setTitle("TARJETAZO");
        alertDialogBuilder
                .setMessage("¿Desea realizar el cobro?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Date cDate = new Date();
                        String fDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cDate);
                        InfoCobro cobro = new InfoCobro();
                        cobro.setPImei(IMEI);
                        cobro.setPFACTNUM(xcobro.getFACTURA());
                        cobro.setPFORMAPAG("CONT");
                        cobro.setPCOBRO(cantidad);
                        cobro.setPFEUSIN(fDate);
                        cobro.setPLATITUD(latitud);
                        cobro.setPLONGITUD(longitud);
                        cobroViewModel.setCobro(cobro).observe(CobroActivity.this, new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                if (integer == null) {
                                    Toast.makeText(getApplicationContext(), "Hubo un problema", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        Intent changePrefs = new Intent(getApplicationContext(), ClienteOpcionesActivity.class);
                        startActivity(changePrefs);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        Intent changePrefs = new Intent(getApplicationContext(), ClienteOpcionesActivity.class);
        startActivity(changePrefs);
        if (changePrefs.resolveActivity(getPackageManager()) != null) {
            startActivity(changePrefs);
        }
        return super.onKeyDown(keyCode, event);
    }

}