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
import com.tpos_prosisco.beans.Cliente;
import com.tpos_prosisco.beans.Vendedor;

import java.util.ArrayList;
import java.util.List;

public class ClienteAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Cliente> clientes = new ArrayList<Cliente>();;
    private LayoutInflater inflater;
    private ValueFilter valueFilter;
    private List<Cliente> mStringFilterList;

    public ClienteAdapter(Context context, List<Cliente> listitems){
        this.context = context;
        this.clientes=listitems;
        mStringFilterList = listitems;
    }

    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return clientes.get(position);
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
            listItemView = inflater.inflate(R.layout.activity_vendedores_item, null);
        }

        Cliente cliente = (Cliente) getItem(position);

        TextView nombre = listItemView.findViewById(R.id.lblVendedorNombre);
        TextView codigo = listItemView.findViewById(R.id.lblVendedorCodigo);

        nombre.setText(cliente.getRespons());
        codigo.setText(cliente.getDirec1());

        return listItemView;
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
                List<Cliente> filterList = new ArrayList<Cliente>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    boolean filter = (mStringFilterList.get(i).getRespons().toUpperCase())
                            .contains(constraint.toString().toUpperCase());
                    if (filter) {
                        Cliente cliente = new Cliente(
                                mStringFilterList.get(i).getCoCli(),
                                mStringFilterList.get(i).getDiasCredito(),
                                mStringFilterList.get(i).getDirec1(),
                                mStringFilterList.get(i).getTelefonos(),
                                mStringFilterList.get(i).getFax(),
                                mStringFilterList.get(i).getRespons(),
                                mStringFilterList.get(i).getEnRuta(),
                                mStringFilterList.get(i).getSaldo()
                        );
                        filterList.add(cliente);
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
            clientes = (List<Cliente>) results.values;
            notifyDataSetChanged();
        }
    }
}
