
package com.ibm.mobileappbuilder.pkg20161229210345.presenters;

import com.ibm.mobileappbuilder.pkg20161229210345.R;
import com.ibm.mobileappbuilder.pkg20161229210345.ds.StoresDSItem;

import java.util.List;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.ListCrudPresenter;
import ibmmobileappbuilder.mvp.view.CrudListView;
import java.util.Map;
import java.util.HashMap;
import ibmmobileappbuilder.analytics.injector.AnalyticsReporterInjector;

public class ReviewsPresenter extends BasePresenter implements ListCrudPresenter<StoresDSItem>,
      Datasource.Listener<StoresDSItem>{

    private final CrudDatasource<StoresDSItem> crudDatasource;
    private final CrudListView<StoresDSItem> view;

    public ReviewsPresenter(CrudDatasource<StoresDSItem> crudDatasource,
                                         CrudListView<StoresDSItem> view) {
       this.crudDatasource = crudDatasource;
       this.view = view;
    }

    @Override
    public void deleteItem(StoresDSItem item) {
        crudDatasource.deleteItem(item, this);
    }

    @Override
    public void deleteItems(List<StoresDSItem> items) {
        crudDatasource.deleteItems(items, this);
    }

    @Override
    public void addForm() {
        view.showAdd();
    }

    @Override
    public void editForm(StoresDSItem item, int position) {
        view.showEdit(item, position);
    }

    @Override
    public void detail(StoresDSItem item, int position) {
        view.showDetail(item, position);
    }

    @Override
    public void onSuccess(StoresDSItem item) {
        logCrudAction("deleted", item.id);
        view.showMessage(R.string.items_deleted);
        view.refresh();
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic);
    }

    private void logCrudAction(String action, String id) {
      Map<String, String> paramsMap = new HashMap<>(3);
      //action will be one of created, updated or deleted
      paramsMap.put("action", action);
      paramsMap.put("entity", "StoresDSItem");
      paramsMap.put("identifier", id);

      AnalyticsReporterInjector.analyticsReporter().sendEvent(paramsMap);
    }
}
