package com.tpos_prosisco.api.interfaces;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tpos_prosisco.beans.Promocion;

import java.util.List;

@Dao
public interface PromocionDao {

    @Insert
    void insert(Promocion promocion);

    @Query("SELECT * FROM PROMOCION")
    LiveData<List<Promocion>> getAll();

    @Query("DELETE FROM promocion")
    void deleteAll();
}
