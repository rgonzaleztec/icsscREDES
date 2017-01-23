
package com.ibm.mobileappbuilder.pkg20161229210345.ui;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ibm.mobileappbuilder.pkg20161229210345.R;
import ibmmobileappbuilder.actions.ActivityIntentLauncher;
import ibmmobileappbuilder.actions.MapsAction;
import ibmmobileappbuilder.ds.restds.AppNowDatasource;
import ibmmobileappbuilder.util.ColorUtils;
import ibmmobileappbuilder.util.image.ImageLoader;
import ibmmobileappbuilder.util.image.PicassoImageLoader;
import ibmmobileappbuilder.util.StringUtils;
import java.net.URL;
import static ibmmobileappbuilder.util.image.ImageLoaderRequest.Builder.imageLoaderRequest;
import ibmmobileappbuilder.ds.Datasource;
import ibmmobileappbuilder.ds.SearchOptions;
import ibmmobileappbuilder.ds.filter.Filter;
import java.util.Arrays;
import com.ibm.mobileappbuilder.pkg20161229210345.ds.StoresDSItem;
import com.ibm.mobileappbuilder.pkg20161229210345.ds.StoresDS;

public class StoresMapDetailFragment extends ibmmobileappbuilder.ui.DetailFragment<StoresDSItem>  {

    private Datasource<StoresDSItem> datasource;
    public static StoresMapDetailFragment newInstance(Bundle args){
        StoresMapDetailFragment fr = new StoresMapDetailFragment();
        fr.setArguments(args);

        return fr;
    }

    public StoresMapDetailFragment(){
        super();
    }

    @Override
    public Datasource<StoresDSItem> getDatasource() {
        if (datasource != null) {
            return datasource;
    }
       datasource = StoresDS.getInstance(new SearchOptions());
        return datasource;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

    }

    // Bindings

    @Override
    protected int getLayout() {
        return R.layout.storesmapdetail_detail;
    }

    @Override
    @SuppressLint("WrongViewCast")
    public void bindView(final StoresDSItem item, View view) {
        
        ImageView view0 = (ImageView) view.findViewById(R.id.view0);
        URL view0Media = ((AppNowDatasource) getDatasource()).getImageUrl(item.picture);
        if(view0Media != null){
            ImageLoader imageLoader = new PicassoImageLoader(view0.getContext());
            imageLoader.load(imageLoaderRequest()
                                   .withPath(view0Media.toExternalForm())
                                   .withTargetView(view0)
                                   .fit()
                                   .build()
                    );
            
        } else {
            view0.setImageDrawable(null);
        }
        if (item.rating != null){
            
            TextView view1 = (TextView) view.findViewById(R.id.view1);
            view1.setText(StringUtils.intToString(item.rating) + " ★");
            
        }
        if (item.reviewedon != null){
            
            TextView view2 = (TextView) view.findViewById(R.id.view2);
            view2.setText(DateFormat.getMediumDateFormat(getActivity()).format(item.reviewedon));
            
        }
        if (item.address != null){
            
            TextView view3 = (TextView) view.findViewById(R.id.view3);
            view3.setText(item.address);
            ColorUtils.tintIcon(view3.getCompoundDrawables()[2], R.color.textColor, getActivity());
            bindAction(view3, new MapsAction(new ActivityIntentLauncher(), "http://maps.google.com/maps?q=" + item.address));
        }
        if (item.comments != null){
            
            TextView view4 = (TextView) view.findViewById(R.id.view4);
            view4.setText(item.comments);
            
        }
    }

    @Override
    protected void onShow(StoresDSItem item) {
        // set the title for this fragment
        getActivity().setTitle(item.store);
    }
}
