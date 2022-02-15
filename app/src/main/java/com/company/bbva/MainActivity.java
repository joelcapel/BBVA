package com.company.bbva;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.company.bbva.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        //setSupportActionBar(binding.toolbar);

    }
}