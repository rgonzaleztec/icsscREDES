
package com.ibm.mobileappbuilder.pkg20161229210345.ds;
import java.net.URL;
import com.ibm.mobileappbuilder.pkg20161229210345.R;
import ibmmobileappbuilder.ds.RestService;
import ibmmobileappbuilder.util.StringUtils;

/**
 * "StoresDSService" REST Service implementation
 */
public class StoresDSService extends RestService<StoresDSServiceRest>{

    public static StoresDSService getInstance(){
          return new StoresDSService();
    }

    private StoresDSService() {
        super(StoresDSServiceRest.class);

    }

    @Override
    public String getServerUrl() {
        return "https://ibm-pods.buildup.io";
    }

    @Override
    protected String getApiKey() {
        return "IHpOpTCp";
    }

    @Override
    public URL getImageUrl(String path){
        return StringUtils.parseUrl("https://ibm-pods.buildup.io/app/58657a2e86187104004bb8f6",
                path,
                "apikey=IHpOpTCp");
    }

}
