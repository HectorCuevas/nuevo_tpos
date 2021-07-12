package com.tpos_prosisco.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tpos_prosisco.beans.Correlativo;

public class CorrelativoViewModel extends AndroidViewModel {

    CorrelativoRepository correlativoRepository;

    public CorrelativoViewModel(@NonNull Application application) {
        super(application);
        correlativoRepository = new CorrelativoRepository(application);
    }

    public LiveData<Integer> getID(Correlativo correlativo){
        return correlativoRepository.getID(correlativo);
    }
}
