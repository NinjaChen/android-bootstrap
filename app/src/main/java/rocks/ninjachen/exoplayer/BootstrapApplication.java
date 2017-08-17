

package rocks.ninjachen.exoplayer;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import rocks.ninjachen.exoplayer.model.LocationManager;

/**
 * tzpay application
 */
public abstract class BootstrapApplication extends Application {

    private static BootstrapApplication instance;
    private BootstrapComponent component;

    /**
     * Create main application
     */
    public BootstrapApplication() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

        init();

        instance = this;

        // Perform injection
        //Injector.init(this, )
        component = DaggerComponentInitializer.init();

        onAfterInjection();

        //baidumap在使用SDK各组件之前初始化context信息，传入ApplicationContext
        initBaiduSDK();
        // crashlistic
        initFabric();
    }

    private void initBaiduSDK() {
        SDKInitializer.initialize(getApplicationContext());
        LocationManager.getInstance().start();
    }

    private void initFabric() {
        Fabric.Builder builder = new Fabric.Builder(this).kits(new Crashlytics());
        final Fabric fabric = builder.build();
        Fabric.with(fabric);
    }

    public static BootstrapComponent component() {
        return instance.component;
    }

    protected abstract void onAfterInjection();

    protected abstract void init();

    public static BootstrapApplication getInstance() {
        return instance;
    }

    public BootstrapComponent getComponent() {
        return component;
    }

    public final static class DaggerComponentInitializer {

        public static BootstrapComponent init() {
            return DaggerBootstrapComponent.builder()
                    .androidModule(new AndroidModule())
                    .bootstrapModule(new BootstrapModule())
                    .build();
        }

    }

}
