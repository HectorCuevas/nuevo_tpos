package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.FacturasConDetalles;
import com.tpos_prosisco.beans.Inventario;

import java.util.List;

/**
 * Creada por Norman el 9/21/2020
 **/
public class InventarioAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    List<Inventario> inventario;

    public InventarioAdapter(Context context, List<Inventario> inventario) {
        this.context = context;
        this.inventario = inventario;
    }


    @Override
    public int getCount() {
        return inventario.size();
    }

    @Override
    public Object getItem(int i) {
        return inventario.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View listItemView = view;
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.activity_layout_reporte_inventario, null);
        }

        Inventario inventario = (Inventario) getItem(i);

        TextView codigo = listItemView.findViewById(R.id.lblCarritoCodigo);
        TextView descripcion = listItemView.findViewById(R.id.lblCarritoDescripcion);
        TextView stock = listItemView.findViewById(R.id.lblCarritoStock);
        TextView precio = listItemView.findViewById(R.id.lblCarritoPrecio);

        codigo.setText(inventario.getCODIGO());
        descripcion.setText(inventario.getDESCRIPCION());
        stock.setText(String.valueOf(inventario.getSTOCK()));
        precio.setText(ApplicationTpos.CARACTER_MONEDA + String.valueOf(inventario.getPRECIO()));

        return listItemView;
    }
}



