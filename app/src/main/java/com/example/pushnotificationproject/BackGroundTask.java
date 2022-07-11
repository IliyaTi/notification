package com.example.pushnotificationproject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackGroundTask extends AsyncTask<String,Void,String>{
    Context ctx;
    BackGroundTask(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://tourbazi.ir:3306/tourbazi_notif";
        String method = params[0];
        if(method.equals("register")){
            String inputName = params[1];
            String txtToken = params[2];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("post");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("inputName", "UTF-8") + "="+URLEncoder.encode(inputName,"UTF-8")+"&"+
                        URLEncoder.encode("txtToken", "UTF-8") + "="+URLEncoder.encode(txtToken,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream Is = httpURLConnection.getInputStream();
                Is.close();
                return "save success...";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx, "result", Toast.LENGTH_SHORT).show();
    }
}
