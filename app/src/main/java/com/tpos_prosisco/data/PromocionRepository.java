package com.tpos_prosisco.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.PromocionDao;
import com.tpos_prosisco.api.interfaces.PromocionService;
import com.tpos_prosisco.beans.Promocion;
import com.tpos_prosisco.beans.Responses.PromocionResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tpos_prosisco.ApplicationTpos.IMEI;

public class PromocionRepository {

    private PromocionService promocionService;
    private AppClient appClient;
    private List<Promocion> promociones = new ArrayList<Promocion>();
    PromocionDao promocionDao;

    PromocionRepository(Application application){
        appClient = AppClient.getInstance();
        promocionService = appClient.getPromocionService();
        TposDatabase tposDatabase  = TposDatabase.getDatabase(application);
        promocionDao = tposDatabase.promocionDao();
    }

    public MutableLiveData<List<Promocion>> getPromociones(String canal){
        final MutableLiveData<List<Promocion>> promocionesMut = new MutableLiveData<List<Promocion>>();
        Call<PromocionResponse> call = promocionService.getPromociones(IMEI, canal);
        call.enqueue(new Callback<PromocionResponse>() {
            @Override
            public void onResponse(Call<PromocionResponse> call, Response<PromocionResponse> response) {
                if(response.isSuccessful()){
                    Object o = response.body();
                    promociones = Arrays.asList(response.body().getPromociones());
                    promocionesMut.setValue(promociones);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<PromocionResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return promocionesMut;
    }

    public LiveData<List<Promocion>> getDBPromociones(){
        return promocionDao.getAll();
    }

    public void insert(Promocion promocion){
        new insertAsync(promocionDao).execute(promocion);
    }

    public void delete() {
        new deleteAll(promocionDao).execute();
    }

    private static class insertAsync extends AsyncTask<Promocion, Void, Void> {
        private PromocionDao promocionDaoAsync;

        insertAsync(PromocionDao promocionDao){
            promocionDaoAsync = promocionDao;
        }

        @Override
        protected Void doInBackground(Promocion... promociones) {
            promocionDaoAsync.insert(promociones[0]);
            return null;
        }
    }
    private static class deleteAll extends AsyncTask<Void, Void, Void> {
        private PromocionDao promocionDaoAsync;

        deleteAll(PromocionDao promocionDao){
            promocionDaoAsync = promocionDao;
        }

        @Override

        protected Void doInBackground(Void... voids) {
            promocionDaoAsync.deleteAll();
            return null;
        }
    }
}
