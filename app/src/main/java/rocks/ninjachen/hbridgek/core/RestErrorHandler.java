package rocks.ninjachen.hbridgek.core;

import rocks.ninjachen.hbridgek.events.NetworkErrorEvent;
import rocks.ninjachen.hbridgek.events.RestAdapterErrorEvent;
import rocks.ninjachen.hbridgek.events.UnAuthorizedErrorEvent;

import org.greenrobot.eventbus.EventBus;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

public class RestErrorHandler implements ErrorHandler {

    public static final int HTTP_NOT_FOUND = 404;
    public static final int INVALID_LOGIN_PARAMETERS = 101;

    private EventBus bus;

    public RestErrorHandler(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {
        if(cause != null) {
            if (cause.isNetworkError()) {
                bus.post(new NetworkErrorEvent(cause));
            } else if(isUnAuthorized(cause)) {
                bus.post(new UnAuthorizedErrorEvent(cause));
            } else {
                bus.post(new RestAdapterErrorEvent(cause));
            }
        }

        // Example of how you'd check for a unauthorized result
        // if (cause != null && cause.getStatus() == 401) {
        //     return new UnauthorizedException(cause);
        // }

        // You could also put some generic error handling in here so you can start
        // getting analytics on error rates/etc. Perhaps ship your logs off to
        // Splunk, Loggly, etc

        return cause;
    }

    /**
     * If a user passes an incorrect username/password combo in we could
     * get a unauthorized error back from the API. On parse.com this means
     * we get back a HTTP 404 with an error as JSON in the body as such:
     *
     *  {
     *     code: 101,
     *     error: "invalid login parameters"
     *  }
     *
     *  }
     *
     * Therefore we need to check for the 101 and the 404.
     *
     * @param cause The initial error.
     * @return
     */
    private boolean isUnAuthorized(RetrofitError cause) {
        boolean authFailed = false;
        return false; // TODO: 6/14/17 fix in future
//        if(cause.getResponse() != null && cause.getResponse().getStatus() == HTTP_NOT_FOUND) {
//            final ApiError err = (ApiError) cause.getBodyAs(ApiError.class);
//            if(err != null && err.getCode() == INVALID_LOGIN_PARAMETERS) {
//                authFailed = true;
//            }
//        }
//
//        return authFailed;
    }
}
