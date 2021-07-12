package com.tpos_prosisco.fragments.venta;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import com.tpos_prosisco.R;
import com.tpos_prosisco.activities.ConfiguracionActivity;
import com.tpos_prosisco.activities.VendedoresActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class VendedoresFragment extends Fragment {
    private GridLayout mainGrid;

    public VendedoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendedores, container, false);
        try {
            // Inflate the layout for this fragment

            mainGrid = (GridLayout) view.findViewById(R.id.mainGrid);
            setSingleEvent(mainGrid);
        }catch (Exception ex){

            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }
        return view;
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = null;
                    switch (finalI){
                        case 0:
                            Intent i = new Intent(getActivity(), VendedoresActivity.class);
                            startActivity(i);
                            break;
                        case 1:
                            break;
                        default:

                            break;
                    }
                }
            });
        }
    }

}
