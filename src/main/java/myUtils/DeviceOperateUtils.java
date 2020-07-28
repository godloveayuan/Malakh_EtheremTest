package myUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 控制灯的命令语句
 * 操作由5部分组成：[1]命令类型 [2]用户名 [3]密码 [4]设备地址 [5]操作内容
 * 搜索所有设备： 0D wm 123456 searchAll
 * 控制单个设备： 0A wm 123456 18-fe-34-a4-8c-2d 1
 */
public class DeviceOperateUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceOperateUtils.class);

    public static final String userName = "wm";           // 点灯操作用户名
    public static final String password = "123456";       // 点灯操作密码
    public static final String turnOn = "1";              // 打开设备
    public static final String turnOff = "0";             // 关闭设备
    public static final String searchAll = "searchall";   // 搜索所有设备
    public static final String redLight = "18-fe-34-a4-8c-2d";           // 黄灯
    public static final String yellowLight = "18-fe-34-a4-8c-b7";        // 红灯
    public static final String searchCMD = "0D";          // 搜索命令
    public static final String controlCMD = "0A";         // 控制命令


    /**
     * 获取搜索设备的命令
     */
    public static String getSearchAllCmd() {
        StringBuilder sb = new StringBuilder();
        sb.append(searchCMD + " ");
        sb.append(userName + " ");
        sb.append(password + " ");
        sb.append(searchAll);

        return sb.toString();
    }

    /**
     * 获取控制设备的命令
     *
     * @param device
     * @param operateData
     * @return
     */
    public static String getControlLightCmd(String device, String operateData) {
        StringBuilder sb = new StringBuilder();
        sb.append(controlCMD + " ");
        sb.append(userName + " ");
        sb.append(password + " ");
        sb.append(device + " ");
        sb.append(operateData);

        return sb.toString();
    }

    /**
     * 获取设备状态
     *
     * @param message
     * @return
     */
    public static Boolean getDeviceStatus(String message, String device) {
        if (StringUtils.isEmpty(message)) {
            LOGGER.error("[getDeviceStatus] message is null");
            return null;
        }
        String[] splitArr = message.split(" ");
        int size = splitArr.length;
        for (int index = 0; index < size; index++) {
            String data = splitArr[index];
            if (StringUtils.isNotEmpty(data) && StringUtils.equalsIgnoreCase(data.trim(), device)) {
                String status = splitArr[index + 1];

                if (StringUtils.isNotEmpty(status) && StringUtils.equalsIgnoreCase("1", status.trim())) {
                    return true;
                } else if (StringUtils.isNotEmpty(status) && StringUtils.equalsIgnoreCase("0", status.trim())) {
                    return false;
                }
            }
        }
        return null;
    }

}
