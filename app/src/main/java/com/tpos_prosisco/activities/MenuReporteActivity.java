package com.tpos_prosisco.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import com.tpos_prosisco.R;
import com.tpos_prosisco.ReporteImeiSerialActivity;
import com.tpos_prosisco.beans.ReporteCategoria;

public class MenuReporteActivity extends AppCompatActivity {
    private View view;
    private GridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_reporte_activty);
        grid = findViewById(R.id.mainGrid);
        selectedOption(grid);
    }

    private void selectedOption(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    switch (finalI) {
                        case 0:
                            intent = new Intent(MenuReporteActivity.this, ReporteVendedorActivity.class);
                            break;
                        case 1:
                            intent = new Intent(MenuReporteActivity.this, ReporteFacturaActivity.class);
                            break;
                        case 2:
                            intent = new Intent(MenuReporteActivity.this, ReporteCategoriaActivity.class);
                            break;
                        case 3:
                            intent = new Intent(MenuReporteActivity.this, ReporteVentaActivity.class);
                            break;
                        case 4:
                            intent = new Intent(MenuReporteActivity.this, ReporteImeiActivity.class);
                            break;
                        case 5:
                            intent = new Intent(MenuReporteActivity.this, ReporteDiarioActivity.class);

                            break;
                        case 6:
                            intent = new Intent(MenuReporteActivity.this, ReporteImeiSerialActivity.class);

                            break;
                        case 7:
                            intent = new Intent(MenuReporteActivity.this, TrasladoActivity.class);
                            break;
                        case 8:
                            intent = new Intent(MenuReporteActivity.this, AnulacionActivity.class);
                            break;
                        default:
                            break;
                    }
                    startActivity(intent);
                }
            });
        }
    }
}
