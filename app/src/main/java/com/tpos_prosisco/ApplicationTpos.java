package com.tpos_prosisco;

import android.app.Application;

import com.tpos_prosisco.beans.Venta;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Logueo;
import com.tpos_prosisco.beans.Promocion;

import java.util.ArrayList;

public class ApplicationTpos extends Application {

      //public static String IMEI = "0d41593f5a46a6b9";
      public static String IMEI = "";
      public static String API_URL = "http://23.101.155.203/api_tpos_v2/api/";
      //public static String API_URL = "http://grupomenas.carrierhouse.us/api_tpos_v2/api/";
      //public static String API_URL = "http://grupomenas.carrierhouse.us/api_tpos/api/";
      private static ApplicationTpos instance;
      public static ArrayList<Item> carrito = new ArrayList<>();
      public static Venta nuevaVenta = new Venta();
      public static Promocion promocion = new Promocion();
      public static Logueo logueoInfo = new Logueo();
      public static double latitud = 0D;
      public static double longitud = 0D;
      public static String CARACTER_MONEDA = "";
      // public static String ruta =logueoInfo.getCoSucu().trim();

      public static ApplicationTpos getInstance(){
          return  instance;
      }

      @Override
      public void onCreate() {
          instance=this;
          super.onCreate();
      }

      //cantidad a enviar -o
      //verfifcar si se inserto -o
      //verficar stock del articulo
      //verifcar el serial para que se pueda volver a agregar al carrito -o


}
