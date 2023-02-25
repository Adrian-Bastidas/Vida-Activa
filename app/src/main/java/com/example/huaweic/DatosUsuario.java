package com.example.huaweic;

import java.util.ArrayList;

public class DatosUsuario {
    String Nombre;
    Integer Edad;
    Integer peso;
    ArrayList dias=new ArrayList();
    String hora;
    String mensaje;

    public DatosUsuario(String nombre, Integer edad, Integer peso, ArrayList dias, String hora, String mensaje) {
        Nombre = nombre;
        Edad = edad;
        this.peso = peso;
        this.dias = dias;
        this.hora = hora;
        this.mensaje = mensaje;
    }

    public DatosUsuario() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Integer getEdad() {
        return Edad;
    }

    public void setEdad(Integer edad) {
        Edad = edad;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public ArrayList getDias() {
        return dias;
    }

    public void setDias(ArrayList dias) {
        this.dias = dias;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
