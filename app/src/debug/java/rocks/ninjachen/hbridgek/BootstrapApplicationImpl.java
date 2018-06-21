package rocks.ninjachen.hbridgek;


import rocks.ninjachen.hbridgek.BootstrapApplication;
import timber.log.Timber;

public class BootstrapApplicationImpl extends BootstrapApplication {

    @Override
    protected void onAfterInjection() {

    }

    @Override
    protected void init() {
        Timber.plant(new Timber.DebugTree());
    }
}
