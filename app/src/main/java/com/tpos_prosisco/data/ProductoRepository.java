package com.tpos_prosisco.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.ProductoDao;
import com.tpos_prosisco.api.interfaces.ProductoService;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Responses.ProductoResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tpos_prosisco.ApplicationTpos.IMEI;

public class ProductoRepository {
    private ProductoService productoService;
    private AppClient appClient;
    private List<Producto> respuesta = new ArrayList<Producto>();
    private LiveData<List<Producto>>  carrito;
     ProductoDao productoDao;

    public ProductoRepository(Application application){
        appClient = AppClient.getInstance();
        productoService = appClient.getProductoService();
        TposDatabase tposDatabase  = TposDatabase.getDatabase(application);
        productoDao = tposDatabase.productoDao();
    }


    public MutableLiveData<List<Producto>>  getProductos(String ruta){
        final MutableLiveData<List<Producto>> productos = new MutableLiveData<List<Producto>>();
        Call<ProductoResponse> call = productoService.getProductos(IMEI, ruta);
        call.enqueue(new Callback<ProductoResponse>() {
            @Override
            public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                if(response.isSuccessful()){
                    respuesta = Arrays.asList(response.body().getProductos());
                    productos.setValue(respuesta);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: productos", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ProductoResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return productos;
    }

    public LiveData<List<Producto>>  getDBProductos(){
        return productoDao.getAll();
    }

    public void insert(Producto producto){
        new insertAsync(productoDao).execute(producto);
    }

    public void delete() {

        new deleteAll(productoDao).execute();
    }
    public void deleteById(Producto producto) {

        new deleteByid(productoDao).execute(producto);
    }
    private static class deleteAll extends AsyncTask<Void, Void, Void> {
        private ProductoDao productoDaoAsync;

        deleteAll(ProductoDao producto){
            productoDaoAsync = producto;
        }
        @Override

        protected Void doInBackground(Void... voids) {
            productoDaoAsync.deleteAll();
            return null;
        }
    }
    private static class deleteByid extends AsyncTask<Producto, Void, Void> {
        private ProductoDao productoDaoAsync;

        deleteByid(ProductoDao producto){
            productoDaoAsync = producto;
        }
        @Override

        protected Void doInBackground(Producto... productos) {
            productoDaoAsync.deleteById(productos[0].getId());
            return null;
        }
    }
    private static class insertAsync extends AsyncTask<Producto, Void, Void>{
        private ProductoDao productoDaoAsync;

        insertAsync(ProductoDao producto){
            productoDaoAsync = producto;
        }

        @Override
        protected Void doInBackground(Producto... productos) {
            productoDaoAsync.insert(productos[0]);
            return null;
        }


    }
}
