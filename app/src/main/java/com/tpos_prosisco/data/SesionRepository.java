package com.tpos_prosisco.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.LogueoService;
import com.tpos_prosisco.api.interfaces.SesionDao;
import com.tpos_prosisco.beans.Logueo;
import com.tpos_prosisco.beans.Responses.LogueoResponse;
import com.tpos_prosisco.beans.Sesion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SesionRepository {
    Call<LogueoResponse> call;
    private LogueoService logueoService;
    private AppClient appClient;
    SesionDao sesionDao;
    private List<Logueo> logueos = new ArrayList<Logueo>();


    public SesionRepository(Application application) {
        appClient = AppClient.getInstance();
        logueoService = appClient.getLogueoService();
        TposDatabase tposDatabase  = TposDatabase.getDatabase(application);
        sesionDao = tposDatabase.sesionDao();
    }

    public MutableLiveData<Logueo> getUser(Sesion user) {
        final MutableLiveData<Logueo> logueosMut = new MutableLiveData<Logueo>();
        call = logueoService.setLogueo(user);
        call.enqueue(new Callback<LogueoResponse>() {
            @Override
            public void onResponse(Call<LogueoResponse> call, Response<LogueoResponse> response) {
                Object value = response.body();
                if (response.isSuccessful()) {
                    //Object o = response.body();
                    logueos = Arrays.asList(response.body().getLogueos());
                    //logueosMut.setValue(logueos);
                    delete();
                    for(Logueo logueo : logueos){
                        insert(logueo);
                        ApplicationTpos.logueoInfo = logueo;
                        logueosMut.setValue(logueo);
                    }
                   // Toast.makeText(ApplicationTpos.getInstance(), "Se almaceno", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LogueoResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return logueosMut;
    }

    public void setLogout(Sesion user) {
        final MutableLiveData<List<Logueo>> logueosMut = new MutableLiveData<List<Logueo>>();
        call = logueoService.setLogueo(user);
        call.enqueue(new Callback<LogueoResponse>() {
            @Override
            public void onResponse(Call<LogueoResponse> call, Response<LogueoResponse> response) {
                Object value = response.body();
                if (response.isSuccessful()) {
                    Object o = response.body();
                    logueos = Arrays.asList(response.body().getLogueos());
                   // Toast.makeText(ApplicationTpos.getInstance(), "Logout", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LogueoResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void insert(Logueo logueo){
        new insertAsync(sesionDao).execute(logueo);
    }

    public void delete(){
        new deleteAsync(sesionDao).execute();
    }

    public LiveData<List<Logueo>> getDBinfoLogueo(){
        return sesionDao.getAll();
    }

    private static class insertAsync extends AsyncTask<Logueo, Void, Void> {
        private SesionDao sesionDaoAsync;

        insertAsync(SesionDao sesionDao){
            sesionDaoAsync = sesionDao;
        }

        @Override
        protected Void doInBackground(Logueo... logueos) {
            sesionDaoAsync.insert(logueos[0]);
            return null;
        }
    }
    private static class deleteAsync extends AsyncTask<Logueo, Void, Void> {
        private SesionDao sesionDaoAsync;

        deleteAsync(SesionDao sesionDao){
            sesionDaoAsync = sesionDao;
        }

        @Override
        protected Void doInBackground(Logueo... logueos) {
            sesionDaoAsync.deleteAlllogueos();
            return null;
        }
    }

}
