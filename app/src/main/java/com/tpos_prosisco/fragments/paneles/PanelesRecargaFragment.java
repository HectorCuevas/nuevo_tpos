package com.tpos_prosisco.fragments.paneles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tpos_prosisco.activities.MainActivity;
import com.tpos_prosisco.activities.ProductosActivity;
import com.tpos_prosisco.adapters.RecargasAdapter;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Recarga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

import static com.tpos_prosisco.ApplicationTpos.carrito;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;
import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;


public class PanelesRecargaFragment extends Fragment {

    static int REQUEST_PHONE_CALL = 100;
    RecargasAdapter recargasAdapter;
    ListView lstRecargas;
    private List<Recarga> listitems = new ArrayList<Recarga>();
    Button btnBack;
    private BottomSheetDialog mBottomSheetDialog;
    TextView lblRecarga;
    double cantidadTotal = 0d;
    private View view;

    public PanelesRecargaFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_paneles_recarga, container, false);
        lstRecargas = view.findViewById(R.id.lstRecargas);
        lblRecarga =  view.findViewById(R.id.lblRecarga);
        btnBack = view.findViewById(R.id.btnMenuPrincipal);

        for(Item item : ApplicationTpos.carrito){
            if(item.getProducto().getCoArt().contains("SIM")){
                Recarga recarga = new Recarga();
                recarga = setCanal(String.valueOf(item.getProducto().getTel())
                        , item.getProducto().getCantidadAsociada()
                        , item.getProducto().getCoArt()+" " + item.getProducto().getSerial());
                cantidadTotal += item.getCantidad();
                listitems.add(recarga);
            }

            if(item.getProducto().getCoArt().contains("ORGA.01") && item.isEsPaneles()){
                Recarga recarga = new Recarga();
                recarga = setCanal("No ha agregado el numero de Tel"
                        , item.getCantidad()
                        , item.getProducto().getCoArt());
                cantidadTotal += item.getCantidad();
                listitems.add(recarga);
            }
        }

        recargasAdapter = new RecargasAdapter(getActivity(), listitems);
        lstRecargas.setAdapter(recargasAdapter);


        lstRecargas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //aqui debemos de pedir la cantidad de producto con una ventana modal
                Recarga recarga = (Recarga)recargasAdapter.getItem(i);
                if(recarga.getCliente().contains("ORGA.01")){
                    getTelefono(recarga);
                }else{
                    getCantidad(recarga);
                }


            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cambiarActividad = new Intent(getContext(), MainActivity.class);
                startActivity(cambiarActividad);
                ApplicationTpos.carrito.clear();
            }
        });

        return view;
    }


    @NonNull
    private void getCantidad(Recarga recarga) {
        View modal_cantidad = getLayoutInflater().inflate(R.layout.bottom_sheet_cantidad, null);
        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        mBottomSheetDialog.setContentView(modal_cantidad);
        mBottomSheetDialog.show();
        modal_cantidad.findViewById(R.id.button_closeDialog1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        EditText txtcantidad = modal_cantidad.findViewById(R.id.txtCantidadDialog);
        txtcantidad.setText(String.valueOf(recarga.getMonto()));
        modal_cantidad.findViewById(R.id.button_okDialog1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int cantidad = Integer.parseInt(txtcantidad.getText().toString());
                    recarga.setMonto(cantidad);
                    String ussd = recarga.makeUssdCode(recarga);
                    startUssd(ussd);
                    double can = cantidadTotal-cantidad;
                    cantidadTotal = cantidadTotal - cantidad;
                    lblRecarga.setText("Cantidad total: " + ApplicationTpos.CARACTER_MONEDA+String.valueOf(can));
                    mBottomSheetDialog.dismiss();
                }catch(Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void getTelefono(Recarga recarga) {
        View modal = getLayoutInflater().inflate(R.layout.bottom_sheet_tel, null);
        mBottomSheetDialog = new BottomSheetDialog(getActivity());
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
                recarga.setNumero(String.valueOf(numero));
                String ussd = recarga.makeUssdCode(recarga);
                startUssd(ussd);
                mBottomSheetDialog.dismiss();
            }
        });
    }

    private Recarga  setCanal(String telefonos, int monto, String cliente) {
        Recarga recarga = new Recarga(telefonos, monto, cliente);
        return recarga;
    }
    private void startUssd(String ussd){
        Recarga recarga = new Recarga();
        Intent intent = new Intent(Intent.ACTION_DIAL, recarga.ussdToCallableUri(ussd));
        startActivity(intent);
    }

    /****
     * TODO: necesitamos pedir la cantidad de recarga, y necesitamos saber si debemos agrupar por numero de telefono osea
     * saber si tenemos que meter en un mismo item la orga y la simcard bueno hector nos tiene que decir que pedo :)
     */


}