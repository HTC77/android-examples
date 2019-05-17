package com.example.androidexamples;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetData extends AsyncTask<String,Void,String> {
    private Context context;
    private String url= "http://10.0.3.2/a.txt";
    public GetData(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL mUrl = new URL(this.url);
            HttpURLConnection uc =  (HttpURLConnection) mUrl.openConnection();
            uc.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            StringBuilder value = new StringBuilder();
            String line =null;
           while ((line = reader.readLine()) !=null){
               value.append(line);
           }
            return value.toString();
        } catch (MalformedURLException e) {
            Toast.makeText(context.getApplicationContext(),
                    "Err:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(),
                    "Error I/O occurred", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(),
                    "Err:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context.getApplicationContext(),
                "Result: "+s, Toast.LENGTH_LONG).show();
        super.onPostExecute(s);
    }
}
