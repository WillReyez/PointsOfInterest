package com.apps.poi.presenters.list;

import com.apps.poi.models.data.PointPOJO;

import java.util.ArrayList;

/**
 * This interface contains the method to know when the list data have been loaded.
 *
 * Created by Victor Tellez on 27/10/2015.
 */
public interface OnListDataLoadedListener {

    /**
     * This used to know when the data is loaded.
     *
     * @param list  of points
     */
    void onDataLoaded(ArrayList<PointPOJO> list);

    /**
     * This is used to know if some error happen loading the data.
     */
    void onErrorDataLoaded();
}
