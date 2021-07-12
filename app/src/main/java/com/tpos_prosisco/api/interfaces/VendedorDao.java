package com.tpos_prosisco.api.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tpos_prosisco.beans.Vendedor;

import java.util.List;

@Dao
public interface VendedorDao {
    @Insert
    void insert(Vendedor vendedor);

    @Query("DELETE FROM vendedor")
    void deleteAll();

    @Query("SELECT * FROM vendedor")
    LiveData<List<Vendedor>> getAll();
}
