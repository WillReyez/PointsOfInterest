package com.apps.poi.models.domain;

import com.apps.poi.models.data.Detail;

import java.util.ArrayList;

/**
 * Interface to manage the details in the database
 *
 * Created by Victor Tellez on 13/10/2015.
 */
public interface IDetailListener {
    /**
     * Adds a detail to the database
     *
     * @param detail    to add
     */
    public void addDetail(Detail detail);

    /**
     * Gets all the details in the database
     *
     * @return  array with all the details
     */
    public ArrayList<Detail> getAllDetails();

    /**
     * Gets the number of details in the database
     *
     * @return  detail number
     */
    public int getDetailCount();
}