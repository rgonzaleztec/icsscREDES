
package com.ibm.mobileappbuilder.pkg20161229210345.ds;

import ibmmobileappbuilder.mvp.model.IdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Item implements Parcelable, IdentifiableBean {

    @SerializedName("id") public String id;

    @Override
    public String getIdentifiableId() {
      return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            Item item = new Item();

            item.id = in.readString();
            return item;
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

}

