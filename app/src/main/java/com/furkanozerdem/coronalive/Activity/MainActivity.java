package com.furkanozerdem.coronalive.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
import java.util.Collections;
import java.util.Comparator;

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
    ArrayList<Ulkeler> ulke;
    ProgressBar pb;
    Integer siralama;
    private Toolbar toolbar;
   // EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://api.covid19api.com/summary
                searchView = findViewById(R.id.searchView);
                recyclerView = findViewById(R.id.recyclerView);
                pb = findViewById(R.id.progressBar);
                toolbar= findViewById(R.id.toolbar1);
               Gson gson = new GsonBuilder().setLenient().create();
                  retrofit = new Retrofit.Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create(gson))
                 .build();
                  ulke = new ArrayList<>();

              toolbar.setTitle("COVID19'");
              setSupportActionBar(toolbar);

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
             final ArrayList<Ulkeler> ulkeListesi = new ArrayList<>(response.body().getUlkeListesi());

                ulke = ulkeListesi;
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                myAdapter = new MyAdapter(ulkeListesi);
                recyclerView.setAdapter(myAdapter);
                pb.setVisibility(View.INVISIBLE);
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
        pb.setVisibility(View.VISIBLE);
        Toast.makeText(MainActivity.this,"Veriler yenileniyor...", Toast.LENGTH_SHORT).show();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       switch (item.getItemId()) {
           case R.id.accordingTotalRecovered:

               Collections.sort(ulke, new Comparator<Ulkeler>() {
                   @Override
                   public int compare(Ulkeler u1, Ulkeler u2) {
                       return Integer.compare(u2.getTotalRecovered(),u1.getTotalRecovered());
                   }
               });
               break;
           case R.id.accordinNewCase:

               Collections.sort(ulke, new Comparator<Ulkeler>() {
                   @Override
                   public int compare(Ulkeler u1, Ulkeler u2) {
                       return Integer.compare(u2.getNewConfirmed(),u1.getNewConfirmed());

                   }
               });
               break;
           case R.id.accordinTotalCase:

               Collections.sort(ulke, new Comparator<Ulkeler>() {
                   @Override
                   public int compare(Ulkeler u1, Ulkeler u2) {
                       return Integer.compare(u2.getTotalConfirmed(),u1.getTotalConfirmed());

                   }
               });
               break;
           case R.id.accordinTotalDeath:

               Collections.sort(ulke, new Comparator<Ulkeler>() {
                   @Override
                   public int compare(Ulkeler u1, Ulkeler u2) {
                       return Integer.compare(u2.getTotalDeaths(),u1.getTotalDeaths());

                   }
               });
               break;

       }
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        myAdapter = new MyAdapter(ulke);
        recyclerView.setAdapter(myAdapter);
        return super.onOptionsItemSelected(item);
    }

}
