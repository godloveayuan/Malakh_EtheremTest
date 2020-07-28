package service;

import bean.DeviceInfo;
import dao.DeviceInfoDao;

import java.util.List;

/**
 * @Author: Malakh
 * @Date: 2020/2/24
 * @Description:
 */
public class DeviceInfoService {
    private static DeviceInfoService deviceInfoService;

    /**
     * 根据uid查询设备信息
     *
     * @param deviceUid
     * @return
     */
    public static DeviceInfo queryByUid(String deviceUid) {
        return DeviceInfoDao.selectByUid(deviceUid);
    }

    /**
     * 根据mac查询设备信息
     *
     * @param deviceMac
     * @return
     */
    public static DeviceInfo queryByMac(String deviceMac) {
        return DeviceInfoDao.selectByMac(deviceMac);
    }

    /**
     * 查询所有终端设备信息
     *
     * @return
     */
    public static List<DeviceInfo> queryAllTerminal() {
        return DeviceInfoDao.selectAllTerminal();
    }

    /**
     * 设置设备状态为不可用
     *
     * @param deviceUid
     */
    public static void setDeviceUnable(String deviceUid) {
        DeviceInfoDao.updateStatus(deviceUid, 0);
    }
}