package rocks.ninjachen.exoplayer.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import rocks.ninjachen.exoplayer.BootstrapApplication;

/**
 * Created by ninja on 2/23/17.
 */

public class NetworkUtils {

    public static boolean isOnline() {
        // do not use context, if context is activity, leaks !
        ConnectivityManager cm =
                (ConnectivityManager) BootstrapApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
