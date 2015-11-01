package com.apps.poi.models.rest;


import com.apps.poi.models.data.Detail;
import com.apps.poi.models.data.Points;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * This interface describes the methods to get the data using retrofit.
 *
 * Created by Victor Tellez on 29/10/2015.
 */
public interface DataService {

    /**
     * Gets the points.
     *
     * @param callback  to know when the data is ready
     */
    @Headers("Content-Type: application/json")
    @GET("/points")
    void getPointsJson(Callback<Points> callback);

    /**
     * Gets the detail.
     * @param id        of the detail
     * @param callback  to know when the data is ready
     */
    @Headers("Content-Type: application/json")
    @GET("/points/{id}")
    void getDetailJson(@Path("id")String id, Callback<Detail> callback);
}
