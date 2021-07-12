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
import com.tpos_prosisco.beans.Cliente;
import com.tpos_prosisco.beans.Vendedor;
import com.tpos_prosisco.data.ClienteViewModel;
import com.tpos_prosisco.data.VendedorViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;

public class ClientesFueraRutaActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ClienteViewModel clienteViewModel;
    private ClienteAdapter clienteAdapter;
    private SearchView searchView;
    private ListView lstClientes;
    private VendedorViewModel vendedorViewModel;
    private List<Cliente> clientesArray = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_fuera_ruta);
        lstClientes = findViewById(R.id.lstClientes);
        searchView = findViewById(R.id.searchviewClientes);
        clienteViewModel = ViewModelProviders.of(this)
                .get(ClienteViewModel.class);
        vendedorViewModel = ViewModelProviders.of(this)
                .get(VendedorViewModel.class);
        getClientes();

        lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = (Cliente) clienteAdapter.getItem(position);
                nuevaVenta.setCliente(cliente);
                getVendedor();
            }
        });

        searchView.setOnQueryTextListener(this);
        searchView.clearFocus();

    }

    private void getClientes(){

        clienteViewModel.getDBClientesFueraRuta().observe(ClientesFueraRutaActivity.this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clientes) {
                clientesArray = clientes;
                clienteAdapter = new ClienteAdapter(getApplicationContext(), clientes);
                lstClientes.setAdapter(clienteAdapter);
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
        vendedorViewModel.getDBVendedores().observe(ClientesFueraRutaActivity.this, new Observer<List<Vendedor>>() {
            @Override
            public void onChanged(List<Vendedor> lsvendedores) {
                if(lsvendedores.size() > 0){
                    nuevaVenta.setVendedor(lsvendedores.get(0));
                    Intent i = new Intent(ClientesFueraRutaActivity.this, ClienteOpcionesActivity.class);
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
