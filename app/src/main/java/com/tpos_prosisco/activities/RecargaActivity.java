package com.tpos_prosisco.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.RecargasAdapter;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Recarga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tpos_prosisco.ApplicationTpos.carrito;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;
import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;
import static com.tpos_prosisco.ApplicationTpos.promocion;

public class RecargaActivity extends AppCompatActivity {
    static int REQUEST_PHONE_CALL = 100;
    RecargasAdapter recargasAdapter;
    ListView lstRecargas, lstTels;
    private List<Recarga> listitems = new ArrayList<Recarga>();
    private List<Recarga> filter = new ArrayList<Recarga>();
    Button btnBack;
    String[] arrTels;
    HashMap<String, Integer> capitalCities;
    private BottomSheetDialog mBottomSheetDialog;
    private View modal;
    TextView lblRecarga;
    Map<String, List<Recarga>> studlistGrouped;
    ArrayList<String> myArrayList;

    Recarga recarga;
    private ArrayList<String> listTels = new ArrayList<>();
    double cantidadTotal = 0d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga);
        lstRecargas = findViewById(R.id.lstRecargas);
        lblRecarga =  findViewById(R.id.lblRecarga);
        btnBack = findViewById(R.id.btnMenuPrincipal);


        //pin incorrecto
        //clientes fuera de ruta busqueda

        for(Item item : ApplicationTpos.carrito){
            if(item.getProducto().getCoArt().contains("ORGA.01")){
                Recarga recarga = new Recarga();
                if(logueoInfo.getNombreCanal().contains("MASIVO")){
                    //cantidad para clientes que tengan telefonos
                   recarga = setCanal(nuevaVenta.getCliente().getTelefonos().trim(), item.getCantidad(), nuevaVenta.getCliente().getRespons());
                   cantidadTotal += item.getCantidad();
                }else {
                    recarga = setCanal(String.valueOf(item.getProducto().getTel()), item.getCantidad(), "Venta");
                }
                listitems.add(recarga);
            }
        }

        lblRecarga.setText("Cantidad total: " + ApplicationTpos.CARACTER_MONEDA+String.valueOf(cantidadTotal));
        //lblRecarga.setText("pitos");



        int monto = 0;
        capitalCities = new HashMap<String, Integer>();
        for(int i= 0; i<listitems.size(); i ++){
            if(capitalCities.get(listitems.get(i).getNumero()) == null){
                capitalCities.put(listitems.get(i).getNumero(), listitems.get(i).getMonto());
            }else{
                int g = (int)capitalCities.get(listitems.get(i).getNumero());
                 monto = g +  listitems.get(i).getMonto();
                capitalCities.put(listitems.get(i).getNumero(), monto);
            }
        }

        filter.add(new Recarga(nuevaVenta.getCliente().getTelefonos(), monto, nuevaVenta.getCliente().getRespons()));



        recargasAdapter = new RecargasAdapter(getApplicationContext(), filter);
        lstRecargas.setAdapter(recargasAdapter);


        lstRecargas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(myArrayList != null){
              if(myArrayList.size() >0){
                  recarga = (Recarga) recargasAdapter.getItem(i);

                  modal = getLayoutInflater().inflate(R.layout.bottom_sheet_tels, null);
                  mBottomSheetDialog = new BottomSheetDialog(RecargaActivity.this);
                  mBottomSheetDialog.setContentView(modal);
                  mBottomSheetDialog.show();
                  modal.findViewById(R.id.lstTels);
                  ListView listView = (ListView) modal.findViewById(R.id.lstTels);
                  ArrayAdapter<String> adapter =
                          new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,myArrayList);
                  listView.setAdapter(adapter);
                  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                      @Override
                      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                          // recarga.setNumero(listTels.get(i));
                          //String ussd = makeUssdCode(recarga);
                          // startUssd(ussd);
                          // Toast.makeText(getApplicationContext(), "No existe informacion de numero de Telefono", Toast.LENGTH_SHORT).show();
                          mBottomSheetDialog.dismiss();
                          getCantidad(i);
                      }
                  });
              }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "El cliente no tiene numeros de telefono disponibles",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cambiarActividad = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(cambiarActividad);
                if (cambiarActividad.resolveActivity(getPackageManager()) != null) {
                    startActivity(cambiarActividad);
                }
                ApplicationTpos.carrito.clear();
            }
        });
        ///Toast.makeText(getApplicationContext(), ussd, Toast.LENGTH_LONG).show();
    }



    private Uri ussdToCallableUri(String ussd) {

        String uriString = "";

        if (!ussd.startsWith("tel:" + ussd))
            uriString += "tel:";

        for (char c : ussd.toCharArray()) {

            if (c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }


        return Uri.parse(uriString);
    }

    private String makeUssdCode(Recarga recarga) {
        String ussd = logueoInfo
                .getUssdRecarga()
                .replace("<pin>", logueoInfo.getPin().trim())
                .replace("<pos tienda>", String.valueOf(recarga.getNumero()))
                .replace("<cantidad>", String.valueOf(recarga.getMonto()))
                .replace("LLAMAR", "") + "#";
        return ussd;
    }

    private void startUssd(String ussd){

        Intent intent = new Intent(Intent.ACTION_DIAL, ussdToCallableUri(ussd));
        startActivity(intent);
    }

    private Recarga  setCanal(String telefonos, int monto, String cliente) {
        int tel = 0;
        switch (logueoInfo.getNombreCanal()) {
            case "MASIVO":
                if(!nuevaVenta.getCliente().getTelefonos().equals("")){
                    String tels = telefonos;
                    arrTels = tels.split("-");
                   // tel = Integer.parseInt(arrTels[0]);
                    listTels.addAll(Arrays.asList(arrTels));
                    //se agregan a una nueva lista porque este tipo de lista no permite duplicados
                    myArrayList = new ArrayList<String>(new LinkedHashSet<String>(listTels));

                }else
                {
                    listTels.add("0");
                    Toast.makeText(getApplicationContext(), "No existe informacion de numero de Telefono", Toast.LENGTH_SHORT).show();
                }
                break;
            case "PANELES":
            case "KIOSKOS":
                tel = Integer.parseInt(telefonos);
                break;
            default:

                break;
        }
        Recarga recarga = new Recarga(telefonos, monto, cliente);

        return recarga;
    }


    @NonNull
    private void getCantidad(int pos) {
        View modal_cantidad = getLayoutInflater().inflate(R.layout.bottom_sheet_cantidad, null);
        mBottomSheetDialog = new BottomSheetDialog(RecargaActivity.this);
        mBottomSheetDialog.setContentView(modal_cantidad);
        mBottomSheetDialog.show();
        modal_cantidad.findViewById(R.id.button_closeDialog1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        EditText txtcantidad = modal_cantidad.findViewById(R.id.txtCantidadDialog);
        txtcantidad.setText(String.valueOf((int)cantidadTotal));
        modal_cantidad.findViewById(R.id.button_okDialog1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int cantidad = Integer.parseInt(txtcantidad.getText().toString());
                    //producto.setTel(0);
                     recarga.setNumero(listTels.get(pos));
                     recarga.setMonto(cantidad);
                     String ussd = makeUssdCode(recarga);
                     startUssd(ussd);
                     double can = cantidadTotal-cantidad;
                     cantidadTotal = cantidadTotal - cantidad;
                    lblRecarga.setText("Cantidad total: " + ApplicationTpos.CARACTER_MONEDA+String.valueOf(can));
                    mBottomSheetDialog.dismiss();
                  //  listTels.remove(pos);
                   //recargasAdapter.notifyDataSetChanged();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
