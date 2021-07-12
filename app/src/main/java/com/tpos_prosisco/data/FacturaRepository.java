package com.tpos_prosisco.data;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.activities.MainActivity;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.FacturaDao;
import com.tpos_prosisco.api.interfaces.FacturaService;
import com.tpos_prosisco.api.interfaces.ProductoDao;
import com.tpos_prosisco.beans.Correlativo;
import com.tpos_prosisco.beans.DetalleFactura;
import com.tpos_prosisco.beans.Factura;
import com.tpos_prosisco.beans.FacturasConDetalles;
import com.tpos_prosisco.beans.Producto;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class FacturaRepository {

    private AppClient appClient;
    private FacturaService facturaService;
    private FacturaDao facturaDao;

    public FacturaRepository(Application application){
        appClient = AppClient.getInstance();
        facturaService = appClient.getFacturaService();
        TposDatabase tposDatabase  = TposDatabase.getDatabase(application);
        facturaDao = tposDatabase.facturaDao();
    }

    public int getUpdateID(Correlativo correlativo) {
        final int[] id_global = {0};
        Call<Integer> call = facturaService.getID(correlativo);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Object value = response.body();
                int id = 0;
                if(response.isSuccessful()){
                    id = Integer.parseInt(value.toString());
                    id_global[0] = id;
                    //Toast.makeText(ApplicationTpos.getInstance(), "ID actual " + String.valueOf(id), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });

        return id_global[0];
    }

    public void envioFactura(Factura pedido, boolean isInsert) {
      //  pedido.setCo_ven("ABCDEFLSJDSLJDSJDS");
        Call<Integer> call = facturaService.sendPedidoProfit(pedido);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Object value = response.body();
                try {
                    //27312536
                    if(response.isSuccessful()){
                        if(Integer.parseInt(value.toString()) == 1){
                            Toast.makeText(ApplicationTpos.getInstance(), "Enviado exitosamente", Toast.LENGTH_LONG).show();
                            getUpdateID(new Correlativo(IMEI,"FACTURA", logueoInfo.getCoSucu().trim(), 1));
                            deleteById(pedido);
                        }else{
                            Toast.makeText(ApplicationTpos.getInstance(), "(E100) Algo ha ido mal: No se ha enviado la factura", Toast.LENGTH_LONG).show();
                            if(isInsert)insert(pedido);
                        }
                    }else{
                        String str = response.errorBody().toString();
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ApplicationTpos.getInstance(), "(E101) Algo ha ido mal: No se ha enviado la factura" , Toast.LENGTH_LONG).show();
                        if(isInsert)insert(pedido);
                    }
                   // ApplicationTpos.carrito.clear();
                } catch (Exception e) {
                    Toast.makeText(ApplicationTpos.getInstance(),"(E103) Algo ha ido mal: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    if(isInsert)insert(pedido);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

    public LiveData<List<FacturasConDetalles>> getDBFacturas(){
        return facturaDao.getAll();
    }

    public LiveData<Factura> getFactura(int id){
        return facturaDao.getFactura(id);
    }

    public void insert(Factura factura)
    {
        new insertAsync(facturaDao).execute(factura);
    }
    public void deleteById(Factura factura) {

        new deleteByid(facturaDao).execute(factura);
    }
    private static class insertAsync extends AsyncTask<Factura, Void, Void> {
        private FacturaDao facturaDaoAsync;

        insertAsync(FacturaDao facturaDao){
            facturaDaoAsync = facturaDao;
        }

        @Override
        protected Void doInBackground(Factura... productos) {
            LiveData<Factura>  factura = facturaDaoAsync.getFactura(productos[0].getId());
            long i = facturaDaoAsync.insertEncabezado(productos[0]);
            for (DetalleFactura detalleFactura : productos[0].getItems()){
                detalleFactura.setId_factura(i);
            }
            facturaDaoAsync.insertDetalles(productos[0].getItems());
            //Toast.makeText(ApplicationTpos.getInstance(), "se inserto", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private static class deleteByid extends AsyncTask<Factura, Void, Void> {
        private FacturaDao facturaDaoAsync;

        deleteByid(FacturaDao facturaDao){
            facturaDaoAsync = facturaDao;
        }
        @Override
        protected Void doInBackground(Factura... factura) {
            facturaDaoAsync.deleteById(factura[0].getId());
            return null;
        }
    }
}
