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

import com.airbnb.lottie.LottieAnimationView;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.InventarioAdapter;
import com.tpos_prosisco.adapters.ProductosAdapter;
import com.tpos_prosisco.beans.Inventario;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.data.InventarioViewModel;
import com.tpos_prosisco.data.ProductoViewModel;
import java.util.List;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class InventarioActivity extends AppCompatActivity {
    private InventarioViewModel productoViewModel;
    private InventarioAdapter carritoAdapter;
    private ListView lst;
    private LottieAnimationView lottieAnimationView;
    private TextView lblMensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        lst = findViewById(R.id.lstReporte);
        productoViewModel = ViewModelProviders.of(this)
                .get(InventarioViewModel.class);
        lblMensaje = findViewById(R.id.lblReporteMensaje);
        lottieAnimationView = findViewById(R.id.animationView);
        getProductos();

    }

    private void getProductos() {
        productoViewModel.getReporte(logueoInfo.getCoSucu().trim()).observe(InventarioActivity.this, new Observer<List<Inventario>>() {
            @Override
            public void onChanged(List<Inventario> inventario) {
                carritoAdapter = new InventarioAdapter(getApplicationContext(), inventario);
                lst.setAdapter(carritoAdapter);
                carritoAdapter.notifyDataSetChanged();
                if(inventario.size() == 0){
                    lst.setVisibility(View.GONE);
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
