package com.example.hai;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hai.models.Categoria;
import com.example.hai.sesion.GCEASesion;

public class EntretenimientoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entretenimiento);

        final String nombreCategoria = getIntent().getStringExtra("nombre_categoria");
        TextView txtCategoria = findViewById(R.id.txtEntretenimiento);
        txtCategoria.setText(nombreCategoria);
        final EditText editCantidad = findViewById(R.id.editCantidad);
        final EditText editCantidadDeseada = findViewById(R.id.editCantidadDeseada);
        Button btnTerminar = findViewById(R.id.btnTerminar);

        btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cantidad = editCantidad.getText().toString();
                String cantidadDeseada = editCantidadDeseada.getText().toString();
                if (!cantidad.isEmpty()&& !cantidadDeseada.isEmpty()){
                    float fCantidad = Float.parseFloat(cantidad);
                    float fCantidadDeseada = Float.parseFloat(cantidadDeseada);
                    if (fCantidad>0 && fCantidadDeseada>0){
                        Categoria categoria = new Categoria(nombreCategoria,fCantidad,fCantidadDeseada);

                        GCEASesion.guardarStringEnLista(getSharedPreferences(LoginActivity.FILE_NAME, 0), "categorias", categoria.toString());

                        // Eres temporal jejeje
                        Toast.makeText(getApplicationContext(), categoria.toString(), Toast.LENGTH_LONG).show();
                        // todo falta guardar los valores en share preferences
                    }else {
                        Toast.makeText(getApplicationContext(),"Ingresa una cantidad mayor a cero", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"Por favor completa los espacios en blanco", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
