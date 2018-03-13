package rocks.ninjachen.exoplayer.util;

import android.support.annotation.Nullable;
import android.util.Patterns;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rocks.ninjachen.exoplayer.BootstrapApplication;
import rocks.ninjachen.exoplayer.R;

/**
 * Created by ninja on 7/13/16.
 */

public class ValidationUtils {
    public static final String NAME_REGULAR_EXPRESS = "[^a-zA-Z0-9\\u4e00-\\u9fa5_\\-]+";
    public static final String CHINESE_CHAR_REGULAR_EXPRESS = "[\\u4e00-\\u9fa5]+";

    public static void main(String[] args){
//        String s = "2030";
//        System.out.println(isInteger(s));
        String nickname = "我是1个🍎?🇳人";
//        String reg = "^1(3|5|7|8|4)\\d{9}$";
        nickname = filterNickName(nickname);
//        String text = "1890-12-23";
//        System.out.print(isMatchYearMonthDay(text));

//        String str = "阿萨德的飞士大夫1000米后阿斯顿发是";
//        String regex = "\\d*米";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(str);
//        while(m.find()) {
//            if(!"".equals(m.group()))
//                System.out.println(m.group());
//        }
    }

    /**
     * 验证日期字符串是否是YYYY-MM-DD格式
     *
     * @param str
     * @return
     */
    public static boolean isDataFormat(String str) {
        boolean flag = false;
        //String regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }


//    public static boolean isNumeric1(@Nullable String str) {
//
//    }
    /**
     * 功能：判断字符串是否为数字,整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(@Nullable String str) {
        if(ZeonicUtils.isEmpty(str)) return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDouble(@Nullable String str) {
        if(ZeonicUtils.isEmpty(str)) return false;
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    private static Hashtable<String, String> GetAreaCode() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * check the IDcard owner is male or not
     *
     * @param IDStr
     * @return
     */
    public static boolean isMale(String IDStr) {
        if (IDStr == null || IDStr.isEmpty())
            return false;
        if (IDStr.length() != 15 && IDStr.length() != 18)
            return false;
        Integer gender = 0;
        if (IDStr.length() == 18) {
            gender = Integer.valueOf(IDStr.substring(16, 17));
        } else if (IDStr.length() == 15) {
            gender = Integer.valueOf(IDStr.substring(14, 15));
        }
        //if 1,3,5,7,9 => male
        if (gender % 2 == 1)
            return true;
        else
            return false;
    }

    /**
     * 功能：手机号的校验
     *
     * @param text
     * @return errorMsg, null is validate
     */
    public static String validateMobilePhone(String text) {
        String errorMsg = BootstrapApplication.getInstance().getString(R.string.please_input_valid_mobile_number);
//        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,3,5-9]))\\d{8}$");
        Pattern pattern = Pattern.compile("^1(3|5|7|8|4)\\d{9}$");
        if (text != null && pattern.matcher(text).matches()) {
            //validate
            return null;
        } else {
            return errorMsg;
        }
    }

    /**
     * 功能：座机号的校验
     * 格式:区号+座机号码+分机号码
     *
     * @param text
     * @return errorMsg, null is validate
     */
    public static String validateTelPhone(String text) {
        String errorMsg = BootstrapApplication.getInstance().getString(R.string.please_input_valid_landline_number);
        Pattern pattern = Pattern.compile("^(0[0-9]{2,3}/-)?([2-9][0-9]{6,7})+(/-[0-9]{1,4})?$");
        if (pattern.matcher(text).matches()) {
            //validate
            return null;
        } else {
            return errorMsg;
        }
    }

    /**
     * 是否符合yyyy-mm-dd的格式
     *
     * @param value
     * @return
     */
    public static boolean isMatchYearMonthDay(String value) {
        return value.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 校验没错：返回"" ; 校验出错：返回出错信息
     * @throws java.text.ParseException
     */
    public static String validateIDNO(String IDStr) {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isInteger(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return errorInfo;
            }
        } catch (Exception e) {

        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable<String, String> h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return null;
        }
        // =====================(end)=====================
        return null;
    }

    private static boolean isBankCardValid(String number) {
        if (!number.matches("^[0-9]*$"))
            return false;
        int length = number.length();
        //13~19位
        if (length < 13 || length > 19)
            return false;
        int[] bits = new int[length];
        for (int i = 0; i < number.length(); i++) {
            bits[i] = Integer.parseInt(number.substring(length - i - 1, length
                    - i));// 从最末一位开始提取，每一位上的数值
        }

        int sum = 0;
        for (int i = 1; i < bits.length; i++) {
            if ((i & 1) == 1) {  //Odd
                if (bits[i] * 2 > 9) {
                    sum += bits[i] * 2 - 9;
                } else {
                    sum += bits[i] * 2;
                }
            } else {
                sum += bits[i];
            }
        }
        if ((bits[0] + sum) % 10 == 0)
            return true;
        else {
            return false;
        }
    }

    public static boolean isUrl(@Nullable String url) {
        if(url == null) return  false;
        return Patterns.WEB_URL.matcher(url).matches();
    }

    @Deprecated
    public static String filterNickName(String input) {
//        String regEx = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字
        System.out.println("input before : " + input);
//        if(input.matches("[^a-zA-Z0-9]+")){
            String out = input.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]+","");
            System.out.println("input after : " + out);
            return out;
//        } else {
//            return input;
//        }
    }
}
