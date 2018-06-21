package rocks.ninjachen.hbridgek.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Location implements Serializable, Parcelable{
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(List<Double> locations) {
        this.latitude = locations.get(0);
        this.longitude = locations.get(1);
    }

    protected Location(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

//  Remove BaiduMap dependency
//    public LatLng toLatLng() {
//        return new LatLng(getLatitude(), getLongitude());
//    }
//
//    public static ArrayList<LatLng> toLatLngs(List<Location> locations) {
//        if(locations == null) return null;
//        ArrayList<LatLng> lls = new ArrayList<>();
//        for(Location location : locations){
//            lls.add(location.toLatLng());
//        }
//        return lls;
//    }
//
//    // utils
//    public static Location from(LatLng ll) {
//        if(ll == null) return null;
//        return new Location(ll.latitude, ll.longitude);
//    }
//    public static ArrayList<Location> from(List<LatLng> lls) {
//        if(ZeonicUtils.isEmpty(lls)) return null;
//        ArrayList<Location> locations = new ArrayList<>();
//        for(LatLng ll :lls){
//            locations.add(Location.from(ll));
//        }
//        return locations;
//    }
}
