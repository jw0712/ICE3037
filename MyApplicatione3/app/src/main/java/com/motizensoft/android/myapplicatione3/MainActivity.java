package com.motizensoft.android.myapplicatione3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1 = findViewById(R.id.btn_01);
        Button btn2 = findViewById(R.id.btn_02);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_01:
                startService(new Intent(this, MyService.class));
                MyService myService = new MyService();
                Log.d("MyActivity", myService.getData());
                break;
            case R.id.btn_02:
                stopService(new Intent(this, MyService.class));
                break;
        }
    }
}









