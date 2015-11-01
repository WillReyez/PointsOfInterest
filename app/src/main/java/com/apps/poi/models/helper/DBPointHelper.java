package com.apps.poi.models.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.apps.poi.models.data.PointPOJO;
import com.apps.poi.models.data.Points;
import com.apps.poi.models.domain.IPointListener;
import com.apps.poi.utils.Utils;

import java.util.ArrayList;

/**
 * A helper class to manage database creation and version management of Points.
 * <p/>
 * Created by Victor Tellez on 13/10/2015.
 */
public class DBPointHelper extends SQLiteOpenHelper implements IPointListener {

    /**
     * Constants
     */
    private static final int DB_VERSION = 1;
    public static final String DB_POINTS_NAME = "PointDatabase.db";
    private static final String TABLE_NAME = "point_table";
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "_title";
    private static final String KEY_COORDINATES = "_geocoordinates";

    /**
     * Sentence to create the table
     */
    String CREATE_POINT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT UNIQUE," + KEY_COORDINATES + " TEXT)";
    String DROP_POINT_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Constructor
     * @param context of the application
     */
    public DBPointHelper(Context context) {
        super(context, DB_POINTS_NAME, null, DB_VERSION);
    }

    /**
     * Handles the on create of the sql lite helper.
     *
     * @param db    database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_POINT_TABLE);
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
        db.execSQL(DROP_POINT_TABLE);
        onCreate(db);
    }

    /**
     * Adds a detail to the database.
     * IPointListener interface
     *
     * @param pointPOJO    to add
     */
    @Override
    public void addPoint(PointPOJO pointPOJO) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TITLE, pointPOJO.getTitle());
            values.put(KEY_COORDINATES, pointPOJO.getGeocoordinates());
            db.insert(TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            Log.e(Utils.POI_TAG, " Exception :" + e);
        }
    }

    /**
     * Gets all the points of the database
     * IPointListener interface
     *
     * @return  the array of all the points
     */
    @Override
    public ArrayList<PointPOJO> getAllPoints() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<PointPOJO> pointList = null;
        try {
            pointList = new ArrayList<PointPOJO>();
            String QUERY = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if (!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    PointPOJO pointPOJO = new PointPOJO();
                    pointPOJO.setId(cursor.getString(0));
                    pointPOJO.setTitle(cursor.getString(1));
                    pointPOJO.setGeocoordinates(cursor.getString(2));
                    pointList.add(pointPOJO);
                }
            }
            db.close();
        } catch (Exception e) {
            Log.e(Utils.POI_TAG, " Exception :" + e);
        }
        return pointList;
    }

    /**
     * Gets the number of the points in the database
     * IPointListener interface
     *
     * @return point number
     */
    @Override
    public int getPointCount() {
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

    /**
     * Gets an array with all the ids of the points database
     *
     * @return  the ids in an array
     */
    public int[] getIds() {
        SQLiteDatabase db = this.getReadableDatabase();
        int[] ids = null;
        try {
            String QUERY = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            ids = new int[cursor.getCount()];

            if (!cursor.isLast()) {
                int i = 0;
                while (cursor.moveToNext()) {
                    ids[i] = cursor.getInt(0);
                    i++;
                }
            }
            db.close();
        } catch (Exception e) {
            Log.e(Utils.POI_TAG, " Exception :" + e);
        }
        return ids;
    }

    /**
     * Adds a detail to the database.
     * IPointListener interface
     *
     * @param points    list to be added
     */
    public void addListPoint(final Points points) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ArrayList<com.apps.poi.models.data.PointPOJO> listPointPOJOs = points.getList();
            for (com.apps.poi.models.data.PointPOJO pointPOJO : listPointPOJOs) {
                ContentValues values = new ContentValues();
                values.put(KEY_TITLE, pointPOJO.getTitle());
                values.put(KEY_COORDINATES, pointPOJO.getGeocoordinates());
                db.insert(TABLE_NAME, null, values);
            }
        } catch (Exception e) {
            db.close();
            Log.e(Utils.POI_TAG, " Exception :" + e);
        }
        db.close();
    }
}