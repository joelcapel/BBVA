package com.company.bbva;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TransferenciasRepositorio {

    ElementosBaseDeDatos.TransferenciasDao transferenciasDao;
    Executor executor = Executors.newSingleThreadExecutor();

    TransferenciasRepositorio(Application application){
        transferenciasDao = ElementosBaseDeDatos.obtenerInstancia(application).obtenerTransferenciasDao();
    }


    LiveData<List<Transferencia>> buscar(String d) {

        return transferenciasDao.buscar(d);
    }


    void insertar(Transferencia transferencia){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                transferenciasDao.insertar(transferencia);
            }
        });
    }

    void eliminar(com.company.bbva.Transferencia transferencia) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                transferenciasDao.eliminar(transferencia);
            }
        });
    }

    public void actualizar(com.company.bbva.Transferencia transferencia , float valoracion) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                transferencia.valoracion = valoracion;
                transferenciasDao.actualizar(transferencia);
            }
        });
    }

    public void actualizarTitulo(Transferencia transferencia,String titulo,String texto) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                transferencia.texto=texto;
                transferencia.titulo=titulo;
                transferenciasDao.actualizar(transferencia);
            }
        });
    }
}