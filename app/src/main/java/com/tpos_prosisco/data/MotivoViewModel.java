package com.tpos_prosisco.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tpos_prosisco.beans.CodigoMotivo;
import com.tpos_prosisco.beans.Correlativo;
import com.tpos_prosisco.beans.Motivo;
import com.tpos_prosisco.beans.VentaDiaria;

import java.util.List;

/**
 * Creada por Norman el 11/10/2020
 **/
public class MotivoViewModel extends AndroidViewModel {

    MotivoRepository motivoRepository;

    public MotivoViewModel(@NonNull Application application) {
        super(application);
        motivoRepository= new MotivoRepository(application);
    }


    public LiveData<List<CodigoMotivo>> getMotivos(){
        return  motivoRepository.getMotivos();
    }


    public LiveData<Integer> setMotivo(Motivo motivo){
        return motivoRepository.setMotivo(motivo);
    }
}
