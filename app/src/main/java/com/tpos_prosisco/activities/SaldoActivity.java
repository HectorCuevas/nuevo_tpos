package com.tpos_prosisco.activities;

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
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.RecargasAdapter;
import com.tpos_prosisco.beans.Recarga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.logueoInfo;
import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;

public class SaldoActivity extends AppCompatActivity {

    static int REQUEST_PHONE_CALL = 100;
    RecargasAdapter recargasAdapter;
    Button btn;
    ListView lstTels;
    String[] arrTels;
    ArrayAdapter<String> adapter;
    private ArrayList<String> listTels = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);
        getTels(nuevaVenta.getCliente().getTelefonos().trim());
        lstTels = findViewById(R.id.lstNumeros);
        btn = findViewById(R.id.btnBack);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTels);
        lstTels.setAdapter(adapter);


        lstTels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ussd = makeUssdCode(adapter.getItem(i));
                startUssd(ussd);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cambiarActividad = new Intent(getApplicationContext(), ClienteOpcionesActivity.class);
                startActivity(cambiarActividad);
                if (cambiarActividad.resolveActivity(getPackageManager()) != null) {
                    startActivity(cambiarActividad);
                }
            }
        });
    }


    private void getTels(String str){
        if(!str.isEmpty()){
            int tel = 0;
            String tels = str;
            arrTels = tels.split("-");
            tel = Integer.parseInt(arrTels[0]);
            listTels.addAll(Arrays.asList(arrTels));
        }else{
            Toast.makeText(getApplicationContext(), "El cliente no tiene numeros de telefonos agregados", Toast.LENGTH_SHORT).show();
        }
    }
    private String makeUssdCode(String numero) {
        String str = "*977*3*<pos tienda>*<pin>#";
        str = logueoInfo.getUssdSaldo();
        String ussd = str
                .replace("<pos tienda>", numero) + "#";
        return ussd;
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
    private void startUssd(String ussd){

        Intent intent = new Intent(Intent.ACTION_DIAL, ussdToCallableUri(ussd));
        startActivity(intent);
        /*if (ContextCompat.checkSelfPermission(SaldoActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SaldoActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(ussdToCallableUri(ussd));
            try {
                startActivity(intent);

            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }*/
    }
}