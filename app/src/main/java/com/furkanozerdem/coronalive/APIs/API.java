package com.furkanozerdem.coronalive.APIs;

import com.furkanozerdem.coronalive.Model.UlkeList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    //GET,POST,UPDATE,DELETE işlemleri !
    //https://api.covid19api.com/summary
    //https://github.com/atilsamancioglu/K21-JSONDataSet/blob/master/crypto.json

    @GET("summary")
    Call<UlkeList> getData();



}
