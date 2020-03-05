package com.eo.cars.rest;


import com.eo.cars.model.CarsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("cars")
    Call<CarsResponse> getCarsList(@Query("page") int page);
}