package com.furkanozerdem.coronalive.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UlkeList {

    @SerializedName("Countries")
    public ArrayList<Ulkeler> ulkeListesi;

    public ArrayList<Ulkeler> getUlkeListesi() {
        return ulkeListesi;
    }

    public void setUlkeListesi(ArrayList<Ulkeler> ulkeListesi) {
        this.ulkeListesi = ulkeListesi;
    }

    public UlkeList() {

    }



}
