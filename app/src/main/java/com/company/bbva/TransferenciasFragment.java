package com.company.bbva;

import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;


import com.company.bbva.databinding.FragmentTransferenciasBinding;
import com.company.bbva.databinding.ViewholderTransferenciaBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TransferenciasFragment extends Fragment {
    private FragmentTransferenciasBinding binding;
    TransferenciasViewModel transferenciasViewModel;
    private NavController navController;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentTransferenciasBinding.inflate(inflater, container, false)).getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        transferenciasViewModel = new ViewModelProvider(requireActivity()).get(TransferenciasViewModel.class);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        binding.NuevaTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nuevaTransferenciaFragment);
            }
        });



        TransferenciasAdapter transferenciasAdapter;
        transferenciasAdapter = new TransferenciasAdapter();




        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();



                Transferencia transferencia = transferenciasAdapter.obtenerTransferencia(posicion);
                transferenciasViewModel.eliminar(transferencia);
                Snackbar.make(view, transferencia.titulo.toUpperCase()+" eliminada.", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        transferenciasViewModel.insertar(transferencia);
                    }

                }).setBackgroundTint(getResources().getColor(R.color.purple_500,getActivity().getTheme())).setActionTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setDuration(2500).show();


            }
        }).attachToRecyclerView(binding.recyclerView);

        obtenerTransferencias().observe(getViewLifecycleOwner(), new Observer<List<Transferencia>>() {
            @Override
            public void onChanged(List<Transferencia> transferencias) {
                transferenciasAdapter.establecerLista(transferencias);
            }
        });


        binding.recyclerView.setAdapter(transferenciasAdapter);

        transferenciasViewModel.establecerTerminoBusqueda("");

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                transferenciasViewModel.establecerTerminoBusqueda(newText);
                return false;
            }
        });

    }
    //////////   activar aixó

    LiveData<List<Transferencia>> obtenerTransferencias() {
        return transferenciasViewModel.buscar();
    }

    class TransferenciasAdapter extends RecyclerView.Adapter<TransferenciaViewHolder> {

        List<Transferencia> transferencias;


        @NonNull
        @Override
        public TransferenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TransferenciaViewHolder(ViewholderTransferenciaBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TransferenciaViewHolder holder, int position) {

            Transferencia transferencia = transferencias.get(position);

            holder.binding.titulo.setText(transferencia.titulo);
            holder.binding.texto.setText(transferencia.texto);
            holder.binding.ratingbar.setRating(transferencia.valoracion);

            holder.binding.ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) {
                        transferenciasViewModel.actualizar(transferencia, rating);
                    }
                }
            });




            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transferenciasViewModel.seleccionar(transferencia);
                    navController.navigate(R.id.action_mostrarTransferenciaFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return transferencias != null ? transferencias.size() : 0;
        }

        public void establecerLista(List<Transferencia> transferencias) {
            this.transferencias = transferencias;
            notifyDataSetChanged();
        }

        public Transferencia obtenerTransferencia(int posicion) {
            return transferencias.get(posicion);
        }

       /* public void setPosicion(int fromPos, int toPos) {

        }*/
    }

    static class TransferenciaViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderTransferenciaBinding binding;

        public TransferenciaViewHolder(ViewholderTransferenciaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}