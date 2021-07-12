package com.tpos_prosisco.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tpos_prosisco.api.interfaces.ClienteDao;
import com.tpos_prosisco.api.interfaces.FacturaDao;
import com.tpos_prosisco.api.interfaces.ProductoDao;
import com.tpos_prosisco.api.interfaces.PromocionDao;
import com.tpos_prosisco.api.interfaces.SesionDao;
import com.tpos_prosisco.api.interfaces.VendedorDao;
import com.tpos_prosisco.beans.Cliente;
import com.tpos_prosisco.beans.DetalleFactura;
import com.tpos_prosisco.beans.Factura;
import com.tpos_prosisco.beans.Logueo;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Promocion;
import com.tpos_prosisco.beans.Vendedor;

@Database(entities = {
        Producto.class,
        Promocion.class,
        Vendedor.class,
        Logueo.class,
        Factura.class,
        DetalleFactura.class,
        Cliente.class
}
        , version = 21
        , exportSchema = false
)
public abstract class TposDatabase extends RoomDatabase {
    public abstract ProductoDao productoDao();

    public abstract PromocionDao promocionDao();

    public abstract VendedorDao vendedorDao();

    public abstract SesionDao sesionDao();

    public abstract FacturaDao facturaDao();

    public abstract ClienteDao clienteDao();

    private static volatile TposDatabase INSTANCE;


    public static TposDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TposDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TposDatabase.class, "tpos_database_v3")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
