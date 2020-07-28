package service;

import access.main.ObjectTcpThread;
import bean.AccessLog;
import bean.ContractInfo;
import bean.DeviceInfo;
import bean.PunishInfo;
import com.google.common.collect.Maps;
import myUtils.HttpUtils;
import myUtils.JsonParseUtils;
import myUtils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author: Malakh
 * @Date: 2020/2/26
 * @Description: 请求web服务的相关接口。通过web接口访问EthereumWeb端获取设备、访问日志等相关信息
 * 备注：暂未使用这种方式，改为使用直连DB的方式
 */
public class HttpRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectTcpThread.class);

    /**
     * 获取设备相关信息
     *
     * @param deviceUid
     * @return
     */
    public static DeviceInfo httpQueryDeviceByUid(String deviceUid) {
        if (StringUtils.isEmpty(deviceUid)) {
            return null;
        }
        String url = PropertyUtils.getUrl("device.queryByUid");
        Map<String, Object> paraMap = Maps.newHashMap();
        paraMap.put("deviceUid", deviceUid);
        // Get 或 Post 请求均可使用
        // String responseJson = HttpUtils.httpPost(baseUrl, paraMap);
        String responseJson = HttpUtils.httpGet(url, paraMap);
//        LOGGER.info("=== [httpQueryDeviceByUid] responseJson:{}", responseJson);

        return (DeviceInfo) JsonParseUtils.getObjectFromHomePage(responseJson, DeviceInfo.class);
    }

    /**
     * 获取设备相关信息
     *
     * @param deviceMac
     * @return
     */
    public static DeviceInfo httpQueryDeviceByMac(String deviceMac) {
        if (StringUtils.isEmpty(deviceMac)) {
            return null;
        }
        String url = PropertyUtils.getUrl("device.queryByMac");
        Map<String, Object> paraMap = Maps.newHashMap();
        paraMap.put("deviceMac", deviceMac);
        // Get 或 Post 请求均可使用
        // String responseJson = HttpUtils.httpPost(baseUrl, paraMap);
        String responseJson = HttpUtils.httpGet(url, paraMap);
//        LOGGER.info("=== [httpQueryDeviceByMac] responseJson:{}", responseJson);

        return (DeviceInfo) JsonParseUtils.getObjectFromHomePage(responseJson, DeviceInfo.class);
    }

    /**
     * 获取设备惩罚信息
     *
     * @param deviceUid
     * @return
     */
    public static List<PunishInfo> httpQueryPunishByUid(String deviceUid) {
        if (StringUtils.isEmpty(deviceUid)) {
            return null;
        }
        String url = PropertyUtils.getUrl("punish.queryByUid");
        Map<String, Object> paraMap = Maps.newHashMap();
        paraMap.put("deviceUid", deviceUid);
        // Get 或 Post 请求均可使用
        // String responseJson = HttpUtils.httpPost(baseUrl, paraMap);
        String responseJson = HttpUtils.httpGet(url, paraMap);
//        LOGGER.info("=== [httpQueryPunishByUid] responseJson:{}", responseJson);

        return JsonParseUtils.getListFromHomePage(responseJson, PunishInfo.class);
    }

    /**
     * 获取设备专属合约和公共合约
     *
     * @param deviceUid
     * @return
     */
    public static List<ContractInfo> httpQueryContractsByUid(String deviceUid) {
        if (StringUtils.isEmpty(deviceUid)) {
            return null;
        }
        String url = PropertyUtils.getUrl("contract.queryByUid");
        Map<String, Object> paraMap = Maps.newHashMap();
        paraMap.put("deviceUid", deviceUid);
        // Get 或 Post 请求均可使用
        // String responseJson = HttpUtils.httpPost(baseUrl, paraMap);
        String responseJson = HttpUtils.httpGet(url, paraMap);
//        LOGGER.info("=== [httpQueryContractsByUid] responseJson:{}", responseJson);

        return JsonParseUtils.getListFromHomePage(responseJson, ContractInfo.class);
    }

    /**
     * 添加访问日志
     *
     * @param accessLog
     * @return
     */
    public static AccessLog httpAddAccessLog(AccessLog accessLog) {
        if (accessLog == null) {
            return null;
        }
        String url = PropertyUtils.getUrl("access.addLog");
        String responseJson = HttpUtils.httpPostJson(url, accessLog);
//        LOGGER.info("=== [httpAddAccessLog] responseJson:{}", responseJson);

        return (AccessLog) JsonParseUtils.getObjectFromHomePage(responseJson, AccessLog.class);
    }

    /**
     * 更新访问日志，添加访问结果
     *
     * @param accessLog
     * @return
     */
    public static AccessLog httpUpdateAccessLog(AccessLog accessLog) {
        if (accessLog == null) {
            return null;
        }
        String url = PropertyUtils.getUrl("access.updateLog");
        String responseJson = HttpUtils.httpPostJson(url, accessLog);
//        LOGGER.info("=== [httpUpdateAccessLog] responseJson:{}", responseJson);

        return (AccessLog) JsonParseUtils.getObjectFromHomePage(responseJson, AccessLog.class);
    }

}
