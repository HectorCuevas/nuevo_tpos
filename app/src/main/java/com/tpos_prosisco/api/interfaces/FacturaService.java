package com.tpos_prosisco.api.interfaces;

import com.tpos_prosisco.beans.Correlativo;
import com.tpos_prosisco.beans.DetalleFactura;
import com.tpos_prosisco.beans.Factura;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FacturaService {
    @Headers({
            "Content-type: application/json"
    })
    @POST("Correlativo")
    Call<Integer> getID(@Body Correlativo correlativo);
    @Headers({
            "Content-type: application/json"
    })
    @POST("Pedido")
    Call<Integer> sendPedidoProfit(@Body Factura factura);

    @Headers({
            "Content-type: application/json"
    })
    @POST("DetallePedido")
    Call<Integer> sendPedidoDetProfit(@Body DetalleFactura detalleFactura);
}
