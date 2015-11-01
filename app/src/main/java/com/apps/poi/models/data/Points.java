package com.apps.poi.models.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;

/**
 * This class represents a list of points.
 *
 * Created by Victor Tellez on 29/10/2015.
 */
@Generated("org.jsonschema2pojo")
public class Points {

    @SerializedName("list")
    @Expose
    private ArrayList<PointPOJO> list = new ArrayList<PointPOJO>();

    /**
     * Gets a list of points.
     *
     * @return  the arrylist which contains the points
     */
    public ArrayList<PointPOJO> getList() {
        return list;
    }

    /**
     * Sets the list of points.
     *
     * @param list  of points
     */
    public void setList(ArrayList<PointPOJO> list) {
        this.list = list;
    }

}