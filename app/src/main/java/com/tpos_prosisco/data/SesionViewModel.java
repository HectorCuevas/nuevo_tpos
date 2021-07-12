package com.tpos_prosisco.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tpos_prosisco.beans.Logueo;
import com.tpos_prosisco.beans.Sesion;

import java.util.List;

public class SesionViewModel extends AndroidViewModel {

    SesionRepository sesionRepository;

    public SesionViewModel(@NonNull Application application) {
        super(application);
        sesionRepository = new SesionRepository(application);
    }

    public void insertLogueo(Logueo promocion){
        sesionRepository.insert(promocion);
    }

    public LiveData<Logueo> getLogueo(Sesion sesion){
        return sesionRepository.getUser(sesion);
    }

    public void setLogout(Sesion sesion){
        sesionRepository.setLogout(sesion);
    }

    public LiveData<List<Logueo>> getDBLogueo(){
        return sesionRepository.getDBinfoLogueo();
    }

}
