package com.tpos_prosisco.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tpos_prosisco.R;
import com.tpos_prosisco.beans.Promocion;

import java.util.List;

public class PromocionAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    List<Promocion> beanList;

    @Override
    public int getCount() {
        return beanList.size();
    }
    public PromocionAdapter(Context context, List<Promocion> listitems){
        this.context = context;
        this.beanList=listitems;
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
            listItemView = inflater.inflate(R.layout.bottom_list_promocion_item, null);
        }

        Promocion item = (Promocion) getItem(position);

        TextView codigo = listItemView.findViewById(R.id.lblPromocionCodigo);
        TextView descripcion = listItemView.findViewById(R.id.lblPromocionDesc);


        codigo.setText(item.getCodigoPromocion());
        descripcion.setText(item.getDescripcion());

        return  listItemView;
    }
}
