package rocks.ninjachen.hbridgek.authenticator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import rocks.ninjachen.hbridgek.BootstrapApplication;
import rocks.ninjachen.exoplayer.R;
import rocks.ninjachen.hbridgek.core.ZeonicSettings;
import rocks.ninjachen.hbridgek.entity.ZeonicUser;
import rocks.ninjachen.hbridgek.ui.MainActivity;
import rocks.ninjachen.hbridgek.util.ZeonicUtils;

import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ninja on 1/13/17.
 */
public class ZeonicLoginManager {
    private static ZeonicLoginManager instance;
    private ZeonicUser loginedUser;
    private static final String SHARED_PREFERENCES_NAME = "ZeonicLoginManager";
    String LOGIN_USER_TAG = "LOGIN_USER";
    public static final int REQUEST_FOR_LOGIN = 9999;
    // todo should be fixed when use in business logic
    final private Class<?> loginActivity = MainActivity.class;

    public synchronized static ZeonicLoginManager getInstance() {
        if (instance == null)
            instance = new ZeonicLoginManager();
        return instance;
    }

    /**
     * return logined user
     * if not login,return null
     *
     * @return
     */
    public synchronized ZeonicUser getLoggedUser() {
        ZeonicUser zeonicUser = getCachedUser();
        if (zeonicUser != null) {
            zeonicUser = zeonicUser.isLogin() ? zeonicUser : null;
        }
        return zeonicUser;
    }

    private ZeonicUser getCachedUser() {
        String json = BootstrapApplication.getInstance()
                .getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
                .getString(LOGIN_USER_TAG, null);
//        Timber.e("getCachedUser " + json);
        if (json != null) {
            try {
                ZeonicUser zeonicUser = new Gson().fromJson(json, ZeonicUser.class);
//                Timber.e("zeonicUser " + zeonicUser);
                return zeonicUser;
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public void login(@Nullable ZeonicUser loginedUser) {
        Timber.e("login " + (loginedUser == null ? "null" : loginedUser.getUsername()));
        if (loginedUser == null) return;
        // save to shared-preference
        loginedUser.setLogin(true);
        setCachedUser(loginedUser);
    }

    public void setCachedUser(@Nullable ZeonicUser user) {
        if (user == null) return;
        String json = new Gson().toJson(user);
        if (!ZeonicUtils.isEmpty(json)) {
//            Timber.e("login " + json);
            BootstrapApplication.getInstance()
                    .getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
                    .edit()
                    .putString(LOGIN_USER_TAG, json)
                    .commit();
            this.loginedUser = user;
        }
    }


    /**
     * @return phone number or null when not login
     */
    public String getLoggedUserName() {
        ZeonicUser loggedUser = getLoggedUser();
        if (loggedUser != null) {
            String phoneNo = loggedUser.getUsername();
            return phoneNo;
        }
        return null;
    }

    /**
     * @return phone number or lastPhone number
     */
    public String getCachedUserPhoneNumber() {
        ZeonicUser loginedUser = getCachedUser();
        if (loginedUser != null) {
            String phoneNo = loginedUser.getUsername();
            return phoneNo;
        }
        return null;
    }

    public boolean isLogin() {
        if (getLoggedUser() == null) {
            return false;
        } else {
            ZeonicUser loginedUser = getLoggedUser();
            return loginedUser.isLogin();
        }
    }

    public void logout() {
        ZeonicUser loginedUser = getLoggedUser();
        if (loginedUser != null) {
            Timber.e("user [%s] log out", loginedUser.getUsername());
            loginedUser.setLogin(false);
            setCachedUser(loginedUser);
        }else {
            Timber.d("call the logout(), but already logged out");
        }
        sendLogoutBroadcast();
    }

    /**
     * used after getUserInfo() sync
     * if username match , update
     * else do nothing
     */
    public void updateLoginUser(ZeonicUser newUser) {
        Timber.d("try to updateLoginUser info " + (newUser == null ? "null" : new Gson().toJson(newUser)));
        ZeonicUser loginedUser = getLoggedUser();
        if (loginedUser != null && newUser != null) {
            if (loginedUser.getUsername() != null && loginedUser.getUsername().equals(newUser.getUsername())) {
                if (!ZeonicUtils.isEmpty(newUser.getNickname())) {
                    loginedUser.setNickname(newUser.getNickname());
                }
                boolean needUpdatePortrait = false;
                if (!ZeonicUtils.isEmpty(newUser.getUserPortrait())) {
                    needUpdatePortrait = !newUser.getUserPortrait().equals(loginedUser.getUserPortrait());
                    loginedUser.setUserPortrait(newUser.getUserPortrait());
                }
               login(loginedUser);
                if(needUpdatePortrait)
                    sendUserPortraitChangeBroadcast();
            }
//            if(!ZeonicUtils.isEmpty(newUser.get())){
//                loginedUser.setUserPortrait(newUser.getUserPortrait());
//            }
        }
    }

    public void sendLoginBroadcast() {
        Intent i = new Intent(ZeonicSettings.USER_LOGIN);
        BootstrapApplication.getInstance().sendBroadcast(i);
    }
    public void sendUserPortraitChangeBroadcast() {
        Intent i = new Intent(ZeonicSettings.USER_PORTRAIT_CHANGE);
        BootstrapApplication.getInstance().sendBroadcast(i);
    }

    public void sendLogoutBroadcast() {
        Intent i = new Intent(ZeonicSettings.USER_LOGOUT);
        BootstrapApplication.getInstance().sendBroadcast(i);
    }

//    public BroadcastReceiver createLoginAndLogoutReceiver(final MapActivity activity) {
//        BroadcastReceiver receiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if(intent != null && !ZeonicUtils.isEmpty(intent.getAction())){
//                    switch (intent.getAction()){
//                        case ZeonicSettings.USER_LOGIN:
//                            Timber.e("User login event received");
//                            activity.syncUserIconAsMyLocationIcon();
//                            break;
//                        case ZeonicSettings.USER_PORTRAIT_CHANGE:
//                            Timber.e("User portrait change  event received");
//                            activity.syncUserIconAsMyLocationIcon();
//                            break;
//                        case ZeonicSettings.USER_LOGOUT:
//                            Timber.e("User logout event received");
////                            Timber.e("ready to show dialog of BUS_ARRIVING_REMINDER_SHOW_DIALOG_ACTION");
//                            activity.syncUserIconAsMyLocationIcon();
//                            break;
//                    }
//                }
//            }
//        };
//        return receiver;
//    }

    public AlertDialog buildReloginDialog(final Activity activity) {
        return new AlertDialog.Builder(activity)
                .setMessage(R.string.user_logged_out_click_to_relogin)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(activity, loginActivity);
                        activity.startActivityForResult(i, REQUEST_FOR_LOGIN);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finish();
                    }
                }).create();
    }

    public AlertDialog buildLoginDialog(final Activity activity) {
        return new AlertDialog.Builder(activity)
                .setMessage(R.string.user_not_login_click_to_login)
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(activity, loginActivity);
                        activity.startActivityForResult(i, REQUEST_FOR_LOGIN);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finish();
                    }
                }).create();
    }

}
