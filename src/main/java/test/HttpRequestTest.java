package test;

import bean.AccessLog;
import bean.ContractInfo;
import bean.DeviceInfo;
import bean.PunishInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.HttpRequestService;

import java.util.List;

/**
 * @Author: Malakh
 * @Date: 2020/2/25
 * @Description: 测试类
 */
public class HttpRequestTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestTest.class);

    public static void main(String[] args) {
//        httpQueryDeviceByUidTest();
//        httpQueryDeviceByMacTest();

//        httpQueryPunishByUidTest();
//        httpQueryContractsByUidTest();

//        httpAddAccessLogTest();
//        httpUpdateAccessLogTest();
    }

    public static void httpQueryDeviceByUidTest() {
//        DeviceInfo deviceInfo = HttpRequestService.httpQueryDeviceByUid("f1c91b61-c9f1-4381-93ea-eadb30b2cc7a");
//        System.out.println("deviceInfo:" + deviceInfo);
    }

    public static void httpQueryDeviceByMacTest() {
//        DeviceInfo deviceInfo = HttpRequestService.httpQueryDeviceByMac("18-fe-34-a4-8c-b7");
//        System.out.println("deviceInfo:" + deviceInfo);
    }

    public static void httpQueryPunishByUidTest() {
//        List<PunishInfo> punishInfos = HttpRequestService.httpQueryPunishByUid("f1c91b61-c9f1-4381-93ea-eadb30b2cc7a");
//        System.out.println("punishInfos:" + punishInfos);
    }

    public static void httpQueryContractsByUidTest() {
//        List<ContractInfo> contractInfos = HttpRequestService.httpQueryContractsByUid("f1c91b61-c9f1-4381-93ea-eadb30b2cc7a");
//        System.out.println("contractInfos:" + contractInfos);
    }

    public static void httpAddAccessLogTest() {
        /*AccessLog accessLog = new AccessLog();
        accessLog.setSubjectUid("063a786e-2ad8-4d3b-a9fd-97d0a3e568cd");
        accessLog.setObjectUid("5067bf08-84a6-452c-80f5-d03f58d32af1");
        accessLog.setOperateType("control");
        accessLog.setOperateData("on");

        AccessLog addAccessLog = HttpRequestService.httpAddAccessLog(accessLog);
        LOGGER.info("addAccessLog:{}", addAccessLog);*/
    }

    public static void httpUpdateAccessLogTest() {
        /*AccessLog accessLog = new AccessLog();
        accessLog.setSubjectUid("063a786e-2ad8-4d3b-a9fd-97d0a3e568cd");
        accessLog.setObjectUid("5067bf08-84a6-452c-80f5-d03f58d32af1");
        accessLog.setOperateType("control");
        accessLog.setOperateData("on");
        accessLog.setAccessAllow(false);
        accessLog.setFailReason(1);
        accessLog.setId(10);

        AccessLog updateAccessLog = HttpRequestService.httpUpdateAccessLog(accessLog);
        LOGGER.info("updateAccessLog:{}", updateAccessLog);*/
    }
}
