package com.tpos_prosisco.data;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.CorrelativoService;
import com.tpos_prosisco.beans.Correlativo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CorrelativoRepository {
    private CorrelativoService correlativoService;
    private AppClient appClient;

    public int getId_global() {
        return id_global;
    }

    public void setId_global(int id_global) {
        this.id_global = id_global;
    }

    int id_global = 0;

    public CorrelativoRepository(Application application){
        appClient = AppClient.getInstance();
        correlativoService = appClient.getCorrelativoService();
        TposDatabase tposDatabase  = TposDatabase.getDatabase(application);

    }

    public MutableLiveData<Integer> getID(Correlativo correlativo) {
        final MutableLiveData<Integer> resMut = new MutableLiveData<Integer>();
        Call<Integer> call = correlativoService.getID(correlativo);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Object value = response.body();
                int id = 0;
                if(response.isSuccessful()) {
                    int i = Integer.parseInt(value.toString());
                    setId_global(i);
                    resMut.setValue(i);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });
     //   Toast.makeText(ApplicationTpos.getInstance(), "ID actual " + String.valueOf( getId_global()), Toast.LENGTH_SHORT).show();
        return resMut;
    }

}
