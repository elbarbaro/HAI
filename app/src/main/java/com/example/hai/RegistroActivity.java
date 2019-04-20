package com.example.hai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hai.sesion.GCEASesion;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        String registro = getIntent().getStringExtra("dato_registro");
        final EditText editNombre = findViewById(R.id.editNombre);
        final EditText editFechadeNacimiento = findViewById(R.id.editFechadeNacimiento);
        final EditText editCorreo = findViewById(R.id.editCorreoElectronico);
        final EditText editContrasena = findViewById(R.id.editContrasena);
        Button btnSiguiente = findViewById(R.id.btnSiguienteRegistro);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editNombre.getText().toString();
                String fechaNacimiento = editFechadeNacimiento.getText().toString();
                String correo = editCorreo.getText().toString();
                String contrasena = editContrasena.getText().toString();
                if (!nombre.isEmpty() && !fechaNacimiento.isEmpty() && !correo.isEmpty() && !contrasena.isEmpty()){
                    SharedPreferences preferences = getSharedPreferences(LoginActivity.FILE_NAME, 0);
                    GCEASesion.guardarString(preferences, "nombre", nombre );
                    GCEASesion.guardarString(preferences, "fechaNacimiento", fechaNacimiento);
                    GCEASesion.guardarString(preferences, "correo", correo);

                    GCEASesion.guardarBoolean(preferences,"estaRegistrado", true);
                    // Todo implementar almacenamiento de datos
                    cerrarRegistro();

                } else {
                    Toast.makeText(getApplicationContext(),"Por favor llena los datos en blanco", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cerrarRegistro(){
        Intent pantallaPrincipal = new Intent (this, PrincipalActivity.class);
        startActivity(pantallaPrincipal);
    }
}
