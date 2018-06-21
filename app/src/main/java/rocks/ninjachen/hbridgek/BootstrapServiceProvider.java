package rocks.ninjachen.hbridgek;

import android.accounts.AccountsException;
import android.app.Activity;

import rocks.ninjachen.hbridgek.core.BootstrapService;

import java.io.IOException;

public interface BootstrapServiceProvider {
    BootstrapService getService(Activity activity) throws IOException, AccountsException;
}
