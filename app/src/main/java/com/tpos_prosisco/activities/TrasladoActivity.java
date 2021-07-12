package com.tpos_prosisco.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.VendedoresAdapter;
import com.tpos_prosisco.beans.Vendedor;
import com.tpos_prosisco.data.ClienteViewModel;
import com.tpos_prosisco.data.ReporteVentaViewModel;
import com.tpos_prosisco.data.VendedorViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;
import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;

public class TrasladoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private List<Vendedor> vendedores = new ArrayList<Vendedor>();
    private VendedoresAdapter vendedoresAdapter;
    private ListView lst;
    private VendedorViewModel vendedorViewModel;
    private ClienteViewModel clienteViewModel;
    private SearchView searchView;
    private ReporteVentaViewModel reporteVentaViewModel;
    private BottomSheetDialog mBottomSheetDialog;
    private String ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traslado);
        getSupportActionBar().hide();

        lst = findViewById(R.id.lstVendedores);
        searchView = findViewById(R.id.searchviewProducts);

        vendedorViewModel = ViewModelProviders.of(this)
                .get(VendedorViewModel.class);
        reporteVentaViewModel = ViewModelProviders.of(this)
                .get(ReporteVentaViewModel.class);

        getVendedores();

        lst.setTextFilterEnabled(true);

        ruta = logueoInfo.getCoSucu().trim();

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vendedor vendedor = (Vendedor) vendedoresAdapter.getItem(position);
                setTraslado(vendedor.getCOVEN());
            }
        });

        searchView.setOnQueryTextListener(this);
        searchView.clearFocus();
    }
    private void getVendedores(){
        vendedorViewModel.getDBVendedores().observe(TrasladoActivity.this, new Observer<List<Vendedor>>() {
            @Override
            public void onChanged(List<Vendedor> lsvendedores) {
                vendedores = lsvendedores;
                vendedoresAdapter = new VendedoresAdapter(getApplicationContext(), vendedores);
                lst.setAdapter(vendedoresAdapter);
                vendedorViewModel.getDBVendedores().removeObserver(this);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        vendedoresAdapter.getFilter().filter(query);
        lst.setAdapter(vendedoresAdapter);
        vendedoresAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        vendedoresAdapter.getFilter().filter(newText);
        lst.setAdapter(vendedoresAdapter);
        vendedoresAdapter.notifyDataSetChanged();
        return true;
    }

    @NonNull
    private void setTraslado(String coven){
        View modal_confirmacion = getLayoutInflater().inflate(R.layout.bottom_sheet_cambio_ruta, null);
        mBottomSheetDialog = new BottomSheetDialog(TrasladoActivity.this);
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
                EditText txtrutanueva = modal_confirmacion.findViewById(R.id.txtCambioRuta);
                String rutaNueva = txtrutanueva.getText().toString();
                boolean confirmacion = txtrutanueva.getText().toString().isEmpty();
                if(!confirmacion){
                    final Observer<String> observer = new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable final String str) {
                            Toast.makeText(getApplicationContext(), " "+str, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    };

                    reporteVentaViewModel.setTraslado(IMEI, coven.trim(), rutaNueva).observe(TrasladoActivity.this, observer);
                }else
                    Toast.makeText(getApplicationContext(), "No Confirmacion", Toast.LENGTH_SHORT).show();

                mBottomSheetDialog.dismiss();
            }
        });
    }
}