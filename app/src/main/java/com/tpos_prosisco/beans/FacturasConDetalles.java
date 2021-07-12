package com.tpos_prosisco.beans;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.tpos_prosisco.beans.DetalleFactura;
import com.tpos_prosisco.beans.Factura;

import java.util.List;

/*** CLASE EXCLUSIVA PARA MANEJAR LAS RELACIONes DE ONE TO MANY EN ROOM  ***/
public class FacturasConDetalles {

    @Embedded
    public Factura factura;
    @Relation(
            parentColumn = "id",
            entityColumn = "id_factura"
    )

    public List<DetalleFactura> detalleFacturaList;

}
