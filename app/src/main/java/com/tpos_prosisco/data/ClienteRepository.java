package com.tpos_prosisco.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.api.Client.AppClient;
import com.tpos_prosisco.api.interfaces.ClienteDao;
import com.tpos_prosisco.api.interfaces.ClienteService;
import com.tpos_prosisco.beans.Cliente;
import com.tpos_prosisco.beans.Responses.ClienteResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tpos_prosisco.ApplicationTpos.IMEI;

public class ClienteRepository {

    private AppClient appClient;
    private ClienteService clienteService;
    private List<Cliente> clientes = new ArrayList<>();
    private ClienteDao clienteDao;


    public ClienteRepository(Application application) {
        appClient = AppClient.getInstance();
        clienteService = appClient.getClienteService();
        TposDatabase tposDatabase  = TposDatabase.getDatabase(application);
        clienteDao = tposDatabase.clienteDao();
    }

    public MutableLiveData<List<Cliente>> getClientes(String ruta, String canal){
        final MutableLiveData<List<Cliente>> clientesMut = new MutableLiveData<List<Cliente>>();
        Call<ClienteResponse> call = clienteService.getClientes(IMEI, ruta, canal);
        call.enqueue(new Callback<ClienteResponse>() {
            @Override
            public void onResponse(Call<ClienteResponse> call, Response<ClienteResponse> response) {
                if(response.isSuccessful()){
                   // Object o = response.body();
                    clientes = Arrays.asList(response.body().getClientes());
                    clientesMut.setValue(clientes);
                }else{
                    Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ClienteResponse> call, Throwable t) {
                Toast.makeText(ApplicationTpos.getInstance(), "Algo ha salido mal: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return clientesMut;
    }

    public void insert(Cliente cliente){
        new insertAsync(clienteDao).execute(cliente);
    }

    public void update(Cliente cliente){
        new update(clienteDao, cliente).execute();
    }

    public LiveData<List<Cliente>> getDBClientes(){
        return  clienteDao.getAll();
    }

    public LiveData<List<Cliente>> getDBClientesFueraRuta(){
        return  clienteDao.getAllFueraRuta();
    }

    public void delete(){
        new deleteAll(clienteDao).execute();
    }


    private static class deleteAll extends AsyncTask<Void, Void, Void> {
        private ClienteDao clienteDaoAsync;

        deleteAll(ClienteDao clienteDao){
            clienteDaoAsync = clienteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            clienteDaoAsync.deleteAll();
            return null;
        }
    }

    private static class update extends AsyncTask<Void, Void, Void> {
        private ClienteDao clienteDaoAsync;
        private Cliente cliente;

        update(ClienteDao clienteDao, Cliente cliente){
            clienteDaoAsync = clienteDao;
            this.cliente = cliente;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            clienteDaoAsync.updateCliente(cliente.getCoCli());
            return null;
        }
    }

    private static class insertAsync extends AsyncTask<Cliente, Void, Void> {
        private ClienteDao clienteDaoAsync;

        insertAsync(ClienteDao clienteDao){
            clienteDaoAsync = clienteDao;
        }

        @Override
        protected Void doInBackground(Cliente... clientes) {
            clienteDaoAsync.insert(clientes[0]);
            return null;
        }
    }
}
