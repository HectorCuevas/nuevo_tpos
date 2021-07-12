package com.tpos_prosisco.api.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tpos_prosisco.beans.Producto;

import java.util.List;

@Dao
public interface ProductoDao {
    @Insert
    void insert(Producto producto);

    @Update
    void update(Producto producto);

    @Query("DELETE FROM producto")
    void deleteAll();

    @Query("DELETE FROM PRODUCTO WHERE id = :idProducto and serial !='' ")
    void deleteById(int idProducto);

    @Query("SELECT * FROM PRODUCTO")
    LiveData<List<Producto>> getAll();

}
