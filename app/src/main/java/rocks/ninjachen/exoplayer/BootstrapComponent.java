package rocks.ninjachen.exoplayer;

import rocks.ninjachen.exoplayer.authenticator.BootstrapAuthenticatorActivity;
import rocks.ninjachen.exoplayer.model.LocationManager;
import rocks.ninjachen.exoplayer.ui.BootstrapActivity;
import rocks.ninjachen.exoplayer.ui.BootstrapFragment;
import rocks.ninjachen.exoplayer.ui.BootstrapFragmentActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AndroidModule.class,
                BootstrapModule.class
        }
)
public interface BootstrapComponent {

    void inject(BootstrapApplication target);

    void inject(BootstrapAuthenticatorActivity target);

    void inject(BootstrapFragmentActivity target);

    void inject(BootstrapActivity target);

//    void inject(NearbyLineFragment nearbyLineFragment);

    void inject(BootstrapFragment bootstrapFragment);

    void inject(LocationManager locationManager);

//    void inject(ZeonicFragment zeonicFragment);
}
