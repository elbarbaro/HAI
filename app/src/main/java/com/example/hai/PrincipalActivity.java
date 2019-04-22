package com.example.hai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hai.models.Categoria;
import com.example.hai.sesion.GCEASesion;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        TextView txtMensajeGrafica = findViewById(R.id.txt_mensajeGrafica);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView txtNombre = headerView.findViewById(R.id.txtNombre);
        TextView txtCorreo = headerView.findViewById(R.id.txtCorreo);
        SharedPreferences preferences = getSharedPreferences(LoginActivity.FILE_NAME,0);

        String nombreUsuario = GCEASesion.leerString(preferences, "nombre");
        txtNombre.setText(nombreUsuario);
        String correo = GCEASesion.leerString(preferences, "correo");
        txtCorreo.setText(correo);

        PieChart pieChart = findViewById(R.id.graficaCategorias);
        pieChart.setNoDataText("");

        int colorJubilacion = Color.rgb(255, 159, 191);
        int colorComida = Color.rgb(255,145,63);
        int colorEntretenimiento = Color.rgb(255,209,67);
        int colorEducacion = Color.rgb(0,187,212);
        int colorTrasporte = Color.rgb(190,149,224);

        int[] colors = {
               colorJubilacion,
                colorComida,
                colorEducacion,
                colorEntretenimiento,
                colorTrasporte
        };

        Set<String> categorias = GCEASesion.leerLista(getSharedPreferences(LoginActivity.FILE_NAME,0), "categorias");


        List<PieEntry> list = new ArrayList<>();

        if (categorias != null) {
            txtMensajeGrafica.setText("");
            for(String categoria: categorias){

                try {
                    JSONObject jsonObject = new JSONObject(categoria);
                    String nombre = jsonObject.getString("nombre");
                    float cantidad = (float) jsonObject.getDouble("cantidad");
                    float cantidadDeseada = (float) jsonObject.getDouble("cantidadDeseada");

                    Categoria datosCategoria = new Categoria(nombre, cantidad, cantidadDeseada);
                    list.add(new PieEntry(datosCategoria.getCantidad(), datosCategoria.getNombre()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(getApplicationContext(),categoria, Toast.LENGTH_SHORT).show();
            }
        }


        PieDataSet pieDataSet =  new PieDataSet(list, "Categorias");
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        mostrarConsejo();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Cerrar_sesion) {
            cerrasSesion();
            // Handle the camera action
        } else if (id == R.id.historial) {

        } else if (id == R.id.registro) {
            navegarPantallaDiagnostico();

        } else if (id == R.id.Cupones) {

        } else if (id == R.id.Configuraciones) {

        } else if (id == R.id.Meta){
            navegarPantallaMetas();
            // Esto llama el metodo que esta escrito abajo que me permite cambiar de pantalla
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void cerrasSesion(){
        GCEASesion.limpiarDatos(getSharedPreferences(LoginActivity.FILE_NAME,0));
        Intent pantallaInicio = new Intent(this, LoginActivity.class);
        startActivity(pantallaInicio);
    }

    public void navegarPantallaDiagnostico(){
        Intent pantallaDiagnostico = new Intent(this, DiagnosticoActivity.class);
        // Aqui se esta guardando dos valores String (titulo, mensaje) con valores String
        pantallaDiagnostico.putExtra("titulo", getResources().getString(R.string.titulo_mensaje_registro));
        pantallaDiagnostico.putExtra("mensaje", getResources().getString(R.string.mensaje_registro));
        startActivity(pantallaDiagnostico);
    }
    public void navegarPantallaMetas(){
        // Con estas intrucciones doy a una pantalla nueva
        Intent pantallaMetas = new Intent(this, ListaMetasActivity.class);
        startActivity(pantallaMetas);
    }

    public void mostrarConsejo(){
        String[] listaConsejos = getResources().getStringArray(R.array.lista_consejos);
        int numeroAliatorio = new Random().nextInt(listaConsejos.length);
        String consejo = listaConsejos[numeroAliatorio];
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogoConsejo = layoutInflater.inflate(R.layout.dialog_consejo,(ViewGroup) findViewById(R.id.container));
        TextView txtConsejo = dialogoConsejo.findViewById(R.id.txtConsejos);
        // Aqui pongo un texto en un TextView
        txtConsejo.setText(consejo);
        // Esto es un Toast personalizado. Se le pone una vista, una duracion y que sea visible
        Toast toast = new Toast(getApplicationContext());
        /*ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogoConsejo.setLayoutParams(params);*/
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(dialogoConsejo);
        toast.show();
    }
}

