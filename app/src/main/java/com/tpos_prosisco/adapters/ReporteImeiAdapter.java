package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.Imei;
import com.tpos_prosisco.beans.Responses.ReporteFactura;

import java.util.List;

/**
 * Creada por Norman el 9/22/2020
 **/
public class ReporteImeiAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    List<Imei> reporteFacturas;

    public ReporteImeiAdapter(Context applicationContext, List<Imei> reporteFacturas) {
        this.context = applicationContext;
        this.reporteFacturas = reporteFacturas;
    }

    @Override
    public int getCount() {
        return reporteFacturas.size();
    }

    @Override
    public Object getItem(int position) {
        return reporteFacturas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View listItemView = view;
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.activity_reporte_imei_item, null);
        }

        Imei imei = (Imei) getItem(i);

        TextView serial = listItemView.findViewById(R.id.lblReporteSerial);
        TextView numfact = listItemView.findViewById(R.id.lblReporteNumFact);
        TextView descripcion = listItemView.findViewById(R.id.lblReporteArt);


        serial.setText(imei.getSERIAL());
        numfact.setText(String.valueOf(imei.getFACTURA()));
        descripcion.setText(imei.getARTICULO());

        return listItemView;
    }
}
