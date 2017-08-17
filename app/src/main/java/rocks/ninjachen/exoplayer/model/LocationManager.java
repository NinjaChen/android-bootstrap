package rocks.ninjachen.exoplayer.model;

import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import rocks.ninjachen.exoplayer.BootstrapApplication;
import rocks.ninjachen.exoplayer.BuildConfig;
import rocks.ninjachen.exoplayer.entity.FirstRecvLocEvent;
import timber.log.Timber;

/**
 * Created by ninja on 6/21/17.
 */

public class LocationManager implements BDLocationListener {
    private static LocationManager instance = new LocationManager();
    private LocationClient mLocClient;
    private boolean firstReceiveLocation = true;
    private BDLocation mMyLastLocation;

    @Inject
    protected EventBus eventBus;

    public LocationManager(){
        BootstrapApplication.component().inject(this);
    }
    public static LocationManager getInstance() {
        return instance;
    }

    /**
     * init baidu location client and start it. the callback will invoke onReceiveLocation()
     *
     * @param
     */
    public void start() {
        if(mLocClient == null){
            mLocClient = new LocationClient(BootstrapApplication.getInstance());
            //开始定位
            if (BuildConfig.DEBUG) Timber.i("开始定位");
            // 定位初始化
            mLocClient.registerLocationListener(this);
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true);// 打开gps
            option.setCoorType("bd09ll"); // 设置坐标类型
            int scanSpan = 30*1000;
            option.setScanSpan(scanSpan); //scanSpan in Millisecond, 0 is scan once
            mLocClient.setLocOption(option);
        }
        mLocClient.start();
    }

    public void stop() {
        if(mLocClient != null){
            mLocClient.stop();
        }
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        Timber.d("onReceiveLocation in global context");
        if (firstReceiveLocation) {
            firstReceiveLocation = false;
            mMyLastLocation = location;
            eventBus.post(new FirstRecvLocEvent());
            // Do something in first locating
        }
//        String message = buildLocationMessage(location);
//        Timber.d(message);
        mMyLastLocation = location;
    }

    public @Nullable LatLng getLastLocation(){
        if(mMyLastLocation == null){
            return null;
        }else {
            return new LatLng(mMyLastLocation.getLatitude(), mMyLastLocation.getLongitude());
        }
    }
}
