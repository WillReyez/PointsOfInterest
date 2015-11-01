package com.apps.poi.presenters.list.Sync;

import android.content.Context;

/**
 * This class loads the model as a list.
 * This list is formed by Points.
 *
 * Created by Victor Tellez on 27/10/2015.
 */
public interface IListInteractor {

    /**
     * Gets the list of Points.
     *
     * @param context   of the application.
     */
    void loadListPointData(Context context);
}
