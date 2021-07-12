package com.tpos_prosisco.data;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.tpos_prosisco.beans.Vendedor;
import java.util.List;

public class VendedorViewModel extends AndroidViewModel {

    private VendedorRepository vendedorRepository;


    public VendedorViewModel(@NonNull Application application){
        super(application);
        vendedorRepository = new VendedorRepository(application);
    }

    public void deleteAll(){
        vendedorRepository.delete();
    }

    public void insertVendedor(Vendedor vendedor){
        vendedorRepository.insert(vendedor);
    }

    public LiveData<List<Vendedor>> getVendedores(String ruta){
        return vendedorRepository.getVendedores(ruta.trim());
    }

    public LiveData<List<Vendedor>> getDBVendedores(){
        return vendedorRepository.getDBVendedores();
    }


}
