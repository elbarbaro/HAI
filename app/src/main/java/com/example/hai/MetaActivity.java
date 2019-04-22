package com.example.hai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hai.models.Meta;
import com.example.hai.sesion.GCEASesion;

public class MetaActivity extends AppCompatActivity {

    EditText editRazon;
    EditText editCantidad;
    EditText editTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta);
        editRazon = findViewById(R.id.editMeta1);
        editCantidad = findViewById(R.id.editMeta2);
        editTiempo = findViewById(R.id.editMeta3);
        String razon = getIntent().getStringExtra("razon");
        float cantidad = getIntent().getFloatExtra("cantidad",0);
        int tiempo = getIntent().getIntExtra("tiempo", 0);
        if (razon != null && cantidad > 0 && tiempo > 0){
            editRazon.setText(razon);
            editCantidad.setText(String.valueOf(cantidad));
            editTiempo.setText(String.valueOf(tiempo));
        }

    }
    public void agregarMeta (View view){
        String razon = editRazon.getText().toString();
        String cantidad = editCantidad.getText().toString();
        String tiempo = editTiempo.getText().toString();
        if (!razon.isEmpty()&& !cantidad.isEmpty() && !tiempo.isEmpty()){
            float fcatidad = Float.parseFloat(cantidad);
            int itiempo = Integer.parseInt(tiempo);
            if ((fcatidad > 0 && itiempo > 0)) {

                Meta meta = new Meta(razon, fcatidad, itiempo);
                GCEASesion.guardarStringEnLista(getSharedPreferences(LoginActivity.FILE_NAME, 0), "ListaMetas", meta.toString());
                setResult(RESULT_OK);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "Ingresa una cantidad mayor a cero", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Por favor ingresa los datos", Toast.LENGTH_SHORT).show();
        }
        // Esto es para cerrar una pantalla
    }


}
