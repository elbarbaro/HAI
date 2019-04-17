package com.example.hai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EntretenimientoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entretenimiento);

        String categoria = getIntent().getStringExtra("nombre_categoria");
        TextView txtCategoria = findViewById(R.id.txtEntretenimiento);
        txtCategoria.setText(categoria);
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

                    // todo falta guardar los valores en share preferences
                } else {
                    Toast.makeText(getApplicationContext(),"Compa por favor llena los datos en blanco", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
