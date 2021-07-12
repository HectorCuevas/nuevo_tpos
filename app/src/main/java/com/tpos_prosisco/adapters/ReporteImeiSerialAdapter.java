package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.ReporteImeiSerial;
import com.tpos_prosisco.beans.ReporteVendedor;

import java.util.List;

/**
 * Creada por Norman el 2/9/2021
 **/
public class ReporteImeiSerialAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    List<ReporteImeiSerial> reporte;

    public ReporteImeiSerialAdapter(Context context, List<ReporteImeiSerial> rImei) {
        this.context = context;
        this.reporte = rImei;
    }

    @Override
    public int getCount() {
        return reporte.size();
    }

    @Override
    public Object getItem(int i) {
        return reporte.get(i);
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
            listItemView = inflater.inflate(R.layout.activity_reporte_imeiserial_item, null);
        }

        ReporteImeiSerial reporte = (ReporteImeiSerial) getItem(i);

        TextView vendedor = listItemView.findViewById(R.id.lblReporteImeiSerialFactura);
        TextView codigo  = listItemView.findViewById(R.id.lblReporteImeiSerialFecha);
        TextView ruta = listItemView.findViewById(R.id.lblReporteImeiSerialDatos);
        TextView monto = listItemView.findViewById(R.id.lblReporteImeiSerialMonto);

        vendedor.setText(String.valueOf(reporte.getFACTURA()));
        codigo.setText(reporte.getFECHA());
        ruta.setText(reporte.getDATOS());
        monto.setText(ApplicationTpos.CARACTER_MONEDA  +String.valueOf(reporte.getMONTO()));

        return listItemView;
    }
}
