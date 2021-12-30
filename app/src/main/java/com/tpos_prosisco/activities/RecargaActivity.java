package com.tpos_prosisco.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.RecargasAdapter;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Recarga;
import com.tpos_prosisco.fragments.MenuFragment;
import com.tpos_prosisco.fragments.paneles.MasivoRecargaFragment;
import com.tpos_prosisco.fragments.paneles.PanelesRecargaFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.logueoInfo;
import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;

public class RecargaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recarga);

        Fragment fragment= new MasivoRecargaFragment();

        switch (logueoInfo.getNombreCanal()) {
            case "MASIVO":
                fragment = new MasivoRecargaFragment();
                break;
            case "PANELES":
                fragment = new PanelesRecargaFragment();
                break;
            case "KIOSKOS":
                break;
            default:
                break;
        }

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.container, fragment)
                .commit();
    }

}
