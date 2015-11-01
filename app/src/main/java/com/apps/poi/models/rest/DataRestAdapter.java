package com.apps.poi.models.rest;

import android.util.Log;

import com.apps.poi.BuildConfig;
import com.apps.poi.models.data.Detail;
import com.apps.poi.models.data.Points;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * This is the adapter to get the data.
 *
 * Created by Victor Tellez on 29/10/2015.
 */
public class DataRestAdapter {

    public static final String BASE_URL_DATA = "YYY";

    /**
     * Callback
     */
    private OnCustomCallback onCustomsCallback;

    /**
     * Constuctor.
     *
     * @param onCustomsCallback
     */
    public DataRestAdapter(OnCustomCallback onCustomsCallback) {
        this.onCustomsCallback = onCustomsCallback;
    }

    /**
     * The request interceptor.
     */
    RequestInterceptor requestInterceptor = new RequestInterceptor() {

        @Override
        public void intercept(RequestFacade request) {
            request.addHeader("Accept", "application/json");
            int maxAge = 120 * 120;
            request.addHeader("Cache-Control", "public, max-age=" + maxAge);
        }
    };

    /**
     * Gets the points.
     */
    public void getPointsAsync() {

        OkHttpClient httpClient = new OkHttpClient();

        retrofit.RestAdapter restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(BASE_URL_DATA)
                .setClient(new OkClient(httpClient))
                .setLogLevel(BuildConfig.DEBUG ?
                        retrofit.RestAdapter.LogLevel.FULL :
                        retrofit.RestAdapter.LogLevel.NONE)
                .setRequestInterceptor(requestInterceptor)
                .build();

        DataService service = restAdapter.create(DataService.class);

        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                Log.e("Object", "" + o);
                Log.e("response", "" + response);
                ((OnPointsCallbackListener)onCustomsCallback).onCallbackListPointsfinished((Points) o);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", "" + error);
                ((OnPointsCallbackListener)onCustomsCallback).onCallbackError();
            }
        };

        service.getPointsJson(callback);
    }

    /**
     * Gets the details.
     *
     * @param id    of the detail
     */
    public void getDetailAsync(String id) {

        OkHttpClient httpClient = new OkHttpClient();

        retrofit.RestAdapter restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(BASE_URL_DATA)
                .setClient(new OkClient(httpClient))
                .setLogLevel(BuildConfig.DEBUG ?
                        retrofit.RestAdapter.LogLevel.FULL :
                        retrofit.RestAdapter.LogLevel.NONE)
                .setRequestInterceptor(requestInterceptor)
                .build();

        DataService service = restAdapter.create(DataService.class);

        Callback callback = new Callback() {
            @Override
            public void success(Object o, Response response) {
                Log.e("Object", "" + o);
                Log.e("response", "" + response);
                ((OnDetailCallbackListener)onCustomsCallback).onCallbackListDetailfinished((Detail) o);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("RetrofitError", "" + error);
                ((OnDetailCallbackListener)onCustomsCallback).onCallbackError();
            }
        };

        service.getDetailJson(id, callback);
    }

}
