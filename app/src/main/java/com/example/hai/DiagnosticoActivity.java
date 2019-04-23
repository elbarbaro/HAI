package com.example.hai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DiagnosticoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico);
        Button btnOmitir = findViewById(R.id.btnOmitir);
        Button btnContinuar = findViewById(R.id.btnContinuar);
        Button btnDatosDiagnostico = findViewById(R.id.btnDatosFinancieros);
        TextView txtTitulo = findViewById(R.id.txtTitulo);
        TextView txtMensaje = findViewById(R.id.txtMensaje);
        String titulo = getIntent().getStringExtra("titulo");
        String mensaje = getIntent().getStringExtra("mensaje");
        if (titulo != null && mensaje != null){
            txtTitulo.setText(titulo);
            txtMensaje.setText(mensaje);
        }


        btnDatosDiagnostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDatosFinancieros();
            }
        });

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
            nameCategoria = getResources().getString(R.string.categoria_comida);
        } else if (idButton == R.id.btnEducacion){
            nameCategoria = getResources().getString(R.string.categoria_educacion);
        } else if (idButton == R.id.btnEntretenimiento){
            nameCategoria = getResources().getString(R.string.categoria_entretenimiento);
        } else if (idButton == R.id.btnJubilacion){
            nameCategoria = getResources().getString(R.string.categoria_jubilacion);
        } else if (idButton == R.id.btnTransporte){
            nameCategoria = getResources().getString(R.string.categoria_transporte);
        }
        Intent pantallaCategoria = new Intent(this, EntretenimientoActivity.class);
        pantallaCategoria.putExtra("nombre_categoria", nameCategoria);
        startActivity (pantallaCategoria);

    }


    public void abrirDatosFinancieros(){
        Intent pantallaDatosFinancieros = new Intent(this, DatosFinancierosActivity.class);
        startActivity(pantallaDatosFinancieros);
    }

}
