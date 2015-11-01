package com.apps.poi.models.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.apps.poi.models.data.Detail;
import com.apps.poi.models.domain.IDetailListener;
import com.apps.poi.utils.Utils;

import java.util.ArrayList;

/**
 * A helper class to manage database creation and version management for the Details.
 * <p/>
 * Created by Victor Tellez on 15/10/2015.
 */
public class DBDetailHelper extends SQLiteOpenHelper implements IDetailListener {

    /**
     * Constants
     */
    private static final int DB_VERSION = 1;
    public static final String DB_POINTS_DETAIL_NAME = "PointDetailDatabase.db";
    private static final String TABLE_NAME = "detail_table";
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "_title";
    private static final String KEY_ADDRESS = "_address";
    private static final String KEY_TRANSPORT = "_transport";
    private static final String KEY_EMAIL = "_email";
    private static final String KEY_COORDINATES = "_geocoordinates";
    private static final String KEY_DESCRIPTION = "_description";
    private static final String KEY_PHONE = "_phone";

    /**
     * Sentence to create the table
     */
    String CREATE_DETAIL_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT UNIQUE," + KEY_ADDRESS +  " TEXT,"
            + KEY_TRANSPORT + " TEXT," + KEY_EMAIL + " TEXT," + KEY_COORDINATES + " TEXT," + KEY_DESCRIPTION  + " TEXT," + KEY_PHONE +" TEXT)";
    String DROP_DETAIL_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Constructor
     *
     * @param context   of the aplication
     */
    public DBDetailHelper(Context context) {
        super(context, DB_POINTS_DETAIL_NAME, null, DB_VERSION);
    }

    /**
     * Handles the on create of the sql lite helper.
     *
     * @param db    database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DETAIL_TABLE);
    }

    /**
     * Handles the on upgrade of the sql lite helper.
     *
     * @param db            database
     * @param oldVersion    of the old database
     * @param newVersion    of the new databases
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_DETAIL_TABLE);
        onCreate(db);
    }

    /**
     * Adds a detail to the database.
     * IDetailListener interface
     *
     * @param detail    to add
     */
    @Override
    public void addDetail(Detail detail) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, detail.getTitle());
            values.put(KEY_ADDRESS, detail.getAddress());
            values.put(KEY_TRANSPORT, detail.getTransport());
            values.put(KEY_EMAIL, detail.getEmail());
            values.put(KEY_EMAIL, detail.getEmail());
            values.put(KEY_COORDINATES, detail.getGeocoordinates());
            values.put(KEY_DESCRIPTION, detail.getDescription());
            values.put(KEY_PHONE, detail.getPhone());
            db.insert(TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            Log.e(Utils.POI_TAG, " Exception :" + e);
        }
    }

    /**
     * Gets all the details in the database.
     * IDetailListener interface
     *
     * @return  array with all the details
     */
    @Override
    public ArrayList<Detail> getAllDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Detail> detailList = null;
        try {
            detailList = new ArrayList<Detail>();
            String QUERY = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    Detail detail = new Detail();
                    detail.setId(""+cursor.getInt(0));
                    detail.setTitle(cursor.getString(1));
                    detail.setAddress(cursor.getString(2));
                    detail.setTransport(cursor.getString(3));
                    detail.setEmail(cursor.getString(4));
                    detail.setGeocoordinates(cursor.getString(5));
                    detail.setDescription(cursor.getString(6));
                    detail.setPhone(cursor.getString(7));
                    detailList.add(detail);
                }
            }
            db.close();
        } catch (Exception e) {
            Log.e(Utils.POI_TAG, " Exception :" + e);
        }
        return detailList;
    }

    /**
     * Gets the number of details in the database.
     * IDetailListener interface
     *
     * @return  detail number
     */
    @Override
    public int getDetailCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String QUERY = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            db.close();
            return num;
        } catch (Exception e) {
            Log.e(Utils.POI_TAG, " Exception :" + e);
        }
        return 0;
    }

}