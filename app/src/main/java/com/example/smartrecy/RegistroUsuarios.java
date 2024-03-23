package com.example.smartrecy;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.smartrecy.Inicio_Smartrecy;
import com.example.smartrecy.databinding.ActivityRegistroUsuariosBinding;
import java.util.ArrayList;

public class RegistroUsuarios extends AppCompatActivity {
    private ActivityRegistroUsuariosBinding binding;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroUsuariosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        ArrayList<String> userList = databaseHelper.obtenerTodosLosUsuarios();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        binding.listView.setAdapter(adapter);



        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroUsuarios.this, Inicio_Smartrecy.class);
                startActivity(intent);
            }
        });
    }
}

