package com.apps.poi.presenters.splash;

/**
 * This interface provide the methods to manage the view in the splash view.
 * <p/>
 * Created by Victor Tellez on 23/10/2015.
 */
public interface ISplashView {

    /**
     * Shows dialog connection.
     */
    void showDialogConnection();

    /**
     * Informs that the data have been loaded properly.
     */
    void onPointLoadedSuccess();

    /**
     * Informs that some errors happened whe tries to load the data.
     */
    void onPointLoadedError();
}
