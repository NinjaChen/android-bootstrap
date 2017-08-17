package rocks.ninjachen.exoplayer.events;

import retrofit.RetrofitError;
import rocks.ninjachen.exoplayer.ui.BootstrapActivity;

/**
 * The event that is posted when a network error event occurs.
 * TODO: Consume this event in the {@link BootstrapActivity} and
 * show a dialog that something went wrong.
 */
public class NetworkErrorEvent {
    private RetrofitError cause;

    public NetworkErrorEvent(RetrofitError cause) {
        this.cause = cause;
    }

    public RetrofitError getCause() {
        return cause;
    }
}
