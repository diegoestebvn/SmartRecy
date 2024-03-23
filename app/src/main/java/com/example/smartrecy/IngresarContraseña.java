package com.example.smartrecy;
import com.example.smartrecy.databinding.ActivityIngresarContrasenaBinding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class IngresarContraseña extends AppCompatActivity {
    private ActivityIngresarContrasenaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIngresarContrasenaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText editTextContraseña = findViewById(R.id.editTextContra);
        Button confirmarContraseñaButton = findViewById(R.id.confirmarContra);

        confirmarContraseñaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contraseñaIngresada = editTextContraseña.getText().toString();

                Log.d("IngresarContraseña", "Contraseña ingresada: " + contraseñaIngresada);

                if (contraseñaIngresada.equals("1812d")) {
                    Log.d("IngresarContraseña", "Contraseña correcta. Intentando iniciar RegistroUsuarios...");
                    Intent intent = new Intent(IngresarContraseña.this, RegistroUsuarios.class);
                    startActivity(intent);
                } else {
                    Log.d("IngresarContraseña", "Contraseña incorrecta");
                    Toast.makeText(IngresarContraseña.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}