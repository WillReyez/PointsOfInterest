package com.apps.poi.presenters.map;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.apps.poi.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * This is the info view adapter to set the info view in the Map markers.
 *
 * Created by Victor Tellez on 14/10/2015.
 */
public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    /**
     * Content views.
     */
    private final View myContentsView;

    /**
     * Context of tha application.
     */
    private final Context context;

    /**
     * Constructor
     *
     * @param mContext the context
     */
    public InfoWindowAdapter(Context mContext) {
        this.context = mContext;
        myContentsView = ((Activity) mContext).getLayoutInflater()
                .inflate(R.layout.map_marker_info_window, null);
    }

    /**
     * Handles the get info window for each marker in the google map.
     *
     * @param marker    the marker in the google map.
     * @return          the View of the info window.
     */
    @Override
    public View getInfoWindow(final Marker marker) {

        setInfoViewText(marker);

        return myContentsView;
    }

    /**
     * Sets the text in the info view.
     *
     * @param marker    to get the title
     */
    private void setInfoViewText(Marker marker) {
        TextView titleTextView = ((TextView) myContentsView.findViewById(R.id.title));
        String title = marker.getTitle();
        titleTextView.setText(title);
    }

    /**
     * Handles the get info contents of each marker in the google map.
     *
     * @param marker    the marker in the google map.
     * @return null
     */
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
