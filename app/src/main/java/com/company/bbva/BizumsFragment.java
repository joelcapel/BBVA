package com.company.bbva;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;

import com.company.bbva.databinding.FragmentBizumsBinding;
import com.company.bbva.databinding.ViewholderBizumBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class BizumsFragment extends Fragment {

    private FragmentBizumsBinding binding;
    BizumsViewModel bizumsViewModel;
    NavController navController;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentBizumsBinding.inflate(inflater, container, false)).getRoot();

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        bizumsViewModel = new ViewModelProvider(requireActivity()).get(BizumsViewModel.class);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        binding.NuevoBizum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nuevoBizumFragment);
            }
        });

        BizumsFragment.BizumsAdapter bizumsAdapter;
        bizumsAdapter = new BizumsFragment.BizumsAdapter();

        binding.recyclerViewB.setAdapter(bizumsAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int posicion = viewHolder.getAdapterPosition();
                Bizum bizum = bizumsAdapter.obtenerBizum(posicion);
                bizumsViewModel.eliminar(bizum);

                Snackbar snack = null;


                snack.make(view, bizum.bizum+" eliminado.", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bizumsViewModel.insertar(bizum);
                    }

                }).setBackgroundTint(getResources().getColor(R.color.purple_500,getActivity().getTheme())).setActionTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setDuration(2500).show();


            }
        }).attachToRecyclerView(binding.recyclerViewB);

        obtenerBizums().observe(getViewLifecycleOwner(), new Observer<List<Bizum>>() {
            @Override
            public void onChanged(List<Bizum> bizums) {
                bizumsAdapter.establecerLista(bizums);
            }
        });
        /*binding.recyclerViewT.setAdapter(tasquesAdapter);*/


    }
    //////////   activar aixó

    LiveData<List<Bizum>> obtenerBizums(){
        return bizumsViewModel.obtener();
    }

    class BizumsAdapter extends RecyclerView.Adapter<BizumsFragment.BizumViewHolder> {

        List<Bizum> bizums;

        @NonNull
        @Override
        public BizumsFragment.BizumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BizumsFragment.BizumViewHolder(ViewholderBizumBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull BizumsFragment.BizumViewHolder holder, int position) {

            Bizum bizum = bizums.get(position);



            holder.binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    bizumsViewModel.actualizar(bizum,b);

                }
            });




            holder.binding.bizum.setText(bizum.bizum);
            holder.binding.checkBox.setChecked(bizum.check);

        }



        @Override
        public int getItemCount() {
            return bizums != null ? bizums.size() : 0;
        }

        public void establecerLista(List<Bizum> bizums){
            this.bizums = bizums;
            notifyDataSetChanged();
        }
        public Bizum obtenerBizum(int posicion){
            return bizums.get(posicion);
        }


    }

    static class BizumViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderBizumBinding binding;

        public BizumViewHolder(ViewholderBizumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
