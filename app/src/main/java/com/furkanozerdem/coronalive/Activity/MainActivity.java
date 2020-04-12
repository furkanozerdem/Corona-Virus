package com.furkanozerdem.coronalive.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.furkanozerdem.coronalive.APIs.API;
import com.furkanozerdem.coronalive.Adapter.MyAdapter;
import com.furkanozerdem.coronalive.Model.UlkeList;
import com.furkanozerdem.coronalive.Model.Ulkeler;
import com.furkanozerdem.coronalive.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private  String BASE_URL = "https://api.covid19api.com/";
    Retrofit retrofit;
    MyAdapter myAdapter;
    RecyclerView recyclerView;
    SearchView searchView;
   // EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://api.covid19api.com/summary
                searchView = findViewById(R.id.searchView);
                recyclerView = findViewById(R.id.recyclerView);
               Gson gson = new GsonBuilder().setLenient().create();
              retrofit = new Retrofit.Builder()
             .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();



         loadData();

     //   System.out.println("2. basım için : " +veriler.size());
           searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {



                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myAdapter.getFilter().filter(s);
                return false;
            }
        });






    }

    private void loadData() {
        API api = retrofit.create(API.class);
        Call<UlkeList> call = api.getData();

            call.enqueue(new Callback<UlkeList>() {
        @Override
        public void onResponse(Call<UlkeList> call, Response<UlkeList> response) {
            if(response.isSuccessful()) {
            //227 => Türkiye
             ArrayList<Ulkeler> ulkeListesi = new ArrayList<>(response.body().getUlkeListesi());
             Ulkeler u = ulkeListesi.get(227);
             ulkeListesi.set(227,ulkeListesi.get(0));
             ulkeListesi.set(0,u);


                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                myAdapter = new MyAdapter(ulkeListesi);
                recyclerView.setAdapter(myAdapter);

            }
        }

        @Override
        public void onFailure(Call<UlkeList> call, Throwable t) {
            if(t.getMessage().equals("timeout"))
            Toast.makeText(MainActivity.this,"Veriler alınırken hata oluştu. Lütfen daha sonra tekrar deneyin.",Toast.LENGTH_LONG).show();
        }
    });
    }
    public void refresh(View view) {
        Toast.makeText(MainActivity.this,"Veriler yenileniyor.", Toast.LENGTH_SHORT).show();
        loadData();
    }



}
