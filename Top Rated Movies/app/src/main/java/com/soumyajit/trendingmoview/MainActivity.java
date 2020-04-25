package com.soumyajit.trendingmoview;

import java.util.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ImageView imageView;
    TextView textView;
    TextView textView2;

    ArrayList<String> movie_name;
    ArrayList<String> movie_rating;
    ArrayList<String> movie_poster;
    ArrayList<String> movie_id;

    CustomAdapter customAdapter;

    String url="https://api.themoviedb.org/3/movie/top_rated?api_key=d93b03476fdae4ce83e89b27a4e0176d";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Top Rated");
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.listView);


        StringRequest request=new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    movie_name=new ArrayList<>();
                    movie_rating=new ArrayList<>();
                    movie_poster=new ArrayList<>();
                    movie_id=new ArrayList<>();

                    JSONArray jsonArray=jsonObject.getJSONArray("results");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject list=jsonArray.getJSONObject(i);
                        movie_name.add(list.getString("title"));
                        movie_rating.add(list.getString("vote_average"));
                        movie_poster.add("https://image.tmdb.org/t/p/w500"+list.getString("poster_path"));
                        movie_id.add(list.getString("id"));


                    }
                    customAdapter=new CustomAdapter();
                    lv.setAdapter(customAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Nothing Found", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("movie_id",movie_id.get(i));
                startActivity(intent);
            }
        });

    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return movie_name.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate(R.layout.single_movie,null);
            imageView=view.findViewById(R.id.imageView2);
            textView=view.findViewById(R.id.textView);
            textView2=view.findViewById(R.id.textView2);

            textView.setText(movie_name.get(i));
            textView2.setText(movie_rating.get(i));
            Picasso.with(getApplicationContext()).load(movie_poster.get(i)).into(imageView);


            return view;
        }
    }

    }
