package com.tpos_prosisco.api.interfaces;

import com.tpos_prosisco.beans.Responses.InventarioResponse;
import com.tpos_prosisco.beans.Responses.ReporteCategoriaResponse;
import com.tpos_prosisco.beans.Responses.ReporteFacturaResponse;
import com.tpos_prosisco.beans.Responses.ReporteImeiResponse;
import com.tpos_prosisco.beans.Responses.ReporteImeiSerialResponse;
import com.tpos_prosisco.beans.Responses.ReporteVendedorResponse;
import com.tpos_prosisco.beans.Responses.ReporteVentaDiariaResponse;
import com.tpos_prosisco.beans.Responses.ReporteVentaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReporteService {

    String API_ROUTE = "Reportes/Ventas/{imei}/{ruta}/{mes}";
    @GET(API_ROUTE)
    Call<ReporteVentaResponse> getReporteVenta(
            @Path("imei") String imei,
            @Path("ruta") String ruta,
            @Path("mes") String mes
    );

    String API_ROUTE_VENTAS = "Reportes/Categorias/{imei}/{ruta}/{mes}";
    @GET(API_ROUTE_VENTAS)
    Call<ReporteCategoriaResponse> getReporteCategoria(
            @Path("imei") String imei,
            @Path("ruta") String ruta,
            @Path("mes") String mes
    );

    String API_ROUTE_FACTURAS = "Reportes/Facturas/{imei}/{ruta}/{dia}";
    @GET(API_ROUTE_FACTURAS)
    Call<ReporteFacturaResponse> getReporteFactura(
            @Path("imei") String imei,
            @Path("ruta") String ruta,
            @Path("dia") String dia
    );

    String API_ROUTE_VENDEDORES = "Reportes/Vendedor/{imei}/{ruta}/{dia}";
    @GET(API_ROUTE_VENDEDORES)
    Call<ReporteVendedorResponse> getReporteVendedor(
            @Path("imei") String imei,
            @Path("ruta") String ruta,
            @Path("dia") String dia
    );

    String API_ROUTE_INVENTARIO = "Reportes/Inventario/{imei}/{ruta}";
    @GET(API_ROUTE_INVENTARIO)
    Call<InventarioResponse> getReporteInventario(
            @Path("imei") String imei,
            @Path("ruta") String ruta
    );

    String API_ROUTE_IMEI = "Reportes/Imei/{imei}/{ruta}/{fecha}";
    @GET(API_ROUTE_IMEI)
    Call<ReporteImeiResponse> getReporteIMEI(
            @Path("imei") String imei,
            @Path("ruta") String ruta,
            @Path("fecha") String fecha
    );

    String API_ROUTE_VENTADIARIA = "Reportes/VentaDiaria/{imei}/{ruta}/{fecha}";
    @GET(API_ROUTE_VENTADIARIA)
    Call<ReporteVentaDiariaResponse> getReporteVentaDiaria(
            @Path("imei") String imei,
            @Path("ruta") String ruta,
            @Path("fecha") String fecha
    );

    String API_ROUTE_TRASLADO = "Reportes/Traslado/{imei}/{vendedor}/{ruta}";
    @GET(API_ROUTE_TRASLADO)
    Call<String> getReporteTraslado(
            @Path("imei") String imei,
            @Path("vendedor") String vendedor,
            @Path("ruta") String num_fact
    );

    String API_ROUTE_IMEISERIAL = "Reportes/ImeiSerial/{imei}/{serial}";
    @GET(API_ROUTE_IMEISERIAL)
    Call<ReporteImeiSerialResponse> getReporteImeiSerial(
            @Path("imei") String imei,
            @Path("serial") String serial
    );





}
