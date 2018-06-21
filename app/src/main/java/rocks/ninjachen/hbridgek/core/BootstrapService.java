
package rocks.ninjachen.hbridgek.core;

import rocks.ninjachen.hbridgek.apis.ZeonicService;

import retrofit.RestAdapter;

/**
 * Bootstrap API service
 */
public class BootstrapService {

    private RestAdapter restAdapter;

    /**
     * Create bootstrap service
     * Default CTOR
     */
    public BootstrapService() {
    }

    /**
     * Create bootstrap service
     *
     * @param restAdapter The RestAdapter that allows HTTP Communication.
     */
    public BootstrapService(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    private ZeonicService getZeonicService() {
        return getRestAdapter().create(ZeonicService.class);
    }

    private RestAdapter getRestAdapter() {
        return restAdapter;
    }
}