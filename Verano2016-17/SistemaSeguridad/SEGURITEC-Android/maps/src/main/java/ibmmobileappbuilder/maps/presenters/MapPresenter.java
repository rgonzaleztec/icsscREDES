package ibmmobileappbuilder.maps.presenters;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import ibmmobileappbuilder.ds.restds.GeoPoint;
import ibmmobileappbuilder.maps.interactors.MapPointsFetcher;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;

public class MapPresenter<T> extends BasePresenter implements Datasource.Listener<List<T>>{

    private final Datasource<T> datasource;
    private final SearchOptions searchOptions = new SearchOptions();
    private final MapView<T> mapView;
    private final MapPointsFetcher<T> mapPointsFetcher;

    public static final int MODE_NORMAL = 0;
    public static final int MODE_SEARCH = 1;

    private @MapMode int mode = MODE_NORMAL;

    public MapPresenter(Datasource<T> datasource, String field, MapView<T> view) {
        this.datasource = datasource;
        mapView = view;
        mapPointsFetcher = new MapPointsFetcher<>(datasource, field);
    }

    public void queryMapData(GeoPoint center, long distance) {
        // if we are searching, then deactivate distance query
        long realDistance = searchOptions.getSearchText() != null ? 0 : distance;
        mapPointsFetcher.fetch(center, realDistance, this);

    }

    public void queryMapData(GeoPoint sw, GeoPoint ne) {
        String st = searchOptions.getSearchText();
        mapPointsFetcher.fetch(st != null ? null : sw, st != null ? null : ne, this);
    }

    public void setTextFilter(String textFilter) {
        if (textFilter != null && textFilter.length() > 0) {
            setMode(MODE_SEARCH);
        } else {
            setMode(MODE_NORMAL);
        }
        //For now keeping the searchOptions here just to keep track if we are searching. I still need to understand the requirement to kill it
        searchOptions.setSearchText(textFilter);
        datasource.onSearchTextChanged(textFilter);
    }

    public void cameraChange(GeoPoint mCenter, long distance) {
        if (mode == MODE_NORMAL) {
            queryMapData(mCenter, distance);
        }
    }

    public void cameraChange(GeoPoint sw, GeoPoint ne) {
        if (mode == MODE_NORMAL) {
            queryMapData(sw, ne);
        }
    }

    // Sets a filter that will be applied to all jobs queries
    public void addFilter(Filter filter) {
        datasource.addFilter(filter);
    }

    @Override
    public void onSuccess(List<T> ts) {
        mapView.bindMapData(ts);
        if (mode == MODE_SEARCH && !ts.isEmpty()) {
            mapView.revealItem(ts.get(0));
        }
    }

    @Override
    public void onFailure(Exception e) {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MODE_NORMAL, MODE_SEARCH})
    public @interface MapMode {
    }

    public void setMode(@MapMode int mode) {
        this.mode = mode;
    }

    public interface MapView<T> {
        void bindMapData(List<T> data);

        void navigateToDetail(T item);

        void revealItem(T item);
    }
}
