package com.apps.poi.models.rest;


import com.apps.poi.models.data.Detail;
import com.apps.poi.models.data.Points;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by Victor Tellez on 29/10/2015.
 */
public interface DataService {

    @Headers("Content-Type: application/json")
    @GET("/points")
    void getPointsJson(Callback<Points> callback);

    @Headers("Content-Type: application/json")
    @GET("/points/{id}")
    void getDetailJson(@Path("id")String id, Callback<Detail> callback);
}
