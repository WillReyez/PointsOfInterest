package com.apps.poi.presenters.map.Async;

import android.content.Context;

import com.apps.poi.models.data.PointPOJO;
import com.apps.poi.models.helper.DBDetailHelper;
import com.apps.poi.models.helper.DBPointHelper;
import com.apps.poi.models.data.Detail;
import com.apps.poi.models.rest.DataRestAdapter;
import com.apps.poi.models.rest.OnDetailCallbackListener;

import java.util.ArrayList;

/**
 * This class is used to trigger the asynchronous detail loader of the model.
 *
 * Created by Victor Tellez on 28/10/2015.
 */
public class AsyncDetailLoaderInteractor implements IAsyncDetailLoaderInteractor, OnDetailCallbackListener {

    /**
     * Data model helpers.
     */
    private DBDetailHelper dbDetailHelper;
    private DBPointHelper dbPointHelper;

    /**
     * This listener is used to know when the data have been loaded.
     */
    private OnDataLoadFinishedListener onDataLoadFinishedListener;

    /**
     * Context of the application.
     */
    private Context context;

    /**
     * Constructor
     *
     * @param context                       of the application
     * @param onDataLoadFinishedListener    is the listener to manage the event for loading the model
     * @param dbPointHelper                 point database helper
     * @param dbDetailHelper                detail database helper
     */
    public AsyncDetailLoaderInteractor(Context context,
                                       OnDataLoadFinishedListener onDataLoadFinishedListener,
                                       DBPointHelper dbPointHelper,
                                       DBDetailHelper dbDetailHelper) {
        this.context = context;
        this.onDataLoadFinishedListener = onDataLoadFinishedListener;
        this.dbPointHelper = dbPointHelper;
        this.dbDetailHelper = dbDetailHelper;
    }

    /**
     * Makes the asynchronous detail loading.
     */
    @Override
    public void makeDetailAsyncLoader(String id) {

        DataRestAdapter restAdapter = new DataRestAdapter(this);
        restAdapter.getDetailAsync(id);
    }

    /**
     * Loads the point model from the database.
     *
     * @param context   of the application
     */
    @Override
    public void loadListPointData(Context context) {
        dbPointHelper = new DBPointHelper(context);

        ArrayList<PointPOJO> pointPOJOArrayList = null;
        if (dbPointHelper.getPointCount() != 0) {
            pointPOJOArrayList = dbPointHelper.getAllPoints();
            onDataLoadFinishedListener.onPointLoadFinished(pointPOJOArrayList);

        } else {
            onDataLoadFinishedListener.onPointLoadFinished(new ArrayList<PointPOJO>());
        }
    }

    @Override
    public void onCallbackListDetailfinished(Detail detail) {
        onDataLoadFinishedListener.onSingleDetailLoadedFinished(detail);
    }

    @Override
    public void onCallbackError() {
        onDataLoadFinishedListener.onDetailLoadedError();
    }

}
