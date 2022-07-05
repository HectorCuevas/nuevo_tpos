package com.tpos_prosisco.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.tpos_prosisco.ApplicationTpos;
import com.tpos_prosisco.R;
import com.tpos_prosisco.adapters.ProductosAdapter;
import com.tpos_prosisco.adapters.PromocionAdapter;
import com.tpos_prosisco.beans.Item;
import com.tpos_prosisco.beans.Producto;
import com.tpos_prosisco.beans.Promocion;
import com.tpos_prosisco.data.ProductoViewModel;
import com.tpos_prosisco.data.PromocionViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.tpos_prosisco.ApplicationTpos.carrito;
import static com.tpos_prosisco.ApplicationTpos.logueoInfo;
import static com.tpos_prosisco.ApplicationTpos.promocion;

public class CarritoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private List<Producto> items = new ArrayList<Producto>();
    private List<Producto> allItems = new ArrayList<Producto>();
    private ProductosAdapter carritoAdapter;
    private ListView lst;
    private Button btnCheckout;
    private SearchView searchView;
    private ProductoViewModel productoViewModel;
    private TextView lblITems;
    private TextView lblTotal;
    private BottomSheetDialog mBottomSheetDialog;
    private View modal;
    private PromocionViewModel promocionViewModel;
    private List<Promocion> promociones = new ArrayList<Promocion>();
    ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // carrito.clear();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        getSupportActionBar().hide();

        lst = findViewById(R.id.lstCarrito);
        btnCheckout = findViewById(R.id.btnECheckout);
        lblITems = findViewById(R.id.lblCarritoItems);
        lblTotal = findViewById(R.id.lblCarritoTotal);
        searchView = findViewById(R.id.searchviewProducts);


        /*** conexion con retrofit ***/
        productoViewModel = ViewModelProviders.of(this)
                .get(ProductoViewModel.class);
        promocionViewModel = ViewModelProviders.of(this)
                .get(PromocionViewModel.class);

        getProductos();
        getPromociones();



        /*** interfaces ***/
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Producto producto = (Producto) carritoAdapter.getItem(position);
                //verificamos que el producto no este en el carrito
             //   producto.setSerial("123");
                if(carrito.size() > 0 ){//primer item
                    if(!producto.getSerial().equals("")){//tiene serial
                        if(findUsingEnhancedForLoop(producto, carrito) != null){//si lo encuentra
                            Toast.makeText(getApplicationContext(), "El producto ya fue agregado", Toast.LENGTH_LONG).show();
                        }else{//si no lo encuentra
                            setTipoVenta(producto);
                            items.remove(position);
                            carritoAdapter.notifyDataSetChanged();
                        }
                    }else{
                        setTipoVenta(producto);
                       // items.remove(position);
                        carritoAdapter.notifyDataSetChanged();
                    }
                 }else{
                    setTipoVenta(producto);
                   // items.remove(position);
                    carritoAdapter.notifyDataSetChanged();
                }
            }
        });
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carrito.size() > 0) {
                    Intent i = new Intent(CarritoActivity.this, PromocionesActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Debe agregar articulos", Toast.LENGTH_LONG).show();
                }
            }
        });
        searchView.setOnQueryTextListener(this);
        searchView.clearFocus();
    }
    public Item findUsingEnhancedForLoop(Producto name, List<Item> customers) {
        for (Item customer : customers) {
            if (customer.getProducto().getSerial().equals(name.getSerial())) {
                return customer;
            }
        }
        return null;
    }
    private void getProductos() {
        productoViewModel.getDBProductos().observe(CarritoActivity.this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                for (Producto item : productos) {
                    allItems = productos;
                    if (item.getTipo()
                            .trim()
                            .equals(
                                    ApplicationTpos.promocion
                                            .getCategoriaFiltro()
                                            .trim()
                            )
                    ) {

                        items.add(item);
                    }
                }

                if (ApplicationTpos.promocion.getCategoriaFiltro().equals("")) {
                    items = productos;
                }
                carritoAdapter = new ProductosAdapter(getApplicationContext(), items);
                lst.setAdapter(carritoAdapter);
            }
        });
    }

    private void getPromociones() {
        promocionViewModel.getDBPromociones().observe(CarritoActivity.this, new Observer<List<Promocion>>() {
            @Override
            public void onChanged(List<Promocion> dbpromociones) {
                promociones = dbpromociones;
                //   Toast.makeText(getApplicationContext(), String.valueOf(promociones.size()), Toast.LENGTH_LONG).show();
                promocionViewModel.getDBPromociones().removeObserver(this);
            }

        });
    }

    //Para productos que piden numero de telefono
    private void setWindow(View modal, Producto producto) {
        mBottomSheetDialog = new BottomSheetDialog(CarritoActivity.this);
        mBottomSheetDialog.setContentView(modal);
        mBottomSheetDialog.show();
        modal.findViewById(R.id.button_closeDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        modal.findViewById(R.id.button_okDialog).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText txtNumero = modal.findViewById(R.id.txtCantidadDialog);
                int numero = Integer.parseInt(txtNumero.getText().toString());
                producto.setTel(numero);
                setPrecio(ApplicationTpos.promocion, producto);
                mBottomSheetDialog.dismiss();
            }
        });
    }



    private void setCategorias(Producto producto) {

        modal = getLayoutInflater().inflate(R.layout.bottom_sheet_tel, null);

        setWindow(modal, producto);
    }

    void setPrecio(Promocion promocion, Producto producto) {
        double precio = 0d;
        switch (promocion.getTipoPrecioItem()) {
            case 1:
                precio = producto.getPrecVta1();
                break;
            case 2:
                precio = producto.getPrecVta2();
                break;
            case 3:
                precio = producto.getPrecVta3();
                break;
            case 4:
                precio = producto.getPrecVta31();
                break;
            case 5:
                precio = producto.getPrecVta5();
                break;
        }
        Item item = new Item(producto, 1, setDescuento(precio, promocion.getPorDescuento()));
        carrito.add(item);
        // cosas por hacer: ver que pasa cuando se agrega un numero de telefono, tratar de amarrar la
        // recarga a su producto por medio del numero de telefono
        //revisar audios de hector
        setBonifcacion(1);
        if (ApplicationTpos.promocion.getCantidadTA() != 0) {
            addItemExtra(promocion.getCantidadTA(), producto.getTel(), promocion.getPorDescuento(), logueoInfo.getCodigoTA());
        }

        item.getProducto().setCantidadAsociada(promocion.getCantidadTA());

        setTotal();


        // mBottomSheetDialog.dismiss();
    }

    private void addItemExtra(int cantidad, int tel, double descuento, String codigoProducto) {
        double precio = 0d;
        for (Producto producto : allItems) {
            if (producto.getCoArt().contains(codigoProducto)) {
                switch (promocion.getTipoPrecioTA()) {
                    case 1:
                        precio = producto.getPrecVta1();
                        break;
                    case 2:
                        precio = producto.getPrecVta2();
                        break;
                    case 3:
                        precio = producto.getPrecVta3();
                        break;
                    case 4:
                        precio = producto.getPrecVta31();
                        break;
                    case 5:
                        precio = producto.getPrecVta5();
                        break;
                }
                producto.setTel(tel);
                carrito.add(new Item(producto, cantidad, setDescuento(precio, descuento)));
                break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        carrito.clear();
        Intent changePrefs = new Intent(getApplicationContext(), PromocionesActivity.class);
        finish();
        startActivity(changePrefs);
        if (changePrefs.resolveActivity(getPackageManager()) != null) {
            startActivity(changePrefs);
        }
        return super.onKeyDown(keyCode, event);
    }

    private double setTotal() {
        double total = 0D;
        for (Item producto : carrito) {
            total = total + (producto.getCantidad() * producto.getPrecio());
        }
        carritoAdapter.notifyDataSetChanged();
        lblTotal.setText(ApplicationTpos.CARACTER_MONEDA + String.valueOf(total));
        lblITems.setText(String.valueOf(carrito.size()));
        return total;
    }

    /*** Interfaz para Searchview ***/
    @Override
    public boolean onQueryTextSubmit(String s) {
        //searchView.clearFocus();
        carritoAdapter.getFilter().filter(s);
        lst.setAdapter(carritoAdapter);
        carritoAdapter.notifyDataSetChanged();


        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //searchView.clearFocus();
        carritoAdapter.getFilter().filter(s);
        lst.setAdapter(carritoAdapter);
        carritoAdapter.notifyDataSetChanged();

        return true;
    }

    private void setTipoVenta(Producto producto) {

        //Variable para controlar el tipo de venta si es producto o orga
        boolean esRecarga = promocion.getCantidadTA() == 999;


        switch (producto.getTipo().trim()) {
            case "O1":
            case "T1":
            case "A2":
                getCantidad(producto);
                break;
            default:
                setCategorias(producto);
        }


        setTotal();
        //SCOM
    }

    @NonNull
    private void getCantidad(Producto producto) {
        View modal_cantidad = getLayoutInflater().inflate(R.layout.bottom_sheet_cantidad, null);
        mBottomSheetDialog = new BottomSheetDialog(CarritoActivity.this);
        mBottomSheetDialog.setContentView(modal_cantidad);
        mBottomSheetDialog.show();
        modal_cantidad.findViewById(R.id.button_closeDialog1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        modal_cantidad.findViewById(R.id.button_okDialog1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               try{
                   EditText txtcantidad = modal_cantidad.findViewById(R.id.txtCantidadDialog);
                   int cantidad = Integer.parseInt(txtcantidad.getText().toString());
                   producto.setTel(0);
                   double precio = setPrecioPromocion(ApplicationTpos.promocion, producto);
                   carrito.add(new Item(producto, cantidad, setDescuento(precio, promocion.getPorDescuento())));
                   mBottomSheetDialog.dismiss();
                   setBonifcacion(cantidad);
                   setTotal();
               }catch(Exception e){
                   Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private void setBonifcacion(int cantidad) {
        //si es cero no lleva bonificacion
        if (promocion.getPorBonificacion() != 0) {
            addItemExtra(setCantidadDescuento(cantidad, promocion.getPorBonificacion()), 0, promocion.getPorDescuento(), promocion.getItemBonifacacion());
            //addItemExtra(4, 0, promocion.getPorDescuento() , promocion.getItemBonifacacion());
        }
    }


    double setDescuento(double precioVenta, double porcentaje) {
        return precioVenta - ((precioVenta * porcentaje) / 100);
    }

    int setCantidadDescuento(int cantidad, int bonificacion) {
        return ((cantidad * bonificacion) / 100);
    }

    double setPrecioPromocion(Promocion promocion, Producto producto) {
        double precio = 0d;
        switch (promocion.getTipoPrecioItem()) {
            case 1:
                precio = producto.getPrecVta1();
                break;
            case 2:
                precio = producto.getPrecVta2();
                break;
            case 3:
                precio = producto.getPrecVta3();
                break;
            case 4:
                precio = producto.getPrecVta31();
                break;
            case 5:
                precio = producto.getPrecVta5();
                break;
        }

        return precio;
    }

}
