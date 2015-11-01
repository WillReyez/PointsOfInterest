package com.apps.poi.presenters.splash.Async;

import android.content.Context;

/**
 * This interface is used to make the operations asynchronous.
 *
 * Created by Victor Tellez on 23/10/2015.
 */
public interface IAsyncPointLoaderInteractor {

    /**
     * Triggers the async loading from the server.
     *
     * @param context   of the application
     */
    void makePointAsyncLoader(Context context, OnPointLoadFinished listener);

    /**
     * Checks if the database is empty and returns true in that case and false if is already
     * initialize.
     *
     * @return the value of check the data
     */
    boolean isPointModelEmpty();
}
