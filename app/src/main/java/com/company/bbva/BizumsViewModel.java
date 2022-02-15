package com.company.bbva;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class BizumsViewModel extends AndroidViewModel {


    BizumsRepositorio bizumsRepositorio;

    public BizumsViewModel(@NonNull Application application) {
        super(application);
        bizumsRepositorio = new BizumsRepositorio(application);
    }

    LiveData<List<Bizum>> obtener(){
        return bizumsRepositorio.obtener();
    }

    void insertar(Bizum bizum){
        bizumsRepositorio.insertar(bizum);
    }

    void eliminar(Bizum bizum){
        bizumsRepositorio.eliminar(bizum);
    }

    void actualizar(Bizum bizum, boolean b){
        bizum.check=b;
        bizumsRepositorio.actualizar(bizum);
    }



}
