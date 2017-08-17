package rocks.ninjachen.exoplayer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import rocks.ninjachen.exoplayer.BootstrapApplication;
import rocks.ninjachen.exoplayer.core.BootstrapService;
import rocks.ninjachen.exoplayer.events.FakeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by ninja on 6/19/17.
 */

public class BootstrapFragment extends Fragment{
    @Inject
    protected EventBus eventBus;
    @Inject
    protected BootstrapService bootstrapService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    /**
     * FakeEvent should never be fired, only to avoid EventBusException
     * Related with eventBus.register(this)
     * @param event
     */
    @Subscribe
    public void onFakeEvent(FakeEvent event){
        Timber.e("FakeEvent should never be fired");
    }


    protected void hideProgress() {
        // FIXME: 6/21/17
        if(getActivity() instanceof BootstrapActivity){
            BootstrapActivity activity = (BootstrapActivity) getActivity();
            activity.hideProgress();
        }
    }

    protected void showProgress() {
        // FIXME: 6/21/17
        if(getActivity() instanceof BootstrapActivity){
            BootstrapActivity activity = (BootstrapActivity) getActivity();
            activity.showProgress();
        }
    }
}
