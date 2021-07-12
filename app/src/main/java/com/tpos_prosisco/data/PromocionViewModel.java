package com.tpos_prosisco.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.tpos_prosisco.beans.Promocion;

import java.util.List;

public class PromocionViewModel extends AndroidViewModel {


    private PromocionRepository promocionRepository;

    public PromocionViewModel(@NonNull Application application) {
        super(application);
        promocionRepository = new PromocionRepository(application);
    }


    public void insertPromocion(Promocion promocion){
        promocionRepository.insert(promocion);
    }

    public LiveData<List<Promocion>> getPromociones(String canal){
        return promocionRepository.getPromociones(canal);
    }
    public void deleteAll(){
        promocionRepository.delete();
    }
    public LiveData<List<Promocion>> getDBPromociones(){
        return promocionRepository.getDBPromociones();
    }
}
