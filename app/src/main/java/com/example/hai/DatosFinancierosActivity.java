package com.example.hai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hai.sesion.GCEASesion;

public class DatosFinancierosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_financieros);
        final EditText editIngresos = findViewById(R.id.editDatosFinancieros);
        Button btnSiguiente = findViewById(R.id.btnSiguienteDF);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingresos = editIngresos.getText().toString();
                if (!ingresos.isEmpty()){
                    float fIngreso = Float.parseFloat(ingresos);
                    if (fIngreso > 0){
                        GCEASesion.guardarFloat(getSharedPreferences(LoginActivity.FILE_NAME, 0),"ingresos", fIngreso );
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.toast_cantidadCero, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), R.string.toast_datos, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
