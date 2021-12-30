package com.tpos_prosisco.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.ProductosAdapter;
import com.tpos_prosisco.adapters.PromocionAdapter;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Promocion;
import com.tpos_prosisco.data.ProductoViewModel;
import com.tpos_prosisco.data.PromocionViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.carrito;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class ProductosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private List<Producto> items = new ArrayList<Producto>();
    private List<Producto> allItems = new ArrayList<Producto>();
    private ProductosAdapter carritoAdapter;
    private ListView lst;
    private Button btnCheckout;
    private SearchView searchView;
    private ProductoViewModel productoViewModel;
    private TextView lblITems;
    private TextView lblTotal;
    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        getSupportActionBar().hide();

        lst = findViewById(R.id.lstCarrito);
        btnCheckout = findViewById(R.id.btnECheckout);
        lblITems = findViewById(R.id.lblCarritoItems);
        lblTotal = findViewById(R.id.lblCarritoTotal);
        searchView = findViewById(R.id.searchviewProducts);

        productoViewModel = ViewModelProviders.of(this)
                .get(ProductoViewModel.class);

        getProductos();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carrito.size() > 0) {
                    Intent i = new Intent(ProductosActivity.this, PromocionesActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Debe agregar articulos", Toast.LENGTH_LONG).show();
                }
            }
        });

        searchView.setOnQueryTextListener(this);
        searchView.clearFocus();

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Producto producto = (Producto) carritoAdapter.getItem(position);
                setCategorias(producto);
                allItems.remove(position);
            }
        });
    }

    private void getProductos() {
        productoViewModel.getDBProductos().observe(ProductosActivity.this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                allItems = productos;
                carritoAdapter = new ProductosAdapter(getApplicationContext(), productos);
                lst.setAdapter(carritoAdapter);
                carritoAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setCategorias(Producto producto) {
        String tipo = producto.getTipo().trim().substring(0, 1);
        switch (tipo){
            case "S":
                getTelefono(producto);
                break;
            case "A":
            case "O":
            case "T":
            case "P":
            default:
                getCantidad(producto);
                break;
        }
    }


    @NonNull
    private void getCantidad(Producto producto){
        View modal_cantidad = getLayoutInflater().inflate(R.layout.bottom_sheet_cantidad, null);
        mBottomSheetDialog = new BottomSheetDialog(ProductosActivity.this);
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
                int cantidad = Integer.parseInt(txtcantidad.getText().toString());
                producto.setTel(0);
                carrito.add(checkChannel(producto, cantidad));
                mBottomSheetDialog.dismiss();
                setTotal();
            }
        });
    }

    private Item checkChannel(Producto producto, int cantidad){
        if(logueoInfo.getNombreCanal().equals("PANELES")){
            return new Item(producto, cantidad, producto.getPrecVta1(), true);
        }else{
            return new Item(producto, cantidad, producto.getPrecVta1());
        }
    }

    private void getTelefono(Producto producto) {
        View modal = getLayoutInflater().inflate(R.layout.bottom_sheet_tel, null);
        mBottomSheetDialog = new BottomSheetDialog(ProductosActivity.this);
        mBottomSheetDialog.setContentView(modal);
        mBottomSheetDialog.show();
        modal.findViewById(R.id.button_closeDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        modal.findViewById(R.id.button_okDialog).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText txtNumero = modal.findViewById(R.id.txtCantidadDialog);
                int numero = Integer.parseInt(txtNumero.getText().toString());
                producto.setTel(numero);
                double precio = producto.getPrecVta1();
                carrito.add(new Item(producto, 1, precio));
                mBottomSheetDialog.dismiss();
            }
        });
    }
    private double setTotal() {
        double total = 0D;
        for (Item producto : carrito) {
            total = total + (producto.getCantidad() * producto.getPrecio());
        }
        carritoAdapter.notifyDataSetChanged();
        lblTotal.setText("Q" + String.valueOf(total));
        lblITems.setText(String.valueOf(carrito.size()));
        return total;
    }

    /*** Interfaz para Searchview ***/
    @Override
    public boolean onQueryTextSubmit(String s) {
        //searchView.clearFocus();
        lst.setAdapter(carritoAdapter);
        carritoAdapter.getFilter().filter(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //searchView.clearFocus();
        lst.setAdapter(carritoAdapter);
        carritoAdapter.getFilter().filter(s);
        return true;
    }
}
