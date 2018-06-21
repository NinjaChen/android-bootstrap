

package rocks.ninjachen.hbridgek.core;

/**
 * Bootstrap constants
 */
public final class Constants {
    private Constants() {}

    public static final class Setting {
        public static String SHARED_PREFERENCES_NAME = "zeonic_setting";
        public static String SEARCH_REGION_IN_METRE = "search_region_in_metre";
        public static int DEFAULT_SEARCH_REGION_IN_METRE = 500;

        public static String LAUNCH_IMAGE_TAG = "LAUNCH_IMAGE_TAG";
        public static final String LAUNCH_IMAGE_STAT_TIME_TAG = "LAUNCH_IMAGE_STAT_TIME_TAG";
        public static final String LAUNCH_IMAGE_END_TIME_TAG = "LAUNCH_IMAGE_END_TIME_TAG";
    }


    public static final class Auth {
        private Auth() {}

        /**
         * Account type id
         */
        public static final String BOOTSTRAP_ACCOUNT_TYPE = "com.zeonic.ykt";

        /**
         * Account name
         */
        public static final String BOOTSTRAP_ACCOUNT_NAME = "tzpay";

        /**
         * Provider id
         */
        public static final String BOOTSTRAP_PROVIDER_AUTHORITY = "com.zeonic.ykt.sync";

        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = BOOTSTRAP_ACCOUNT_TYPE;
    }

    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for bootstrap!
     */
    public static final class Zeonic {


        private Zeonic() {
        }

        /**
         * Base URL for all requests
         */
//        public static final String URL_BASE = BootstrapApplication.getInstance().getString(R.string.server_url);
        public static final String URL_BASE = "http://112.124.111.153:8888";

        public static final String URL_REGISTER_DEVICE = "/Tokens/Registration/{identification}";
        public static final String URL_BUS_STATIONS = "/Buses/Stations/{c_id}&{lat}&{lon}&{latd}&{lond}&{identification}";
        public static final String URL_BIKE_STATIONS = "/Bikes/Stations/{c_id}&{lat}&{lon}&{latd}&{lond}&{identification}";
        public static final String URL_PARKING_STATIONS = "/Parkings/Stations/{c_id}&{lat}&{lon}&{latd}&{lond}&{identification}";
        public static final String URL_TIME = "/Buses/Time/{c_id}&{b_name}&{s_name}&{direction}&{identification}";
        public static final String URL_LINE = "/Buses/Line/{c_id}&{b_id}&{identification}";
        public static final String URL_HEADER = "/Cities/Header/{identification}";
        public static final String URL_GET_CITY_LIST = "/Cities/Header/{identification}";
        public static final String URL_BUS_REAL_TIME = "/Buses/Time/{city_id}&{bus_name}&{station_name}&{direction}&{identification}";

    }

    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for bootstrap!
     */
    public static final class Http {
        private Http() {}


        /**
         * Base URL for all requests
         */
        public static final String URL_BASE = "https://api.parse.com";


        /**
         * Authentication URL
         */
        public static final String URL_AUTH_FRAG = "/1/login";
        public static final String URL_AUTH = URL_BASE + URL_AUTH_FRAG;

        /**
         * List Users URL
         */
        public static final String URL_USERS_FRAG =  "/1/users";
        public static final String URL_USERS = URL_BASE + URL_USERS_FRAG;


        /**
         * List News URL
         */
        public static final String URL_NEWS_FRAG = "/1/classes/News";
        public static final String URL_NEWS = URL_BASE + URL_NEWS_FRAG;


        /**
         * List Checkin's URL
         */
        public static final String URL_CHECKINS_FRAG = "/1/classes/Locations";
        public static final String URL_CHECKINS = URL_BASE + URL_CHECKINS_FRAG;

        /**
         * PARAMS for auth
         */
        public static final String PARAM_USERNAME = "username";
        public static final String PARAM_PASSWORD = "password";


        public static final String PARSE_APP_ID = "zHb2bVia6kgilYRWWdmTiEJooYA17NnkBSUVsr4H";
        public static final String PARSE_REST_API_KEY = "N2kCY1T3t3Jfhf9zpJ5MCURn3b25UpACILhnf5u9";
        public static final String HEADER_PARSE_REST_API_KEY = "X-Parse-REST-API-Key";
        public static final String HEADER_PARSE_APP_ID = "X-Parse-Application-Id";
        public static final String CONTENT_TYPE_JSON = "application/json";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String SESSION_TOKEN = "sessionToken";


    }


    public static final class Extra {
        private Extra() {}

        public static final String NEWS_ITEM = "news_item";

        public static final String USER = "user";

    }

    public static final class Intent {
        private Intent() {}

        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "com.zeonic.ykt.";

    }

    public static class Notification {
        private Notification() {
        }

        public static final int TIMER_NOTIFICATION_ID = 1000; // Why 1000? Why not? :)
    }

    /**
     * 上海公交车接口SHB
     */
    public static final class SHB {
        public static final String URL_BASE = "http://61.129.57.96:36001";
        public static final String URL_SHB = "/Project/Ver2/carMonitor.ashx";
    }

    /**
     * 上海公交车接口XXG
     */
    public static final class XXG {
        public static final String URL_BASE = "http://xxbs.sh.gov.cn:8080";
        public static final String URL_XXG = "/weixinpage/HandlerThree.ashx";
    }

//    public static final class LBS {
//        /**
//         * SERVER ACCOUNT KEY
//         */
//        @Deprecated
//        public static final String SERVER_AK =  BootstrapApplication.getInstance().getString(R.string.LBS_SERVER_AK);
//    }

    public class ShanghaiTranspCard {
        public static final String URL_BASE = "http://220.248.75.36";
        public static final String URL_QUERY = "/handapp/PGcardAmtServlet";
    }
}


