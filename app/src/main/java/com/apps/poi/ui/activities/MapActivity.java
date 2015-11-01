package com.apps.poi.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.poi.R;
import com.apps.poi.models.data.PointPOJO;
import com.apps.poi.models.data.Detail;
import com.apps.poi.presenters.map.IMapView;
import com.apps.poi.presenters.map.InfoWindowAdapter;
import com.apps.poi.presenters.map.MapPresenter;
import com.apps.poi.utils.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This activity shows a google Map with all the points taken from the database and the
 * correspondent details. The toolbar has the item to go to the ListActivity. The map contains
 * markers, each marker will show its details pressing over it. The details are shown in
 * a scrollable panel to allow the user to see of the information of it point.
 * <p/>
 * The detail data are downloaded now to avoid waiting in the Splash activity. If the details
 * aren't ready it shows a Toast informing the user. A Toast will be shown when the details data
 * are ready to be used.
 * <p/>
 * Created by Victor Tellez on 14/10/2015.
 **/
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        IMapView {

    /**
     * Constants to specify the zoom of the map camera.
     */
    private static final int CAMERA_ZOOM_CITY = 14;
    private static final int CAMERA_ZOOM_POINT = 15;

    /**
     * Constants used in the activity result to know if the result is the correct.
     */
    public static final int REQUEST_CODE = 1;
    public static final int REQUEST_SEARCH = 2;

    /**
     * Constants to do a zoom over the barcelona city. This should be taken from the server
     * for each place.
     */
    public static final double BCN_LATITUDE = 41.3833;
    public static final double BCN_LONGITUDE = 2.1833;

    /**
     * Constant to center the market in its location.
     */
    public static final float CENTER_ANCHOR = 0.5f;

    /**
     * Constant to set the anchor position of the detail panel view.
     */
    public static final float ANCHOR_POINT = 0.05f;

    /**
     * Constant used to getting dynamically the text views from the xml.
     */
    public static final int NUMBER_TEXT_VIEWS = 6;

    /**
     * Constant used to saving the state of detail data. To save this state is used the Bundle of this
     * activity.
     */
    public static final String DETAILS_RECEIVED = "detailsReceived";

    /**
     * Constant used to getting the item selected in the list activity and get it in the on activity
     * result.
     */
    public static final String POINT_RESULT = "pointResult";

    /**
     * Constant used to getting the text view dynamically from the xml.
     */
    public static final String DETAIL_INFO = "detail_info";

    /**
     * Toolbar of the screen
     */
    private Toolbar mToolbar;

    /**
     * Google map
     */
    private GoogleMap googleMap;

    /**
     * Map fragment
     */
    private MapFragment mapFragment;

    /**
     * Points and Details list
     */
    ArrayList<PointPOJO> pointList;

    /**
     * Marker dictionary
     */
    private Map<String, Marker> allMarkersMap = new HashMap<String, Marker>();

    /**
     * Sliding Panel Layout
     */
    private SlidingUpPanelLayout slidingUpPanelLayout;

    /**
     * This variable is use to check if the details has being downloaded
     */
    private boolean detailsReceived = false;

    private MapPresenter mapPresenter;

    /**
     * Handles the onCreate activity state. Sets the initial state of the views.
     * Checks if the detail data have being downloaded and notifies the user to active the location.
     *
     * @param savedInstanceState    bundle to save and restore data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        mapPresenter = new MapPresenter(this);
        initViews();
        Utils.checkLocationActiveWithToast(this);
    }

    /**
     * Handles the onCreateOptionsMenu activity state.
     *
     * @param menu  to be inflate
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    /**
     * Handles the on option Item selected to get the on click event in a menu item.
     *
     * @param item  which was clicked
     * @return      if true get the event, false not
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_list) {
            goToListActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles the on map ready. Initialize the listener to manage the map.
     * And call fillMap to set all the points in the map.
     *
     * @param googleMap to show the points
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (googleMap != null) {
            InfoWindowAdapter myInfoWindowAdapter = new InfoWindowAdapter(this);
            googleMap.setInfoWindowAdapter(myInfoWindowAdapter);
            googleMap.setOnMarkerClickListener(this);
            googleMap.setOnMapClickListener(this);
            googleMap.setMyLocationEnabled(true);
            fillMap();
        } else {
            if (Utils.isConnectingToInternet(this)) {
                Toast.makeText(MapActivity.this, getString(R.string.no_connection),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Handles the click on the Map. Hides the detail view.
     *
     * @param latLng    for describe a point with the coordinates
     */
    @Override
    public void onMapClick(LatLng latLng) {
        hideDetailView();
    }

    /**
     * Handles the click on a Marker. Shows the detail view if the detail data is already
     * downloaded.
     *
     * @param marker    which the user male the click
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        String id = getPointIdByTitle(marker.getTitle());
        mapPresenter.getDetailById(id);

        return false;
    }


    /**
     * Handles the on Activity Result. This is used to coming from the List activity to get the
     * point selected by the user and show the info view and the detail view.
     *
     * @param requestCode the request code of the SiteActivity.
     * @param resultCode  the result code of the SiteActivity.
     * @param data        the data from the SiteActivity.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case REQUEST_SEARCH:
                PointPOJO pointResult = (PointPOJO) data.getSerializableExtra(POINT_RESULT);
                if (pointResult != null) {
                    showSiteMarkerOn(pointResult);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Handles the save state of the activity used to saving if the details are ready or not.
     *
     * @param savedInstanceState    the bundle to be saved
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(DETAILS_RECEIVED, detailsReceived);
    }

    /**
     * Handles the on restore activity data using the Bundle. The data restored is the detail data
     * is ready to be used.
     *
     * @param savedInstanceState    the bundle to be restored
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        detailsReceived = savedInstanceState.getBoolean(DETAILS_RECEIVED);
    }

    /**
     * Handles the press on the physical back of the device.
     * If the detail view is expanded it closes, If not it closes the app.
     */
    @Override
    public void onBackPressed() {
        if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            hideDetailView();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Sets the initial state of the views.
     */
    public void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.map_toolbar_actionbar);
        setSupportActionBar(mToolbar);

        if (googleMap == null) {
            mapFragment = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment));
            mapFragment.getMapAsync(this);
        }

        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        slidingUpPanelLayout.setOverlayed(true);
    }


    /**
     * Fills the map using the list of point which is taken from the database and makers. Fill a
     * <key,value> dictionary to have all the the markers with the correspondent point title.
     * <p/>
     * Makes a zoom over the map to see all the points that we are interested.
     */
    public void fillMap() {

        if (pointList != null && pointList.size() > 0) {

            for (PointPOJO pointPOJO : pointList) {

                String nameTemp = pointPOJO.getTitle() != null ? pointPOJO.getTitle() : "";

                MarkerOptions markerOptions = new MarkerOptions()
                        .title(nameTemp)
                        .position(new LatLng(pointPOJO.getLatitude(), pointPOJO.getLongitude()))
                        .anchor(CENTER_ANCHOR, CENTER_ANCHOR);
                markerOptions.title(nameTemp);

                if (googleMap != null) {
                    Marker marker = googleMap.addMarker(markerOptions);
                    allMarkersMap.put(pointPOJO.getTitle(), marker);
                }
            }

            // Zoom over the map
            CameraUpdate cameraCenter = CameraUpdateFactory.newLatLng(new LatLng(BCN_LATITUDE,
                    BCN_LONGITUDE));
            CameraUpdate cameraZoom = CameraUpdateFactory.zoomTo(CAMERA_ZOOM_CITY);
            googleMap.moveCamera(cameraCenter);
            googleMap.moveCamera(cameraZoom);
        }
    }


    /**
     * Shows the detail view of a point selected. If the detail data is no ready shows a Toast
     * informing the user.
     *
     * @param detail    to show in the detail view.
     */
    private void showDetailView(Detail detail) {
        if (detail == null)
            return;

        slidingUpPanelLayout.setAnchorPoint(ANCHOR_POINT);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        setDetailView(detail);
    }

    /**
     * Sets the detail view. The first is look for the Detail knowing that the title is the same
     * and it is stored as a Unique key. Then gets the text views dynamically and sets the info
     * to them.
     *
     * @param detail    of the point selected.
     */
    private void setDetailView(Detail detail) {

        ArrayList<String> detailData = detail.getArrayDataDetails();
        if (detailData == null) {
            return;
        }

        // Gets TexView ids
        int[] textViewsIds = Utils.getTextViewInfo(this, DETAIL_INFO, NUMBER_TEXT_VIEWS);

        for (int i = 0; i < textViewsIds.length; i++) {
            TextView textView = (TextView) findViewById(textViewsIds[i]);
            setTextViewInitialState(textView);
            setTextFromDetail(textView, detailData.get(i));
        }
    }

    /**
     * Gets the point id using the title value
     *
     * @param title to get the Detail
     * @return      the id of the point
     */
    private String getPointIdByTitle(String title) {

        PointPOJO detailAux = null;
        for (PointPOJO point : pointList) {
            if (point.getTitle().equalsIgnoreCase(title)) {
                detailAux = point;
            }
        }

        return detailAux.getId();
    }

    /**
     * Initializes the text view of the detail view.
     *
     * @param textViewInitialState  the text view to be initialize.
     */
    public void setTextViewInitialState(TextView textViewInitialState) {
        if (textViewInitialState == null)
            return;
        textViewInitialState.setText("");
        textViewInitialState.setVisibility(View.VISIBLE);
    }

    /**
     * Sets the text from the detail checking if is not correct.
     *
     * @param textView  to set the text.
     * @param text      to be set in the text view
     */
    private void setTextFromDetail(TextView textView, String text) {
        String finalText = "";
        if (text != null && !text.trim().equals("null") && !text.trim().equals("undefined")) {
            finalText = text.trim();
        }

        // For phone number
        if (finalText.endsWith("?")) {
            finalText = finalText.substring(0, finalText.length() - 1);
        }

        if (finalText.length() > 0) {
            textView.setText(finalText);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    /**
     * Hides the detail view.
     */
    private void hideDetailView() {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    /**
     * Shows the info window marker on the map.
     *
     * @param pointPOJO the pointPOJO data in marker of the map
     */
    private void showSiteMarkerOn(PointPOJO pointPOJO) {

        if(pointPOJO == null || googleMap == null) {
            return;
        }

        CameraUpdate cameraCenter = CameraUpdateFactory.newLatLng(new LatLng(pointPOJO.getLatitude(),
                pointPOJO.getLongitude()));
        CameraUpdate cameraZoom = CameraUpdateFactory.zoomTo(CAMERA_ZOOM_POINT);

        googleMap.moveCamera(cameraCenter);
        googleMap.moveCamera(cameraZoom);

        Marker marker = allMarkersMap.get(pointPOJO.getTitle());
        if (marker != null) {
            marker.showInfoWindow();
        }

        String id = getPointIdByTitle(marker.getTitle());
        mapPresenter.getDetailById(id);
    }

    /**
     * Goes to the List Activity
     */
    private void goToListActivity() {
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onDetailLoadError() {
        Toast.makeText(MapActivity.this, getString(R.string.error_loading_details),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPointLoadFinished(ArrayList<PointPOJO> pointPOJOArrayList) {
        pointList = pointPOJOArrayList;
    }

    @Override
    public void onSingleDetailLoadFinished(Detail detail) {
        showDetailView(detail);
    }

    /**
     * Handles the on resume event and calls the load data of the presenter.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapPresenter.loadPointData();
    }

}
