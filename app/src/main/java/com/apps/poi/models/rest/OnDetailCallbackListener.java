package com.apps.poi.models.rest;

import com.apps.poi.models.data.Detail;

/**
 * This is class is the listener to know when the detail is ready.
 *
 * Created by Victor Tellez on 29/10/2015.
 */
public interface OnDetailCallbackListener extends OnCustomCallback{
    void onCallbackDetailfinished(Detail detail);
}
