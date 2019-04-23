package com.example.hai;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hai.models.Categoria;
import com.example.hai.sesion.GCEASesion;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mindorks.paracamera.Camera;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;



public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int CODIGO_CAMARA = 5002;
    public static final int DURACION_CONSEJO = 8000;
    private Camera camera;
    private ImageView imgPerfil;


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
        imgPerfil = headerView.findViewById(R.id.imgPerfil);
        String rutaImagen = GCEASesion.leerString(getSharedPreferences(LoginActivity.FILE_NAME, 0), "rutaImagen");
        if (!rutaImagen.isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen, options);
            imgPerfil.setImageBitmap(bitmap);
        }
        SharedPreferences preferences = getSharedPreferences(LoginActivity.FILE_NAME,0);

        String nombreUsuario = GCEASesion.leerString(preferences, "nombre");
        txtNombre.setText(nombreUsuario);
        String correo = GCEASesion.leerString(preferences, "correo");
        txtCorreo.setText(correo);
        float ingresos = GCEASesion.leerFloat(preferences, "ingresos");

        PieChart pieChart = findViewById(R.id.graficaCategorias);
        pieChart.setNoDataText("");

        int colorJubilacion = Color.rgb(255, 159, 191);
        int colorComida = Color.rgb(255,145,63);
        int colorEntretenimiento = Color.rgb(255,209,67);
        int colorEducacion = Color.rgb(0,187,212);
        int colorTrasporte = Color.rgb(190,149,224);
        int colorAhorro = Color.rgb(182, 255, 69);

        int[] colors = {
               colorJubilacion,
                colorComida,
                colorEducacion,
                colorEntretenimiento,
                colorTrasporte,
                colorAhorro
        };

        Set<String> categorias = GCEASesion.leerLista(getSharedPreferences(LoginActivity.FILE_NAME,0), "categorias");


        List<PieEntry> list = new ArrayList<>();

        if (categorias != null) {
            txtMensajeGrafica.setText("");
            float sumaCantidad = 0;
            for(String categoria: categorias){

                try {
                    JSONObject jsonObject = new JSONObject(categoria);
                    String nombre = jsonObject.getString("nombre");
                    float cantidad = (float) jsonObject.getDouble("cantidad");
                    // Aqui se realiza la suma de las cantidades de manera ciclica.
                    sumaCantidad = sumaCantidad + cantidad;
                    float cantidadDeseada = (float) jsonObject.getDouble("cantidadDeseada");

                    Categoria datosCategoria = new Categoria(nombre, cantidad, cantidadDeseada);
                    // Aqui se estan guardando los datos de categoria como entradas de datos para la grafica de pie.
                    list.add(new PieEntry(datosCategoria.getCantidad(), datosCategoria.getNombre()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(getApplicationContext(),categoria, Toast.LENGTH_SHORT).show();
            }

            if (ingresos > 0){

                //Aqui se realiza la operacion para calcular el ahorro
                float resultado = ingresos - sumaCantidad;
                // Cree una categoria que guardara la informacion del ahorro
                Categoria ahorro = new Categoria(getResources().getString(R.string.categoria_ahorro), resultado,0 );
                //Aqui le mande los datos del ahorro a la grafica
                list.add(new PieEntry(ahorro.getCantidad(), ahorro.getNombre()));

            }
        }


        PieDataSet pieDataSet =  new PieDataSet(list, "Categorias");
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        mostrarConsejo();
        iniciarCamara();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bitmap = BitmapFactory.decodeFile(camera.resizeAndGetCameraBitmapPath(80), options);
            if (bitmap != null){
                imgPerfil.setImageBitmap(bitmap);
                String rutaImagen = camera.resizeAndGetCameraBitmapPath(80);
                GCEASesion.guardarString(getSharedPreferences(LoginActivity.FILE_NAME, 0), "rutaImagen", rutaImagen);
            } else {
              Toast.makeText(getApplicationContext(), (R.string.toast_error_foto), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODIGO_CAMARA){
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_DENIED
            && grantResults[1] != PackageManager.PERMISSION_DENIED){
                try {
                    camera.takePicture();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), (R.string.toast_permisos), Toast.LENGTH_LONG).show();
            }
        }
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
        } /*else if (id == R.id.historial) {

        }*/ else if (id == R.id.registro) {
            navegarPantallaDiagnostico();

        } else if (id == R.id.Cupones) {
            abrirPantallaCupon();

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

    public void abrirPantallaCupon(){
        Intent pantallaCupon = new Intent(this, CuponActivity.class);
        startActivity(pantallaCupon);
    }

    public void iniciarCamara(){
        camera = new Camera.Builder()
                .resetToCorrectOrientation(true)
                .setDirectory("Pictures Hai")
                .setTakePhotoRequestCode(5001)
                .setName("hai_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000) //Manejar 480
                .build(this);

    }

    public void elegirImagenPerfil(View view){
        try {
            int permision = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (permision != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, CODIGO_CAMARA);
            } else {
                camera.takePicture();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.toast_error_camara, Toast.LENGTH_SHORT).show();
        }

    }
}

