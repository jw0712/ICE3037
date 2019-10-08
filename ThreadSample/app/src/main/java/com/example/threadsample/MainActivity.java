package com.example.threadsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 1){
                etId.setText((String)msg.obj);
            }else if(msg.what==2){
                etPwd.setText((String)msg.obj);
            }
        }
    };
    class MyThread extends Thread{
        @Override
        public void run(){
            for(int i =0; i<10; i++){
                Log.d("skku","count : "+i);
//                etId.setText("count"+i);
                Message msg = new Message();
                if(i%2 == 0){
                    msg.what = 1;
                    msg.obj = "count : " + i;
                } else{
                    msg.what = 2;
                    msg.obj = "count : " + i;
                }
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    EditText etId, etPwd;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etId = findViewById(R.id.et_id);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("skku","id:"+etId.getText().toString()+", pwd:"+etPwd.getText());
                MyThread myThread = new MyThread();
                myThread.start();
                Log.d("skku","end click");
            }
        });
    }
}