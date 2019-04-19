package com.example.hai;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PieChart pieChart = findViewById(R.id.graficaCategorias);
        String[] categories = getResources().getStringArray(R.array.array_categorias);

        int colorJubilacion = Color.rgb(255, 145, 63);
        int colorComida = Color.rgb(255,145,63);
        int colorEntretenimiento = Color.rgb(255,209,67);
        int colorEducacion = Color.rgb(0,187,212);
        int colorTrasporte = Color.rgb(190,149,224);

        int[] colors = {
               colorJubilacion, Color.rgb(255, 145, 63),
                colorComida,Color.rgb(255,145,63),
                colorEducacion, Color.rgb(0,187,212),
                colorEntretenimiento, Color.rgb(255,209,67),
                colorTrasporte, Color.rgb(190,149,224),};

        List<PieEntry> list = new ArrayList<>();

        for(String categoria: categories){
            list.add(new PieEntry(25.0f, categoria));
        }

        PieDataSet pieDataSet =  new PieDataSet(list, "Categorias");
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Registro) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            navegarPantallaMeta();
            // Esto llama el metodo que esta escrito abajo que me permite cambiar de pantalla
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void navegarPantallaMeta(){
        // Con estas intrucciones doy a una pantalla nueva
        Intent pantallaMeta = new Intent(this, MetaActivity.class);
        startActivity(pantallaMeta);
    }

    public void cerrasSesion(){
        Intent pantallaInicio = new Intent(this, LoginActivity.class);
        startActivity(pantallaInicio);
    }

    public void navegarPantallaDiagnostico(){
        Intent pantallaDiagnostico = new Intent(this, DiagnosticoActivity.class);
        startActivity(pantallaDiagnostico);
    }
}

