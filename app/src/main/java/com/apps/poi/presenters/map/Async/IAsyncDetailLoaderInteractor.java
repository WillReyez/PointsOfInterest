package com.apps.poi.presenters.map.Async;

import android.content.Context;

/**
 * This interface has the methods to manage the model loading.
 *
 * Created by Victor Tellez on 28/10/2015.
 */
public interface IAsyncDetailLoaderInteractor {

    /**
     * Makes the asynchronous detail loading.
     *
     * @param id    of the detail
     */
    void makeDetailAsyncLoader(String id);

    /**
     * Loads the point model from the database.
     *
     * @param context   of the application
     */
    void loadListPointData(Context context);

}
