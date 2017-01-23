

package com.ibm.mobileappbuilder.pkg20161229210345.ds;
import com.ibm.mobileappbuilder.pkg20161229210345.R;
import java.util.ArrayList;
import java.util.List;
import ibmmobileappbuilder.util.StringUtils;

// Item static data
public class EmptyDatasourceItems{

    public static List<Item> ITEMS = new ArrayList<Item>();
    public static void addItem(Item item) {
        ITEMS.add(item);
    }
}

