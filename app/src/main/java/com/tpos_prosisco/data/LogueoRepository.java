package com.tpos_prosisco.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.LogueoService;
import com.tpos_prosisco.beans.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogueoRepository {
    Call<Integer> call;
    private LogueoService logueoService;
    private AppClient appClient;
    private boolean exist = false;
    int id_global = 10;

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public int getId_global() {
        return id_global;
    }

    public void setId_global(int id_global) {
        this.id_global = id_global;
    }


    public LogueoRepository(Application application) {
        appClient = AppClient.getInstance();
        logueoService = appClient.getLogueoService();
    }

    public boolean getUser(User user) {
        call = logueoService.getUser(user);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Object value = response.body();
                if (response.isSuccessful()) {
                    int i = Integer.parseInt(value.toString());
                    //Si el usuario existe devuelve 2 sino 0
                    setId_global(i);
                    if (i == 2) {
                        setExist(true);
                    }
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return  isExist();
    }

    private  class insertAsync extends AsyncTask<User, Void, Void> {


        insertAsync(){

        }

        @Override
        protected Void doInBackground(User... promociones) {
            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Object value = response.body();
                    if (response.isSuccessful()) {
                        int i = Integer.parseInt(value.toString());
                        //Si el usuario existe devuelve 2 sino 0
                        setId_global(i);
                        if (i == 2) {
                            setExist(true);
                        }
                    }
                }
                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });

            return null;
        }
    }


}


