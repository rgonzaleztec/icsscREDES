
package com.ibm.mobileappbuilder.pkg20161229210345.ui;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.ibm.mobileappbuilder.pkg20161229210345.R;
import ibmmobileappbuilder.ds.Datasource;
import android.widget.TextView;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.pkg20161229210345.ds.Item;
import com.ibm.mobileappbuilder.pkg20161229210345.ds.EmptyDatasource;
import static ibmmobileappbuilder.analytics.injector.PageViewBehaviorInjector.pageViewBehavior;

public class AcercaFragment extends ibmmobileappbuilder.ui.DetailFragment<Item>  {

    private Datasource<Item> datasource;
    private SearchOptions searchOptions;

    public static AcercaFragment newInstance(Bundle args){
        AcercaFragment card = new AcercaFragment();
        card.setArguments(args);

        return card;
    }

    public AcercaFragment(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBehavior(pageViewBehavior("Acerca"));
        searchOptions = SearchOptions.Builder.searchOptions().build();
    }

    @Override
    public Datasource getDatasource() {
        if (datasource != null) {
            return datasource;
        }
        datasource = EmptyDatasource.getInstance(searchOptions);
        return datasource;
    }

    // Bindings

    @Override
    protected int getLayout() {
        return R.layout.acerca_custom;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final Item item, View view) {
        
        TextView view0 = (TextView) view.findViewById(R.id.view0);
        view0.setText("Esta aplicación consiste en un sistema que permita denunciar actos delictivos en diferentes zonas de Costa Rica, por medio de una aplicación móvil, sincronizada en tiempo real. Esta aplicación permitirá tanto agregar fotografías como agregar la ubicación en google maps. Los usuarios serán registrados debidamente por el encargado de la aplicación.");
        
    }

    @Override
    protected void onShow(Item item) {
        // set the title for this fragment
        getActivity().setTitle("Acerca");
    }
}
