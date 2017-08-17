package rocks.ninjachen.exoplayer.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import rocks.ninjachen.exoplayer.BootstrapApplication;
import rocks.ninjachen.exoplayer.entity.ZeonicCity;

/**
 * Created by ninja on 8/30/16.
 */

public class ExtDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "cities1.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static ExtDatabase instance;
    private String CITY_TABLE = "cities";


    public ExtDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized ExtDatabase getInstance() {
//        if(instance == null){
            instance = new ExtDatabase(BootstrapApplication.getInstance());
//        }
        return instance;
    }

    /**
     *
     * @param c
     * @return poiResult or null
     */
    public static ZeonicCity getCityFromCursor(Cursor c) {
        try {
            int id = c.getInt(0);
            String name = c.getString(1);
            String pinyin = c.getString(2);
            String filename = c.getString(3);
            int transitAvailability = c.getInt(4);
            double lat = c.getDouble(5);
            double lng = c.getDouble(6);
            ZeonicCity city = new ZeonicCity();
            city.setId(Long.valueOf(id));
            city.setName(name);
            city.setPinyin(pinyin);
            city.setFilename(filename);
            city.setTransitAvailability(transitAvailability);
            city.setLatitude(lat);
            city.setLongitude(lng);
            return city;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cursor getCities() {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"city_id", "name", "pinyin", "file_name", "transit_availability", "latitude", "longitude"};
        qb.setTables(CITY_TABLE);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        return c;

    }

    public ZeonicCity getCity(long cityId) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            String [] sqlSelect = {"city_id", "name", "pinyin", "file_name", "transit_availability", "latitude", "longitude"};

            qb.setTables(CITY_TABLE);
            String condition = "city_id="+cityId;
            Cursor c = qb.query(db, sqlSelect, condition, null,
                    null, null, null);
            if(c.moveToFirst()){
                //build poiResult
//                int id = c.getInt(0);
//                String name = c.getString(1);
//                String pinyin = c.getString(2);
//                String filename = c.getString(3);
//                int transitAvailability = c.getInt(4);
//                double lat = c.getDouble(5);
//                double lng = c.getDouble(6);
//                ZeonicCity poiResult = new ZeonicCity();
//                poiResult.setId(id);
//                poiResult.setName(name);
//                poiResult.setPinyin(pinyin);
//                poiResult.setFilename(filename);
//                poiResult.setTransitAvailability(transitAvailability);
//                poiResult.setLatitude(lat);
//                poiResult.setLongitude(lng);
                return getCityFromCursor(c);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    /**
     * get poiResult by poiResult name
     * @param cityName
     */
    public ZeonicCity getCity(String cityName) {
        try {
            SQLiteDatabase db = getReadableDatabase();
//            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//            String [] sqlSelect = {"city_id", "name", "pinyin", "file_name", "transit_availability", "latitude", "longitude"};
//            qb.setTables(CITY_TABLE);
//            String condition = "name="+cityName;
////            Cursor c = qb.query(db, sqlSelect, condition, null,
////                    null, null, null);
            Cursor c = db.rawQuery(
                    "SELECT city_id, name, pinyin, file_name, transit_availability, latitude, longitude FROM "
                            + CITY_TABLE +
                            " WHERE name=?",
                    new String[] {cityName}
            );
            if(c.moveToFirst()){
                //build poiResult
//                int id = c.getInt(0);
//                String name = c.getString(1);
//                String pinyin = c.getString(2);
//                String filename = c.getString(3);
//                int transitAvailability = c.getInt(4);
//                double lat = c.getDouble(5);
//                double lng = c.getDouble(6);
//                ZeonicCity poiResult = new ZeonicCity();
//                poiResult.setId(id);
//                poiResult.setName(name);
//                poiResult.setPinyin(pinyin);
//                poiResult.setFilename(filename);
//                poiResult.setTransitAvailability(transitAvailability);
//                poiResult.setLatitude(lat);
//                poiResult.setLongitude(lng);
                return getCityFromCursor(c);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }
}