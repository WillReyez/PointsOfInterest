package com.apps.poi.models.data;

import org.parceler.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class to represent the detail of a point of interest.
 * <p/>
 * Created by Victor Tellez on 29/10/2015.
 */
@Generated("org.jsonschema2pojo")
public class Detail implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("transport")
    @Expose
    private String transport;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("geocoordinates")
    @Expose
    private String geocoordinates;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("phone")
    @Expose
    private String phone;

    /**
     * Constructor
     */
    public Detail() {
    }

    /**
     * Constructor
     *
     * @param id             index
     * @param title          of detail
     * @param geocoordinates to locate the detail
     * @param description    the description
     * @param transport      to use
     * @param email          to contact the POI
     * @param phone          to call the POI
     */
    public Detail(String id, String title, String address, String transport, String email, String geocoordinates, String description,  String phone) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.transport = transport;
        this.email = email;
        this.geocoordinates = geocoordinates;
        this.description = description;
        this.phone = phone;
    }


    /**
     * Gets the id of a detail
     *
     * @return  the index
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of a detail
     *
     * @param id    the index value
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title of a detail
     *
     * @return  the value of the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of a detail
     *
     * @param title the title value
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the address of the detail
     *
     * @return  the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the detail
     *
     * @param address   value of address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the transport to go to the detail
     *
     * @return  the transport value
     */
    public String getTransport() {
        return transport;
    }

    /**
     * Sets the transport to go to the detail
     *
     * @param transport value of the transport
     */
    public void setTransport(String transport) {
        this.transport = transport;
    }

    /**
     * Gets the email of a detail
     *
     * @return  value of the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of a detail
     *
     * @param email value of the detail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the coordinates of a detail
     *
     * @return  the coordinates value
     */
    public String getGeocoordinates() {
        return geocoordinates;
    }

    /**
     * Sets the geo coordinates of a detail
     *
     * @param geocoordinates    the coorditates value
     */
    public void setGeocoordinates(String geocoordinates) {
        this.geocoordinates = geocoordinates;
    }

    /**
     * Gets the description of a detail
     *
     * @return  the description value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the detail
     *
     * @param description   value of the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the phone number of a detail
     *
     * @return  value of the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone of a detail
     *
     * @param phone value of the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the latitude of a detail
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
     * Gets the longitude of a detail
     *
     * @return  the longitude
     */
    public double getLongitude() {
        if (geocoordinates == null)
            return 0;
        String[] coordinates = geocoordinates.split(",");
        return new Double(coordinates[1]).doubleValue();
    }

    /**
     * Gets the data detail in an array
     *
     * @return  data details
     */
    public ArrayList<String> getArrayDataDetails(){
        ArrayList<String> arrayDataDetails = new ArrayList<>();
        arrayDataDetails.add(0, getTitle());
        arrayDataDetails.add(1, getAddress());
        arrayDataDetails.add(2, getTransport());
        arrayDataDetails.add(3, getDescription());
        arrayDataDetails.add(4, getEmail());
        arrayDataDetails.add(5, getPhone());

        return arrayDataDetails;
    }

}