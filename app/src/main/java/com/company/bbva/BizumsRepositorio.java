package com.company.bbva;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BizumsRepositorio {


    ElementosBaseDeDatos.TransferenciasDao bizumsDao;
    Executor executor= Executors.newSingleThreadExecutor();

    BizumsRepositorio(Application application){
        bizumsDao = ElementosBaseDeDatos.obtenerInstancia(application).obtenerTransferenciasDao();

    }


    LiveData<List<Bizum>> obtener(){

        return bizumsDao.obtenerB();
    }

    void insertar(Bizum bizum){
        if (bizum.bizum.length()!=0) {

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    bizumsDao.insertarB(bizum);
                }
            });
        } }

    void eliminar(Bizum bizum) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bizumsDao.eliminarB(bizum);
            }
        });
    }

    public void actualizar(Bizum bizum) {


        executor.execute(new Runnable() {

            @Override
            public void run() {

                bizumsDao.actualizarB(bizum);

            }
        });
    }

}
