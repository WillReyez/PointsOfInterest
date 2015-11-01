package com.apps.poi.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.apps.poi.R;
import com.apps.poi.presenters.splash.ISplashView;
import com.apps.poi.presenters.splash.SplashPresenter;
import com.apps.poi.utils.Utils;

/**
 * This activity shows the loading screen while the presenter is loading the model data.
 * <p/>
 * Created by Victor Tellez on 13/10/2015.
 */
public class SplashActivity extends Activity implements ISplashView {

    /**
     * This is the presenter which connects the view with the model data.
     */
    private SplashPresenter splashPresenter;

    /**
     * Handles the onCreate state of the activity.
     *
     * @param savedInstanceState    bundle to save data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        splashPresenter = new SplashPresenter(this);
        splashPresenter.attemptDownloadPoints();
    }

    /**
     * Shows dialog connection.
     */
    @Override
    public void showDialogConnection() {
        Utils.showAlertNoNetworkConnection(this);
    }

    /**
     * Informs that the data have been loaded properly and shows the next actvity.
     */
    @Override
    public void onPointLoadedSuccess() {
        goToMapActivity();
    }

    /**
     * Informs that some errors happened whe tries to load the data.
     */
    @Override
    public void onPointLoadedError() {
        Toast.makeText(this, getString(R.string.error_downloading_points), Toast.LENGTH_LONG).show();
    }

    /**
     * Goes to the map activity.
     */
    private void goToMapActivity() {
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(intent);
        finish();
    }

}
