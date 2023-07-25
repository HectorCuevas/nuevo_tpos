package com.tpos_prosisco.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tpos_prosisco.beans.Cliente;

import java.util.List;

public class ClienteViewModel extends AndroidViewModel {

    private ClienteRepository clienteRepository;


    public ClienteViewModel(@NonNull Application application) {
        super(application);

        clienteRepository = new ClienteRepository(application);
    }


    public void deleteAll(){
        clienteRepository.delete();
    }

    public LiveData<List<Cliente>> getClientes(String ruta, String canal){
        return clienteRepository.getClientes(ruta.trim(), canal);
    }

    public LiveData<List<Cliente>> getDBClientes(){
        return clienteRepository.getDBClientes();
    }
    public LiveData<List<Cliente>> getDBClientesFueraRuta(){
        return clienteRepository.getDBClientesFueraRuta();
    }

    @Deprecated
    public void insertCliente(Cliente cliente){
        clienteRepository.insert(cliente);
    }

    public void updateCliente(Cliente cliente){
        clienteRepository.update(cliente);
    }

}
