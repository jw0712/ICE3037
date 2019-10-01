package com.example.myapplication5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1234 && resultCode==RESULT_OK){
            Log.d("",data.getStringExtra("from_second"));
        }
    }
    int count=0;

    @Override
    public void onBackPressed() {
        if(count==0) {
            count++;
            Toast.makeText(this, "한번 더 누르시면 종료됩니다", Toast.LENGTH_LONG).show();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_first:
                Intent intent= new Intent(this, SecondActivity.class);
                //startActivity(intent); //그냥 보여주는 것
                startActivityForResult(intent, 1234); //결과를 받기 위해 호출
                break;
            case R.id.tv_second:
                Intent intent2 = new Intent(Intent.ACTION_DIAL);
                intent2.setData(Uri.parse("tel:01012341234"));
                startActivity(intent2);
                break;

        }
    }
}
