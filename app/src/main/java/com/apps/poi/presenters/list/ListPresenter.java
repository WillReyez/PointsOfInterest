package com.apps.poi.presenters.list;

import android.content.Context;

import com.apps.poi.models.data.PointPOJO;
import com.apps.poi.presenters.list.Sync.ListInteractor;

import java.util.ArrayList;

/**
 * This class retrieves data from the model and formats it for display in the view.
 * <p/>
 * Created by Victor Tellez on 27/10/2015.
 */
public class ListPresenter implements IListPresenter, OnListDataLoadedListener {

    /**
     * The view to be shown.
     */
    private IListView view;

    /**
     * The interactor to manage the model.
     */
    private ListInteractor listInteractor;

    /**
     * Constructor
     *
     * @param listView  to be shown
     */
    public ListPresenter(IListView listView) {
        this.view = listView;
        this.listInteractor = new ListInteractor(this);
    }

    /**
     * Loads the point data.
     */
    @Override
    public void loadPointData() {
        listInteractor.loadListPointData((Context) view);
    }

    /**
     * This used to know when the data is loaded.
     *
     * @param list  of points
     */
    @Override
    public void onDataLoaded(ArrayList<PointPOJO> list) {
        view.onPointsLoadedSuccess(list);
    }

    /**
     * This is used to know if some error happen loading the data.
     */
    @Override
    public void onErrorDataLoaded() {
        view.onPointsLoadedFailure();
    }
}
