package com.example.hai.sesion;

import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class GCEASesion {

    public static void guardarString(SharedPreferences preferences, String nombreValor, String valor){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(nombreValor, valor);
        editor.apply();
    }

    public static void guardarFloat(SharedPreferences preferences, String nombreValor, float valor){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(nombreValor, valor);
        editor.apply();
    }

    public static void guardarBoolean(SharedPreferences preferences, String nombreValor, boolean valor){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(nombreValor, valor);
        editor.apply();
    }

    public static String leerString(SharedPreferences preferences, String nombreValor){
        return preferences.getString(nombreValor,"");
    }

    public static float leerFloat (SharedPreferences preferences, String nombreValor){
        return preferences.getFloat(nombreValor, 0);
    }

    public static boolean leerBoolean (SharedPreferences preferences, String nombreValor){
        return preferences.getBoolean(nombreValor, false);
    }

    public static void guardarStringEnLista(SharedPreferences preferences, String nombreValor, String valor){
        Set<String> categorias = preferences.getStringSet(nombreValor, null);
        if (categorias == null) {
            categorias = new HashSet<>();
        }

        categorias.add(valor);
        preferences.edit().remove(nombreValor).apply();
        preferences.edit().putStringSet(nombreValor, categorias).apply();
    }

    public static void guardarStringEnLista(SharedPreferences preferences, String nombreValor, String valor, String nombreCategoria){
        Set<String> categorias = preferences.getStringSet(nombreValor, null);
        if (categorias == null) {
            categorias = new HashSet<>();
        }

        boolean repetido = false;
        String valorRepetido = "";
        for (String categoria: categorias){
            // Comprueba si existe un valor repetido para nombre de la categoria
            if (categoria.contains(nombreCategoria)){
                valorRepetido = categoria;
                repetido = true;
                break;
            }
        }
        // Este if comprueba que sea verdadero, osea que realmente se esta repitiendo el valor
        if (repetido){
            // Aqui ya estoy removiendo una categoria que ya se comprobo que esta repetida
            categorias.remove(valorRepetido);
        }

        categorias.add(valor);
        preferences.edit().remove(nombreValor).apply();
        preferences.edit().putStringSet(nombreValor, categorias).apply();
    }

    public static Set<String> leerLista(SharedPreferences preferences, String nombreValor){
        return preferences.getStringSet(nombreValor, null);
    }

    public static void limpiarDatos(SharedPreferences preferences){
        preferences.edit().clear().apply();
    }
}
