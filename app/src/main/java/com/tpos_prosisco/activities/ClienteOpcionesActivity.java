
package com.tpos_prosisco.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.tpos_prosisco.R;
import com.tpos_prosisco.fragments.venta.MasivoFragment;

import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;

public class ClienteOpcionesActivity extends AppCompatActivity {
    private GridLayout mainGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_opciones);
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);
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
                            if(nuevaVenta.getCliente().getSaldo() == 0.0){
                                Intent i = new Intent(ClienteOpcionesActivity.this, PromocionesActivity.class);
                                startActivity(i);
                            }else
                                Toast.makeText(getApplicationContext(), "El cliente tiene facturas pendientes de pago", Toast.LENGTH_LONG).show();
                            
                            break;
                        case 1:
                        {
                            Intent intent = new Intent(ClienteOpcionesActivity.this, MotivoActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }
                        case 2:
                        {
                            Intent intent = new Intent(ClienteOpcionesActivity.this, CobroActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }
                        case 3:
                        {
                            Intent intent = new Intent(ClienteOpcionesActivity.this, SaldoActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }
                        case 4:
                        {
                            Intent intent = new Intent(ClienteOpcionesActivity.this, MovimientosActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }
                        default:
                            break;
                    }
                }
            });
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent changePrefs = new Intent(getApplicationContext(), ClienteActivity.class);
        finish();
        startActivity(changePrefs);
        if (changePrefs.resolveActivity(getPackageManager()) != null) {
            startActivity(changePrefs);
        }
        return super.onKeyDown(keyCode, event);
    }

}
