package com.company.bbva;


import android.annotation.SuppressLint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.company.bbva.databinding.FragmentNuevaTransferenciaBinding;
import com.google.android.material.snackbar.Snackbar;


public class NuevaTransferenciaFragment extends Fragment {
    private FragmentNuevaTransferenciaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentNuevaTransferenciaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TransferenciasViewModel transferenciasViewModel = new ViewModelProvider(requireActivity()).get(TransferenciasViewModel.class);
        NavController navController = Navigation.findNavController(view);

        binding.crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = binding.titulo.getText().toString();
                String texto = binding.texto.getText().toString();
                String cantidad = binding.cantidad.getText().toString();

                if (titulo.length()<1&&texto.length()<1&&cantidad.length()<1) noGuardar(1); //mostrar error doble;
                else if (texto.length()<1) noGuardar(2);//mostrar error texto;
                else if (titulo.length()<1) noGuardar(3); //mostrar error titulo;
                else if (cantidad.length()<1) noGuardar(4); // mostrar error cantidad
                else{
                    transferenciasViewModel.insertar(new Transferencia(titulo, texto));
                    navController.popBackStack();
                    Snackbar.make(view, "Transferencia: "+titulo+" guardada.", Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.purple_500,getActivity().getTheme())).setActionTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setDuration(2000).show();

                }


            }
        });
    }
    @SuppressLint("ResourceAsColor")
    public void noGuardar(int i){
        if (i==1){
            binding.titulo.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.titulo.setHint("NO PUEDES DEJARLO EN BLANCO");

            binding.texto.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.texto.setHint("NO PUEDES DEJARLO EN BLANCO");

        }else if (i==2){
            binding.texto.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.texto.setHint("NO PUEDES DEJARLO EN BLANCO");


        }else if (i==3){
            binding.titulo.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.titulo.setHint("NO PUEDES DEJARLO EN BLANCO");
        }
    }
}