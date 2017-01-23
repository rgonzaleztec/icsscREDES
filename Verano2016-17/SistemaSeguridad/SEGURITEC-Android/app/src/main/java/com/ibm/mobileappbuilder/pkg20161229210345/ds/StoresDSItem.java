
package com.ibm.mobileappbuilder.pkg20161229210345.ds;
import android.graphics.Bitmap;
import ibmmobileappbuilder.ds.restds.GeoPoint;
import java.util.Date;
import android.net.Uri;

import ibmmobileappbuilder.mvp.model.IdentifiableBean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class StoresDSItem implements Parcelable, IdentifiableBean {

    @SerializedName("store") public String store;
    @SerializedName("rating") public Long rating;
    @SerializedName("picture") public String picture;
    @SerializedName("comments") public String comments;
    @SerializedName("location") public GeoPoint location;
    @SerializedName("reviewedon") public Date reviewedon;
    @SerializedName("address") public String address;
    @SerializedName("id") public String id;
    @SerializedName("pictureUri") public transient Uri pictureUri;

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
        dest.writeString(store);
        dest.writeValue(rating);
        dest.writeString(picture);
        dest.writeString(comments);
        dest.writeDoubleArray(location != null  && location.coordinates.length != 0 ? location.coordinates : null);
        dest.writeValue(reviewedon != null ? reviewedon.getTime() : null);
        dest.writeString(address);
        dest.writeString(id);
    }

    public static final Creator<StoresDSItem> CREATOR = new Creator<StoresDSItem>() {
        @Override
        public StoresDSItem createFromParcel(Parcel in) {
            StoresDSItem item = new StoresDSItem();

            item.store = in.readString();
            item.rating = (Long) in.readValue(null);
            item.picture = in.readString();
            item.comments = in.readString();
            double[] location_coords = in.createDoubleArray();
            if (location_coords != null)
                item.location = new GeoPoint(location_coords);
            Long reviewedonAux = (Long) in.readValue(null);
            item.reviewedon = reviewedonAux != null ? new Date(reviewedonAux) : null;
            item.address = in.readString();
            item.id = in.readString();
            return item;
        }

        @Override
        public StoresDSItem[] newArray(int size) {
            return new StoresDSItem[size];
        }
    };

}

