package myUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Author: Malakh
 * @Date: 19-6-30
 * @Description:
 */
public class MyStringUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyStringUtils.class);
    private static final SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String HOST_KEY = "host";
    public static final String PORT_KEY = "port";

    /**
     * 根据文件名读取输入流，兼容 IDEA 本地运行和 jar 包运行
     * 注意：需要返回InputStream，因为如果是Jar包方式，如果返回文件路径，new File() 找不到文件
     *
     * @param fileName
     * @return
     */
    public static InputStream getInputStream(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }
        if (fileName.startsWith("/")) {
            fileName = fileName.substring(1, fileName.length());
        }
        if (fileName.startsWith("./")) {
            fileName = fileName.substring(2, fileName.length());
        }

        /**
         * IDEA本地运行时读取文件
         * 使用：classloader
         * 根目录使用: ./
         */
        URL ideaResource = MyStringUtils.class.getClassLoader().getResource("./" + fileName);
        if (ideaResource != null) {
            return MyStringUtils.class.getClassLoader().getResourceAsStream("./" + fileName);
        }

        /**
         * 在 ./${fileName} 下没有找到文件，则尝试 jar包的运行方式
         * 不使用 classloader
         * 根目录使用： /
         */
        URL jarResource = MyStringUtils.class.getResource("/" + fileName);
        if (jarResource != null) {
            return MyStringUtils.class.getResourceAsStream("/" + fileName);
        }

        return null;
    }

    /**
     * 根据地址获取ip和端口号
     *
     * @param address
     * @return
     */
    public static Map<String, String> getHostAndPortFromAddress(String address) {
        if (StringUtils.isEmpty(address)) {
            return null;
        }
        Map<String, String> resultMap = Maps.newHashMap();
        String[] split = address.split(":");
        if (split.length > 0) {
            resultMap.put(HOST_KEY, split[0].trim());
        }
        if (split.length > 1) {
            resultMap.put(PORT_KEY, split[1].trim());
        }

        return resultMap;
    }

    /**
     * 检查字符串是否只由数字组成
     *
     * @param str
     * @return
     */
    public static boolean checkIsNumber(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }

        return str.matches("^[0-9]*$");
    }

    public static String parseDateToStr(Date date) {
        if (date != null) {
            return sm.format(date);
        }
        return null;
    }

    /**
     * 检查日期是否符合 yyyy-MM-dd HH:mm:ss 格式
     *
     * @param str
     * @return
     */
    public static boolean checkDateStrFormat(String str) {
        if (StringUtils.isEmpty(str)) {
            return true;
        }
        return str.matches("[12][0-9]{3}-[01][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]");
    }

    public static Date parseStrToDate(String str) {
        Preconditions.checkArgument(checkDateStrFormat(str), "时间格式%s 不合法!", str);
        try {
            return sm.parse(str);
        } catch (ParseException e) {
            throw new IllegalArgumentException("时间格式" + str + "不合法");
        }
    }

    /**
     * 时间date加上 n 分钟/小时 后的时间
     *
     * @param number
     * @param unit
     * @return
     */
    public static Date plusDate(Date date, Integer number, String unit) {
        if (number == null || number <= 0) {
            LOGGER.error("[plusDate] number:{} illegal.");
            return new Date();
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(unit)) {
            LOGGER.error("[plusDate] unit is empty.");
            return new Date();
        }

        DateTime dateTime = new DateTime(date);
        unit = unit.toLowerCase();
        switch (unit) {
            case "second":
                dateTime = dateTime.plusSeconds(number);
                break;
            case "minute":
                dateTime = dateTime.plusMinutes(number);
                break;
            case "hour":
                dateTime = dateTime.plusHours(number);
                break;
            case "day":
                dateTime = dateTime.plusDays(number);
                break;
            case "month":
                dateTime = dateTime.plusMonths(number);
                break;
            case "year":
                dateTime = dateTime.plusYears(number);
                break;
            default:
                LOGGER.error("unit:{} illegal", unit);
        }

        return dateTime.toDate();
    }

    /**
     * 获取时间date减去 n 分钟/小时 后的时间
     *
     * @param number
     * @param unit
     * @return
     */
    public static Date minusDate(Date date, Integer number, String unit) {
        if (number == null || number <= 0) {
            LOGGER.error("[minusDate] number:{} illegal.");
            return new Date();
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(unit)) {
            LOGGER.error("[minusDate] unit is empty.");
            return new Date();
        }

        DateTime dateTime = new DateTime(date);
        unit = unit.toLowerCase();
        switch (unit) {
            case "second":
                dateTime = dateTime.minusSeconds(number);
                break;
            case "minute":
                dateTime = dateTime.minusMinutes(number);
                break;
            case "hour":
                dateTime = dateTime.minusHours(number);
                break;
            case "day":
                dateTime = dateTime.minusDays(number);
                break;
            case "month":
                dateTime = dateTime.minusMonths(number);
                break;
            case "year":
                dateTime = dateTime.minusYears(number);
                break;
            default:
                LOGGER.error("unit:{} illegal", unit);
        }

        return dateTime.toDate();
    }

    /**
     * 查看时间 timeStr 是否在当前时间 n 分钟/小时 之内
     *
     * @param dateStr
     * @param timeNumber
     * @param unit
     * @return
     */
    public static boolean checkDateIsBetweenNow(String dateStr, Integer timeNumber, String unit) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(dateStr)) {
            return false;
        }
        try {
            Date parseDate = sm.parse(dateStr);

            Date minDate = minusDate(new Date(), timeNumber, unit);
            Date maxDate = plusDate(new Date(), timeNumber, unit);

            return checkAIsAfterB(parseDate, minDate) && checkAIsBeforeB(parseDate, maxDate);
        } catch (ParseException e) {
            LOGGER.error("[] Exception", e);
            return false;
        }
    }

    public static boolean checkAIsBeforeB(Date dateA, Date dateB) {
        return dateA.before(dateB);
    }

    public static boolean checkAIsAfterB(Date dateA, Date dateB) {
        return dateA.after(dateB);
    }
}
