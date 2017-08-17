package rocks.ninjachen.exoplayer;

import android.accounts.AccountsException;
import android.app.Activity;

import rocks.ninjachen.exoplayer.core.BootstrapService;

import java.io.IOException;

public interface BootstrapServiceProvider {
    BootstrapService getService(Activity activity) throws IOException, AccountsException;
}
