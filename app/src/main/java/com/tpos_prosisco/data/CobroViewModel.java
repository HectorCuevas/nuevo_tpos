package com.tpos_prosisco.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.tpos_prosisco.beans.Cobro;
import com.tpos_prosisco.beans.InfoCobro;
import com.tpos_prosisco.beans.Motivo;
import com.tpos_prosisco.beans.Movimiento;

import java.util.List;

/**
 * Creada por Norman el 11/18/2020
 **/
public class CobroViewModel extends AndroidViewModel {

    CobroRepository cobroRepository;

    public CobroViewModel(@NonNull Application application) {
        super(application);
        cobroRepository = new CobroRepository(application);
    }

    public LiveData<List<Cobro>> getCobros(String co_cli){
        return  cobroRepository.getCobros(co_cli);
    }

    public LiveData<List<Movimiento>> getMovimientos(String co_cli){
        return  cobroRepository.getMovimientos(co_cli);
    }


    public LiveData<Integer> setCobro(InfoCobro cobro){
        return cobroRepository.setCobro(cobro);
    }

}
