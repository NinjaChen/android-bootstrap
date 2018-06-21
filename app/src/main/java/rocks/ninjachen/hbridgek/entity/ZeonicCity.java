package rocks.ninjachen.hbridgek.entity;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by ninja_chen on 14-3-13.
 * Contact Model
 */
@Entity
public class ZeonicCity implements Cloneable, Serializable, Sectionable {
    private static final long serialVersionUID = 1L;

    @Id
    @SerializedName("city_id")
    private Long id;
    @SerializedName("city_name")
    private String name;
    @SerializedName("pinyin")
    private String pinyin;

    // is upcase
    @Transient
    private Character searchKey = Character.MIN_VALUE;

    private String filename;
    @SerializedName("transit_availability")
    private int transitAvailability;

    @Transient
    @SerializedName("location")
    private double[] location;
    private double latitude;
    private double longitude;
    private String temp;

    @Generated(hash = 685723540)
    public ZeonicCity() {
    }

    @Generated(hash = 1388622053)
    public ZeonicCity(Long id, String name, String pinyin, String filename,
                      int transitAvailability, double latitude, double longitude,
                      String temp) {
        this.id = id;
        this.name = name;
        this.pinyin = pinyin;
        this.filename = filename;
        this.transitAvailability = transitAvailability;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temp = temp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
        if(!TextUtils.isEmpty(pinyin)
                && searchKey != null && searchKey == Character.MIN_VALUE){
            this.searchKey = this.pinyin.toCharArray()[0];
            this.searchKey = Character.toUpperCase(this.searchKey);
        }
    }

    public Character getSearchKey() {
        if(!TextUtils.isEmpty(pinyin)
                && searchKey != null && searchKey == Character.MIN_VALUE){
            this.searchKey = this.pinyin.charAt(0);
            this.searchKey = Character.toUpperCase(this.searchKey);
        }
        return searchKey;
    }

    public void setSearchKey(Character searchKey) {
        this.searchKey = searchKey;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getTransitAvailability() {
        return transitAvailability;
    }

    public void setTransitAvailability(int transitAvailability) {
        this.transitAvailability = transitAvailability;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }
// private final void createSeachKey(String nickname) {
//
//        if (TextUtils.isEmpty(nickname)) {
//            return;
//        }
//
//        nicknamePinyin = PinyinHelper.getInstance().getPinyins(nickname, "");
//
//        if (nicknamePinyin != null && nicknamePinyin.length() > 0) {
//            char key = nicknamePinyin.charAt(0);
//            if (key >= 'A' && key <= 'Z') {
//
//            } else if (key >= 'a' && key <= 'z') {
//                key -= 32;
//            } else if (key == '★' ) {
//                key = '★';
//            }else {
//                // unexpected first char
//                key = '#';
//            }
//            searchKey = key;
//        } else {
//            searchKey = '#';
//        }
//    }

    @Override
    public ZeonicCity clone() throws CloneNotSupportedException {
        return (ZeonicCity) super.clone();
    }

    @Override
    public String getSectionName() {
        return String.valueOf(getSearchKey());
    }

    public String getTemp() {
        return this.temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    // format before save
    public void format() {
        if(!TextUtils.isEmpty(pinyin)
                && searchKey != null && searchKey == Character.MIN_VALUE){
            this.searchKey = this.pinyin.charAt(0);
            this.searchKey = Character.toUpperCase(this.searchKey);
        }
        if(getLocation() != null){
            setLatitude(getLocation()[0]);
            setLongitude(getLocation()[1]);
        }
    }

    /**
     * remove space in pinyin,and return
     * @return
     */
    public String removeSpaceInPinyin() {
        String s = getPinyin().replaceAll("\\s+","");
        this.pinyin = s;
        return s;
    }

//    public static class CharIntConverter implements PropertyConverter<Character, Integer> {
//
//        @Override
//        public Character convertToEntityProperty(Integer databaseValue) {
//            return Character.valueOf((char)databaseValue.intValue());
//        }
//
//        @Override
//        public Integer convertToDatabaseValue(Character entityProperty) {
//            return Integer.valueOf(entityProperty);
//        }
//    }
}

