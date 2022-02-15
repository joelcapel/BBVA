package com.company.bbva;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.company.bbva.databinding.FragmentMostrarTransferenciaBinding;
import com.google.android.material.snackbar.Snackbar;


public class MostrarTransferenciaFragment extends Fragment {
    private FragmentMostrarTransferenciaBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMostrarTransferenciaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TransferenciasViewModel transferenciasViewModel = new ViewModelProvider(requireActivity()).get(TransferenciasViewModel.class);

        NavController navController = Navigation.findNavController(view);


        transferenciasViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Transferencia>() {
            @Override
            public void onChanged(Transferencia transferencia) {

                binding.titulo.setText(transferencia.titulo);
                binding.texto.setText(transferencia.texto);
                binding.ratingBar.setRating(transferencia.valoracion);

                binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if (fromUser) {
                            transferenciasViewModel.actualizar(transferencia, rating);
                        }
                    }
                });


                binding.guardar.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        boolean change = false;
                        binding.guardar.setBackgroundColor(getResources().getColor(R.color.purple_500, getActivity().getTheme()));
                        if (!transferencia.titulo.equals(binding.titulo.getText().toString())) {
                            transferencia.titulo = binding.titulo.getText().toString();
                            change = true;
                        }
                        if (!transferencia.texto.equals(binding.texto.getText().toString())) {
                            transferencia.texto = binding.texto.getText().toString();
                            change = true;
                        }

                        if (transferencia.titulo.length() < 1 && transferencia.texto.length() < 1)
                            noGuardar(1); //mostrar error doble;
                        else if (transferencia.texto.length() < 1) noGuardar(2);//mostrar error text;
                        else if (transferencia.titulo.length() < 1) noGuardar(3); //mostrar error titol;
                        else if (change) {
                            transferenciasViewModel.actualizar(transferencia, binding.titulo.getText().toString(), binding.texto.getText().toString());
                            Snackbar.make(view, "Transferencia: "+transferencia.titulo+" guardada.", Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.purple_500,getActivity().getTheme())).setActionTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setDuration(2000).show();
                            navController.popBackStack();
                        }else{
                            Snackbar.make(view, "No has hecho cambios.", Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.purple_500,getActivity().getTheme())).setActionTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setDuration(1300).show();

                        }
                    }
                });

            }


        });
        /*binding.desar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notasViewModel.actualizar(notasViewModel.notaSeleccionada.getValue(),view.get.titol,notasViewModel.notaSeleccionada.getValue().text);
                navController.popBackStack();
            }
        });*/
    }

    public void noGuardar(int i) {
        if (i == 1) {
            binding.titulo.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.titulo.setHint(" !");

            binding.texto.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.texto.setHint(" !");

        } else if (i == 2) {
            binding.texto.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.texto.setHint(" !");


        } else if (i == 3) {
            binding.titulo.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.titulo.setHint(" !");
        }
    }
}