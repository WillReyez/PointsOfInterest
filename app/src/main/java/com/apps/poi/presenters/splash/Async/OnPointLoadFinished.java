package com.apps.poi.presenters.splash.Async;

/**
 * This interface provide the methods to know when the data have been downloaded with
 * onPointLoadedFinished.
 * If some error happen while the model is being loaded it notifies with onPointLoadedError().
 *
 * Created by Victor Tellez on 23/10/2015.
 */
public interface OnPointLoadFinished {

    /**
     * Triggers an event to notify when the loading of the model has finished.
     */
    void onPointLoadedFinished();

    /**
     * Triggers an event to notify when some error happen trying to load the model.
     */
    void onPointLoadedError();
}
