package com.example.weather;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetWeatherTask extends AsyncTask<String, Void, JSONObject> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... data) {
        try {
            HttpURLConnection http= (HttpURLConnection) new URL(data[0]).openConnection();
            //http.setRequestMethod("POST");
            http.setConnectTimeout(10*1000);
            http.setReadTimeout(10*1000);
            //http.setDoInput(true);
            //http.setDoOutput(true);

            http.connect();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream is = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(reader);

                String alreadyRead;
                while((alreadyRead=br.readLine()) !=null){
                    JSONObject jObject= new JSONObject(alreadyRead);
                    return jObject;
                }
            }else{
                return null;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        String finalmsg ="error";
        if(result != null){
            String icon = "";
            String maininfo="";

            String country="";
            String city="";

            String temp_max="";
            String temp_min="";
            String temp="";

            String humidity="";
            String windspeed="";

            int sunrise;
            int sunset;

            String descrpt="";

            try {
                icon=result.getJSONArray("weather").getJSONObject(0).getString("icon");
                maininfo=result.getJSONArray("weather").getJSONObject(0).getString("main");

                country=result.getJSONObject("sys").getString("country");
                //city= result.getJSONArray("weather").getJo;

                temp_max=result.getJSONObject("main").getString("temp_max");
                temp_min=result.getJSONObject("main").getString("temp_min");
                temp=result.getJSONObject("main").getString("temp");

                humidity=result.getJSONObject("main").getString("humidity");
                windspeed=result.getJSONObject("wind").getString("speed");
                descrpt=result.getJSONArray("weather").getJSONObject(0).getString("description");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            finalmsg = "Country: "+country+"\n"+maininfo+"\n"+descrpt+"\n"+"습도: "+humidity+"%"+"\n"+
                    "풍속: "+windspeed+"m/s"+"\n"+
                    "현재 온도: "+temp+", 최고 온도: "+temp_max+", 최저 온도: "+temp_min+"\n";


        }
    }
}
