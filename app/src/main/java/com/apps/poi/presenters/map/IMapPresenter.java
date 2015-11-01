package com.apps.poi.presenters.map;

/**
 * This interface has the methods to trigger the detail loading from the server.
 *
 * Created by Victor Tellez on 28/10/2015.
 */
public interface IMapPresenter {

    /**
     * Loads the point data from the database.
     */
    void loadPointData();

    /**
     * Gets the detail using the id
     *
     * @param id    of the detail
     */
    void getDetailById(String id);
}
