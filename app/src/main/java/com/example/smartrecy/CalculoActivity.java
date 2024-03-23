package com.example.smartrecy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CalculoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo);

        final EditText editTextKg = findViewById(R.id.editTextKg);
        final EditText editTextPrecio = findViewById(R.id.editTextPrecio);
        final Button botonCalcular = findViewById(R.id.botonCalcular);
        final TextView textViewResultado = findViewById(R.id.textViewResultado);

        botonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kgStr = editTextKg.getText().toString();
                String precioStr = editTextPrecio.getText().toString();


                if (!kgStr.isEmpty() && !precioStr.isEmpty()) {

                    double kg = Double.parseDouble(kgStr);
                    double precio = Double.parseDouble(precioStr);

                    double resultado = kg * precio;

                    textViewResultado.setText("Resultado: " + resultado);
                } else {
                    textViewResultado.setText("Ingrese la cantidad en kg y el precio por kg.");
                }
            }
        });
    }
}
