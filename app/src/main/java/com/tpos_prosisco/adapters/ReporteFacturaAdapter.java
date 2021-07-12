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
import com.tpos_prosisco.beans.Responses.ReporteFactura;

import java.util.List;

public class ReporteFacturaAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    List<ReporteFactura> reporteFacturas;

    public ReporteFacturaAdapter(Context context, List<ReporteFactura> reporteFacturas) {
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.activity_reporte_factura_item, null);
        }

        ReporteFactura reporteFactura = (ReporteFactura) getItem(position);

        TextView numFact = listItemView.findViewById(R.id.lblReporteNumFact);
        TextView vendedor = listItemView.findViewById(R.id.lblReporteVendedor);
        TextView cobro = listItemView.findViewById(R.id.lblReporteCobro);
        TextView monto = listItemView.findViewById(R.id.lblReporteMonto);

        numFact.setText("Numero de factura: " + String.valueOf(reporteFactura.getFACTURA()));
        vendedor.setText("Vendedor: " + reporteFactura.getVENDEDOR());
        cobro.setText("Cobro: " + ApplicationTpos.CARACTER_MONEDA  + String.valueOf(reporteFactura.getCOBRO()));
        monto.setText("Monto: "+ ApplicationTpos.CARACTER_MONEDA  + String.valueOf(reporteFactura.getMONTO()));

        return listItemView;
    }
}
