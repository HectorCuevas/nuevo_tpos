package com.tpos_prosisco.api.interfaces;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tpos_prosisco.beans.Cliente;

import java.util.List;

@Dao
public interface ClienteDao {

    @Insert
    void insert(Cliente cliente);

    @Query("SELECT * FROM cliente where enRuta = 1")
    LiveData<List<Cliente>> getAll();

    @Query("SELECT * FROM cliente where enRuta = 0")
    LiveData<List<Cliente>> getAllFueraRuta();

    @Query("DELETE FROM cliente")
    void deleteAll();

    @Query("UPDATE CLIENTE SET enRuta = 0 WHERE coCli = :idCliente")
    void updateCliente(String idCliente);

}
