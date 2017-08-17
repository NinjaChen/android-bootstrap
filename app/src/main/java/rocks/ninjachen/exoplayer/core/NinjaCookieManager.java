package rocks.ninjachen.exoplayer.core;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by ninja on 6/14/17.
 */

public class NinjaCookieManager extends CookieManager {
    /**
     * Constructs a new cookie manager.
     *
     * The invocation of this constructor is the same as the invocation of
     * CookieManager(null, null).
     *
     */
    public NinjaCookieManager() {
        this(null, null);
    }

    /**
     * Constructs a new cookie manager using a specified cookie store and a
     * cookie policy.
     *
     * @param store
     *            a CookieStore to be used by cookie manager. The manager will
     *            use a default one if the arg is null.
     * @param cookiePolicy
     *            a CookiePolicy to be used by cookie manager
     *            ACCEPT_ORIGINAL_SERVER will be used if the arg is null.
     */
    public NinjaCookieManager(CookieStore store, CookiePolicy cookiePolicy) {
        super(store, cookiePolicy);
    }

    @Override
    public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
//        Timber.e("Cookie-get:"+ new Gson().toJson(uri));
        Map<String, List<String>> result = super.get(uri, requestHeaders);
//        Timber.e("Cookie-get: result "+ new Gson().toJson(result));
        return result;
    }

    @Override
    public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
//        Timber.e("Cookie-put:"+ new Gson().toJson(uri) + new Gson().toJson(responseHeaders));
        super.put(uri, responseHeaders);
    }
}
