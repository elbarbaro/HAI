package com.example.hai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DiagnosticoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico);
        Button btnOmitir = findViewById(R.id.btnOmitir);
        Button btnContinuar = findViewById(R.id.btnContinuar);

        btnOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallaPrincipal = new Intent(DiagnosticoActivity.this, PrincipalActivity.class);
                startActivity(pantallaPrincipal);
            }
        });
    }

    public void abrirCategoria(View view){
        int idButton = view.getId();
        String nameCategoria = "";
        if (idButton == R.id.btnComida){
            nameCategoria = "Comida";
        } else if (idButton == R.id.btnEducacion){
            nameCategoria = "Educacion";
        } else if (idButton == R.id.btnEntretenimiento){
            nameCategoria = "Entretenimiento";
        } else if (idButton == R.id.btnJubilacion){
            nameCategoria = "Jubilación";
        } else if (idButton == R.id.btnTransporte){
            nameCategoria = "Trasporte";
        }
        Intent pantallaCategoria = new Intent(this, EntretenimientoActivity.class);
        pantallaCategoria.putExtra("nombre_categoria", nameCategoria);
        startActivity (pantallaCategoria);

    }


}
