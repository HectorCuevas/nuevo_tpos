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
import com.tpos_prosisco.adapters.CobroAdapter;
import com.tpos_prosisco.adapters.MovimientoAdapter;
import com.tpos_prosisco.beans.Cobro;
import com.tpos_prosisco.beans.Movimiento;
import com.tpos_prosisco.data.CobroViewModel;

import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class MovimientosActivity extends AppCompatActivity {


    private ListView lstCobros;
    private LottieAnimationView lottieAnimationView;
    private TextView lblMensaje;
    private CobroViewModel cobroViewModel;
    private MovimientoAdapter cobroAdapter;
//G70JAN
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
        lstCobros = findViewById(R.id.lstCobro);
        lottieAnimationView = findViewById(R.id.animationView);
        lblMensaje = findViewById(R.id.lblReporteMensaje);
        cobroViewModel= ViewModelProviders.of(this)
                .get(CobroViewModel.class);

        getCobros(logueoInfo.getCoSucu().trim());


        lstCobros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Movimiento movimiento = (Movimiento) cobroAdapter.getItem(i);
                String url  = movimiento.getURLFACTURA().trim();
               if(!movimiento.getTIPO().trim().contains("COBRO")){
                   Intent intent = new Intent(Intent.ACTION_VIEW);
                   intent.setData(Uri.parse(url));
                   startActivity(intent);
               }
            }
        });


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

    private void getCobros(String cod){
        //nuevaVenta.getCliente().getCoCli()
        cobroViewModel.getMovimientos(cod).observe(MovimientosActivity.this, new Observer<List<Movimiento>>() {
            @Override
            public void onChanged(List<Movimiento> movimientos) {
                cobroAdapter = new MovimientoAdapter(getApplicationContext(), movimientos);
                // Toast.makeText(getApplicationContext(), String.valueOf(nuevaVenta.getCliente().getCoCli()), Toast.LENGTH_SHORT).show();
                lstCobros.setAdapter(cobroAdapter);
                cobroAdapter.notifyDataSetChanged();
                if(movimientos.size() ==0){
                    lstCobros.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lblMensaje.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}