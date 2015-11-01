package com.apps.poi.presenters.splash;

import android.content.Context;

import com.apps.poi.presenters.splash.Async.AsyncPointLoaderInteractor;
import com.apps.poi.presenters.splash.Async.OnPointLoadFinished;
import com.apps.poi.utils.Utils;

/**
 * This class retrieves data from the model and formats it for display in the view.
 * <p/>
 * Created by Victor Tellez on 23/10/2015.
 */
public class SplashPresenter implements ISplashPresenter, OnPointLoadFinished {

    /**
     * The view of the application.
     */
    private ISplashView iSplashView;

    /**
     * The interactor to load the model data.
     */
    private AsyncPointLoaderInteractor interactor;

    /**
     * Constructor
     *
     * @param iSplashView is the view to be shown
     */
    public SplashPresenter(ISplashView iSplashView) {
        this.iSplashView = iSplashView;
        interactor = new AsyncPointLoaderInteractor((Context) iSplashView);
    }

    /**
     * Triggers an event to notify when the loading of the model has finished.
     */
    @Override
    public void onPointLoadedFinished() {
        iSplashView.onPointLoadedSuccess();
    }

    /**
     * Triggers an event to notify when some error happen trying to load the model.
     */
    @Override
    public void onPointLoadedError() {
        iSplashView.onPointLoadedError();
    }

    /**
     * Attempts the process to load the model.
     * First checks if the data base is empty which means that we should load the model,
     * in that case checks if the connections are available. If there is not connection
     * stopped the flow and shows a dialog to active the connection, once the connection is ready
     * goes to the process to load the model.
     * If the model is not empty goes to the next view directly.
     */
    @Override
    public void attemptDownloadPoints() {
        if (interactor.isPointModelEmpty()) {

            if (Utils.isConnectingToInternet((Context) iSplashView)) {
                interactor.makePointAsyncLoader((Context) iSplashView, this);
            } else {
                iSplashView.showDialogConnection();

                Thread t = new Thread() {
                    @Override
                    public void run() {
                        try {
                            //check if connected!
                            while (!Utils.isConnectingToInternet((Context) iSplashView)) {
                                //Wait to connect
                                Thread.sleep(1000);
                            }
                            interactor.makePointAsyncLoader((Context) iSplashView, SplashPresenter.this);

                        } catch (Exception e) {
                        }
                    }
                };
                t.start();
            }
        } else {
            iSplashView.onPointLoadedSuccess();
        }
    }
}
