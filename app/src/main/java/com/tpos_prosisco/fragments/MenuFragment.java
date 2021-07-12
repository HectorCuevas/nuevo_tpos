package com.tpos_prosisco.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.activities.ConfiguracionActivity;
import com.tpos_prosisco.activities.FacturaActivity;
import com.tpos_prosisco.activities.InventarioActivity;
import com.tpos_prosisco.activities.LoginActivity;
import com.tpos_prosisco.activities.MainActivity;
import com.tpos_prosisco.activities.MenuReporteActivity;
import com.tpos_prosisco.activities.ReporteVentaActivity;
import com.tpos_prosisco.beans.Sesion;
import com.tpos_prosisco.data.SesionViewModel;
import com.tpos_prosisco.fragments.venta.MasivoFragment;
import com.tpos_prosisco.fragments.venta.VendedoresFragment;

import java.util.Objects;

import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;


/**
 *
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    private View view;
    private GridLayout mainGrid;
    private SesionViewModel sesionViewModel;
    private TextView lblSalir;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu, container, false);
        mainGrid = (GridLayout) view.findViewById(R.id.mainGrid);

        lblSalir = view.findViewById(R.id.lblSalir);
        setSingleEvent(mainGrid);

        lblSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changePrefs = new Intent(getContext(), LoginActivity.class);
                startActivity(changePrefs);
                getActivity().finish();
            }
        });

        sesionViewModel = ViewModelProviders.of(this)
                .get(SesionViewModel.class);

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
                            changeFragment(setFragment(logueoInfo.getNombreCanal()));
                            break;
                        case 1:
                        {
                            Intent i = new Intent(getActivity(), MenuReporteActivity.class);
                            startActivity(i);
                            getActivity().finish();
                            break;
                        }
                        case 2:
                        {
                           // Toast.makeText(getActivity(), "No disponible", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), FacturaActivity.class);
                            startActivity(i);
                            getActivity().finish();
                            break;
                        }
                        case 3:
                        {
                            Intent i = new Intent(getActivity(), InventarioActivity.class);
                            startActivity(i);
                            getActivity().finish();
                            break;
                        }
                        case 4:
                        {
                            dialogFinish();
                            break;
                        }
                        case 5:
                            Intent i = new Intent(getActivity(), ConfiguracionActivity.class);
                            startActivity(i);
                            getActivity().finish();
                            break;
                        default:
                            fragment = new VendedoresFragment();
                            changeFragment(fragment);
                            break;
                    }



                }
            });
        }
    }

    private void changeFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private Fragment setFragment(String canal){
        Fragment fragment = null;
        switch (canal){
            case "MASIVO":
                fragment = new MasivoFragment();
                break;
            default:
                fragment = new VendedoresFragment();
                break;
        }

        return fragment;

    }

    private void dialogFinish() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set title
        alertDialogBuilder.setTitle("TARJETAZO");
        alertDialogBuilder
                .setMessage("¿Desea realizar el cierre del día?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        setLogout();
                        Intent changePrefs = new Intent(getContext(), LoginActivity.class);
                        startActivity(changePrefs);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();


    }

    private void setLogout() {
        sesionViewModel.setLogout(new Sesion("1", IMEI, ApplicationTpos.latitud, ApplicationTpos.longitud));
    }
}
