package com.company.bbva;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

public class TransferenciasViewModel extends AndroidViewModel {

    TransferenciasRepositorio transferenciasRepositorio;


    MutableLiveData<Transferencia> transferenciaSeleccionada = new MutableLiveData<>();

    MutableLiveData<String> terminoBusqueda = new MutableLiveData<>();

    LiveData<List<Transferencia>> resultadoBusqueda = Transformations.switchMap(terminoBusqueda, new Function<String, LiveData<List<Transferencia>>>() {
        @Override
        public LiveData<List<Transferencia>> apply(String input) {
            return transferenciasRepositorio.buscar(input);
        }
    });




    public TransferenciasViewModel(@NonNull Application application) {
        super(application);

        transferenciasRepositorio = new TransferenciasRepositorio(application);
    }



/*
    LiveData<List<Nota>> obtener(){
        return notasRepositorio.obtener();
    }

    LiveData<List<Nota>> masValorados(){
        return notasRepositorio.masValorados();
    }
*/

    LiveData<List<Transferencia>> buscar(){
        return resultadoBusqueda;
    }

    void insertar(Transferencia transferencia){
        transferenciasRepositorio.insertar(transferencia);
    }

    void eliminar(Transferencia transferencia){
        transferenciasRepositorio.eliminar(transferencia);
    }

    void actualizar(Transferencia transferencia,float valoracion){
        transferenciasRepositorio.actualizar(transferencia, valoracion);
    }


    void seleccionar(Transferencia transferencia){
        transferenciaSeleccionada.setValue(transferencia);
    }

    MutableLiveData<Transferencia> seleccionado(){
        return transferenciaSeleccionada;
    }


    public void establecerTerminoBusqueda(String s){
        terminoBusqueda.setValue(s);
    }

    public void actualizar(Transferencia transferencia,String titulo,String texto) {
        transferenciasRepositorio.actualizarTitulo(transferencia,titulo,texto);
    }
}