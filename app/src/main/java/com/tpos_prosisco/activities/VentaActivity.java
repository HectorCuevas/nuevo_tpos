package com.tpos_prosisco.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tpos_prosisco.R;

public class VentaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);
        getSupportActionBar().hide();
    }
}
