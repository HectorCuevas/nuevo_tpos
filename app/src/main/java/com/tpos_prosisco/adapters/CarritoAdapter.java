package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.Item;

import java.util.List;


public class CarritoAdapter extends BaseAdapter {
    private Context context;
    List<Item> mStringFilterList;
    List<Item> beanList;
    private LayoutInflater inflater;

    public CarritoAdapter(Context context, List<Item> listItems){
        this.beanList = listItems;
        this.context = context;
        mStringFilterList = listItems;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.activity_list_carrito_item, null);
        }

        Item item =  getItem(position);

        TextView lblProducto = listItemView.findViewById(R.id.lblChartNombre);
        TextView lblPrecio = listItemView.findViewById(R.id.lblChartPrecio);
        TextView lblCantidad =  listItemView.findViewById(R.id.lblChartCantidad);

        lblCantidad.setText("Cantidad: " + item.getCantidad());
        lblPrecio.setText("Precio: "+ApplicationTpos.CARACTER_MONEDA + item.getPrecio());
        lblProducto.setText("Producto: " + item.getProducto().getColumn1());

        return listItemView;
    }
    public int getCount() {
        return beanList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public Item getItem(int position) {
        return beanList.get(position);
    }

}
