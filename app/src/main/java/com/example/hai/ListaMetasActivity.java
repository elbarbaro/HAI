package com.example.hai;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.hai.models.Meta;
import com.example.hai.sesion.GCEASesion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListaMetasActivity extends AppCompatActivity implements MetaAdapter.OnItemClickListener{

    public static final int CODIGO_META = 5000;

    private RecyclerView listaMetas;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_metas);
        Button btnAgregarMeta = findViewById(R.id.btnAgregarMeta);
        listaMetas = findViewById(R.id.listaMetas);
        layoutManager = new LinearLayoutManager(this);
        listaMetas.setLayoutManager(layoutManager);

        //Aqui estoy creando un Adapter con los datos de Meta
        listaMetas.setAdapter(llenarMetas());

        btnAgregarMeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navegarPantallaMeta();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODIGO_META){
            if (resultCode == RESULT_OK) {
                listaMetas.setAdapter(llenarMetas());
            }
        }
    }

    private RecyclerView.Adapter llenarMetas() {
        List<Meta> lista = new ArrayList<>();
        Set<String> listaMetas = GCEASesion.leerLista(getSharedPreferences(LoginActivity.FILE_NAME, 0), "ListaMetas");
        if (listaMetas != null){
            // For me permite recuperar Strings de mi listaMetas, en una nueva variable llamada meta de tipo String
            for (String meta: listaMetas){
                try {
                    JSONObject jsonObject = new JSONObject(meta);
                    String razon = jsonObject.getString("razon");
                    float cantidad = (float) jsonObject.getDouble("cantidad");
                    int tiempo = jsonObject.getInt("tiempo");

                    Meta datosMeta = new Meta(razon, cantidad, tiempo);
                    lista.add(datosMeta);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        // Aqui se esta creando un nuevo Adapter con la informacion de listaMetas
        return new MetaAdapter(lista, this);

    }

    public void navegarPantallaMeta(){
        Intent pantallaMeta = new Intent(this, MetaActivity.class);
        startActivity(pantallaMeta);
    }

    @Override
    public void onItemClick(Meta meta) {
        Intent pantallaMeta = new Intent(this, MetaActivity.class);
        //Esto es para mandar datos en un Intent para una nueva pantalla
        pantallaMeta.putExtra("razon", meta.getRazon());
        pantallaMeta.putExtra("cantidad", meta.getCantidad());
        pantallaMeta.putExtra("tiempo", meta.getTiempo());
        startActivityForResult(pantallaMeta, CODIGO_META);
    }
}
