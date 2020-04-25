package com.soumyajit.trendingmoview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {

    ImageView imageView;
    TextView textView,textView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView=findViewById(R.id.imageView3);
        textView=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);

        Intent intent=getIntent();
        String movie_id=getIntent().getStringExtra("movie_id");
        String url="https://api.themoviedb.org/3/movie/"+movie_id+"?api_key=d93b03476fdae4ce83e89b27a4e0176d";
        StringRequest stringRequest=new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    textView.setText("Title: "+object.getString("title"));
                    textView4.setText("Overview: "+object.getString("overview"));
                    Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+object.getString("poster_path")).into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
