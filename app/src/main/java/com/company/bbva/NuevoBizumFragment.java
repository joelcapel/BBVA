package com.company.bbva;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.bbva.databinding.FragmentNuevoBizumBinding;
import com.google.android.material.snackbar.Snackbar;


public class NuevoBizumFragment extends Fragment {

    private FragmentNuevoBizumBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = FragmentNuevoBizumBinding.inflate(inflater,container,false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BizumsViewModel bizumsViewModel=new ViewModelProvider(requireActivity()).get(BizumsViewModel.class);
        NavController navController= Navigation.findNavController(view);


        binding.crear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                String bizum= binding.bizum.getText().toString();

                if (bizum.length()>0) {
                    bizumsViewModel.insertar(new Bizum(bizum, false));
                    navController.popBackStack();
                    Snackbar snack = null;


                    snack.make(view, bizum+" creado.", Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.purple_500,getActivity().getTheme())).setActionTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setDuration(2500).show();


                }else{
                    binding.bizum.setHintTextColor(getResources().getColor(R.color.red,getActivity().getTheme()));
                    binding.bizum.setHint(" !");
                }
            }
        });
    }


}