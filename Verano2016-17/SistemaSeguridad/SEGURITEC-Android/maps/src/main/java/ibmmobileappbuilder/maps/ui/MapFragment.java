package ibmmobileappbuilder.maps.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.SphericalUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ibmmobileappbuilder.behaviors.Behavior;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.filter.Filter;
import ibmmobileappbuilder.ds.restds.GeoPoint;
import ibmmobileappbuilder.maps.presenters.MapPresenter;
import ibmmobileappbuilder.ui.Filterable;
import ibmmobileappbuilder.ui.Refreshable;

/**
 * A {@link SupportMapFragment} with data binding
 * @param <T> the type of the binded data
 */
public abstract class MapFragment<T> extends SupportMapFragment implements
        OnMapReadyCallback, MapPresenter.MapView<T>, Refreshable, Filterable,
        GoogleMap.OnCameraChangeListener, GoogleMap.OnInfoWindowClickListener {

    static final int MODE_BOX = 0;
    static final int MODE_DISTANCE = 1;

    private final List<GeoPoint> currentItems = new ArrayList<>();

    protected List<Behavior> behaviors = new ArrayList<>();

    protected MapPresenter<T> mPresenter;
    private GoogleMap mMap;
    private GeoPoint mCenter;

    Map<String, T> mappedMarkers = new HashMap<>();
    private LatLngBounds mBounds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);

        this.getMapAsync(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (Behavior b : behaviors) {
            b.onViewCreated(view, savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        for (Behavior behavior : behaviors) {
            behavior.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startPresenting();

        for (Behavior b : behaviors) {
            b.onResume();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        for (Behavior b : behaviors) {
            b.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean managed = false;
        for (Behavior b : behaviors) {
            managed = managed || b.onOptionsItemSelected(item);
        }
        return managed;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Behavior b : behaviors) {
            b.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mPresenter.stopPresenting();

        for (Behavior b : behaviors) {
            b.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (Behavior behavior : behaviors) {
            behavior.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // clear resources
        behaviors.clear();
    }

    /**
     * Adds a {@link Behavior} to this fragment
     *
     * @param behavior the behavior to add to this fragment
     */
    public void addBehavior(Behavior behavior) {
        this.behaviors.add(behavior);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        initMap(map);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        mCenter = new GeoPoint(new double[]{
                cameraPosition.target.longitude, cameraPosition.target.latitude});

        mBounds = mMap.getProjection().getVisibleRegion().latLngBounds;

        if(getQueryMode() == MODE_BOX)
            mPresenter.cameraChange(new GeoPoint(mBounds.southwest.longitude, mBounds.southwest.latitude),
                    new GeoPoint(mBounds.northeast.longitude, mBounds.northeast.latitude));
        else
            mPresenter.cameraChange(mCenter, getDistance());
    }

    @Override
    public void refresh() {
        mMap.clear();
        currentItems.clear();
        mappedMarkers.clear();

        if(getQueryMode() == MODE_BOX)
            mPresenter.queryMapData(new GeoPoint(mBounds.southwest.longitude, mBounds.southwest.latitude),
                    new GeoPoint(mBounds.northeast.longitude, mBounds.northeast.latitude));
        else
            mPresenter.queryMapData(mCenter, getDistance());
    }

    @Override
    public void bindMapData(List<T> data) {
        GoogleMap map = getMap();
        if (map == null) {
            return;
        }

        for(T item : data){
            GeoPoint location = getLocationForItem(item);
            if(location != null && !currentItems.contains(location)){
                // only allow one item for each point

                Marker marker = createAndBindMarker(map, item);

                currentItems.add(location);
                mappedMarkers.put(marker.getId(), item);
            }
        }
    }

    @Override
    public void revealItem(T item) {
        GeoPoint point = getLocationForItem(item);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(point.coordinates[1], point.coordinates[0]), getInitialZoom()));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        T item = mappedMarkers.get(marker.getId());

        navigateToDetail(item);
    }

    protected void initMap(GoogleMap map){
        map.setOnCameraChangeListener(this);
        map.setOnInfoWindowClickListener(this);

        // bind map type
        map.setMapType(getMapType());

        // set initial location and zoom
        LatLng initial = getInitialLatLng();
        if(initial != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(initial, getInitialZoom()));
            mCenter = new GeoPoint(new double[]{initial.longitude, initial.latitude});
            mBounds = map.getProjection().getVisibleRegion().latLngBounds;
        }

        map.setMyLocationEnabled(true);
    }

    protected LatLng getInitialLatLng() {
        return getLastKnownLocation();
    }

    protected float getInitialZoom(){
        return 12f;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_NONE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_TERRAIN})
    public @interface MapType {}
    protected @MapType int getMapType(){
        return GoogleMap.MAP_TYPE_SATELLITE;
    }

    /**
     * override for your own datasource implementations
     * @return
     */
    protected abstract Datasource<T> getDatasource();

    /**
     * get the field name for geolocated searches
     * @return the field name, eg "location"
     */
    protected abstract String getLocationField();

    /**
     * create a marker from an item
     * @param map the map instance
     * @param item the current item
     * @return a marker, as returned by map.addMarker method
     */
    protected abstract Marker createAndBindMarker(GoogleMap map, T item);

    /**
     * Create a geopoint object from an item, used for caching
     * @param item an item
     * @return the Geopoint object derived from this item
     */
    protected abstract GeoPoint getLocationForItem(T item);

    /**
     * search distance in meters
     * @return
     */
    protected long getDistance(){

        // by default, we get the visible bounds
        LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        LatLng center = bounds.getCenter();

        return (long) Math.max(SphericalUtil.computeDistanceBetween(center, bounds.northeast),
                SphericalUtil.computeDistanceBetween(center, bounds.southwest));
    }

    protected MapPresenter<T> getPresenter(){
        return mPresenter;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MapFragment.MODE_BOX,
            MapFragment.MODE_DISTANCE})
    public @interface QueryMode {}
    protected @QueryMode int getQueryMode(){
        return MODE_BOX;
    }

    private LatLng getLastKnownLocation(){

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria cr = new Criteria();
        cr.setAccuracy(Criteria.ACCURACY_COARSE);

        String provider = lm.getBestProvider(cr, true);
        if(provider == null)
            return null;

        Location location = lm.getLastKnownLocation(provider);
		if(location == null)
            return null;
		
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    // filters

    @Override
    public void addFilter(Filter filter) {
        getPresenter().addFilter(filter);
    }

    @Override
    public void clearFilters() {

    }

    @Override
    public void onSearchTextChanged(String s) {
        getPresenter().setTextFilter(s);
        refresh();
    }
}
