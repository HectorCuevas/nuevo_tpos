package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.Cobro;
import com.tpos_prosisco.beans.Movimiento;

import java.util.List;

/**
 * Creada por Norman el 12/2/2020
 **/
public class MovimientoAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    List<Movimiento> cobros;

    public MovimientoAdapter(Context context, List<Movimiento> lstcobros) {
        this.context = context;
        this.cobros = lstcobros;
    }

    @Override
    public int getCount() {
        return cobros.size();
    }

    @Override
    public Object getItem(int i) {
        return cobros.get(i);
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
            listItemView = inflater.inflate(R.layout.activity_cobro_item, null);
        }

        Movimiento cobro = (Movimiento) getItem(i);

        TextView factura = listItemView.findViewById(R.id.lblCobroFactura);
        TextView fecha  = listItemView.findViewById(R.id.lblCobroFecha);
        TextView comentario = listItemView.findViewById(R.id.lblCobroComentario);
        TextView monto = listItemView.findViewById(R.id.lblCobroSaldo);

        factura.setText(String.valueOf(cobro.getNUMERO()));
        fecha.setText("Fecha: " + cobro.getFECHA());
        comentario.setText("Comentario: " + cobro.getOBSERVACIONES());
        monto.setText(ApplicationTpos.CARACTER_MONEDA +String.valueOf(cobro.getMONTO()));


        return listItemView;
    }

}
