package com.example.hai.sesion;

import android.content.SharedPreferences;

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
}
