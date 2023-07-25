package com.tpos_prosisco.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.BuildConfig;
import com.tpos_prosisco.PreferenceManager;
import com.tpos_prosisco.R;
import com.tpos_prosisco.Tools;
import com.tpos_prosisco.ViewAnimation;
import com.tpos_prosisco.beans.Correlativo;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Venta;
import com.tpos_prosisco.data.ClienteViewModel;
import com.tpos_prosisco.data.CorrelativoViewModel;
import com.tpos_prosisco.data.FacturaViewModel;
import com.tpos_prosisco.data.ProductoViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.carrito;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;
import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;

public class DetalleActivity extends AppCompatActivity {

    private NestedScrollView nested_scroll_view;
    private ImageButton img_datos, img_datos_ubicacion, img_fotografias;
    private Button btn_ocultar_datos, btn_ocultar_datos_ubicacion, btnConfirmar, btnSave, btn_ocultar_fotografias;
    private View lyDatosPersonales, lyDatosUbicacion, lyFotografias;
    private EditText txtDireccionRecarga;
    private EditText txtDpi;
    private EditText txtNombre;
    private EditText txtTel;
    private EditText txtNit;
    private RadioButton rbContado;
    private RadioButton rbCredito;
    private RadioGroup rgOpcionesPago;
    private TextView lblDepto;
    private TextView lblMun;
    private TextView lblZona;
    private Venta   venta = new Venta();
    private final int SELECT_FROM_GALLERY_FRONT = 10;
    private final int SELECT_FROM_GALLERY_BEHIND = 20;
    private final int TAKE_FROM_CAMERA_FRONT = 100;
    private final int TAKE_FROM_CAMERA_BEHIND = 200;
    private byte[] imagenEnvio;
    private byte[] imagenEnvioTrasera;
    private ImageView imagen1, imagen2;
    private FacturaViewModel facturaViewModel;
    private CorrelativoViewModel correlativoViewModel;
    private ClienteViewModel clienteViewModel;
    private ProductoViewModel productoViewModel;
    private ProgressDialog pdialog;
    private Button btnFrontal, btnPosterior;
    private String currentPhotoPath;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        getSupportActionBar().hide();
        initComponent();

        btnFrontal = findViewById(R.id.btnFrontal);
        btnPosterior = findViewById(R.id.btnPosterior);

        txtDireccionRecarga = findViewById(R.id.txtIngresoDireccionRecarga);
        txtDpi = findViewById(R.id.txtIngresoDpi);
        txtNombre = findViewById(R.id.txtIngresoNombre);
        txtTel = findViewById(R.id.txtIngresoTelefono);
        txtNit = findViewById(R.id.txtIngresoNit);

        txtNombre.requestFocus();

        lblDepto = findViewById(R.id.txtIngresoDireccionDepto);
        lblZona = findViewById(R.id.txtIngresoDireccionZona);
        lblMun = findViewById(R.id.txtIngresoDireccionMunicipio);


        imagen1 = (ImageView) findViewById(R.id.imgFrontal);
        imagen2 = (ImageView) findViewById(R.id.imgPosterior);

        rbContado = findViewById(R.id.radioContado);
        rbCredito = findViewById(R.id.radioCredito);
        rgOpcionesPago = (RadioGroup) findViewById(R.id.radioGroup_opciones_pago);

        btnConfirmar = findViewById(R.id.btnECheckout);
        btnSave = findViewById(R.id.btnSave);

        rbContado.setChecked(true);
        venta.setCondicionPago("CONT");
        rgOpcionesPago.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbContado.isChecked()) {
                    venta.setCondicionPago("CONT");
                } else {
                    venta.setCondicionPago("CRED");
                }

            }
        });

        validaPermisos();
        configurarPreferencias();
        productoViewModel = ViewModelProviders.of(this)
                .get(ProductoViewModel.class);
        correlativoViewModel = ViewModelProviders.of(this)
                .get(CorrelativoViewModel.class);
        clienteViewModel = ViewModelProviders.of(this)
                .get(ClienteViewModel.class);
        facturaViewModel = ViewModelProviders.of(this)
                .get(FacturaViewModel.class);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClienteDireccion();
                if (isNetworkConnected()) {
                    if (getClienteinfo()) {
                        getFotos();
                        sendFactura();
                        setCanal();
                        Intent intent = new Intent(DetalleActivity.this, RecargaActivity.class);
                        startActivity(intent);
                        //Toast.makeText(getApplicationContext(), "Factura almacenada", Toast.LENGTH_LONG).show();
                        //insert();
                    } else
                        Toast.makeText(getApplicationContext(), "Debe completar los campos!", Toast.LENGTH_LONG).show();
                } else {
                    insert();
                    Toast.makeText(getApplicationContext(), "No cuenta con conexión a internet", Toast.LENGTH_LONG).show();
                   // AestheticDialog.showConnectify(DetalleActivity.this, "No cuenta con conexión a internet", AestheticDialog.ERROR);
                    Intent intent = new Intent(DetalleActivity.this, MainActivity.class);
                    startActivity(intent);
                    carrito.clear();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClienteDireccion();
                if (getClienteinfo()) {
                    getFotos();
                    //sendFactura();
                    setCanal();
                    Intent intent = new Intent(DetalleActivity.this, RecargaActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Factura almacenada", Toast.LENGTH_LONG).show();
                    insert();
                } else
                    Toast.makeText(getApplicationContext(), "Debe completar los campos!", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void sendFactura() {
        pdialog = new ProgressDialog(DetalleActivity.this);
        pdialog.setMessage("Iniciando ...");
        pdialog.show();
        final Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer id) {
                if (id != null) {
                    facturaViewModel.sendFactura(id, venta);
                }
                pdialog.dismiss();
                pdialog = null;
            }
        };
        correlativoViewModel.getID(new Correlativo(IMEI,"FACTURA",
                logueoInfo.getCoSucu().trim(), 0)).observe(this, observer);
    }

    private void insert() {
        facturaViewModel.insertDBFactura(venta);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void initComponent() {


        /*** Layouts ***/
        lyDatosPersonales = findViewById(R.id.ly_datos_cliente);
        lyDatosUbicacion = (View) findViewById(R.id.ly_datos_ubicacion);
        lyFotografias = findViewById(R.id.ly_fotografias);


        /*** Botones ocultar ***/
        btn_ocultar_datos = (Button) findViewById(R.id.btn_ocultar_datos);
        btn_ocultar_datos_ubicacion = (Button) findViewById(R.id.btn_ocultar_datos_ubicacion);
        btn_ocultar_fotografias = findViewById(R.id.btn_ocultar_fotografias);

        /*** Flechas ***/
        img_datos = (ImageButton) findViewById(R.id.img_datos);
        img_fotografias = findViewById(R.id.img_fotografias);
        img_datos_ubicacion = (ImageButton) findViewById(R.id.img_datos_ubicacion);


        lyDatosPersonales.setVisibility(View.GONE);
        lyDatosUbicacion.setVisibility(View.GONE);
        lyFotografias.setVisibility(View.GONE);

        toggleSectionInput(img_datos, lyDatosPersonales);

        //flecha
        img_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtDpi.requestFocus();
                toggleSectionInput(img_datos, lyDatosPersonales);
            }
        });


        //ocultar
        btn_ocultar_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getClienteinfo();
                toggleSectionInput(img_datos, lyDatosPersonales);
            }
        });

        /**** UBICACION ***/
        //ocultar
        btn_ocultar_datos_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClienteDireccion();
                toggleSectionInput(img_datos_ubicacion, lyDatosUbicacion);
            }
        });

        //flecha
        img_datos_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDireccionRecarga.requestFocus();
                toggleSectionInput(img_datos_ubicacion, lyDatosUbicacion);
            }
        });


        /**** fotografoas ****/
        //ocultar
        btn_ocultar_fotografias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if ((imagenEnvio != null) && (imagenEnvioTrasera != null)) {
                    getFotos();
                    toggleSectionInput(img_fotografias, lyFotografias);
            /*    } else {
                    Toast.makeText(getApplicationContext(), "Las imagenes son obligatorias", Toast.LENGTH_LONG).show();
                }*/

            }
        });

        //flecha
        img_fotografias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSectionInput(img_fotografias, lyFotografias);
            }
        });


        // nested scrollview
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view_trabajadores);
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    private boolean getClienteinfo() {
        venta.setNombre("");
        venta.setNumero(0);
        venta.setDpi("");
        if(!logueoInfo.getNombreCanal().trim().equals("MASIVO")){
            if (!isEmpty(txtNombre) && !isEmpty(txtDpi) && !isEmpty(txtTel)) {
                venta.setNombre(txtNombre.getText().toString());
                venta.setNumero(Integer.parseInt(txtTel.getText().toString()));
                venta.setDpi(txtDpi.getText().toString());
                String nit = isEmpty(txtNit) ? "Na" : txtNit.getText().toString();
                venta.setNit(nit);
                toggleSectionInput(img_datos, lyDatosPersonales);
                return true;
            } else {
                txtDpi.setError("Obligatorio");
                txtNombre.setError("Obligatorio");
                txtTel.setError("Obligatorio");
                return false;
            }
        }else{
            venta.setNombre(isEmpty(txtNombre) ? "": txtNombre.getText().toString());
            venta.setNumero(isEmpty(txtTel) ? 0: Integer.parseInt(txtTel.getText().toString()));
            venta.setDpi(isEmpty(txtDpi) ? "" : txtDpi.getText().toString());
            String nit = isEmpty(txtNit) ? "Na" : txtNit.getText().toString();
            venta.setNit(nit);
            toggleSectionInput(img_datos, lyDatosPersonales);
            return true;
        }
    }

    private void getClienteDireccion() {
        venta.setDepto(lblDepto.getText().toString());
        venta.setMunicipio(lblMun.getText().toString());
        venta.setZona(Integer.parseInt(lblZona.getText().toString()));
        venta.setDireccionRecarga(txtDireccionRecarga.getText().toString());
    }

    private void getFotos() {
        try {
            venta.setImagen(imagenEnvio);
            venta.setImagen2(imagenEnvioTrasera);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void toggleSectionInput(View view, final View layout) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(layout, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyDatosPersonales);
                }
            });
        } else {
            ViewAnimation.collapse(layout);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    private boolean validaPermisos() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if ((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        if ((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //  botonCargar.setEnabled(true);
            } else {
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones = {"si", "no"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(DetalleActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Los permisos no fueron aceptados", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(DetalleActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    public void onclick(View view) {
        try {
            cargarImagen(TAKE_FROM_CAMERA_FRONT, SELECT_FROM_GALLERY_FRONT);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void addSecondPhoto(View view) {
        try {
            cargarImagen(TAKE_FROM_CAMERA_BEHIND, SELECT_FROM_GALLERY_BEHIND);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("IntentReset")
    private void cargarImagen(final int CAMERA, final int GALLERY) {
        final CharSequence[] opciones = {"Tomar Foto", "Cargar Imagen", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(DetalleActivity.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")) {
                    dispatchTakePictureIntent(CAMERA);
                } else {
                    if (opciones[i].equals("Cargar Imagen")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(Intent.createChooser(intent, "Seleccione la Aplicación"), GALLERY);
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent(final int ACTION_CODE) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                        BuildConfig.APPLICATION_ID + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                uri = photoURI;
                startActivityForResult(takePictureIntent, ACTION_CODE);
            }
        }
    }

    private Bitmap redimensionarImagen(Bitmap bitmap, float anchoNuevo, float altoNuevo) {

        int ancho = bitmap.getWidth();
        int alto = bitmap.getHeight();

        if (ancho > anchoNuevo || alto > altoNuevo) {
            float escalaAncho = anchoNuevo / ancho;
            float escalaAlto = altoNuevo / alto;

            Matrix matrix = new Matrix();
            matrix.postScale(escalaAncho, escalaAlto);

            return Bitmap.createBitmap(bitmap, 0, 0, ancho, alto, matrix, false);

        } else {
            return bitmap;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_FROM_GALLERY_FRONT: {
                    uploadImageFromGallery(data, imagen1, true);
                    break;
                }
                case SELECT_FROM_GALLERY_BEHIND: {
                    uploadImageFromGallery(data, imagen2, false);
                    break;
                }
                case TAKE_FROM_CAMERA_FRONT: {
                    takeImageFromCamera(imagen1, true);
                    break;
                }
                case TAKE_FROM_CAMERA_BEHIND: {
                    takeImageFromCamera(imagen2, false);
                    break;
                }
            }
        }
    }

    private void uploadImageFromGallery(Intent intent, ImageView imageView, boolean isFront){
        Uri selectedImage = intent.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            imageView.setImageBitmap(redimensionarImagen(bitmap, 762, 500));
            if(isFront){
                imagenEnvio = imageViewToByte(imageView);
            }else imagenEnvioTrasera = imageViewToByte(imageView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void takeImageFromCamera(ImageView imageView, boolean isFront){
        Uri selectedImage = uri;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            imageView.setImageBitmap(redimensionarImagen(bitmap, 762, 500));
            if(isFront){
                imagenEnvio = imageViewToByte(imageView);
            }else imagenEnvioTrasera = imageViewToByte(imageView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        // bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void configurarPreferencias() {
        /*** Verificacion de preferencias ***/
        try {
            if (PreferenceManager.checkPref(DetalleActivity.this, PreferenceManager.PREF_STR_DEPTO)) {
                String str = PreferenceManager.getPref(DetalleActivity.this, PreferenceManager.PREF_STR_DEPTO);
                lblDepto.setText(str);
            } else {
                lblDepto.setText("Guatemala");
            }

            if (PreferenceManager.checkPref(DetalleActivity.this, PreferenceManager.PREF_STR_MUN)) {
                String str = PreferenceManager.getPref(DetalleActivity.this, PreferenceManager.PREF_STR_MUN);
                lblMun.setText(str);
            } else {
                lblMun.setText("Guatemala");
            }

            if (PreferenceManager.checkPref(DetalleActivity.this, PreferenceManager.PREF_STR_ZONA)) {
                String str = PreferenceManager.getPref(DetalleActivity.this, PreferenceManager.PREF_STR_ZONA);
                lblZona.setText(str);
            } else {
                lblZona.setText("1");
            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent changePrefs = new Intent(getApplicationContext(), PromocionesActivity.class);
        finish();
        startActivity(changePrefs);
        if (changePrefs.resolveActivity(getPackageManager()) != null) {
            startActivity(changePrefs);
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }

    private void setCanal() {
        switch (logueoInfo.getNombreCanal()) {
            case "MASIVO":
                updateCliente();
                break;
            case "PANELES":
                break;
            case "KIOSKOS":
                break;
            default:
                break;
        }
        deleteById();
    }

    private void updateCliente() {
        clienteViewModel.updateCliente(nuevaVenta.getCliente());
    }

    private void deleteById() {
        for (Item item: carrito) {
            productoViewModel.deleteById(item.getProducto());
        }
    }
}
