package rocks.ninjachen.hbridgek;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import rocks.ninjachen.hbridgek.authenticator.ApiKeyProvider;
import rocks.ninjachen.hbridgek.authenticator.LogoutService;
import rocks.ninjachen.hbridgek.authenticator.LogoutServiceImpl;
import rocks.ninjachen.hbridgek.core.BootstrapService;
import rocks.ninjachen.hbridgek.core.Constants;
import rocks.ninjachen.hbridgek.core.NinjaCookieManager;
import rocks.ninjachen.hbridgek.core.PersistentCookieStore;
import rocks.ninjachen.hbridgek.core.RestAdapterRequestInterceptor;
import rocks.ninjachen.hbridgek.core.RestErrorHandler;
import rocks.ninjachen.hbridgek.core.UserAgentProvider;

import org.greenrobot.eventbus.EventBus;

import java.net.CookieManager;
import java.net.CookiePolicy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
public class BootstrapModule {

    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutServiceImpl(context, accountManager);
    }

    @Provides
    BootstrapService provideBootstrapService(RestAdapter restAdapter) {
        return new BootstrapService(restAdapter);
    }

    @Provides
    BootstrapServiceProvider provideBootstrapServiceProvider(RestAdapter restAdapter, ApiKeyProvider apiKeyProvider) {
        return new BootstrapServiceProviderImpl(restAdapter, apiKeyProvider);
    }

    @Provides
    ApiKeyProvider provideApiKeyProvider(AccountManager accountManager) {
        return new ApiKeyProvider(accountManager);
    }


    @Singleton
    @Provides
    PersistentCookieStore providePersistentCookieStore() {
        return new PersistentCookieStore(BootstrapApplication.getInstance());
    }

    @Singleton
    @Provides
    CookieManager provideCookieManager(PersistentCookieStore cookieStore) {
        return new NinjaCookieManager(cookieStore, CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    /**
     * The method will invoke twice at start, and do not know why
     * @param cookieManager
     * @return
     */
    @Provides
    OkHttpClient provideOkHttpClient(CookieManager cookieManager) {
        return new OkHttpClient().setCookieHandler(cookieManager);
    }

    @Provides
    Gson provideGson() {
        /**
         * GSON instance to use for all request  with date format set up for proper parsing.
         * <p/>
         * You can also configure GSON with different naming policies for your API.
         * Maybe your API is Rails API and all json values are lower case with an underscore,
         * like this "first_name" instead of "firstName".
         * You can configure GSON as such below.
         * <p/>
         *
         * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd")
         *         .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
         */
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    @Provides
    RestErrorHandler provideRestErrorHandler(EventBus bus) {
        return new RestErrorHandler(bus);
    }

    @Provides
    UserAgentProvider providesUserAgentProvider(ApplicationInfo appInfo, PackageInfo packageInfo, TelephonyManager telephonyManager, ClassLoader classLoader) {
        return new UserAgentProvider(appInfo, packageInfo, telephonyManager, classLoader);
    }

    @Provides
    RestAdapterRequestInterceptor provideRestAdapterRequestInterceptor(UserAgentProvider userAgentProvider) {
        return new RestAdapterRequestInterceptor(userAgentProvider);
    }

    @Provides
    RestAdapter provideRestAdapter(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        return new RestAdapter.Builder()
                .setEndpoint(Constants.Zeonic.URL_BASE)
                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

//    /**
//     * Set okhttp to retrofit, and share cookie with auth and future(for webview extend)
//     * @param restErrorHandler
//     * @param restRequestInterceptor
//     * @param gson
//     * @param okHttpClient
//     * @return
//     */
//    @Provides
//    @Named("restAdapterYKT")
//    RestAdapter provideRestAdapterYKT(RestErrorHandler restErrorHandler,
//                                      RestAdapterRequestInterceptor restRequestInterceptor,
//                                      Gson gson,
//                                      OkHttpClient okHttpClient) {
//        return new RestAdapter.Builder()
//                .setEndpoint(Constants.YKT.URL_BASE)
//                .setErrorHandler(restErrorHandler)
//                .setRequestInterceptor(restRequestInterceptor)
//                .setLogLevel(RestAdapter.LogLevel.FULL)
//                .setClient(new OkClient(okHttpClient))
//                .setConverter(new GsonConverter(gson))
//                .build();
//    }

    @Singleton
    @Provides
    EventBus provideEventBus() {
        return EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false).build();
    }
}
