package com.example.smartrecy;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartrecy.databinding.ActivityRegistroSmartRecyBinding;

public class RegistroSmartRecy extends AppCompatActivity {
    ActivityRegistroSmartRecyBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroSmartRecyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();

                if (email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(RegistroSmartRecy.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if (!checkUserEmail) {
                            Boolean insert = databaseHelper.insertData(email, password);

                            if (insert) {
                                Toast.makeText(RegistroSmartRecy.this, "Registro exitoso!", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(RegistroSmartRecy.this, "Registro fallido!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegistroSmartRecy.this, "¡El usuario ya existe! Por favor Iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegistroSmartRecy.this, "Contraseña invalida!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroSmartRecy.this, Inicio_Smartrecy.class);
                startActivity(intent);
            }
        });
    }
}

