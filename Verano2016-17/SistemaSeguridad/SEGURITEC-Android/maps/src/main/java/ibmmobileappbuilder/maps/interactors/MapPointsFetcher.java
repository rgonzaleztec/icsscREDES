package ibmmobileappbuilder.maps.interactors;

import java.util.List;

import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.GeoDatasource;
import ibmmobileappbuilder.ds.Pagination;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.restds.AppNowDatasource;
import ibmmobileappbuilder.ds.restds.GeoPoint;
import ibmmobileappbuilder.maps.ds.GeoFilter;

public class MapPointsFetcher<T> {

    private final Datasource<T> datasource;
    private final String field;

    public MapPointsFetcher(Datasource<T> ds, String field) {
        datasource = ds;
        this.field = field;
    }

    public void fetch(GeoPoint sw, GeoPoint nE, Datasource.Listener<List<T>> listener) {
        GeoFilter filter = new GeoFilter(field, sw, nE);

        fetchAddingFilter(listener, filter);
    }

    public void fetch(GeoPoint center, long distance, Datasource.Listener<List<T>> listener) {
        fetchAddingFilter(listener, new GeoFilter(field, center, distance));
    }

    private void fetchAddingFilter(Datasource.Listener<List<T>> listener, GeoFilter filter) {
        if (!(datasource instanceof GeoDatasource)) {
            return;
        }
        datasource.clearFilters();
        datasource.addFilter(filter);

        if (datasource instanceof AppNowDatasource) {
            AppNowDatasource<T> ands = (AppNowDatasource<T>) datasource;
            ands.getItems(listener);
        } else if (datasource instanceof Pagination) {
            Pagination<T> paginatedDs = (Pagination<T>) datasource;
            paginatedDs.getItems(0, listener);
        }

    }
}
