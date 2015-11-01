package com.apps.poi.models.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;

/**
 * Created by Victor Tellez on 29/10/2015.
 */
@Generated("org.jsonschema2pojo")
public class Points {

    @SerializedName("list")
    @Expose
    private ArrayList<PointPOJO> list = new ArrayList<PointPOJO>();

    /**
     *
     * @return
     * The list
     */
    public ArrayList<PointPOJO> getList() {
        return list;
    }

    /**
     *
     * @param list
     * The list
     */
    public void setList(ArrayList<PointPOJO> list) {
        this.list = list;
    }

}