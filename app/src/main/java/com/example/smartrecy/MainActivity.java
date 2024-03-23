package com.example.smartrecy;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import com.example.smartrecy.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DatabaseHelper databaseHelper;
    private EditText productoNombreEditText;
    private EditText productoPrecioEditText;
    private EditText productoPesoEditText;

    private ListView productoListView;
    private ArrayAdapter<String> productosAdapter;
    private ArrayList<String> productosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        productoNombreEditText = findViewById(R.id.productoNombre);
        productoPrecioEditText = findViewById(R.id.productoPrecio);
        productoPesoEditText = findViewById(R.id.productoPeso);
        productoListView = findViewById(R.id.productoListView);
        Button registrarProductoButton = findViewById(R.id.registrarProductoButton);
        Button eliminarProductoButton = findViewById(R.id.eliminarProductoButton);
        Button actualizarListaButton = findViewById(R.id.actualizarListaButton);
        Button actualizarPrecioButton = findViewById(R.id.actualizarPrecioButton);
        Button botonCalculo = findViewById(R.id.botonCalculo);

        productosList = new ArrayList<>();
        productosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosList);
        productoListView.setAdapter(productosAdapter);

        registrarProductoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarProducto();
            }
        });

        eliminarProductoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarProducto();
            }
        });

        actualizarListaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarListaProductos();
            }
        });

        actualizarPrecioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarPrecioProducto();
            }
        });

        botonCalculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCalculoActivity();
            }
        });
    }

    private void registrarProducto() {
        String nombre = productoNombreEditText.getText().toString();
        String precioStr = productoPrecioEditText.getText().toString();
        String pesoStr = productoPesoEditText.getText().toString();

        if (nombre.isEmpty() || precioStr.isEmpty() || pesoStr.isEmpty()) {
            showToast("Todos los campos son obligatorios");
        } else {
            double precio = Double.parseDouble(precioStr);
            double peso = Double.parseDouble(pesoStr);

            boolean registroExitoso = databaseHelper.insertarProducto(nombre, precio, peso);

            if (registroExitoso) {
                showToast("Producto registrado con éxito");
                actualizarListaProductos();
            } else {
                showToast("Error al registrar el producto");
            }
        }
    }

    private void eliminarProducto() {
        String nombre = productoNombreEditText.getText().toString();

        if (nombre.isEmpty()) {
            showToast("Nombre del producto es obligatorio");
        } else {
            boolean eliminacionExitosa = databaseHelper.eliminarProducto(nombre);

            if (eliminacionExitosa) {
                showToast("Producto eliminado con éxito");
                actualizarListaProductos();
            } else {
                showToast("Error al eliminar el producto");
            }
        }
    }

    private void actualizarPrecioProducto() {
        String nombre = productoNombreEditText.getText().toString();
        String nuevoPrecioStr = productoPrecioEditText.getText().toString();
        String nuevoPesoStr = productoPesoEditText.getText().toString();

        if (nombre.isEmpty() || nuevoPrecioStr.isEmpty() || nuevoPesoStr.isEmpty()) {
            showToast("Nombre, nuevo precio y nuevo peso son obligatorios");
        } else {
            double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
            double nuevoPeso = Double.parseDouble(nuevoPesoStr);

            boolean actualizacionExitosa = databaseHelper.actualizarPrecioProducto(nombre, nuevoPrecio, nuevoPeso);

            if (actualizacionExitosa) {
                showToast("Precio y peso actualizados con éxito");
                actualizarListaProductos();
            } else {
                showToast("Error al actualizar el precio y peso");
            }
        }
    }

    private void actualizarListaProductos() {
        Cursor cursor = databaseHelper.obtenerTodosLosProductos();
        productosList.clear();

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                double precio = cursor.getDouble(cursor.getColumnIndex("precio"));
                double peso = cursor.getDouble(cursor.getColumnIndex("peso"));
                productosList.add(nombre + " - Precio: $" + precio + " - Peso: " + peso + " Kg");
            } while (cursor.moveToNext());
        }

        productosAdapter.notifyDataSetChanged();
    }

    private void abrirCalculoActivity() {
        Intent intent = new Intent(MainActivity.this, CalculoActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}


