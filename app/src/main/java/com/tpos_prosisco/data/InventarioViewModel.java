package com.tpos_prosisco.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tpos_prosisco.beans.Correlativo;
import com.tpos_prosisco.beans.Inventario;
import com.tpos_prosisco.beans.ReporteVendedor;

import java.util.List;

/**
 * Creada por Norman el 9/21/2020
 **/
public class InventarioViewModel extends AndroidViewModel {


    InventarioRepository inventarioRepository;

    public InventarioViewModel(@NonNull Application application) {
        super(application);
        inventarioRepository = new InventarioRepository();
    }

    public LiveData<List<Inventario>> getReporte(String ruta){
        return  inventarioRepository.getReporte(ruta);
    }
}
