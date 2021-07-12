package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.ReporteCategoria;
import com.tpos_prosisco.beans.ReporteVenta;

import java.util.List;

public class ReporteCategoriaAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    List<ReporteCategoria> reporteVentas;

    public ReporteCategoriaAdapter(Context context, List<ReporteCategoria> reporteVentas) {
        this.context = context;
        this.reporteVentas = reporteVentas;
    }



    @Override
    public int getCount() {
        return reporteVentas.size();
    }

    @Override
    public Object getItem(int position) {
        return reporteVentas.get(position);
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
            listItemView = inflater.inflate(R.layout.activity_reporte_venta_item, null);
        }

        ReporteCategoria reporteVenta = (ReporteCategoria) getItem(position);

        TextView articulo = listItemView.findViewById(R.id.lblReporteArticulo);
        TextView codigo  = listItemView.findViewById(R.id.lblReporteCod);
        TextView categoria = listItemView.findViewById(R.id.lblReporteCategoria);
        TextView monto = listItemView.findViewById(R.id.lblReporteMonto);

        articulo.setText(reporteVenta.getARTICULO());
        codigo.setText("Codigo: " + reporteVenta.getCODARTICULO());
        categoria.setText("Categoria: " + reporteVenta.getVENDEDOR());
        monto.setText(ApplicationTpos.CARACTER_MONEDA  +String.valueOf(reporteVenta.getMONTO()));


        return listItemView;
    }

}
