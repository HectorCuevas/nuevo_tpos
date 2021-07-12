package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.ReporteVendedor;

import java.util.List;

public class ReporteVendedorAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    List<ReporteVendedor> reporteVendedores;

    public ReporteVendedorAdapter(Context context, List<ReporteVendedor> reporteVendedores) {
        this.context = context;
        this.reporteVendedores = reporteVendedores;
    }

    @Override
    public int getCount() {
        return reporteVendedores.size();
    }

    @Override
    public Object getItem(int position) {
        return reporteVendedores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.activity_reporte_vendedor_item, null);
        }

        ReporteVendedor reporteVendedor = (ReporteVendedor) getItem(position);

        TextView vendedor = listItemView.findViewById(R.id.lblReporteVendedorNombre);
        TextView codigo  = listItemView.findViewById(R.id.lblReporteVendedorCod);
        TextView ruta = listItemView.findViewById(R.id.lblReporteVendedorRuta);
        TextView monto = listItemView.findViewById(R.id.lblReporteVendedorMonto);

        vendedor.setText(reporteVendedor.getVENDEDOR());
        codigo.setText("Codigo: " + reporteVendedor.getCODIGO_VENDEDOR());
        ruta.setText("Ruta: " + reporteVendedor.getRUTA());
        monto.setText(ApplicationTpos.CARACTER_MONEDA + String.valueOf(reporteVendedor.getMONTO()));

        return listItemView;
    }
}
