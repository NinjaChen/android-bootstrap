package rocks.ninjachen.exoplayer.ui;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rocks.ninjachen.exoplayer.BootstrapApplication;
import rocks.ninjachen.exoplayer.R;
import rocks.ninjachen.exoplayer.core.BootstrapService;
import rocks.ninjachen.exoplayer.events.FakeEvent;
import timber.log.Timber;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

/**
 * Base activity for a Bootstrap activity which does not use fragments.
 */
public abstract class BootstrapActivity extends AppCompatActivity {

    @Inject
    protected EventBus eventBus;
    @Inject
    protected BootstrapService bootstrapService;
    private ProgressDialog progressDialogWithMessage;
    private AlarmManager mAlarmMgr;
    private PendingIntent mAlarmIntent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BootstrapApplication.component().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    @Override
    public void setContentView(final int layoutResId) {
        super.setContentView(layoutResId);

        // Used to inject views with the Butterknife library
        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            // This is the home button in the top left corner of the screen.
            case android.R.id.home:
                // Don't call finish! Because activity could have been started by an
                // outside activity and the home button would not operated as expected!
                final Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        if (progressDialogWithMessage != null) {
            progressDialogWithMessage.dismiss();
            progressDialogWithMessage = null;
        }
        dismissDialog(0);
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        showDialog(0);
    }

    /**
     * show alert dialog with msg, only with confirm button
     * see showProgress
     * @param msg
     */
    protected Dialog showAlertDialog(String msg) {
        return new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    /**
     * Show progress dialog with message
     */
    @SuppressWarnings("deprecation")
    protected void showProgress(String message) {
        if (progressDialogWithMessage != null) {
            progressDialogWithMessage.dismiss();
            progressDialogWithMessage = null;
        }
        progressDialogWithMessage = new ProgressDialog(this);
        progressDialogWithMessage.setMessage(getResources().getString(R.string.request_date_ing));
        progressDialogWithMessage.setIndeterminate(true);
        progressDialogWithMessage.setCancelable(true);
        progressDialogWithMessage.show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getResources().getString(R.string.request_date_ing));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        return dialog;
    }

    /**
     * FakeEvent should never be fired, only to avoid EventBusException
     * Related with eventBus.register(this)
     * @param event
     */
    @Subscribe
    public void onFakeEvent(FakeEvent event){
        Timber.e("FakeEvent should never be fired");
    }


    /**
     * fire a scheduler  which will send a intent every {repeatInterval} second
     * @param repeatInterval in second
     * @param actionName in broadcast action name
     */
    protected void startScheduler(int repeatInterval, String actionName) {
        Timber.i(getClass().getSimpleName() + ":startScheduler");
        Context context = this;
        if (mAlarmMgr == null)
            mAlarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(actionName);
        if (mAlarmIntent == null)
            mAlarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        /** repeatInterval's unit is second, it is not exact from 4~20second **/
        /**
         08-12 15:51:34.190
         08-12 15:51:54.380
         08-12 15:51:58.730
         08-12 15:52:14.200
         08-12 15:52:34.200
         08-12 15:52:54.200
         **/
//        int repeatInterval = 15;
        //setInexactRepeating
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        long triggerAt = calendar.getTimeInMillis();
        mAlarmMgr.setRepeating(AlarmManager.RTC,
                calendar.getTimeInMillis(),
//                SystemClock.elapsedRealtime(),
                 10,
                mAlarmIntent);
    }

    public void showSoftKeyboard(final View view) {
        Timber.e("showSoftKeyboard");
        view.post(new Runnable() {
            @Override
            public void run() {
                Timber.e("start to view.requestFocus() ");

                if (view.requestFocus()) {
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    Timber.e("view.requestFocus() success");

                    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT, new ResultReceiver(new Handler()
                    ) {
                        @Override
                        protected void onReceiveResult(int resultCode, Bundle resultData) {
                            Timber.e("showSoftKeyboard onReceiveResult:result code " + resultCode);
                            super.onReceiveResult(resultCode, resultData);
                        }
                    });
                } else {
                    Timber.e("view.requestFocus() success");
                }
            }
        });
    }

    // fit with the actionbar height
    public int getStatusBarHeight() {
        int result = -1;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    /**
     * Avoid bug describe in https://stackoverflow.com/a/18306258/1323374
     */
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }
}
