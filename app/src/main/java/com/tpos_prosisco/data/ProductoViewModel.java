package com.tpos_prosisco.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tpos_prosisco.beans.Producto;

import java.util.List;

public class ProductoViewModel extends AndroidViewModel {

    private ProductoRepository productoRepository;


    public ProductoViewModel(@NonNull Application application) {
        super(application);
        productoRepository = new ProductoRepository(application);
    }

    public void deleteAll(){
        productoRepository.delete();
    }

    public void deleteById(Producto producto){
        productoRepository.deleteById(producto);
    }

    @Deprecated
    public void insertProducto(Producto producto){
        productoRepository.insert(producto);
    }

    public LiveData<List<Producto>> getProductos(String ruta){
        return productoRepository.getProductos(ruta);
    }

    public LiveData<List<Producto>> getDBProductos(){
        return productoRepository.getDBProductos();
    }
}
