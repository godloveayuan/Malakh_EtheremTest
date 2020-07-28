package bean;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @Author: Malakh
 * @Date: 2020/2/11
 * @Description: 静态常量
 */
public class ConstantValue {
    public static final Integer PAGE_SIZE_DEFAULT = 20;         // 分页参数默认页面大小
    public static final Integer PAGE_INDEX_DEFAULT = 1;         // 分页参数默认页码

    public static final String QUERY_ALL_STR = "all";           // 查询所有
    public static final Integer QUERY_ALL_INT = -1;             // 查询所有
    public static final String QUERY_ALL_NAME = "不限";          // 不限

    public static final String COOKIE_KEY_LOGIN_USER = "loginUser";    // 登录cookie的key
    public static final String COOKIE_KEY_LOGIN_CHECK = "loginCheck";    // 登录cookie的key

    public static Map<String, String> DEVICE_TYPE_MAP = Maps.newHashMap();              // 设备类型中英对照
    public static Map<String, String> DEVICE_ATTR_TYPE_MAP = Maps.newHashMap();         // 设备类型属性中英对照
    public static Map<String, String> DEVICE_ATTR_POSITION_MAP = Maps.newHashMap();     // 设备位置属性中英对照
    public static Map<String, String> DEVICE_ATTR_SYSTEM_MAP = Maps.newHashMap();       // 设备系统属性中英对照
    public static Map<String, String> CONTRACT_TYPE_MAP = Maps.newHashMap();            // 合约类型中英对照
    public static Map<String, String> TIME_UNIT_MAP = Maps.newHashMap();                // 时间单位中英对照

    public static Map<Integer, String> STATUS_MAP = Maps.newHashMap();                  // 状态对照
    public static Map<Integer, String> SECURITY_RULE_TYPE_MAP = Maps.newHashMap();      // 安全规则对照

    public static Map<Integer, String> PUNISH_TYPE_MAP = Maps.newHashMap();             // 惩罚类型对照
    public static Map<Integer, String> PUNISH_STATUS_MAP = Maps.newHashMap();           // 惩罚状态对照

    public static Map<Integer, String> ACCESS_FAIL_REASON_MAP = Maps.newHashMap();      // 访问失败原因对照

    public static Map<String, String> ACCESS_Allow_MAP = Maps.newHashMap();
    public static Map<String, String> OPERATE_TYPE_MAP = Maps.newHashMap();

    static {
        DEVICE_TYPE_MAP.put("wifi", "wifi设备");
        DEVICE_TYPE_MAP.put("bluetooth", "蓝牙设备");
        DEVICE_TYPE_MAP.put("other", "其他");
        DEVICE_TYPE_MAP.put(QUERY_ALL_STR, QUERY_ALL_NAME);

        DEVICE_ATTR_TYPE_MAP.put("displayer", "显示设备");
        DEVICE_ATTR_TYPE_MAP.put("controller", "控制设备");
        DEVICE_ATTR_TYPE_MAP.put("terminal", "终端设备");
        DEVICE_ATTR_TYPE_MAP.put("other", "其他");
        DEVICE_ATTR_TYPE_MAP.put(QUERY_ALL_STR, QUERY_ALL_NAME);

        DEVICE_ATTR_POSITION_MAP.put("drawingRoom", "客厅");
        DEVICE_ATTR_POSITION_MAP.put("bedRoom", "卧室");
        DEVICE_ATTR_POSITION_MAP.put("secondaryBedRoom", "次卧");
        DEVICE_ATTR_POSITION_MAP.put("studyRoom", "书房");
        DEVICE_ATTR_POSITION_MAP.put(QUERY_ALL_STR, QUERY_ALL_NAME);

        DEVICE_ATTR_SYSTEM_MAP.put("lighting", "灯光系统");
        DEVICE_ATTR_SYSTEM_MAP.put("temperature", "温度系统");
        DEVICE_ATTR_SYSTEM_MAP.put(QUERY_ALL_STR, QUERY_ALL_NAME);

        STATUS_MAP.put(0, "不可用");
        STATUS_MAP.put(1, "可用");
        STATUS_MAP.put(QUERY_ALL_INT, QUERY_ALL_NAME);

        CONTRACT_TYPE_MAP.put("public", "公共合约");
        CONTRACT_TYPE_MAP.put("private", "专属合约");
        CONTRACT_TYPE_MAP.put(QUERY_ALL_STR, QUERY_ALL_NAME);

        TIME_UNIT_MAP.put("second", "秒");
        TIME_UNIT_MAP.put("minute", "分钟");
        TIME_UNIT_MAP.put("hour", "小时");
        TIME_UNIT_MAP.put("day", "天");

        SECURITY_RULE_TYPE_MAP.put(1, "访问频率限制");
        SECURITY_RULE_TYPE_MAP.put(2, "身份认证限制");
        SECURITY_RULE_TYPE_MAP.put(3, "越权访问限制");
        SECURITY_RULE_TYPE_MAP.put(0, "其他");
        SECURITY_RULE_TYPE_MAP.put(QUERY_ALL_INT, QUERY_ALL_NAME);

        PUNISH_TYPE_MAP.put(1, "隔离");
        PUNISH_TYPE_MAP.put(2, "驱逐");
        PUNISH_TYPE_MAP.put(QUERY_ALL_INT, QUERY_ALL_NAME);

        PUNISH_STATUS_MAP.put(0, "惩罚解除");
        PUNISH_STATUS_MAP.put(1, "惩罚中");
        PUNISH_STATUS_MAP.put(QUERY_ALL_INT, QUERY_ALL_NAME);

        ACCESS_FAIL_REASON_MAP.put(1, "访问频率过高");
        ACCESS_FAIL_REASON_MAP.put(2, "身份认证不通过");
        ACCESS_FAIL_REASON_MAP.put(3, "访问权限不通过");
        ACCESS_FAIL_REASON_MAP.put(4, "设备惩罚中");
        ACCESS_FAIL_REASON_MAP.put(5, "未失败");
        ACCESS_FAIL_REASON_MAP.put(QUERY_ALL_INT, QUERY_ALL_NAME);

        ACCESS_Allow_MAP.put("true", "允许");
        ACCESS_Allow_MAP.put("false", "拒绝");
        ACCESS_Allow_MAP.put(QUERY_ALL_STR, QUERY_ALL_NAME);

        OPERATE_TYPE_MAP.put("read", "read");
        OPERATE_TYPE_MAP.put("control", "control");
        OPERATE_TYPE_MAP.put(QUERY_ALL_STR, QUERY_ALL_NAME);
    }
}
