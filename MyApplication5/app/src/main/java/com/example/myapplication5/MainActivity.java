package com.example.myapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvFirst = findViewById(R.id.tv_first);
        TextView tvSecond = findViewById(R.id.tv_second);
        tvFirst.setOnClickListener(this);
        tvSecond.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_first:
                Intent intent= new Intent(this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_second:
                break;

        }
    }
}
