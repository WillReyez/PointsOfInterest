package com.apps.poi.models.domain;

import com.apps.poi.models.data.PointPOJO;

import java.util.ArrayList;

/**
 * Interface to manage the points in the database
 *
 * Created by Victor Tellez on 13/10/2015.
 */
public interface IPointListener {
    /**
     * Adds a pointPOJO to the database
     *
     * @param   pointPOJO to add
     */
    public void addPoint(PointPOJO pointPOJO);

    /**
     * Gets all the points of the database
     *
     * @return  the array of all the points
     */
    public ArrayList<PointPOJO> getAllPoints();

    /**
     * Gets the number of the points in the database
     *
     * @return point number
     */
    public int getPointCount();
}