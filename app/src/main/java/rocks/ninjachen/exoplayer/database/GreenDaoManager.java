package rocks.ninjachen.exoplayer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import rocks.ninjachen.exoplayer.BootstrapApplication;
import rocks.ninjachen.exoplayer.entity.DaoMaster;
import rocks.ninjachen.exoplayer.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ninja on 12/20/16.
 */

public class GreenDaoManager {
    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DaoMaster.DevOpenHelper devOpenHelper;


    private GreenDaoManager() {
        //创建一个数据库
        devOpenHelper = new MyDevOpenHelper(BootstrapApplication.getInstance(), "zeonic-db", null);
        DaoMaster mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            mInstance = new GreenDaoManager();
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    /**
     * 关闭数据连接
     */
    public void close() {
        if (devOpenHelper != null) {
            devOpenHelper.close();
        }
    }

    public class MyDevOpenHelper extends DaoMaster.DevOpenHelper {

        public MyDevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public MyDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            // when upgrade to 5 drop tables
            if (oldVersion < 5 && newVersion == 5) {
//                String sql = "DROP TABLE " + (true ? "IF EXISTS " : "") + "\"TRANSPORTATION_CARD\"";
//                db.execSQL(sql);
//                if(ZeonicSettings.isDebug())
//                    TransportationCardDao.dropTable(db, true);
            }
            DaoMaster.createAllTables(db, true);
//            DaoMaster.dropAllTables(db, true);
//            onCreate(db);
        }
    }
}