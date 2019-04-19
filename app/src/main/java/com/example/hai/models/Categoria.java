package com.example.hai.models;

public class Categoria {

    String nombre;
    float cantidad;
    float cantidadDeseada;


    public Categoria(String nombre, float cantidad, float cantidadDeseada){
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.cantidadDeseada = cantidadDeseada;

    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public float getCantidad(){
        return cantidad;
    }

    public void setCantidad(float cantidad){
        this.cantidad = cantidad;
    }

    public float getCantidadDeseada(){
        return cantidadDeseada;
    }

    public void setCantidadDeseada(float cantidadDeseada){
        this.cantidadDeseada = cantidadDeseada;
    }
}
