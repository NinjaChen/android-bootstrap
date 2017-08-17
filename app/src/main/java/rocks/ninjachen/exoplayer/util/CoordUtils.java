package rocks.ninjachen.exoplayer.util;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import rocks.ninjachen.exoplayer.core.Location;

/**
 * Created by ninja on 9/6/16.
 * Thanks to coolypf
 * http://blog.csdn.net/coolypf/article/details/8569813
 */

public class CoordUtils {
    final static double PI = Math.PI;
    final static double x_pi = PI * 3000 / 180;

    /**
     * convert gcj location to baidu location
     *
     * @param gcj_lat gcj latitude
     * @param gcj_lon gcj longitude
     * @return [baidu_lat, baidu_lon]
     */
    public static Location gcj2bd(double gcj_lat, double gcj_lon) {
        double x = gcj_lon, y = gcj_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double bd_lon = z * Math.cos(theta) + 0.0065;
        double bd_lat = z * Math.sin(theta) + 0.006;
        Location bd_loc = new Location(bd_lat, bd_lon);
        return bd_loc;
    }

    /**
     * convert baidu location to gcj location
     *
     * @param bd_lat gcj latitude
     * @param bd_lon gcj longitude
     * @return [gcj_lat, gcj_lon]
     */
    public static Location bd2gcj(double bd_lat, double bd_lon) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gcj_lon = z * Math.cos(theta);
        double gcj_lat = z * Math.sin(theta);
        Location gcj_loc = new Location(gcj_lat, gcj_lon);
        return gcj_loc;
    }

    //GCJ-02 to WGS-84
    public static Location gcj_decrypt(double gcjLat, double gcjLon) {
        if (outOfChina(gcjLat, gcjLon))
            return new Location(gcjLat, gcjLon);

        Location d = delta(gcjLat, gcjLon);
        return new Location(gcjLat - d.getLatitude(), gcjLon - d.getLongitude());
    }

    //WGS-84 to GCJ-02
    public static Location gcj_encrypt(double wgsLat, double wgsLon) {
        if (outOfChina(wgsLat, wgsLon))
            return new Location(wgsLat, wgsLon);

        Location d = delta(wgsLat, wgsLon);
        return new Location(wgsLat + d.getLatitude(), wgsLon + d.getLongitude());
    }

    public static Location delta(double lat, double lon) {
        // Krasovsky 1940
        //
        // a = 6378245.0, 1/f = 298.3
        // b = a * (1 - f)
        // ee = (a^2 - b^2) / a^2;
        double a = 6378245.0; //  a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
        double ee = 0.00669342162296594323; //  ee: 椭球的偏心率。
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
        return new Location(dLat, dLon);
    }

    //GCJ-02 to WGS-84 exactly
    public static Location gcj_decrypt_exact(double gcjLat, double gcjLon) {
        double initDelta = 0.01;
        double threshold = 0.000000001;
        double dLat = initDelta, dLon = initDelta;
        double mLat = gcjLat - dLat, mLon = gcjLon - dLon;
        double pLat = gcjLat + dLat, pLon = gcjLon + dLon;
        double wgsLat, wgsLon, i = 0;
        while (true) {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;
            Location tmp = gcj_encrypt(wgsLat, wgsLon);
            dLat = tmp.getLatitude() - gcjLat;
            dLon = tmp.getLongitude() - gcjLon;
            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
                break;

            if (dLat > 0) pLat = wgsLat;
            else mLat = wgsLat;
            if (dLon > 0) pLon = wgsLon;
            else mLon = wgsLon;

            if (++i > 10000) break;
        }
        return new Location(wgsLat, wgsLon);
    }

    public static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    public static double[] getRangeWithCenterAndRadius(double lat, double lng, int radius) {
//        double latD = 0.009019;
//        double lngD = 0.010523;
//        int xDelta = 90;
//        int yDelta = 90;
        double latD = 0.0008;
        double lngD = 0.0008;
        LatLng center = new LatLng(lat, lng);

        double distance;
        for (int i = 0 ; i < 200000;i ++){
            lat += latD;
            distance = CoordUtil.getDistance(CoordUtil.ll2point(center), CoordUtil.ll2point(new LatLng(lat, lng)));
            if(distance > radius)
                break;
        }
        double latRight = lat;
        //clear
        lat = center.latitude;
        lng = center.longitude;
        for (int i = 0 ; i < 200000;i ++){
            lat -= latD;
            distance = CoordUtil.getDistance(CoordUtil.ll2point(center), CoordUtil.ll2point(new LatLng(lat, lng)));
            if(distance > radius)
                break;
        }
        double latLeft = lat;
        //clear
        lat = center.latitude;
        lng = center.longitude;
        for (int i = 0 ; i < 200000;i ++){
            lng += lngD;
            distance = CoordUtil.getDistance(CoordUtil.ll2point(center), CoordUtil.ll2point(new LatLng(lat, lng)));
            if(distance > radius)
                break;
        }
        double lngtop = lng;
        //clear
        lat = center.latitude;
        lng = center.longitude;
        for (int i = 0 ; i < 200000;i ++){
            lng -= lngD;
            distance = CoordUtil.getDistance(CoordUtil.ll2point(center), CoordUtil.ll2point(new LatLng(lat, lng)));
            if(distance > radius)
                break;
        }
        double lngbuttom = lng;
        double[] result = new double[2];
        result[0] = latRight - latLeft;
        result[1] = lngtop- lngbuttom;
        return result;
    }
}

