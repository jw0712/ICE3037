package com.example.survival_mapexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mMapButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapButton = findViewById(R.id.map_button);

        mMapButton.setOnClickListener((View.OnClickListener) this);

    }
    public void onClick(View v) {
        Intent intent = new Intent(this, MapFragment.class);
        startActivity(intent);
    }

}