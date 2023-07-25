package com.tpos_prosisco.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.beans.Cliente;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Promocion;
import com.tpos_prosisco.beans.Sesion;
import com.tpos_prosisco.beans.Vendedor;
import com.tpos_prosisco.data.ClienteViewModel;
import com.tpos_prosisco.data.LogueoViewModel;
import com.tpos_prosisco.data.ProductoViewModel;
import com.tpos_prosisco.data.PromocionViewModel;
import com.tpos_prosisco.data.SesionViewModel;
import com.tpos_prosisco.data.VendedorViewModel;
import com.tpos_prosisco.fragments.MenuFragment;
import com.tpos_prosisco.R;
import com.tpos_prosisco.fragments.venta.MasivoFragment;

import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class MainActivity extends AppCompatActivity {
    private SesionViewModel sesionViewModel;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private ProductoViewModel productoViewModel;
    private PromocionViewModel promocionViewModel;
    private VendedorViewModel vendedorViewModel;
    private LogueoViewModel logueoViewModel;
    private TextView lblRuta;
    LocationManager locationManager;
    LocationListener locationListener;
    private ClienteViewModel clienteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblRuta = findViewById(R.id.lblMainRuta);

        lblRuta.setText("Ruta: " + logueoInfo.getCoSucu());

        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        sesionViewModel = ViewModelProviders.of(this)
                .get(SesionViewModel.class);

        getSupportActionBar().hide();
        Fragment fragment = new MenuFragment();
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.container, fragment)
                .commit();

        productoViewModel = ViewModelProviders.of(this)
                .get(ProductoViewModel.class);


        promocionViewModel = ViewModelProviders.of(this)
                .get(PromocionViewModel.class);

        clienteViewModel = ViewModelProviders.of(this)
                .get(ClienteViewModel.class);

        vendedorViewModel = ViewModelProviders.of(this)
                .get(VendedorViewModel.class);

        logueoViewModel = ViewModelProviders.of(this)
                .get(LogueoViewModel.class);


        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mySwipeRefreshLayout.setRefreshing(true);
                        deleteAll();
                        getData();
                    }
                }
        );

        /*** Location manager ***/
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Intent changePrefs = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(changePrefs);
        if (changePrefs.resolveActivity(getPackageManager()) != null) {
            startActivity(changePrefs);
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }



    private void getData(){

        String ruta = logueoInfo.getCoSucu().trim();
        String canal = logueoInfo.getIdCanalVenta().toString();

        getProductos(ruta);
        getVendedores();
        getPromociones(canal);
        getClientes(ruta, logueoInfo.getNombreCanal());

    }
    private void getProductos(String ruta) {

        productoViewModel.getProductos(ruta).observe(MainActivity.this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                /*for (Producto producto : productos
                ) {
                    productoViewModel.insertProducto(producto);
                }*/
            }
        });
    }
    private void getClientes(String ruta, String canal) {
        clienteViewModel.getClientes(ruta, canal).observe(MainActivity.this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clientes) {
               /* for (Cliente cliente : clientes) {
                    clienteViewModel.insertCliente(cliente);
                }*/
            }
        });
    }
    private void getVendedores() {
        vendedorViewModel.getVendedores(logueoInfo.getCoSucu()).observe(MainActivity.this, new Observer<List<Vendedor>>() {
            @Override
            public void onChanged(List<Vendedor> vendedores) {
                for (Vendedor vendedor : vendedores
                ) {
                    vendedorViewModel.insertVendedor(vendedor);
                }
                vendedorViewModel.getVendedores(logueoInfo.getCoSucu()).removeObserver(this);
            }
        });
    }

    void deleteAll(){
        productoViewModel.deleteAll();
        vendedorViewModel.deleteAll();
        promocionViewModel.deleteAll();
        clienteViewModel.deleteAll();
    }

    private void getPromociones(String canal) {

        promocionViewModel.getPromociones(canal).observe(MainActivity.this, new Observer<List<Promocion>>() {
            @Override
            public void onChanged(List<Promocion> promociones) {
                for (Promocion promocion : promociones
                ) {
                    promocionViewModel.insertPromocion(promocion);
                }
                mySwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startListening();
        }


    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
           // Toast.makeText(getApplicationContext(),String.valueOf(latitude) , Toast.LENGTH_SHORT).show();
        }
    }

    public void updateLocationInfo(Location location) {
        try {
            ApplicationTpos.longitud = location.getLongitude();
            ApplicationTpos.latitud = location.getLatitude();
           // Toast.makeText(getApplicationContext(),String.valueOf(latitude) , Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Debe activar el GPS", Toast.LENGTH_SHORT).show();
        }
    }

}
