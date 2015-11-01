package com.apps.poi.presenters.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apps.poi.R;
import com.apps.poi.models.data.PointPOJO;

import java.util.ArrayList;

/**
 * This is the adapter used in the Lisview to set the data to the item views.
 * <p/>
 * Created by Victor Tellez on 13/10/2015.
 */
public class ListAdapter extends BaseAdapter {

    Context context;
    ArrayList<PointPOJO> listData;

    /**
     * Constructor
     *
     * @param context   the context of the application
     * @param listData  the list to get the data
     */
    public ListAdapter(Context context, ArrayList<PointPOJO> listData) {
        this.context = context;
        this.listData = listData;
    }

    /**
     * Gets the count of data in the list data.
     *
     * @return  data number
     */
    @Override
    public int getCount() {
        if (listData == null)
            return 0;

        return listData.size();
    }

    /**
     * Gets the item by a given position
     *
     * @param position  to get the data
     * @return          the item
     */
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    /**
     * Gets item id, in this case the same position is the id.
     *
     * @param position  of the item to get the id
     * @return          the id of the item
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Updates the list data with the list data filtered.
     *
     * @param pointListFiltered list data filtered
     */
    public void update(ArrayList<PointPOJO> pointListFiltered) {
        listData = pointListFiltered;
        notifyDataSetChanged();
    }

    /**
     * Defines the view of the holder of each item in the list. In this case just a TextView.
     */
    class ViewHolder {
        private TextView titleView;
    }

    /**
     * Gets the view using the position. Inflates the layout of the item in the list view.
     *
     * @param position      position of the item
     * @param view          to inflate the layout
     * @param viewGroup     parent
     * @return              configured view
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.titleView = (TextView) view.findViewById(R.id.pointTile);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        PointPOJO pointPOJO = listData.get(position);
        String pointTitle = pointPOJO.getTitle();

        viewHolder.titleView.setText(pointTitle);
        return view;
    }
}
