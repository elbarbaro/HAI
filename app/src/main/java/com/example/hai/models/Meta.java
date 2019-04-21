package com.example.hai.models;

import androidx.annotation.NonNull;

public class Meta {
    String razon;
    float cantidad;
    int tiempo;

    public Meta(String razon, float catidad, int tiempo){
        this.razon = razon;
        this.cantidad = catidad;
        this.tiempo = tiempo;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    @NonNull
    @Override
    public String toString() {
        return "{\"razon\": " +"\""+razon+"\",\"cantidad\":  " +cantidad+",\"tiempo\": " +tiempo+"}";
    }

}
