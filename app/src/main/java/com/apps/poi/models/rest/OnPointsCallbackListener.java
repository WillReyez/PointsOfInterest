package com.apps.poi.models.rest;

import com.apps.poi.models.data.Detail;
import com.apps.poi.models.data.Points;

/**
 * This is class is the listener to know when the points are ready.
 *
 * Created by Victor Tellez on 29/10/2015.
 */
public interface OnPointsCallbackListener extends OnCustomCallback{
    void onCallbackListPointsfinished(Points points);
}
