package com.apps.poi.models.rest;

import com.apps.poi.models.data.Detail;

/**
 * Created by Victor Tellez on 29/10/2015.
 */
public interface OnDetailCallbackListener extends OnCustomCallback{
    void onCallbackListDetailfinished(Detail detail);
}
