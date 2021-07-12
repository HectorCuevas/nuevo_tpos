package com.tpos_prosisco.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.ClienteAdapter;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.LogueoService;
import com.tpos_prosisco.beans.Cliente;
import com.tpos_prosisco.beans.Logueo;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Promocion;
import com.tpos_prosisco.beans.Sesion;
import com.tpos_prosisco.beans.User;
import com.tpos_prosisco.beans.Vendedor;
import com.tpos_prosisco.data.ClienteViewModel;
import com.tpos_prosisco.data.LogueoViewModel;
import com.tpos_prosisco.data.ProductoViewModel;
import com.tpos_prosisco.data.PromocionViewModel;
import com.tpos_prosisco.data.SesionViewModel;
import com.tpos_prosisco.data.VendedorViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tpos_prosisco.ApplicationTpos.CARACTER_MONEDA;
import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.getInstance;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class LoginActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private ProductoViewModel productoViewModel;
    private PromocionViewModel promocionViewModel;
    private VendedorViewModel vendedorViewModel;
    private LogueoViewModel logueoViewModel;
    private TelephonyManager telephonyManager;
    private EditText txtClave;
    Call<Integer> call;
    private LogueoService logueoService;
    private AppClient appClient;
    private SesionViewModel sesionViewModel;
    private ClienteViewModel clienteViewModel;
    ProgressDialog pdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            fab = (FloatingActionButton) findViewById(R.id.fab);
            txtClave = findViewById(R.id.lblPassword);

            getSupportActionBar().hide();
            deviceId();
            productoViewModel = ViewModelProviders.of(this)
                    .get(ProductoViewModel.class);


            promocionViewModel = ViewModelProviders.of(this)
                    .get(PromocionViewModel.class);


            vendedorViewModel = ViewModelProviders.of(this)
                    .get(VendedorViewModel.class);

            logueoViewModel = ViewModelProviders.of(this)
                    .get(LogueoViewModel.class);

            sesionViewModel = ViewModelProviders.of(this)
                    .get(SesionViewModel.class);

            clienteViewModel = ViewModelProviders.of(this)
                    .get(ClienteViewModel.class);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //    Toast.makeText(getApplicationContext(), "Boton", Toast.LENGTH_LONG).show();
                    String clave = txtClave.getText().toString();
                    if (!clave.equals("")) {
                        if (isNetworkConnected()) {
                            pdialog = new ProgressDialog(LoginActivity.this);
                            pdialog.setMessage("Iniciando ...");
                            pdialog.show();
                            getUser( new User(IMEI, clave));
                            //getUser( new User("0d41593f5a46a6b9", "123"));
                        } else {
                            AestheticDialog
                                    .showConnectify(LoginActivity.this,
                                            "No cuenta con conexión a internet",
                                            AestheticDialog.ERROR);
                        }
                    } else {
                        txtClave.setText("");
                        txtClave.setError("Credenciales invalidas!");
                    }

                }
            });
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }


    }


    private void getData(Logueo logueo) {
        if (logueo.getNombreCanal() != null) {
            logueoInfo = logueo;
            String ruta = logueo.getCoSucu().trim();
            String canal = logueo.getIdCanalVenta().toString();
            CARACTER_MONEDA = logueo.getSimboloMoneda().trim();
            getDBClientes(ruta, logueo.getNombreCanal());
            getDBVendedores(ruta);
            getDBPromociones(canal);
            getDBProductos(ruta);
            /** sucursal lista **/


        }else{
            pdialog.dismiss();
            pdialog = null;
        }
    }


    /*********************************/

    private void getClientes(String ruta, String canal) {
        clienteViewModel.getClientes(ruta, canal).observe(LoginActivity.this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clientes) {
                for (Cliente cliente : clientes) {
                    clienteViewModel.insertCliente(cliente);
                }
            }
        });
    }

    private void getProductos(String ruta) {
        productoViewModel.getProductos(ruta).observe(LoginActivity.this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                for (Producto producto : productos
                ) {
                    productoViewModel.insertProducto(producto);
                }
            }
        });
    }

    private void getVendedores(String ruta) {
        vendedorViewModel.getVendedores(ruta).observe(LoginActivity.this, new Observer<List<Vendedor>>() {
            @Override
            public void onChanged(List<Vendedor> vendedores) {
                for (Vendedor vendedor : vendedores
                ) {
                    vendedorViewModel.insertVendedor(vendedor);
                }
            }
        });
    }

    private void getPromociones(String canal) {
        promocionViewModel.getPromociones(canal).observe(LoginActivity.this, new Observer<List<Promocion>>() {
            @Override
            public void onChanged(List<Promocion> promociones) {
                for (Promocion promocion : promociones
                ) {
                    promocionViewModel.insertPromocion(promocion);
                }
            }
        });

    }

    private void getLogueoInfo() {
        // sesionViewModel.getLogueo(new Sesion("0", IMEI, 0, 0));
        final Observer<Logueo> nameObserver = new Observer<Logueo>() {
            @Override
            public void onChanged(@Nullable final Logueo logueo) {
                if (logueo != null) {
                    getData(logueo);
                }
                pdialog.dismiss();
                pdialog = null;
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        sesionViewModel.getLogueo(new Sesion("0", IMEI, 0, 0)).observe(this, nameObserver);

    }

    /*********************************/
    private void getDBVendedores(String ruta) {
        vendedorViewModel.getDBVendedores().observe(LoginActivity.this, new Observer<List<Vendedor>>() {
            @Override
            public void onChanged(List<Vendedor> vendedores) {
                //  Toast.makeText(getApplicationContext(), String.valueOf(vendedores.size()), Toast.LENGTH_SHORT).show();
                if (vendedores.size() == 0) {
                    getVendedores(ruta);
                }

            }
        });
    }

    private void getDBClientes(String ruta, String canal) {
        clienteViewModel.getDBClientes().observe(LoginActivity.this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(List<Cliente> clientes) {
                if (clientes.size() == 0) {
                    getClientes(ruta, canal);
                    clienteViewModel.getDBClientes().removeObserver(this);
                }
            }

        });
    }

    private void getDBProductos(String ruta) {
        productoViewModel.getDBProductos().observe(LoginActivity.this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {

                if (productos.size() == 0) {
                    getProductos(ruta);
                }
                productoViewModel.getDBProductos().removeObserver(this);
            }
        });

    }

    private void getDBPromociones(String canal) {
        promocionViewModel.getDBPromociones().observe(LoginActivity.this, new Observer<List<Promocion>>() {
            @Override
            public void onChanged(List<Promocion> promociones) {
                if (promociones.size() == 0) {
                    getPromociones(canal);
                }
                promocionViewModel.getDBPromociones().removeObserver(this);
            }
        });
    }

    /*********************************/
    private void deviceId() {
        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
                        return;
                    }
                    String imeiNumber = getDeviceId(getApplicationContext());
                    if (IMEI.equals("")) {
                        IMEI = imeiNumber;
                    }
                    //Toast.makeText(LoginActivity.this,IMEI,Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Without permission we check", Toast.LENGTH_LONG).show();
                }
                break;
            default:

                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    //FUNCION PARA SALIR DEL SISTEMA

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
        super.onBackPressed();
    }
    @SuppressLint("HardwareIds")
    public  String getDeviceId(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);


            Toast.makeText(getApplicationContext(), deviceId.toString(), Toast.LENGTH_SHORT).show();
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                Toast.makeText(getApplicationContext(), deviceId.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("devideId", deviceId);
        clipboard.setPrimaryClip(clip);

        return deviceId;
    }
    public void getUser(User user) {
        appClient = AppClient.getInstance();
        logueoService = appClient.getLogueoService();
        call = logueoService.getUser(user);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Object value = response.body();
                if (response.isSuccessful()) {
                    int i = Integer.parseInt(value.toString());
                    if (i == 2) {
                        getLogueoInfo();
                      //  Toast.makeText(getApplicationContext(), value.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        txtClave.setText("");
                        txtClave.setError("Credenciales invalidas!");
                        pdialog.dismiss();
                        pdialog = null;
                    }
                }else{

                    Toast.makeText(getApplicationContext(), "Estoy en el else", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
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

}
