package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.Factura;
import com.tpos_prosisco.beans.FacturasConDetalles;

import java.util.List;

public class FacturaAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    List<FacturasConDetalles> beanList;

    public FacturaAdapter(Context context, List<FacturasConDetalles> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
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
            listItemView = inflater.inflate(R.layout.activity_list_factura_item, null);
        }

        FacturasConDetalles factura = (FacturasConDetalles) getItem(position) ;

        TextView txtNombre =  listItemView.findViewById(R.id.lblFacturaNombre);
        TextView txtFecha =  listItemView.findViewById(R.id.lblFacturaFecha);
        TextView txtDirecion =  listItemView.findViewById(R.id.lblFacturaDireccion);
        TextView txtMonto = listItemView.findViewById(R.id.lblFacturaTotal) ;
        TextView txtDpi = listItemView.findViewById(R.id.lblFacturaDPI);

        txtNombre.setText("Nombre: " + factura.factura.getNombre());
        txtFecha.setText("Fecha y hora: "+factura.factura.getFe_us_in());
        txtDirecion.setText("Dirección: " + factura.factura.getDepartamento() + ", " + factura.factura.getMunicipio());
        txtMonto.setText("MONTO: " + ApplicationTpos.CARACTER_MONEDA + String.valueOf(factura.factura.getTotal()));
        txtDpi.setText("DPI: " + factura.factura.getDpi());

        return listItemView;
    }
}
