package com.example.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;


public class MainActivity extends AppCompatActivity {

    TextView tvLocation;
    TextView tvWeather;
    TextView tvCountry;
    TextView tvCityname;
    RelativeLayout layoutProgress;

    double lat; //경도
    double lon; //위도

    LocationManager locationManager;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    layoutProgress.setVisibility(View.GONE);
                    tvWeather.setText("Request Fail...");
                    break;
                case 1:
                    layoutProgress.setVisibility(View.GONE);
                    tvWeather.setText((String)msg.obj);
                    Gson gson = new Gson();
                    HttpResponseDto httpResponseDto = gson.fromJson((String)msg.obj, HttpResponseDto.class);

                    HttpResponseDto.WeatherDto[] datas = httpResponseDto.getWeather();
                    for(HttpResponseDto.WeatherDto dDto : datas) {
                        tvWeather.setText(dDto.getMain()+" / "+dDto.getDescription());
                    }
                    HttpResponseDto.SysDto sys = httpResponseDto.getSys();
                    tvCountry.setText(sys.getCountry());
                    tvCityname.setText(HttpResponseDto.getName());

                    HttpResponseDto.getName();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLocation=findViewById(R.id.tv_location);
        tvWeather=findViewById(R.id.tv_weather);
        tvCountry=findViewById(R.id.tv_country);
        tvCityname=findViewById(R.id.tv_cityname);
        layoutProgress = findViewById(R.id.layout_progress);

        TextView btnRequest = findViewById(R.id.btn_request);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutProgress.setVisibility(View.VISIBLE);
                HttpThread httpThread = new HttpThread(lat, lon);
                httpThread.start();
            }
        });

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){ //android.marshmallow 이하
            getLocationManager();
        } else{
            if(checkPermission()){
                getLocationManager();
            }else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12345);
            }
        }
    }


    class HttpThread extends Thread {
        private double lat;
        private double lon;

        public HttpThread(double lat, double lon) {
            this.lat=lat;
            this.lon=lon;
        }
        @Override
        public void run() {
            String response = "";

            BufferedReader in = null;

            try {
                URL text = new URL("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"lon="+lon+"&appid=400c047eb6333f9fcba21f2c03564ea8&units=metric");
                HttpURLConnection http = (HttpURLConnection) text.openConnection();
                http.setRequestMethod("GET");
                http.setConnectTimeout(15 * 1000);
                http.setReadTimeout(15 * 1000);
                http.setDoInput(true);
                http.setDoOutput(false);

                in = new BufferedReader(new InputStreamReader( http.getInputStream(), "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }
                response = URLDecoder.decode(sb.toString(), "UTF-8");

                Message msg = new Message();
                msg.what = 1;
                msg.obj = response;
                handler.sendMessage(msg);

            } catch (Exception e) {
                handler.sendEmptyMessage(0);
            }
        }
    }
//=========================================================================
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("main", "위치 변경!! lat= "+location.getLatitude()+" lod경도= "+location.getLongitude());
            lat=location.getLatitude();
            lon=location.getLongitude();
            tvLocation.setText(lat+ "\n"+lon);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {        }
        @Override
        public void onProviderEnabled(String provider) {        }
        @Override
        public void onProviderDisabled(String provider) {        }
    };
    private void getLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==12345){
            if(checkPermission()){
                getLocationManager();
            }else{
                Toast.makeText(this, "위치권한이 필요합니다.",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkPermission(){
        if(PackageManager.PERMISSION_GRANTED!=checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)){
            return false;
        }else{
            return true;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager!=null){
            locationManager.removeUpdates(locationListener);
        }
    }
    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        if(locationManager!=null){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
        }
    }
}
