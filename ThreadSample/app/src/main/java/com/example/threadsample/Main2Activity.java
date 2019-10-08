package com.example.threadsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
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
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute();
                Log.d("skku","end click...");
    }
    });
    }
    class MyAsyncTask extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... voids) {//run(). etId못씀. Thread
            for(int i=0;i<100;i++){
                publishProgress(i); //sendMessage, onProgressUpdate가 integer로 받으므로 integer를 publish
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {//handler msg
            etId.setText("count : "+values[0]);
        }
    }
}