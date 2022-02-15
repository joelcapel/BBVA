package com.company.bbva;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {Transferencia.class,Bizum.class}, version = 2, exportSchema = false)
public abstract class ElementosBaseDeDatos extends RoomDatabase {

    public abstract TransferenciasDao obtenerTransferenciasDao();




    public static volatile ElementosBaseDeDatos INSTANCIA;


    public static ElementosBaseDeDatos obtenerInstancia(final Context context) {
        if (INSTANCIA == null) {
            synchronized (ElementosBaseDeDatos.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context,
                            ElementosBaseDeDatos.class, "elementos.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCIA;
    }




    @Dao
    public
    interface TransferenciasDao {



        @Insert
        void insertar(Transferencia transferencia);


        @Update
        void actualizar(Transferencia transferencia);



        @Delete
        void eliminar(Transferencia transferencia);



        @Query("SELECT * FROM Transferencia WHERE titulo LIKE '%' || :d || '%' order by valoracion desc")
        LiveData<List<Transferencia>> buscar(String d);

        @Query("SELECT * FROM Bizum")
        LiveData<List<Bizum>> obtenerB();

        @Insert
        void insertarB(Bizum bizum);

        @Update
        void actualizarB(Bizum bizum);

        @Delete
        void eliminarB(Bizum bizum);



    }


}
