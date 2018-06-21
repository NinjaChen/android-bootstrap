package rocks.ninjachen.hbridgek;

import javax.inject.Singleton;

import dagger.Component;
import rocks.ninjachen.hbridgek.authenticator.BootstrapAuthenticatorActivity;
import rocks.ninjachen.hbridgek.ui.BootstrapActivity;
import rocks.ninjachen.hbridgek.ui.BootstrapFragment;
import rocks.ninjachen.hbridgek.ui.BootstrapFragmentActivity;

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

//    void inject(ZeonicFragment zeonicFragment);
}
