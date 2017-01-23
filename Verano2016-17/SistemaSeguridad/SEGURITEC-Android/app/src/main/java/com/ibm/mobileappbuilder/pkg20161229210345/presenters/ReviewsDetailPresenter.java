
package com.ibm.mobileappbuilder.pkg20161229210345.presenters;

import com.ibm.mobileappbuilder.pkg20161229210345.R;
import com.ibm.mobileappbuilder.pkg20161229210345.ds.StoresDSItem;

import ibmmobileappbuilder.ds.CrudDatasource;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.mvp.presenter.BasePresenter;
import ibmmobileappbuilder.mvp.presenter.DetailCrudPresenter;
import ibmmobileappbuilder.mvp.view.DetailView;
import java.util.Map;
import java.util.HashMap;
import ibmmobileappbuilder.analytics.injector.AnalyticsReporterInjector;

public class ReviewsDetailPresenter extends BasePresenter implements DetailCrudPresenter<StoresDSItem>,
      Datasource.Listener<StoresDSItem> {

    private final CrudDatasource<StoresDSItem> datasource;
    private final DetailView view;

    public ReviewsDetailPresenter(CrudDatasource<StoresDSItem> datasource, DetailView view){
        this.datasource = datasource;
        this.view = view;
    }

    @Override
    public void deleteItem(StoresDSItem item) {
        datasource.deleteItem(item, this);
    }

    @Override
    public void editForm(StoresDSItem item) {
        view.navigateToEditForm();
    }

    @Override
    public void onSuccess(StoresDSItem item) {
        logCrudAction("deleted", item.id);
        view.showMessage(R.string.item_deleted, true);
        view.close(true);
    }

    @Override
    public void onFailure(Exception e) {
        view.showMessage(R.string.error_data_generic, true);
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
