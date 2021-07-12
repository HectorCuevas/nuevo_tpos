package com.tpos_prosisco.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.tpos_prosisco.beans.User;


public class LogueoViewModel extends AndroidViewModel {

    private LogueoRepository logueoRepository;

    public LogueoViewModel(@NonNull Application application) {
        super(application);

        logueoRepository = new LogueoRepository(application);
    }

    public boolean getUser(User user){
        return  logueoRepository.getUser(user);
    }
}
