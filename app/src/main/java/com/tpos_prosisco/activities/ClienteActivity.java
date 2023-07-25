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

import com.tpos_prosisco.ApplicationTpos;
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

public class ClienteActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ListView lstClientes;
    private ClienteAdapter clienteAdapter;
    private ClienteViewModel clienteViewModel;
    private SearchView searchView;
    private List<Cliente> clientesArray = new ArrayList<>();
    private VendedorViewModel vendedorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        lstClientes = findViewById(R.id.lstClientes);
        searchView = findViewById(R.id.searchviewProducts);
        vendedorViewModel = ViewModelProviders.of(this)
                .get(VendedorViewModel.class);
        clienteViewModel = ViewModelProviders.of(this)
                .get(ClienteViewModel.class);
        lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = (Cliente) clienteAdapter.getItem(position);
                    nuevaVenta.setCliente(cliente);
                    getVendedor();
            }
        });

        getClientes();

        searchView.setOnQueryTextListener(this);
        searchView.clearFocus();
    }


    private void getClientes(){

        clienteViewModel.getDBClientes().observe(ClienteActivity.this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clientes) {
                if(clientes.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Cargando...", Toast.LENGTH_LONG).show();
                }else{
                    clientesArray = clientes;
                    clienteAdapter = new ClienteAdapter(getApplicationContext(), clientes);
                    lstClientes.setAdapter(clienteAdapter);
                    clienteAdapter.notifyDataSetChanged();
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


    void getVendedor(){
        vendedorViewModel.getDBVendedores().observe(ClienteActivity.this, new Observer<List<Vendedor>>() {
            @Override
            public void onChanged(List<Vendedor> lsvendedores) {
                if(lsvendedores.size() > 0){
                    nuevaVenta.setVendedor(lsvendedores.get(0));
                    Intent i = new Intent(ClienteActivity.this, ClienteOpcionesActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "No existe codigo del vendedor: " +
                            "contacte al administrador", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        lstClientes.setAdapter(clienteAdapter);
        clienteAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        lstClientes.setAdapter(clienteAdapter);
        clienteAdapter.getFilter().filter(newText);
        return true;
    }
}
