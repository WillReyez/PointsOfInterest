package com.apps.poi.presenters.list;

import com.apps.poi.models.data.PointPOJO;

import java.util.ArrayList;

/**
 * This interface contains the methods to know if the data have being loaded correctly or
 * if some error happen.
 *
 * Created by Victor Tellez on 27/10/2015.
 */
public interface IListView {

    /**
     * This is used tho know when the data have been loaded correctly.
     *
     * @param list  of points
     */
    void onPointsLoadedSuccess(ArrayList<PointPOJO> list);

    /**
     * This is used to know when some error happen loading the data.
     */
    void onPointsLoadedFailure();
}
