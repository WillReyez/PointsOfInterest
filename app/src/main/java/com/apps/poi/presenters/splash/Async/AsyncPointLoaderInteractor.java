package com.apps.poi.presenters.splash.Async;

import android.content.Context;

import com.apps.poi.models.data.Points;
import com.apps.poi.models.rest.DataRestAdapter;
import com.apps.poi.models.rest.OnPointsCallbackListener;
import com.apps.poi.models.helper.DBPointHelper;

/**
 * This class is in charge of load the model.
 * The model is loaded from the server asynchronously. It can check if the model is empty too.
 *
 * Created by Victor Tellez on 23/10/2015.
 */
public class AsyncPointLoaderInteractor implements IAsyncPointLoaderInteractor, OnPointsCallbackListener {

    /**
     * PointPOJO database helper
     */
    private DBPointHelper dbPointHelper;

    /**
     * This listener is used to manage the loading of the data.
     */
    private OnPointLoadFinished listener;

    /**
     * Constructor
     *
     * @param context   of the application
     */
    public AsyncPointLoaderInteractor(Context context) {
        this.dbPointHelper = new DBPointHelper(context);
    }

    /**
     * Triggers the async loading from the server.
     *
     * @param context   of the application
     */
    @Override
    public void makePointAsyncLoader(Context context, OnPointLoadFinished listener) {
        this.listener = listener;

        DataRestAdapter restAdapter = new DataRestAdapter(this);
        restAdapter.getPointsAsync();
    }

    /**
     * Checks if the database is empty and returns true in that case and false if is already
     * initialize.
     *
     * @return the value of check the data
     */
    @Override
    public boolean isPointModelEmpty() {
        if (dbPointHelper.getPointCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onCallbackListPointsfinished(Points points) {

        dbPointHelper.addListPoint(points);

        listener.onPointLoadedFinished();
    }

    @Override
    public void onCallbackError() {
        listener.onPointLoadedError();
    }

}
