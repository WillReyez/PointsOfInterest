package com.apps.poi.models.data;


import org.parceler.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.io.Serializable;

/**
 * A class to represent the point of interest.
 * <p/>
 * Created by Victor Tellez on 29/10/2015.
 */
@Parcel
@Generated("org.jsonschema2pojo")
public class PointPOJO implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("geocoordinates")
    @Expose
    private String geocoordinates;

    public PointPOJO() {
    }

    /**
     * Constructor
     *
     * @param id                index
     * @param title             of the point
     * @param geocoordinates    to locate the point
     */
    public PointPOJO(String id, String title, String geocoordinates) {
        this.id = id;
        this.title = title;
        this.geocoordinates = geocoordinates;
    }

    /**
     * Gets the id of a point
     *
     * @return  the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of a point
     *
     * @param   id the value of the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title of a point
     *
     * @return  the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of a point
     *
     * @param   title the title value
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the geo coordinates of a point
     *
     * @return  the geo coordinates
     */
    public String getGeocoordinates() {
        return geocoordinates;
    }

    /**
     * Sets the geo coordinates of a point
     *
     * @param   geocoordinates coordinates value
     */
    public void setGeocoordinates(String geocoordinates) {
        this.geocoordinates = geocoordinates;
    }

    /**
     * Gets the latitude of a point
     *
     * @return  the latitude
     */
    public double getLatitude() {
        if (geocoordinates == null)
            return 0;
        String[] coordinates = geocoordinates.split(",");
        return new Double(coordinates[0]).doubleValue();
    }

    /**
     * Gets the longitude of a point
     *
     * @return  the longitude
     */
    public double getLongitude() {
        if (geocoordinates == null)
            return 0;
        String[] coordinates = geocoordinates.split(",");
        return new Double(coordinates[1]).doubleValue();
    }
}