package com.example.adity.healthcareapp2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Mr.Patel on 04-10-2017.
 */
public class BackgroundWorkerDis extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    BackgroundWorkerDis(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = "https://electrometric-dips.000webhostapp.com/find_dis.php";
        String type = params[0];
        try {
                String a = params[1];

                String b = params[1];
                String c = params[1];
                Log.i("=============",a.toString());
                Log.i("=============",b.toString());
                Log.i("=============",c.toString());
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("Sym1", "UTF-8") + "=" + URLEncoder.encode(a, "UTF-8") + "&"
                        + URLEncoder.encode("Sym2", "UTF-8") + "=" + URLEncoder.encode(b, "UTF-8") + "&"
                        + URLEncoder.encode("Sym3", "UTF-8") + "=" + URLEncoder.encode(c, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
               //Toast.makeText(g, result, Toast.LENGTH_SHORT).show();
//            Toast.makeText(context,"Result :"+result,Toast.LENGTH_SHORT).show();
            Log.i("=============",result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
    @Override
    protected void onPreExecute() {

    }
    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, SuggestActivity.class);
        intent.putExtra("Disease", result);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
