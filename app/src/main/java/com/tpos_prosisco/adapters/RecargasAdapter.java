package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.Recarga;

import java.util.List;

public class RecargasAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater inflater;
    private List<Recarga> listitems;

    public RecargasAdapter(Context ctx, List<Recarga> listitems){
        this.ctx = ctx;
        this.listitems = listitems;
    }

    @Override
    public int getCount() {
        return listitems.size();
    }

    @Override
    public Object getItem(int i) {
        return listitems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View listItemView=view;
        if (inflater == null) {
            inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.activity_recargas_list_item, null);
        }

        Recarga recarga = (Recarga) getItem(i);

        TextView lblMonto = listItemView.findViewById(R.id.txtRecargaMonto);
        TextView lblNumero = listItemView.findViewById(R.id.txtRecargaTelefono);
        TextView lblCliente = listItemView.findViewById(R.id.txtRecargaCliente);

        lblCliente.setText(recarga.getCliente());
        lblNumero.setText("Telefonos: " + recarga.getNumero());
        lblMonto.setText("Monto: "+ ApplicationTpos.CARACTER_MONEDA + recarga.getMonto());


        return listItemView;
    }
}
