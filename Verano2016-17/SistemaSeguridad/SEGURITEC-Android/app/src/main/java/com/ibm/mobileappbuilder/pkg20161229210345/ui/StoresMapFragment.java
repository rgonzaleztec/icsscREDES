package com.ibm.mobileappbuilder.pkg20161229210345.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import ibmmobileappbuilder.actions.StartActivityAction;
import ibmmobileappbuilder.behaviors.SearchBehavior;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.restds.GeoPoint;
import ibmmobileappbuilder.ui.Filterable;
import ibmmobileappbuilder.util.Constants;
import ibmmobileappbuilder.util.StringUtils;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.pkg20161229210345.ds.StoresDSItem;
import com.ibm.mobileappbuilder.pkg20161229210345.ds.StoresDS;
import ibmmobileappbuilder.maps.presenters.MapPresenter;
import ibmmobileappbuilder.behaviors.SearchBehavior;
import ibmmobileappbuilder.behaviors.AnalyticsSearchBehavior;
import static ibmmobileappbuilder.analytics.injector.PageViewBehaviorInjector.pageViewBehavior;

/**
 * "StoresMapFragment" listing
 */
public class StoresMapFragment extends ibmmobileappbuilder.maps.ui.MapFragment<StoresDSItem> {
    private SearchBehavior searchBehavior;
    private Datasource<StoresDSItem> datasource;
    private SearchOptions searchOptions;

    public static StoresMapFragment newInstance(Bundle args){
        StoresMapFragment fr = new StoresMapFragment();
        fr.setArguments(args);

        return fr;
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBehavior(pageViewBehavior("StoresMap"));
        // If searchable behavior
        setHasOptionsMenu(true);
        searchBehavior = new AnalyticsSearchBehavior(this, "StoresDS");

		mPresenter = new MapPresenter<>(getDatasource(), getLocationField(), this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        searchBehavior.onCreateOptionsMenu(menu, inflater);
    }
	  @Override
    protected Datasource<StoresDSItem> getDatasource() {
        if (datasource != null) {
            return datasource;
        }
        searchOptions = SearchOptions.Builder.searchOptions().build();
        datasource = StoresDS.getInstance(searchOptions);
        return datasource;
    }

    @Override
    protected int getMapType() {
        return GoogleMap.MAP_TYPE_TERRAIN;
    }

    @Override
    protected String getLocationField() {
        return "location";
    }

    @Override
    protected Marker createAndBindMarker(GoogleMap map, StoresDSItem item) {
        Marker marker = map.addMarker(new MarkerOptions()
                        .position(
                                new LatLng(getLocationForItem(item).coordinates[1],
                                        getLocationForItem(item).coordinates[0])));
        // Binding
        if (item.store != null){
            marker.setTitle(item.store);
        }
        else {
            marker.setTitle("");
        }
        if (item.rating != null){
            marker.setSnippet(StringUtils.intToString(item.rating) + "★");
        }
        else {
            marker.setSnippet("");
        }
        return marker;
    }


    protected GeoPoint getLocationForItem(StoresDSItem item) {
        return item.location;
    }

    @Override
    public void navigateToDetail(StoresDSItem item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.CONTENT, item);
        new StartActivityAction(StoresMapDetailActivity.class, bundle).execute(getActivity());
    }
}

