package com.apps.poi.ui.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.poi.R;
import com.apps.poi.models.data.PointPOJO;
import com.apps.poi.presenters.list.IListView;
import com.apps.poi.presenters.list.ListAdapter;
import com.apps.poi.presenters.list.ListPresenter;
import com.apps.poi.presenters.list.SearchHelper;
import com.apps.poi.utils.Utils;

import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * This activity shows a list of points of interest. The activity has a list view to show
 * all the points stored in the database. The user can filter the points writing in the search view.
 * If the user selects a point the list activity is closed and the Map activity shows this point
 * into the map with the info view and the detail view if the detail data is ready.
 * <p/>
 * Created by Victor Tellez on 13/10/2015.
 */
public class ListActivity extends AppCompatActivity implements IListView, AdapterView.OnItemClickListener {

    /**
     * Constant used to getting the list of points in the Bundle.
     */
    private static final String POINTS = "points";

    /**
     * Constant used to getting the list of filtered points in the Bundle.
     */
    private static final String POINTS_FILTERED = "points_filtered";

    /**
     * Constant used to knowing if the search is opened.
     */
    private static final String SEARCH_OPENED = "search_opened";

    /**
     * Constant used to searching from the edit text.
     */
    private static final String SEARCH_QUERY = "search_query";

    /**
     * The list view and its adapter.
     */
    @Bind(R.id.listview)
    public ListView listView;

    /**
     * Toolbar of the screen
     */
    @Bind(R.id.list_toolbar)
    public Toolbar toolbar;

    /**
     * List adapter
     */
    private ListAdapter adapter;

    /**
     * The array list of all te points and the filtered ones.
     */
    private ArrayList<PointPOJO> pointArrayList, pointListFiltered;

    /**
     * Boolean used to know if the search view is opened.
     */
    private boolean searchOpened;

    /**
     * Title to make the query to the list.
     */
    private String searchQuery;

    /**
     * Drawables to set in the search state an in the close search state.
     */
    private Drawable iconOpenSearch;
    private Drawable iconCloseSearch;

    /**
     * The edit text to write and search.
     */
    private EditText searchEditText;

    /**
     * Menu item
     */
    private MenuItem searchAction;

    /**
     * This is the presenter which connects the view with the model data.
     */
    public ListPresenter listPresenter;


    /**
     * Handles the on create state of the activity. Initializes the views and set the data.
     *
     * @param savedInstanceState    the bundle to save and restore the data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
        ButterKnife.bind(this);
        initViews(savedInstanceState);

        listPresenter = new ListPresenter(this);
        listView.setOnItemClickListener(this);
    }

    /**
     * Initializes the view with the Bundle.
     *
     * @param savedState    to get the saved data.
     */
    private void initViews(Bundle savedState) {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Gets the list of points to fill the list view.
        if (savedState == null) {
            pointListFiltered = pointArrayList;
            searchOpened = false;
            searchQuery = "";
        } else {
            pointArrayList = (ArrayList<PointPOJO>) savedState.get(POINTS);
            pointListFiltered = (ArrayList<PointPOJO>) savedState.get(POINTS_FILTERED);
            searchOpened = savedState.getBoolean(SEARCH_OPENED);
            searchQuery = savedState.getString(SEARCH_QUERY);
        }

        // Gets the icons
        iconOpenSearch = getResources()
                .getDrawable(R.drawable.ic_action_search);
        iconCloseSearch = getResources()
                .getDrawable(R.drawable.ic_action_cancel);

        // Sets the list adapter. Fills the adapter with filtered list
        ListAdapter adapter = new ListAdapter(this, pointListFiltered);
        listView.setAdapter(adapter);

        // If the search bar was opened previously, open it on recreate.
        if (searchOpened) {
            openSearchView(searchQuery);
        }
    }

    /**
     * Handles the save state in the Bundle. Save the array on all the points, the array of the
     * filtered points. Also save if the search view is opened and the text for filter.
     *
     * @param outState  bundle to save the data
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(POINTS, pointArrayList);
        outState.putSerializable(POINTS_FILTERED, pointListFiltered);
        outState.putBoolean(SEARCH_OPENED, searchOpened);
        outState.putString(SEARCH_QUERY, searchQuery);
    }

    /**
     * Opens the search view with the text for filter the list.
     *
     * @param queryText to filter
     */
    private void openSearchView(String queryText) {

        // Sets custom view on action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        View customView = getLayoutInflater().inflate(R.layout.search_bar, null);
        actionBar.setCustomView(customView);

        // Searches edit text field setup
        searchEditText = (EditText) actionBar.getCustomView().findViewById(R.id.editableSearch);
        searchEditText.addTextChangedListener(new SearchWatcher());
        searchEditText.setText(queryText);

        // Changes search icon accordingly
        searchAction.setIcon(iconCloseSearch);
        searchOpened = true;

        Utils.showKeyBoard(this, searchEditText);
    }

    /**
     * Closes the search view.
     */
    private void closeSearchBar() {

        Utils.hideKeyBoard(this);

        // Removes custom view
        getSupportActionBar().setDisplayShowCustomEnabled(false);

        // Changes search icon accordingly
        searchAction.setIcon(iconOpenSearch);
        searchOpened = false;
        searchEditText.setText("");
        getListAdapter().update(pointArrayList);
    }

    /**
     * Handles the on item click event in the list view.
     *
     * @param parent        of the view
     * @param view          to be clicked
     * @param position      in the list view
     * @param id            of the item
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PointPOJO pointPOJO = (PointPOJO) listView.getItemAtPosition(position);
        if (pointPOJO != null) {
            goToMapActivity(pointPOJO);
        }
    }

    /**
     * This is used tho know when the data have been loaded correctly.
     *
     * @param list  of points
     */
    @Override
    public void onPointsLoadedSuccess(ArrayList<PointPOJO> list) {
        this.pointArrayList = list;
        adapter = new ListAdapter(ListActivity.this, pointArrayList);
        listView.setAdapter(adapter);
    }

    /**
     * This is used to know when some error happen loading the data.
     */
    @Override
    public void onPointsLoadedFailure() {
        Toast.makeText(this, getString(R.string.error_loading_points), Toast.LENGTH_LONG).show();
    }

    /**
     * Handles the changes in search edit text.
     */
    private class SearchWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence c, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence c, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            searchQuery = searchEditText.getText().toString();
            pointListFiltered = SearchHelper.performSearch(pointArrayList, searchQuery);
            getListAdapter().update(pointListFiltered);
        }
    }

    /**
     * Gets the list adapter
     * @return  list adapter
     */
    private ListAdapter getListAdapter() {
        return (ListAdapter) listView.getAdapter();
    }

    /**
     * Handles the on option item selected. The menu item which was selected.
     *
     * @param item  the selected menu item
     * @return      take the chick event if true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            if (searchOpened) {
                closeSearchBar();
            } else {
                openSearchView(searchQuery);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles the on create option menu. Inflate the menu.
     *
     * @param menu  the menu to inflate
     * @return      if true the menu is inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);

        return true;
    }

    /**
     * Prepares the view of the menu to be used.
     *
     * @param menu  the menu to get the menu item
     * @return      true to be shown
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        searchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Handles the on resume event and calls the load data of the presenter.
     */
    @Override
    protected void onResume() {
        super.onResume();
        listPresenter.loadPointData();
    }

    /**
     * Goes to the MapActivity using the point selected as parameter in the Bundle
     *
     * @param pointResult   point selected
     */
    public void goToMapActivity(PointPOJO pointResult) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MapActivity.POINT_RESULT, pointResult);
        setResult(MapActivity.REQUEST_SEARCH, returnIntent);
        finish();
    }

}
