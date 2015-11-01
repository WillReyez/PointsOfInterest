package com.apps.poi.presenters.map;

import com.apps.poi.models.data.PointPOJO;
import com.apps.poi.models.data.Detail;

import java.util.ArrayList;

/**
 * This interface has the methods to notify to the view when the data is ready or if any error
 * happen.
 *
 * Created by Victor Tellez on 28/10/2015.
 */
public interface IMapView {

    /**
     * Notifies and error while loading the detail data.
     */
    void onDetailLoadError();

    /**
     * Notifies the point model loading is finished.
     *
     * @param pointPOJOArrayList    the point list
     */
    void onPointLoadFinished(ArrayList<PointPOJO> pointPOJOArrayList);

    /**
     * Notifies the detail loading is finished.
     *
     * @param detail    to show
     */
    void onSingleDetailLoadFinished(Detail detail);

}
