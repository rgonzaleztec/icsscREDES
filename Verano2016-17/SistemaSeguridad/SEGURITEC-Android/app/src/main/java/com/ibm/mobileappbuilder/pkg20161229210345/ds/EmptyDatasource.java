
package com.ibm.mobileappbuilder.pkg20161229210345.ds;

import ibmmobileappbuilder.ds.Count;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.Distinct;
import ibmmobileappbuilder.ds.Pagination;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import ibmmobileappbuilder.util.FilterUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * "EmptyDatasource" static data source (39313d43-15d5-4346-9846-1b68922fa64b)
 */
public class EmptyDatasource implements Datasource<Item>, Count,
            Pagination<Item>, Distinct {

    private static final int PAGE_SIZE = 20;

    private SearchOptions searchOptions;

    public static EmptyDatasource getInstance(SearchOptions searchOptions){
        return new EmptyDatasource(searchOptions);
    }

    private EmptyDatasource(SearchOptions searchOptions){
        this.searchOptions = searchOptions;
    }

    @Override
    public void getItems(Listener<List<Item>> listener) {
        listener.onSuccess(EmptyDatasourceItems.ITEMS);
    }

    @Override
    public int getPageSize(){
        return PAGE_SIZE;
    }

	@Override
	public void getItem(String id, Listener<Item> listener) {
		final int pos = Integer.parseInt(id);
		if(EmptyDatasourceItems.ITEMS.size() <= pos){
			listener.onSuccess(new Item());
		} else {
			Item dc;
			List<Item> filteredItem = applySearchOptions(EmptyDatasourceItems.ITEMS);
			
			if(filteredItem != null && !filteredItem.isEmpty()) {
				dc = filteredItem.get(0);
			} else {
				dc = EmptyDatasourceItems.ITEMS.get(pos);
			}
			
			if( dc != null)
				listener.onSuccess(dc);
			else
				listener.onFailure(new IllegalArgumentException("Item not found: " + pos));
		}
	}

    @Override public int getCount(){
        return EmptyDatasourceItems.ITEMS.size();
    }

    @Override
    public void getItems(int pagenum, Listener<List<Item>> listener) {
        int first = pagenum * PAGE_SIZE;
        int last = first + PAGE_SIZE;
        ArrayList<Item> result = new ArrayList<Item>();
        List<Item> filteredList = applySearchOptions(EmptyDatasourceItems.ITEMS);
        if(first < filteredList.size())
            for (int i = first; (i < last) && (i < filteredList.size()); i++)
                result.add(filteredList.get(i));

        listener.onSuccess(result);
    }

    @Override
    public void onSearchTextChanged(String s){
        searchOptions.setSearchText(s);
    }

    @Override
    public void addFilter(Filter filter){
        searchOptions.addFilter(filter);
    }

    @Override
    public void clearFilters() {
        searchOptions.setFilters(null);
    }

    private List<Item> applySearchOptions(List<Item> result) {
        List<Item> filteredList = result;

        //Searching options
        String searchText = searchOptions.getSearchText();

        if(searchOptions.getFixedFilters() != null)
            filteredList = applyFilters(filteredList, searchOptions.getFixedFilters());

        if(searchOptions.getFilters() != null)
            filteredList = applyFilters(filteredList, searchOptions.getFilters());

        if (searchText != null && !"".equals(searchText))
            filteredList = applySearch(filteredList, searchText);

        //Sorting options
        Comparator comparator = searchOptions.getSortComparator();
        if (comparator != null) {
            if (searchOptions.isSortAscending()) {
                Collections.sort(filteredList, comparator);
            } else {
                Collections.sort(filteredList, Collections.reverseOrder(comparator));
            }
        }

        return filteredList;
    }

    private List<Item> applySearch(List<Item> items, String searchText) {
        List<Item> filteredList = new ArrayList<>();

        for (Item item : items) {
                        
            if (FilterUtils.searchInString(item.id, searchText))
            {
                filteredList.add(item);
            }
        }

        return filteredList;

    }

    private List<Item> applyFilters(List<Item> items, List<Filter> filters) {
        List<Item> filteredList = new ArrayList<>();

        for (Item item : items) {
            if (
                FilterUtils.applyFilters("id", item.id, filters)
                ){

                filteredList.add(item);
            }
        }

        return filteredList;
    }

    // Distinct interface

    @Override
    public void getUniqueValuesFor(String columnName, Listener<List<String>> listener) {
        List<Item> filteredList = applySearchOptions(EmptyDatasourceItems.ITEMS);
        listener.onSuccess(mapItems(filteredList, columnName));
    }

    private List<String> mapItems(List<Item> items, String columnName){
        // return only unique values
        ArrayList<String> res = new ArrayList();
        for (Item item: items){
            String mapped = mapItem(item, columnName);
            if(mapped != null && !res.contains(mapped))
                res.add(mapped);
        }

        return res;
    }

    private String mapItem(Item item, String columnName){
        // get fields
        switch (columnName){
                        
            case "id":
                return item.id;
            default:
               return null;
        }
    }
}

