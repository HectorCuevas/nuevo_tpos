package com.tpos_prosisco.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.ClienteAdapter;
import com.tpos_prosisco.adapters.VendedoresAdapter;
import com.tpos_prosisco.beans.Cliente;
import com.tpos_prosisco.beans.Vendedor;
import com.tpos_prosisco.data.ClienteViewModel;
import com.tpos_prosisco.data.VendedorViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;

public class VendedoresActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private List<Vendedor> vendedores = new ArrayList<Vendedor>();
    private VendedoresAdapter vendedoresAdapter;
    private ListView lst;
    private VendedorViewModel vendedorViewModel;
    private ClienteViewModel clienteViewModel;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedores);
        getSupportActionBar().hide();

        lst = findViewById(R.id.lstVendedores);
        searchView = findViewById(R.id.searchviewProducts);
        vendedorViewModel = ViewModelProviders.of(this)
                .get(VendedorViewModel.class);
        clienteViewModel = ViewModelProviders.of(this)
                .get(ClienteViewModel.class);

        getVendedores();
        lst.setTextFilterEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.clearFocus();


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  nuevaVenta.setVendedor(vendedores.get(position));
                //  getCliente();
               // vendedoresAdapter.notifyDataSetChanged();
                Vendedor vendedor = (Vendedor) vendedoresAdapter.getItem(position);
                nuevaVenta.setVendedor(vendedor);
                getCliente();
               // Toast.makeText(getApplicationContext(), vendedor.getVENDES(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getVendedores(){
        vendedorViewModel.getDBVendedores().observe(VendedoresActivity.this, new Observer<List<Vendedor>>() {
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

    private void getCliente(){

        clienteViewModel.getDBClientes().observe(VendedoresActivity.this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clientes) {
                if(clientes.size()>0){
                    nuevaVenta.setCliente(clientes.get(0));
                    Intent i = new Intent(VendedoresActivity.this, PromocionesActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "No existe codigo del cliente : " +
                            "contacte al administrador", Toast.LENGTH_LONG)
                .show();
                }
            }
        });
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
}
