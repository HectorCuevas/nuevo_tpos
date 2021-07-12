package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductosAdapter extends BaseAdapter implements Filterable {


    private Context context;
    List<Producto> beanList;
    List<Producto> mStringFilterList;
    private LayoutInflater inflater;
    private ValueFilter valueFilter;

    public ProductosAdapter(Context context, List<Producto> listitems){
        this.context = context;
        this.beanList=listitems;
        mStringFilterList = listitems;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.activity_carrito_item, null);
        }

        Producto item = (Producto) getItem(position);

        TextView nombre = listItemView.findViewById(R.id.lblCarritoNombre);
        TextView descripcion = listItemView.findViewById(R.id.lblCarritoDescripcion);
        TextView serial = listItemView.findViewById(R.id.lblCarritoSerial);
        TextView precio = listItemView.findViewById(R.id.lblCarritoPrecio);
        TextView codigo = listItemView.findViewById(R.id.lblCarritoCodigo);

        nombre.setText(item.getCoArt());
        descripcion.setText(item.getColumn1());
        serial.setText("Serial: " + item.getSerial());
        precio.setText(String.valueOf("Q" + item.getPrecVta1()));
        codigo.setText("Tipo: " + item.getTipo());

        return  listItemView;
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
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<Producto> filterList = new ArrayList<Producto>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    boolean filter = (mStringFilterList.get(i).getColumn1().toUpperCase())
                            .contains(constraint.toString().toUpperCase()) ||
                            (mStringFilterList.get(i).getSerial().toUpperCase())
                                    .contains(constraint.toString().toUpperCase());
                    if (filter) {

                        Producto producto = new Producto(
                                mStringFilterList.get(i).getCoArt(),
                                mStringFilterList.get(i).getTipo(),
                                mStringFilterList.get(i).getSerial(),
                                mStringFilterList.get(i).getColumn1(),
                                mStringFilterList.get(i).getCoAlma(),
                                mStringFilterList.get(i).getStock(),
                                mStringFilterList.get(i).getPrecVta1(),
                                mStringFilterList.get(i).getPrecVta2(),
                                mStringFilterList.get(i).getPrecVta3(),
                                mStringFilterList.get(i).getPrecVta31(),
                                mStringFilterList.get(i).getPrecVta5()
                        );
                        filterList.add(producto);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            beanList = (List<Producto>) results.values;
            notifyDataSetChanged();
        }
    }
}
