package com.example.hai;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hai.sesion.GCEASesion;

public class LoginActivity extends AppCompatActivity {
    public static final String FILE_NAME = "haidata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // String inicio = getIntent().getStringExtra("dato_inicio");
        final EditText editCorreo = findViewById(R.id.editCorreo);
        final EditText editContrasena = findViewById(R.id.editContraseña);
        Button btnIniciar = findViewById(R.id.btnInicio);
        Button btnRegistro = findViewById(R.id.btnRegistrase);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pantallaRegistro();
            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = editCorreo.getText().toString();
                String contrasena = editContrasena.getText().toString();
                if (!correo.isEmpty()&& !contrasena.isEmpty()){
                    pantallaPrincipal();
                }else {
                    Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        boolean estaRegistrado = GCEASesion.leerBoolean(getSharedPreferences(FILE_NAME, 0), "estaRegistrado");
        if (estaRegistrado)
            pantallaPrincipal();
    }
    public void pantallaPrincipal(){
        Intent pantallaPrincipal = new Intent(this, PrincipalActivity.class);
        startActivity(pantallaPrincipal);
    }
   public void pantallaRegistro (){
        Intent pantallaRegistro = new Intent(this, RegistroActivity.class);
        startActivity(pantallaRegistro);
   }
}
