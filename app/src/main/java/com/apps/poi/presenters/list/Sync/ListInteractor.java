package com.apps.poi.presenters.list.Sync;

import android.content.Context;

import com.apps.poi.models.data.PointPOJO;
import com.apps.poi.models.helper.DBPointHelper;
import com.apps.poi.presenters.list.OnListDataLoadedListener;
import com.apps.poi.presenters.list.Sync.IListInteractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class is used to manage the model.
 *
 * Created by Victor Tellez on 27/10/2015.
 */
public class ListInteractor implements IListInteractor {

    /**
     * PointPOJO database helper
     */
    private DBPointHelper dbPointHelper;

    /**
     * The array list of all te points and the filtered ones.
     */
    private ArrayList<PointPOJO> pointPOJOArrayList;

    /**
     * This listener is used to know when the list data has been loaded.
     */
    private OnListDataLoadedListener onListDataLoadedListener;

    public ListInteractor(OnListDataLoadedListener onListDataLoadedListener) {
        this.onListDataLoadedListener = onListDataLoadedListener;
    }

    /**
     * Loads the list of Points.
     */
    @Override
    public void loadListPointData(Context context) {
        dbPointHelper = new DBPointHelper(context);

        if (dbPointHelper.getPointCount() != 0) {
            pointPOJOArrayList = dbPointHelper.getAllPoints();
            sortList(pointPOJOArrayList);
            onListDataLoadedListener.onDataLoaded(pointPOJOArrayList);

        } else {
            onListDataLoadedListener.onDataLoaded(new ArrayList<PointPOJO>());
        }
    }

    /**
     * Sorts the ArrayList<PointPOJO> using IgnoreCase.
     *
     * @param list  of points
     */
    public void sortList(ArrayList<PointPOJO> list) {
        Collections.sort(pointPOJOArrayList, new Comparator<PointPOJO>() {
            public int compare(PointPOJO pointPOJO1, PointPOJO pointPOJO2) {
                return pointPOJO1.getTitle().compareToIgnoreCase(pointPOJO2.getTitle());
            }
        });
    }
}
