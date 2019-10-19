package com.example.abhishek.billing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    EditText UrlField;
    TextView ShowResponse;
    Button HitApi;
    static final String API_URL= "https://api.pions.in/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShowResponse = (TextView) findViewById(R.id.response);
        UrlField = (EditText)findViewById(R.id.urlfield);

        Button HitApi =  (Button) findViewById(R.id.submit);
        HitApi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new CallApi().execute();
            }
        });
    }

    class CallApi extends AsyncTask<Void, Void, String>{
        private Exception exception;

        protected void onPreExecute() {
            ShowResponse.setText("");
        }

        protected String doInBackground(Void... urls) {
            String email = UrlField.getText().toString();
            try {
                //+ "email=" + email + "&apiKey=" + API_KEY
                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            ;
            Log.i("INFO", response);
            ShowResponse.setText(response);
            // TODO: check this.exception
            // TODO: do something with the feed

//            try {
//                JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
//                String requestID = object.getString("requestId");
//                int likelihood = object.getInt("likelihood");
//                JSONArray photos = object.getJSONArray("photos");
//                .
//                .
//                .
//                .
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }

    }

}
