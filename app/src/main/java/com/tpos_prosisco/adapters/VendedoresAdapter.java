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
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Vendedor;

import java.util.ArrayList;
import java.util.List;

public class VendedoresAdapter extends BaseAdapter implements Filterable {
    private Context context;


    private List<Vendedor> beanList;
    private LayoutInflater inflater;
    private ValueFilter valueFilter;
    private List<Vendedor> mStringFilterList;

    public VendedoresAdapter(Context context, List<Vendedor> listitems){
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
            listItemView = inflater.inflate(R.layout.activity_vendedores_item, null);
        }

        Vendedor vendedor = (Vendedor) getItem(position);

        TextView nombre = listItemView.findViewById(R.id.lblVendedorNombre);
        TextView codigo = listItemView.findViewById(R.id.lblVendedorCodigo);

        nombre.setText(vendedor.getVENDES());
        codigo.setText(vendedor.getCOVEN());

        return listItemView;
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
                List<Vendedor> filterList = new ArrayList<Vendedor>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    boolean filter = (mStringFilterList.get(i).getVENDES().toUpperCase())
                            .contains(constraint.toString().toUpperCase());
                    if (filter) {
                        Vendedor vendedor = new Vendedor(
                                mStringFilterList.get(i).getCOVEN(),
                                mStringFilterList.get(i).getVENDES()
                        );
                        filterList.add(vendedor);
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
            beanList = (List<Vendedor>) results.values;
            notifyDataSetChanged();
        }
    }
}
