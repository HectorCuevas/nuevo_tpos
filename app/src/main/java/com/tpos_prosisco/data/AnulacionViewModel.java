package com.tpos_prosisco.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * Creada por Norman el 9/16/2020
 **/
public class AnulacionViewModel extends AndroidViewModel {

    AnulacionRepository anulacionRepository;

    public AnulacionViewModel(@NonNull Application application) {
        super(application);
        anulacionRepository = new AnulacionRepository(application);
    }

    public LiveData<String> setAnulacion(String imei, String ruta, int fact_num){
        return anulacionRepository.setAnulacion(imei, ruta, fact_num);
    }
}
