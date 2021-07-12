package com.tpos_prosisco.api.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import com.tpos_prosisco.beans.DetalleFactura;
import com.tpos_prosisco.beans.Factura;
import com.tpos_prosisco.beans.FacturasConDetalles;

import java.util.List;

@Dao
public interface FacturaDao {

    @Transaction
    @Insert
    long insertEncabezado(Factura factura);

    @Insert
    void insertDetalles(List<DetalleFactura> detalleFacturas);

    //Obtenemos el encabezado de las facturas
    @Transaction
    @Query("SELECT * FROM factura")
    LiveData<List<FacturasConDetalles>> getAll();

    @Transaction
    @Query("SELECT * FROM factura where id = :id")
    LiveData<Factura> getFactura(int id);

    @Transaction
    @Query("SELECT count(*) FROM factura where id = :id")
    LiveData<Integer> getFacturaID(int id);

    @Query("DELETE FROM factura WHERE id = :fact_num")
    void deleteById(int fact_num);

}
