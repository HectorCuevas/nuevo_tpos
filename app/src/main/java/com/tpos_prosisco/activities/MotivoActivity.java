package com.tpos_prosisco.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.Cliente;
import com.tpos_prosisco.beans.CodigoMotivo;
import com.tpos_prosisco.beans.Correlativo;
import com.tpos_prosisco.beans.DetalleFactura;
import com.tpos_prosisco.beans.Factura;
import com.tpos_prosisco.beans.Motivo;
import com.tpos_prosisco.data.CorrelativoViewModel;
import com.tpos_prosisco.data.MotivoViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.latitud;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;
import static com.tpos_prosisco.ApplicationTpos.longitud;
import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;


public class MotivoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText mEditText;
    MotivoViewModel motivoViewModel;
    CodigoMotivo codigoMotivo;
    private ProgressDialog pdialog;
    private Button mButton;
    private Spinner mSpinner;
    List<CodigoMotivo> motivos = new ArrayList<CodigoMotivo>();
    List<String> names = new ArrayList<String>();
    String[] array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivo);
        mEditText = findViewById(R.id.txtMotivo);

        motivoViewModel = ViewModelProviders.of(this)
                .get(MotivoViewModel.class);

        mButton = findViewById(R.id.btnMotivo);
        mSpinner = findViewById(R.id.spinner);

        //ME ESCUCHAS?

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(codigoMotivo.getRequire_comentario() && mEditText.getText().toString().equals("")){
                    mEditText.setError("Debe completar el comentario");
                }else
                    sendMotivo();
            }
        });

        mSpinner.setOnItemSelectedListener(this);

        getMotivos();

        mEditText.setVisibility(View.INVISIBLE);
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

    private void getMotivos(){
        motivoViewModel.getMotivos().observe(MotivoActivity.this, new Observer<List<CodigoMotivo>>() {
            @Override
            public void onChanged(List<CodigoMotivo> codigoMotivos) {motivos = codigoMotivos;
             //  Toast.makeText(ApplicationTpos.getInstance(), String.valueOf(codigoMotivos.size()), Toast.LENGTH_LONG).show();
                getNames();

            }
        });
    }

    private void getNames(){
        for(CodigoMotivo motivo : motivos){
           // motivo.setRequire_comentario(true);
            names.add(motivo.getMotivo());
        }
        array = new String[names.size()];
        names.toArray(array);
        setAdapter();
    }

    private void sendMotivo() {
        Date cDate = new Date();
        String fDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cDate);
        Motivo motivo = new Motivo();
        motivo.setImei(IMEI);
        motivo.setRuta(logueoInfo.getNombreRuta());
        motivo.setCo_cli(nuevaVenta.getCliente().getCoCli());
        motivo.setCo_ven(nuevaVenta.getVendedor().getCOVEN());
        motivo.setDatetime(fDate);
        motivo.setCo_motivo(codigoMotivo.getCo_motivo());
        motivo.setMotivo(mEditText.getText().toString());
        motivo.setLatitud(latitud);
        motivo.setLongitud(longitud);

        pdialog = new ProgressDialog(MotivoActivity.this);
        pdialog.setMessage("Enviado ...");
        pdialog.show();
//sendMotivo();
        motivoViewModel.setMotivo(motivo).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null) {
                    Toast.makeText(getApplicationContext(), "Hubo un problema", Toast.LENGTH_LONG).show();
                }
            }
        });
        Intent changePrefs = new Intent(getApplicationContext(), ClienteActivity.class);
        startActivity(changePrefs);
        pdialog.dismiss();
        pdialog = null;
    }

    private void setAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    void checkComentario(){

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(adapterView.getContext(),
                //"OnItemSelectedListener : " + adapterView.getItemAtPosition(i).toString(),
                //"OnItemSelectedListener : " + motivos.get(i).getMotivo(),
               // Toast.LENGTH_SHORT).show();

         codigoMotivo =  motivos.get(i);
        if(codigoMotivo.getRequire_comentario()){
            mEditText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}