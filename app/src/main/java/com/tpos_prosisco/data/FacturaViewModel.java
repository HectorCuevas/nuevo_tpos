package com.tpos_prosisco.data;

import android.app.Application;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tpos_prosisco.beans.DetalleFactura;
import com.tpos_prosisco.beans.Factura;
import com.tpos_prosisco.beans.FacturasConDetalles;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Venta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.IMEI;
import static com.tpos_prosisco.ApplicationTpos.carrito;
import static com.tpos_prosisco.ApplicationTpos.latitud;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;
import static com.tpos_prosisco.ApplicationTpos.longitud;
import static com.tpos_prosisco.ApplicationTpos.nuevaVenta;

public class FacturaViewModel extends AndroidViewModel {

    private FacturaRepository facturaRepository;

    public FacturaViewModel(@NonNull Application application) {
        super(application);
        facturaRepository = new FacturaRepository(application);
    }




    public void sendFactura(int id, Venta venta){
        facturaRepository.envioFactura(setFactura(id, venta), true);
    }
    public void sendFactura(Factura factura){
        facturaRepository.envioFactura(factura, false);
    }


    public LiveData<List<FacturasConDetalles>> getFacturas(){
       return facturaRepository.getDBFacturas();
    }

    public LiveData<Factura> getFactura(int id){
        return facturaRepository.getFactura(id);
    }

    public void deleteById(Factura factura){
        facturaRepository.deleteById(factura);
    }



    public void insertDBFactura(Venta venta){
            facturaRepository.insert(setFactura(99, venta));
    }

    private Factura setFactura(int id, Venta venta) {
        Date cDate = new Date();
        String ruta = logueoInfo.getCoSucu().trim();
        String tipoVenta = logueoInfo.getTipoVenta().trim();
        String fDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cDate);
        Factura factura = new Factura();
        factura.setFact_num(String.valueOf(id));
        factura.setTipo_doc("Factura");
        factura.setImei(IMEI);
        factura.setRuta(ruta);
        factura.setCo_cli(nuevaVenta.getCliente().getCoCli());
        factura.setCo_ven(nuevaVenta.getVendedor().getCOVEN());
        factura.setNit(venta.getNit());
        factura.setDpi(venta.getDpi());
        factura.setNombre(venta.getNombre());
        factura.setDireccion("0");
        factura.setTelefono(String.valueOf(venta.getNumero()));
        factura.setForma_pag(venta.getCondicionPago());
        factura.setTipo_venta(tipoVenta);
        double total = setTotal();
        factura.setTotal(total);
        factura.setCobro(total);
        factura.setComentario("NA");
        factura.setFe_us_in(fDate);
        factura.setNro_doc((double) id);
        //double longtitud = 0;
        //double latitud = 0;
        factura.setLongitud(longitud);
        factura.setLatitud(latitud);
        factura.setDepartamento(venta.getDepto());
        factura.setMunicipio(venta.getMunicipio());
        factura.setZona(venta.getZona());
        factura.setImagen1(setImage(venta.getImagen()));
        factura.setImagen2(setImage(venta.getImagen2()));
        factura.setItems(setDetalles(id, ruta));
        return factura;
    }

    private List<DetalleFactura> setDetalles(int id, String ruta) {
        List<DetalleFactura> pedidos = new ArrayList<>();
        for (Item item : carrito) {
            DetalleFactura detalleFactura = new DetalleFactura();
            detalleFactura.setTipo_doc("Factura");
            detalleFactura.setImei(IMEI);
            detalleFactura.setFact_num(id);
            if (item.getProducto().getComentario() != null && !item.getProducto().getComentario().isEmpty()) {
                detalleFactura.setComentario_renglon(item.getProducto().getComentario());
            } else detalleFactura.setComentario_renglon("NA");
            detalleFactura.setCo_art(item.getProducto().getCoArt());
            detalleFactura.setPrec_vta(item.getPrecio());
            detalleFactura.setRuta(ruta);
            detalleFactura.setTotal_art(item.getCantidad());
            detalleFactura.setDescuento(0.0);
            double total = (double) item.getCantidad() * item.getPrecio();
            detalleFactura.setReng_neto(total);
            detalleFactura.setAux1(item.getProducto().getTel());
            detalleFactura.setAux2(item.getProducto().getSerial());
            pedidos.add(detalleFactura);
        }
        return pedidos;
    }

    private double setTotal() {
        double total = 0D;
        for (Item producto : carrito) {
            total = total + (producto.getCantidad() * producto.getPrecio());
        }
        return total;
    }

    @Nullable
    private String setImage(byte[] image){
       if(image != null){
           return android.util.Base64.encodeToString(image, Base64.DEFAULT);
       }else{
           return null;
       }
    }
}
