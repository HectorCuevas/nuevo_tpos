package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.Imei;
import com.tpos_prosisco.beans.VentaDiaria;

import java.util.List;

/**
 * Creada por Norman el 9/23/2020
 **/
public class ReporteDiarioAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    List<VentaDiaria> reporteFacturas;

    public ReporteDiarioAdapter(Context applicationContext, List<VentaDiaria> reporteFacturas) {
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
            listItemView = inflater.inflate(R.layout.activity_reporte_diario_item, null);
        }

        VentaDiaria ventaDiaria = (VentaDiaria) getItem(i);

        TextView descripcion = listItemView.findViewById(R.id.lblReporteDiarioDesc);
        TextView monto = listItemView.findViewById(R.id.lblReporteDiarioMonto);

        descripcion.setText(ventaDiaria.getDESCRIPCION());
        monto.setText(ApplicationTpos.CARACTER_MONEDA + String.valueOf(ventaDiaria.getVALOR()));


        return listItemView;
    }
}
