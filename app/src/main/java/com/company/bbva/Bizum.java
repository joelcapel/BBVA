package com.company.bbva;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bizum {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String bizum;
    public boolean check;

    public Bizum(String bizum, boolean check) {

        this.bizum=bizum;
        this.check=check;
    }

}
