package com.tpos_prosisco.beans;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "promocion")
public class Promocion {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("Codigo_Promocion")
    @Expose
    private String codigoPromocion;
    @SerializedName("Descripcion")
    @Expose
    private String descripcion;
    @SerializedName("Renglon")
    @Expose
    private Integer renglon;
    @SerializedName("id_canal_venta")
    @Expose
    private Integer idCanalVenta;
    @SerializedName("Categoria_Filtro")
    @Expose
    private String categoriaFiltro;
    @SerializedName("Cantidad_item")
    @Expose
    private Integer cantidadItem;
    @SerializedName("Tipo_Precio_item")
    @Expose
    private Integer tipoPrecioItem;
    @SerializedName("Cantidad_TA")
    @Expose
    private Integer cantidadTA;
    @SerializedName("Tipo_Precio_TA")
    @Expose
    private Integer tipoPrecioTA;
    @SerializedName("Por_Bonificacion")
    @Expose
    private Integer porBonificacion;
    @SerializedName("Por_Descuento")
    @Expose
    private Integer porDescuento;
    @SerializedName("Item_Bonifacacion")
    @Expose
    private String itemBonifacacion;

    public String getCodigoPromocion() {
        return codigoPromocion;
    }

    public void setCodigoPromocion(String codigoPromocion) {
        this.codigoPromocion = codigoPromocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getRenglon() {
        return renglon;
    }

    public void setRenglon(Integer renglon) {
        this.renglon = renglon;
    }

    public Integer getIdCanalVenta() {
        return idCanalVenta;
    }

    public void setIdCanalVenta(Integer idCanalVenta) {
        this.idCanalVenta = idCanalVenta;
    }

    public String getCategoriaFiltro() {
        return categoriaFiltro;
    }

    public void setCategoriaFiltro(String categoriaFiltro) {
        this.categoriaFiltro = categoriaFiltro;
    }

    public Integer getCantidadItem() {
        return cantidadItem;
    }

    public void setCantidadItem(Integer cantidadItem) {
        this.cantidadItem = cantidadItem;
    }

    public Integer getTipoPrecioItem() {
        return tipoPrecioItem;
    }

    public void setTipoPrecioItem(Integer tipoPrecioItem) {
        this.tipoPrecioItem = tipoPrecioItem;
    }

    public Integer getCantidadTA() {
        return cantidadTA;
    }

    public void setCantidadTA(Integer cantidadTA) {
        this.cantidadTA = cantidadTA;
    }

    public Integer getTipoPrecioTA() {
        return tipoPrecioTA;
    }

    public void setTipoPrecioTA(Integer tipoPrecioTA) {
        this.tipoPrecioTA = tipoPrecioTA;
    }

    public Integer getPorBonificacion() {
        return porBonificacion;
    }

    public void setPorBonificacion(Integer porBonificacion) {
        this.porBonificacion = porBonificacion;
    }

    public Integer getPorDescuento() {
        return porDescuento;
    }

    public void setPorDescuento(Integer porDescuento) {
        this.porDescuento = porDescuento;
    }

    public String getItemBonifacacion() {
        return itemBonifacacion;
    }

    public void setItemBonifacacion(String itemBonifacacion) {
        this.itemBonifacacion = itemBonifacacion;
    }


}
