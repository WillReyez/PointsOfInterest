package com.apps.poi.presenters.map.Async;

import com.apps.poi.models.data.PointPOJO;
import com.apps.poi.models.data.Detail;

import java.util.ArrayList;

/**
 * This interface provide the methods to know when the data have been downloaded with
 * onDetailLoadedFinished.
 * If some error happen while the model is being loaded it notifies with onDetailLoadedError().
 *
 * Created by Victor Tellez on 23/10/2015.
 */
public interface OnDataLoadFinishedListener {

    /**
     * Triggers an event to notify when some error happen trying to load the model.
     */
    void onDetailLoadedError();

    /**
     * Notifies the point model loading finished.
     *
     * @param pointPOJOArrayList    the point list
     */
    void onPointLoadFinished(ArrayList<PointPOJO> pointPOJOArrayList);

    /**
     * Triggers an event to notify when the loading of the detail finished.
     *
     * @param detail    the detail to show
     */
    void onSingleDetailLoadedFinished(Detail detail);
}
