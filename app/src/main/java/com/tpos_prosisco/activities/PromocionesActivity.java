 package com.tpos_prosisco.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.CarritoAdapter;
import com.tpos_prosisco.adapters.PromocionAdapter;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Promocion;
import com.tpos_prosisco.data.PromocionViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.carrito;

public class PromocionesActivity extends AppCompatActivity {

    private PromocionAdapter promocionAdapter;
    private PromocionViewModel promocionViewModel;
    private CarritoAdapter carritoAdapter;
    private Button btnVender, btnProductos;
    private ListView ls, lsCarrito;
    private BottomSheetDialog mBottomSheetDialog;
    private List<Promocion> promociones = new ArrayList<Promocion>();
    private View modal;

    LocationManager locationManager;
    LocationListener locationListener;
    private TextView lblTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promociones);
        ls = (ListView) findViewById(R.id.lstPromos);
        lsCarrito = findViewById(R.id.lstCarritoFinal);
        btnVender = findViewById(R.id.btnFacturar);
        lblTotal = findViewById(R.id.lblTotal);
        btnProductos = findViewById(R.id.btnECheckout);
        promocionViewModel = ViewModelProviders.of(this)
                .get(PromocionViewModel.class);


        getSupportActionBar().hide();

        btnVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ApplicationTpos.carrito.size()!=0){
                    Intent i = new Intent(PromocionesActivity.this, DetalleActivity.class);
                    //Intent i = new Intent(PromocionesActivity.this, RecargaActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Debe agregar articulos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationTpos.promocion.setCategoriaFiltro("");
                ApplicationTpos.promocion.setTipoPrecioItem(1);
                ApplicationTpos.promocion.setCantidadTA(999);
                Intent i = new Intent(PromocionesActivity.this, ProductosActivity.class);
                startActivity(i);
            }
        });

        lsCarrito.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Producto producto = ApplicationTpos.carrito.get(position).getProducto();
                modal = getLayoutInflater().inflate(R.layout.bottom_sheet_comentario, null);
                setWindow(modal, producto,  position);
            }
        });

        lsCarrito.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationTpos.carrito.remove(position);
                carritoAdapter.notifyDataSetChanged();
                setTotal();
                return false;
            }
        });

        getPromociones();
        getCarrito();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                updateLocationInfo(lastKnownLocation);
            }
        }
    }


    private void getCarrito(){
        carritoAdapter = new CarritoAdapter(getApplicationContext(), ApplicationTpos.carrito);
        lsCarrito.setAdapter(carritoAdapter);
        setTotal();
    }

    private void getPromociones() {
        promocionViewModel.getDBPromociones().observe(PromocionesActivity.this, new Observer<List<Promocion>>() {
            @Override
            public void onChanged(List<Promocion> dbpromociones) {
                promociones = dbpromociones;
                promocionAdapter = new PromocionAdapter(PromocionesActivity.this, promociones);
                ls.setAdapter(promocionAdapter);
                ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Promocion promocion = promociones.get(position);
                        ApplicationTpos.promocion = promocion;
                        Intent intent = new Intent(getApplicationContext(), CarritoActivity.class);
                        startActivity(intent);
                    }
                });
                promocionAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setWindow(View modal, Producto producto, int pos) {
        mBottomSheetDialog = new BottomSheetDialog(PromocionesActivity.this);
        mBottomSheetDialog.setContentView(modal);
        mBottomSheetDialog.show();
        EditText as = modal.findViewById(R.id.txtCantidad);
        TextView lbl = modal.findViewById(R.id.lblC);
        //producto.setSerial("198273213");
        if(producto.getSerial().equals("")){
            as.setVisibility(View.VISIBLE);
            lbl.setVisibility(View.VISIBLE);
            Item item = carrito.get(pos);
            as.setText(String.valueOf(item.getCantidad()));
        }
        modal.findViewById(R.id.button_okDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtComentario = modal.findViewById(R.id.txtComentario);
                String comentario = txtComentario.getText().toString();
                producto.setComentario(comentario);
                if(producto.getSerial().equals("")){
                    carrito.get(pos).setCantidad(Integer.parseInt((as.getText().toString())));
                    setTotal();
                }
                carritoAdapter.notifyDataSetChanged();
                mBottomSheetDialog.dismiss();
            }
        });
        modal.findViewById(R.id.button_closeDialog).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mBottomSheetDialog.dismiss();

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ApplicationTpos.carrito.clear();
        finish();
        Intent changePrefs = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(changePrefs);
        if (changePrefs.resolveActivity(getPackageManager()) != null) {
            startActivity(changePrefs);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
           /// Toast.makeText(getApplicationContext(),String.valueOf(ApplicationTpos.latitud) , Toast.LENGTH_SHORT).show();
        }
    }

    public void updateLocationInfo(Location location) {
        try {
            ApplicationTpos.longitud = location.getLongitude();
            ApplicationTpos.latitud = location.getLatitude();
        //    Toast.makeText(getApplicationContext(),String.valueOf(ApplicationTpos.latitud) , Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Debe activar el GPS", Toast.LENGTH_SHORT).show();
        }
    }
    private double setTotal() {
        double total = 0D;
        for (Item producto : carrito) {
            total = total + (producto.getCantidad() * producto.getPrecio());
        }
        carritoAdapter.notifyDataSetChanged();
        lblTotal.setText(ApplicationTpos.CARACTER_MONEDA + String.valueOf(total));
        return total;
    }
}
