package com.tpos_prosisco.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.VendedorDao;
import com.tpos_prosisco.api.interfaces.VendedoreService;
import com.tpos_prosisco.beans.Responses.VendedorResponse;
import com.tpos_prosisco.beans.Vendedor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tpos_prosisco.ApplicationTpos.IMEI;

public class VendedorRepository {

    private VendedoreService vendedoreService;
    private AppClient appClient;
    private List<Vendedor> vendedores = new ArrayList<Vendedor>();
    private VendedorDao vendedorDao;

    VendedorRepository(Application application){
        appClient = AppClient.getInstance();
        vendedoreService = appClient.getVendedoreService();
        TposDatabase tposDatabase  = TposDatabase.getDatabase(application);
        vendedorDao = tposDatabase.vendedorDao();
    }

    public MutableLiveData<List<Vendedor>> getVendedores(String ruta){
        final MutableLiveData<List<Vendedor>> promocionesMut = new MutableLiveData<List<Vendedor>>();
        Call<VendedorResponse> call = vendedoreService.getVendedores(IMEI, ruta);
        call.enqueue(new Callback<VendedorResponse>() {
            @Override
            public void onResponse(Call<VendedorResponse> call, Response<VendedorResponse> response) {
                if(response.isSuccessful()){
                    Object o = response.body();
                    vendedores = Arrays.asList(response.body().getVendedores());
                    promocionesMut.setValue(vendedores);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<VendedorResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return promocionesMut;
    }

    public LiveData<List<Vendedor>> getDBVendedores(){
        return vendedorDao.getAll();
    }

    public void insert(Vendedor vendedor){
        new insertAsync(vendedorDao).execute(vendedor);
    }


    public void delete(){
        new deleteAll(vendedorDao).execute();
    }
    private static class deleteAll extends AsyncTask<Void, Void, Void> {
        private VendedorDao vendedorDaoAsync;

        deleteAll(VendedorDao vendedorDao){
            vendedorDaoAsync = vendedorDao;
        }
        @Override

        protected Void doInBackground(Void... voids) {
            vendedorDaoAsync.deleteAll();
            return null;
        }
    }
    private static class insertAsync extends AsyncTask<Vendedor, Void, Void> {
        private VendedorDao vendedorDaoAsync;

        insertAsync(VendedorDao producto){
            vendedorDaoAsync = producto;
        }

        @Override
        protected Void doInBackground(Vendedor... productos) {
            vendedorDaoAsync.insert(productos[0]);
            return null;
        }
    }

}
