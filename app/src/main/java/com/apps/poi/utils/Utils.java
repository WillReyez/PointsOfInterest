package com.apps.poi.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.poi.R;
/**
 * This class is uset to grouping all the general functionality which can be used from all the app.
 *
 * Created by Victor Tellez on 13/10/2015.
 */
public class Utils {

    public static final String POI_TAG = "POI";

    /**
     * Checks the connection status. If true the user has connectivity, false in other case.
     *
     * @param context   to use to check the connectivity
     * @return          the status of the connection
     */
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    /**
     * Shows the keyboard and get the focus of a view.
     *
     * @param activityContext   the context
     * @param view              which request the focus and will get the keyboard value
     */
    public static void showKeyBoard(Activity activityContext, View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) activityContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Hides the keyboard from the current focus.
     *
     * @param activityContext   the context
     */
    public static void hideKeyBoard(Activity activityContext) {
        View view = activityContext.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activityContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Converts dps to pixels.
     *
     * @param context   the context
     * @param dp        value to convert to pixels
     */
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    /**
     * Checks if a text is an email valid format
     *
     * @param textView  the Textview to set the format
     * @param target    the text to validate
     * @return          true is email format
     */
    public final static void setValidFormat(TextView textView, CharSequence target) {

        if (textView == null || target == null)
            return;

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()) {
            Linkify.addLinks(textView, Linkify.EMAIL_ADDRESSES);
        } else if(Patterns.PHONE.matcher(target).matches()) {
            Linkify.addLinks(textView, Linkify.PHONE_NUMBERS);
        } else {
            Linkify.addLinks(textView, Linkify.WEB_URLS);
        }
    }

    /**
     * Gets and array of ids of the text views in the xlm. The textviews should have the same name
     * and must be ordered by a correlative index.
     *
     * @param context           of the application
     * @param prefix            of all the textviews
     * @param numberTextViews   to make a loop
     * @return                  id array
     */
    public static int[] getTextViewInfo(Context context, String prefix, int numberTextViews){
        Resources r = context.getResources();
        String name = context.getPackageName();
        int[] ids = new int[numberTextViews];
        for(int i = 0; i < numberTextViews; i++) {
           ids[i] = r.getIdentifier(prefix + i, "id", name);
        }
        return ids;
    }

    /**
     * Shows a dialog to alert that there is no network connection. There are two options
     * Cancel the dialog will close the app and Go to settings will go to the Android settings to
     * activate the connection.
     *
     * @param context   of the current application.
     */
    public static void showAlertNoNetworkConnection(final Context context) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setTitle(context.getString(R.string.no_connection_dialog_title));
        builder.setMessage(context.getString(R.string.no_connection_dialog_text));
        builder.setPositiveButton(context.getString(R.string.no_connection_dialog_go_to_button),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

        builder.setNegativeButton(context.getString(R.string.no_connection_dialog_cancel_button),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        System.exit(0);
                    }
                });

        builder.setCancelable(false);
        final AlertDialog alert = builder.create();

        alert.show();
    }

    /**
     * Check if the location of the device is active.
     *
     * @param context   the context of the app
     * @return          true if is active and false if is not
     */
    public static void checkLocationActiveWithToast (Context context){

        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if (!gps_enabled && !network_enabled) {
            Toast.makeText(context, context.getString(R.string.activate_location), Toast.LENGTH_LONG)
                    .show();
        }
    }

}

