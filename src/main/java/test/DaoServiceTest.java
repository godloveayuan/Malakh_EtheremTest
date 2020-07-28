package test;

import bean.AccessLog;
import bean.ContractInfo;
import bean.PunishInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AccessLogService;
import service.ContractInfoService;
import service.PunishInfoService;
import service.SecurityRuleService;

import java.util.Date;
import java.util.List;

/**
 * @Author: Malakh
 * @Date: 2020/3/1
 * @Description: 测试类
 */
public class DaoServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DaoServiceTest.class);

    public static void main(String[] args) {
//        queryContractsByUidTest();
//        queryDevicePunishInfoTest();
//        addAccessLogTest();
//        updateAccessLogTest();
//        queryMaxAccessLog();
        executePunishTest();
    }


    public static void queryContractsByUidTest() {
        List<ContractInfo> contractInfos = ContractInfoService.queryContractsByUid("5067bf08-84a6-452c-80f5-d03f58d32af1");
        LOGGER.info("contractInfos:{}", contractInfos);
    }

    public static void queryDevicePunishInfoTest() {
        List<PunishInfo> punishInfos = PunishInfoService.queryPunishByUid("063a786e-2ad8-4d3b-a9fd-97d0a3e568cd");
        LOGGER.info("punishInfos:{}", punishInfos);
    }

    public static void addAccessLogTest() {
        AccessLog accessLog = new AccessLog();
        accessLog.setSubjectUid("f1c91b61-c9f1-4381-93ea-eadb30b2cc7a");
        accessLog.setObjectUid("62e1e963-3a8c-432d-ab76-9f93f9344d77");
        accessLog.setOperateType("control");
        accessLog.setOperateData("on");
        accessLog.setResponseTime(new Date());

        accessLog = AccessLogService.addAccessLog(accessLog);
        LOGGER.info("accessLog:{}", accessLog);
    }

    public static void updateAccessLogTest() {
        AccessLog accessLog = AccessLogService.queryMaxIdLog();
        accessLog.setAccessAllow(false);
        accessLog.setResponseTime(new Date());
        accessLog.setFailReason(SecurityRuleService.RULE_ACCESS_FREQUENCY);

        AccessLogService.updateAccessLog(accessLog);

        accessLog = AccessLogService.queryById(accessLog.getId());
        LOGGER.info("accessLog:{}", accessLog);
    }

    public static void queryMaxAccessLog() {
        AccessLog accessLog = AccessLogService.queryMaxIdLog();
        LOGGER.info("accessLog:{}", accessLog);
    }

    public static void executePunishTest() {
        boolean b = PunishInfoService.executePunish("5067bf08-84a6-452c-80f5-d03f58d32af1");
        LOGGER.info("punish:{}", b);
    }

}
