package com.tpos_prosisco.api.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.tpos_prosisco.beans.Logueo;

import java.util.List;

@Dao
public interface SesionDao {

    @Insert
    void insert(Logueo logueo);

    @Query("SELECT * FROM LOGUEO")
    LiveData<List<Logueo>> getAll();

    @Query("DELETE FROM LOGUEO")
    void deleteAlllogueos();
}
