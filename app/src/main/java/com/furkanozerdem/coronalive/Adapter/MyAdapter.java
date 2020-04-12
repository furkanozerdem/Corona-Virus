package com.furkanozerdem.coronalive.Adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkanozerdem.coronalive.Activity.MainActivity;
import com.furkanozerdem.coronalive.Model.UlkeList;
import com.furkanozerdem.coronalive.Model.Ulkeler;
import com.furkanozerdem.coronalive.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    ArrayList<Ulkeler> ulkeListesi; // listenin tamamı
    ArrayList<Ulkeler> exampleList;

    public MyAdapter(ArrayList<Ulkeler> ulkeListesi) {
        this.ulkeListesi = ulkeListesi;
        exampleList = new ArrayList<>(ulkeListesi);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflatere = LayoutInflater.from(parent.getContext());
        View view = inflatere.inflate(R.layout.cardlayout,parent,false);

         return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


         holder.country.setText(exampleList.get(position).getCountry() + "(" + exampleList.get(position).getCountryCode() + ")");
         holder.newConfirmed.setText("Yeni Vakalar : +" + exampleList.get(position).getNewConfirmed());
         holder.newRecovered.setText("Yeni İyileşenler : +" + exampleList.get(position).getNewRecovered());
         holder.newDeaths.setText("Yeni Ölümler : +" + exampleList.get(position).getNewDeaths());
         holder.totalRecovered.setText("Toplam İyileşme : " + exampleList.get(position).getTotalRecovered());
         holder.totalDeaths.setText("Toplam Ölüm : " + exampleList.get(position).getTotalDeaths());
         holder.totalConfirmed.setText("Toplam Vaka : " + exampleList.get(position).getTotalConfirmed());
         holder.date.setText("Verinin Bildirilme Tarihi : " + exampleList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
      ArrayList<Ulkeler> filteredList = new ArrayList<>();
      if(charSequence == null || charSequence.length()== 0) {
          filteredList.addAll(ulkeListesi); // listenin tamamı eklenir. çünkü filtreleme yok.
      }
      else {
        String filterPattern = charSequence.toString().toLowerCase().trim();//trim => girdinin başındaki ve sonundaki boşlukları önemsemez.
        for(Ulkeler u : ulkeListesi) {
            if(u.getCountry().toLowerCase().contains(filterPattern))
                filteredList.add(u);
               }
          }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                exampleList.clear();
                exampleList.addAll((ArrayList<Ulkeler>) filterResults.values);
                notifyDataSetChanged();


        }
    };





    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView country,newConfirmed,newRecovered, newDeaths,totalRecovered,totalDeaths,totalConfirmed,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.countyName);
            newConfirmed = itemView.findViewById(R.id.newConfirmed);
            newRecovered = itemView.findViewById(R.id.newRecovered);
            newDeaths= itemView.findViewById(R.id.newDeath);
            totalRecovered= itemView.findViewById(R.id.totalRecovered);
            totalDeaths= itemView.findViewById(R.id.totalDeaths);
            totalConfirmed= itemView.findViewById(R.id.totalConfirmed);
            date = itemView.findViewById(R.id.date);
        }
    }
}
