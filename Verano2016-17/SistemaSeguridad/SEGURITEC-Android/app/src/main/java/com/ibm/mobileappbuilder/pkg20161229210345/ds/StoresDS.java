

package com.ibm.mobileappbuilder.pkg20161229210345.ds;

import android.content.Context;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.restds.AppNowDatasource;
import ibmmobileappbuilder.util.StringUtils;
import ibmmobileappbuilder.ds.restds.TypedByteArrayUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ibmmobileappbuilder.analytics.injector.NetworkLoggerInjector;
import ibmmobileappbuilder.analytics.injector.RetrofitResponseNetworkLoggerInjector;

/**
 * "StoresDS" data source. (e37eb8dc-6eb2-4635-8592-5eb9696050e3)
 */
public class StoresDS extends AppNowDatasource<StoresDSItem>{

    // default page size
    private static final int PAGE_SIZE = 20;

    private StoresDSService service;

    public static StoresDS getInstance(SearchOptions searchOptions){
        return new StoresDS(searchOptions);
    }

    private StoresDS(SearchOptions searchOptions) {
        super(searchOptions);
        this.service = StoresDSService.getInstance();
    }

    @Override
    public void getItem(String id, final Listener<StoresDSItem> listener) {
        if ("0".equals(id)) {
            NetworkLoggerInjector.networkLogger().logRequest("https://ibm-pods.buildup.io/app/58657a2e86187104004bb8f6/r/storesDS", "GET");
            getItems(new Listener<List<StoresDSItem>>() {
                @Override
                public void onSuccess(List<StoresDSItem> items) {
                    if(items != null && items.size() > 0) {
                        listener.onSuccess(items.get(0));
                    } else {
                        listener.onSuccess(new StoresDSItem());
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure(e);
                }
            });
        } else {
          NetworkLoggerInjector.networkLogger().logRequest("https://ibm-pods.buildup.io/app/58657a2e86187104004bb8f6/r/storesDS"+id, "GET");
            service.getServiceProxy().getStoresDSItemById(id, new Callback<StoresDSItem>() {
                @Override
                public void success(StoresDSItem result, Response response) {
                    RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(response);
                    listener.onSuccess(result);
                }

                @Override
                public void failure(RetrofitError error) {
                    RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(error.getResponse());
                    listener.onFailure(error);
                }
            });
        }
    }

    @Override
    public void getItems(final Listener<List<StoresDSItem>> listener) {
        getItems(0, listener);
    }

    @Override
    public void getItems(int pagenum, final Listener<List<StoresDSItem>> listener) {
        String conditions = getConditions(searchOptions, getSearchableFields());
        int skipNum = pagenum * PAGE_SIZE;
        String skip = skipNum == 0 ? null : String.valueOf(skipNum);
        String limit = PAGE_SIZE == 0 ? null: String.valueOf(PAGE_SIZE);
        String sort = getSort(searchOptions);
        NetworkLoggerInjector.networkLogger().logRequest("https://ibm-pods.buildup.io/app/58657a2e86187104004bb8f6/r/storesDS?skip="+skip+"&limit="+limit+"&conditions="+conditions+"&sort="+sort+"&select=null&populate=null", "GET");
        service.getServiceProxy().queryStoresDSItem(
                skip,
                limit,
                conditions,
                sort,
                null,
                null,
                new Callback<List<StoresDSItem>>() {
            @Override
            public void success(List<StoresDSItem> result, Response response) {
                RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(response);
                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(error.getResponse());
                listener.onFailure(error);
            }
        });
    }

    private String[] getSearchableFields() {
        return new String[]{"store", "comments", "address"};
    }

    // Pagination

    @Override
    public int getPageSize(){
        return PAGE_SIZE;
    }

    @Override
    public void getUniqueValuesFor(String searchStr, final Listener<List<String>> listener) {
        String conditions = getConditions(searchOptions, getSearchableFields());
        NetworkLoggerInjector.networkLogger().logRequest("https://ibm-pods.buildup.io/app/58657a2e86187104004bb8f6/r/storesDS?distinct="+searchStr+"&conditions="+conditions, "GET");
        service.getServiceProxy().distinct(searchStr, conditions, new Callback<List<String>>() {
             @Override
             public void success(List<String> result, Response response) {
                 RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(response);
                 result.removeAll(Collections.<String>singleton(null));
                 listener.onSuccess(result);
             }

             @Override
             public void failure(RetrofitError error) {
                 RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(error.getResponse());
                 listener.onFailure(error);
             }
        });
    }

    @Override
    public URL getImageUrl(String path) {
        return service.getImageUrl(path);
    }

    @Override
    public void create(StoresDSItem item, Listener<StoresDSItem> listener) {
      NetworkLoggerInjector.networkLogger().logRequest("https://ibm-pods.buildup.io/app/58657a2e86187104004bb8f6/r/storesDS", "POST");
              
        if(item.pictureUri != null){
            service.getServiceProxy().createStoresDSItem(item,
                TypedByteArrayUtils.fromUri(item.pictureUri),
                callbackFor(listener));
        }
        else
            service.getServiceProxy().createStoresDSItem(item, callbackFor(listener));
        
    }

    private Callback<StoresDSItem> callbackFor(final Listener<StoresDSItem> listener) {
      return new Callback<StoresDSItem>() {
          @Override
          public void success(StoresDSItem item, Response response) {
              RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(response);
              listener.onSuccess(item);
          }

          @Override
          public void failure(RetrofitError error) {
              RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(error.getResponse());
              listener.onFailure(error);
          }
      };
    }

    @Override
    public void updateItem(StoresDSItem item, Listener<StoresDSItem> listener) {
      NetworkLoggerInjector.networkLogger().logRequest("https://ibm-pods.buildup.io/app/58657a2e86187104004bb8f6/r/storesDS", "PUT");
              
        if(item.pictureUri != null){
            service.getServiceProxy().updateStoresDSItem(item.getIdentifiableId(),
                item,
                TypedByteArrayUtils.fromUri(item.pictureUri),
                callbackFor(listener));
        }
        else
            service.getServiceProxy().updateStoresDSItem(item.getIdentifiableId(), item, callbackFor(listener));
        
    }

    @Override
    public void deleteItem(StoresDSItem item, final Listener<StoresDSItem> listener) {
        NetworkLoggerInjector.networkLogger().logRequest("https://ibm-pods.buildup.io/app/58657a2e86187104004bb8f6/r/storesDS", "DELETE");
        service.getServiceProxy().deleteStoresDSItemById(item.getIdentifiableId(), new Callback<StoresDSItem>() {
            @Override
            public void success(StoresDSItem result, Response response) {
                RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(response);
                listener.onSuccess(result);
            }

            @Override
            public void failure(RetrofitError error) {
                RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(error.getResponse());
                listener.onFailure(error);
            }
        });
    }

    @Override
    public void deleteItems(List<StoresDSItem> items, final Listener<StoresDSItem> listener) {
        NetworkLoggerInjector.networkLogger().logRequest("https://ibm-pods.buildup.io/app/58657a2e86187104004bb8f6/r/storesDS", "POST");
        service.getServiceProxy().deleteByIds(collectIds(items), new Callback<List<StoresDSItem>>() {
            @Override
            public void success(List<StoresDSItem> item, Response response) {
                RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(response);
                listener.onSuccess(null);
            }

            @Override
            public void failure(RetrofitError error) {
                RetrofitResponseNetworkLoggerInjector.retrofitNetworkLogger().logResponse(error.getResponse());
                listener.onFailure(error);
            }
        });
    }

    protected List<String> collectIds(List<StoresDSItem> items){
        List<String> ids = new ArrayList<>();
        for(StoresDSItem item: items){
            ids.add(item.getIdentifiableId());
        }
        return ids;
    }

}
