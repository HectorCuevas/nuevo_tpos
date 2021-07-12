package com.tpos_prosisco.api.Client;

import com.tpos_prosisco.activities.AnulacionActivity;
import com.tpos_prosisco.api.interfaces.AnulacionService;
import com.tpos_prosisco.api.interfaces.ClienteService;
import com.tpos_prosisco.api.interfaces.CobroService;
import com.tpos_prosisco.api.interfaces.CorrelativoService;
import com.tpos_prosisco.api.interfaces.FacturaService;
import com.tpos_prosisco.api.interfaces.LogueoService;
import com.tpos_prosisco.api.interfaces.MotivoService;
import com.tpos_prosisco.api.interfaces.ProductoService;
import com.tpos_prosisco.api.interfaces.PromocionService;
import com.tpos_prosisco.api.interfaces.ReporteService;
import com.tpos_prosisco.api.interfaces.VendedoreService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tpos_prosisco.ApplicationTpos.API_URL;

public class AppClient {
    private static AppClient instance = null;
    private ProductoService productoService;
    private Retrofit retrofit, retrofit2;
    private CorrelativoService correlativoService;
    private PromocionService promocionService;
    private FacturaService facturaService;
    private VendedoreService vendedoreService;
    private LogueoService logueoService;
    private ClienteService clienteService;
    private ReporteService reporteService;
    private AnulacionService anulacionService;
    private MotivoService motivoService;
    private CobroService cobroService;

    public AppClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productoService = retrofit.create(ProductoService.class);
        promocionService = retrofit.create(PromocionService.class);
        correlativoService = retrofit.create(CorrelativoService.class);
        facturaService = retrofit.create(FacturaService.class);
        vendedoreService = retrofit.create(VendedoreService.class);
        logueoService = retrofit.create(LogueoService.class);
        clienteService = retrofit.create(ClienteService.class);
        reporteService = retrofit.create(ReporteService.class);
        anulacionService = retrofit.create(AnulacionService.class);
        motivoService = retrofit.create(MotivoService.class);
        cobroService = retrofit.create(CobroService.class);
    }

    public static AppClient getInstance(){
        if(instance == null){
            instance = new AppClient();
        }
        return instance;
    }

    public ProductoService getProductoService(){
        return productoService;
    }

    public  CorrelativoService getCorrelativoService(){
        return  correlativoService;
    }

    public FacturaService getFacturaService(){
        return facturaService;
    }

    public PromocionService getPromocionService(){return  promocionService;}

    public VendedoreService getVendedoreService(){
        return vendedoreService;
    }

    public LogueoService getLogueoService(){
        return logueoService;
    }

    public ClienteService getClienteService(){
        return clienteService;
    }

    public ReporteService getReporteService(){return reporteService;}

    public AnulacionService getAnulacionService() {
            return anulacionService;
    }

    public MotivoService getMotivoService() {return motivoService;}

    public CobroService getcobroService() {return cobroService;}

}
