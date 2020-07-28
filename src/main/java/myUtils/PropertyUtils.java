package myUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author: Malakh
 * @Date: 19-6-30
 * @Description:
 */
public class PropertyUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtils.class);
    private static Properties subjectProperties = new Properties();         // subject.properties 配置文件的内容
    private static Properties jdbcProperties = new Properties();            // jdbc.properties 配置文件的内容
    private static Properties urlProperties = new Properties();             // url.properties 配置文件的内容

    static {
        try {
            subjectProperties.load(MyStringUtils.getInputStream("subject.properties"));
            jdbcProperties.load(MyStringUtils.getInputStream("jdbc.properties"));
            urlProperties.load(MyStringUtils.getInputStream("url.properties"));
        } catch (IOException e) {
            LOGGER.error("[PropertyUtils] read properties file exception", e);
        }
    }

    /**
     * 从 properties里读取值
     *
     * @param key
     * @return
     */
    private static String getValue(Properties properties, String key) {
        if (StringUtils.isEmpty(key) || properties == null) {
            return null;
        }

        return properties.getProperty(key);
    }

    /**
     * 获取访问主体的UUID
     *
     * @return
     */
    public static String getSubjectUUID() {
        return getValue(subjectProperties, "subjectUUID");
    }

    /**
     * 获取主体的出厂标识
     *
     * @return
     */
    public static String getSubjectMac() {
        return getValue(subjectProperties, "subjectMac");
    }

    public static String getJdbcUrl() {
        return getValue(jdbcProperties, "jdbc.url");
    }

    public static String getJdbcUsername() {
        return getValue(jdbcProperties, "jdbc.username");
    }

    public static String getJdbcPassword() {
        return getValue(jdbcProperties, "jdbc.password");
    }

    public static String getJdbcMinIdle() {
        return getValue(jdbcProperties, "jdbc.pool.minIdle");
    }

    public static String getJdbcMaxActive() {
        return getValue(jdbcProperties, "jdbc.pool.maxActive");
    }

    public static String getJdbcInitSize() {
        return getValue(jdbcProperties, "jdbc.pool.initSize");
    }

    public static String getUrl(String urlKey) {
        String baseUrl = getValue(urlProperties, "baseUrl");
        String urlValue = getValue(urlProperties, urlKey);
        if (StringUtils.isEmpty(urlValue)) {
            return null;
        }
        return baseUrl + urlValue;
    }

}
