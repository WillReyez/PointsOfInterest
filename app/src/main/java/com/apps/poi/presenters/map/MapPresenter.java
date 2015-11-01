package com.apps.poi.presenters.map;

import android.content.Context;

import com.apps.poi.models.data.PointPOJO;
import com.apps.poi.models.helper.DBDetailHelper;
import com.apps.poi.models.helper.DBPointHelper;
import com.apps.poi.models.data.Detail;
import com.apps.poi.presenters.map.Async.AsyncDetailLoaderInteractor;
import com.apps.poi.presenters.map.Async.OnDataLoadFinishedListener;
import java.util.ArrayList;

/**
 * This class retrieves data from the model and formats it for display in the view.
 * <p/>
 * Created by Victor Tellez on 28/10/2015.
 */
public class MapPresenter implements IMapPresenter , OnDataLoadFinishedListener {

    /**
     * The view.
     */
    private IMapView view;

    /**
     * Interactor to manage the model.
     */
    private AsyncDetailLoaderInteractor interactor;

    /**
     * Constructor
     *
     * @param view  the view to set the data
     */
    public MapPresenter(IMapView view) {
        this.view = view;

        interactor = new AsyncDetailLoaderInteractor((Context)view,
                this,
                new DBPointHelper((Context)view),
                new DBDetailHelper((Context)view));
    }

    /**
     * Triggers an event to notify when some error happen trying to load the model.
     */
    @Override
    public void onDetailLoadedError() {
        view.onDetailLoadError();
    }

    /**
     * Notifies the point model loading finished.
     *
     * @param pointPOJOArrayList    the point list
     */
    @Override
    public void onPointLoadFinished(ArrayList<PointPOJO> pointPOJOArrayList) {
        view.onPointLoadFinished(pointPOJOArrayList);
    }

    @Override
    public void onSingleDetailLoadedFinished(Detail detail) {
        view.onSingleDetailLoadFinished(detail);
    }

    /**
     * Loads the point data from the database.
     */
    @Override
    public void loadPointData() {
        interactor.loadListPointData((Context) view);
    }

    @Override
    public void getDetailById(String id) {
        interactor.makeDetailAsyncLoader(id);
    }
}
