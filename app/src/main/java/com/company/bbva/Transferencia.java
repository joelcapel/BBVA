package com.company.bbva;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transferencia {
    @PrimaryKey(autoGenerate = true)
    public
    int id;

    public String titulo;
    public String texto;
    public float valoracion;

    public Transferencia(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }
}
