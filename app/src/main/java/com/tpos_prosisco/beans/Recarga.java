package com.tpos_prosisco.beans;

import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import static com.tpos_prosisco.ApplicationTpos.logueoInfo;

public class Recarga {
    private int monto;

    public Recarga() {

    }


    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    private String cliente;
    public Recarga(String numero, int monto, String cliente) {
        this.numero = numero;
        this.monto = monto;
        this.cliente  =cliente;
    }

    public String numero;


    public ArrayList<String> setLinkedTels(String telefonos){

        String[] arrTels = telefonos.split("");
        ArrayList<String> listTels = new ArrayList<>();
        listTels.addAll(Arrays.asList(arrTels));
        return new ArrayList<String>(new LinkedHashSet<String>(listTels));

    }
    public String makeUssdCode(Recarga recarga) {
        String ussd = logueoInfo
                .getUssdRecarga()
                .replace("<pin>", logueoInfo.getPin().trim())
                .replace("<pos tienda>", String.valueOf(recarga.getNumero()))
                .replace("<cantidad>", String.valueOf(recarga.getMonto()))
                .replace("LLAMAR", "") + "#";
        return ussd;
    }

    public Uri ussdToCallableUri(String ussd) {

        String uriString = "";

        if (!ussd.startsWith("tel:" + ussd))
            uriString += "tel:";

        for (char c : ussd.toCharArray()) {

            if (c == '#')
                uriString += Uri.encode("#");
            else
                uriString += c;
        }


        return Uri.parse(uriString);
    }


    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }


}
