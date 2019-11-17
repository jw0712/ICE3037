package com.example.weather;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpTask extends AsyncTask<Void, Void, String> {
    private String url;
    private HashMap<String, String> params;
    private HttpCallback httpCallback;

    public HttpTask(String url, HashMap<String, String> params, HttpCallback httpCallback) {
        this.url = url;
        this.params = params;
        this.httpCallback = httpCallback;
    }
    @Override
    protected String doInBackground(Void... args) { //thread역할
        String response = "";
        String postData = "";

        PrintWriter pw = null;
        BufferedReader in = null;

        try {

            if (params != null && params.size() > 0) {
                Iterator<Map.Entry<String, String>> entries = params.entrySet().iterator();

                while (entries.hasNext()) {
                    Map.Entry<String, String> mapEntry = entries.next();
                    postData = postData + mapEntry.getKey() + "=" + URLEncoder.encode(mapEntry.getValue(), "UTF-8");
                    if (entries.hasNext())
                        postData += "&";
                }
            }
            URL text = new URL(url);
            HttpURLConnection http = (HttpURLConnection) text.openConnection();
            http.setRequestMethod("POST");
            http.setConnectTimeout(15 * 1000);
            http.setReadTimeout(15 * 1000);
            http.setDoInput(true);
            http.setDoOutput(true); //post를 넘길거니까 true

            pw = new PrintWriter(new OutputStreamWriter( http.getOutputStream(), "UTF-8"));
            pw.write(postData); //post data를 http에다 그대로 적음. http client를 직접 구현하고 있는 것
            pw.flush(); //데이터 작성되었을 것

            in = new BufferedReader(new InputStreamReader( http.getInputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            response = URLDecoder.decode(sb.toString(), "UTF-8");

            return response; //리턴 시 onPostExecute가 호출됨. 엄마 자원 access 불가능. thread 안.
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) { //엄마자워 ㄴaccess 가능.
        if(this.httpCallback != null) {
            this.httpCallback.onResult(s);
        }
    }

    interface HttpCallback {
        void onResult(String result);
    }//
}
