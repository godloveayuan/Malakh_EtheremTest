package service;

import bean.AccessLog;
import bean.PunishInfo;
import bean.SecurityRule;
import com.google.common.base.Preconditions;
import dao.PunishInfoDao;
import myUtils.MyStringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * @Author: Malakh
 * @Date: 2020/2/24
 * @Description:
 */
public class PunishInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PunishInfoService.class);

    /**
     * 查询所有信息
     *
     * @return
     */
    public static List<PunishInfo> queryAllByStatus(Integer status) {
        List<PunishInfo> infoList = PunishInfoDao.selectAllByStatus(status);
        if (CollectionUtils.isNotEmpty(infoList)) {
            for (PunishInfo info : infoList) {
                fillShowValue(info);
            }
        }
        return infoList;
    }

    /**
     * 根据设备名称查询已经处于惩罚状态的信息
     *
     * @param deviceUid
     * @return
     */
    public static List<PunishInfo> queryPunishByUid(String deviceUid) {
        if (StringUtils.isEmpty(deviceUid)) {
            return null;
        }
        // 先将惩罚时间过期的状态更新
        updateStatusByEndTime();

        List<PunishInfo> punishInfos = PunishInfoDao.selectDevicePunishInfo(deviceUid);
        if (CollectionUtils.isNotEmpty(punishInfos)) {
            for (PunishInfo info : punishInfos) {
                fillShowValue(info);
            }
        }

        return punishInfos;
    }

    /**
     * 插入数据
     *
     * @param insertInfo
     */
    public static void insert(PunishInfo insertInfo) {
        Preconditions.checkArgument(CollectionUtils.isEmpty(queryPunishByUid(insertInfo.getDeviceUid())), "该设备已处于惩罚状态!");
        fillInsertValue(insertInfo);

        PunishInfoDao.insert(insertInfo);
    }

    public static void updateStatusDisable(Integer id) {
        Preconditions.checkArgument(id != null, "请提供要数据的ID");

        PunishInfoDao.updateStatus(id, 0);
    }

    /**
     * 将惩罚结束时间已经到期的惩罚状态设置为不可用
     */
    public static void updateStatusByEndTime() {
        List<PunishInfo> punishInfos = queryAllByStatus(1);
        if (CollectionUtils.isNotEmpty(punishInfos)) {
            for (PunishInfo punish : punishInfos) {
                if (punish.getPunish() == 2) {
                    continue;
                }
                String punishEndStr = punish.getPunishEndStr();
                if (MyStringUtils.checkAIsBeforeB(MyStringUtils.parseStrToDate(punishEndStr), new Date())) {
                    // 惩罚到期后将惩罚状态设置为不可用
                    updateStatusDisable(punish.getId());
                    // 该惩罚依据的访问日志已经使用过，设置为status=0
                    AccessLogService.updateStatusDisable(punish.getDeviceUid());
                }
            }
        }
    }

    /**
     * 查看设备是否有违反的规则，如果有则执行惩罚
     *
     * @param deviceUid
     * @return true：设备有违反的规则，执行惩罚  false:未添加新的惩罚
     */
    public static boolean executePunish(String deviceUid) {

        if (StringUtils.isEmpty(deviceUid)) {
            return false;
        }

        /**
         * 是否已经在惩罚中
         */
        List<PunishInfo> punishInfos = queryPunishByUid(deviceUid);
        if (CollectionUtils.isNotEmpty(punishInfos)) {
            return false;
        }

        /**
         * 访问频率规则
         */
        boolean breakAccessRule = punishIfBreakAccessRule(deviceUid, SecurityRuleService.RULE_ACCESS_FREQUENCY);
        if (breakAccessRule) {
            LOGGER.info("[executePunish] 设备:{} 违反访问频率规则", deviceUid);
            return true;
        }

        /**
         * 身份认证规则
         */
        breakAccessRule = punishIfBreakAccessRule(deviceUid, SecurityRuleService.RULE_IDENTITY_FREQUENCY);
        if (breakAccessRule) {
            LOGGER.info("[executePunish] 设备:{} 违反身份认证规则", deviceUid);
            return true;
        }

        /**
         * 越权访问规则
         */
        breakAccessRule = punishIfBreakAccessRule(deviceUid, SecurityRuleService.RULE_AUTHORITY_FREQUENCY);
        if (breakAccessRule) {
            LOGGER.info("[executePunish] 设备:{} 违反越权访问规则", deviceUid);
            return true;
        }

//        LOGGER.info("[executePunish] 设备:{} 未违反任何访问规则，可正常访问", deviceUid);

        return false;
    }

    /**
     * 查看设备是否违反了访问频率规则，如果违反了则进行惩罚
     *
     * @param deviceUid
     * @return
     */
    public static boolean punishIfBreakAccessRule(String deviceUid, Integer ruleType) {
        // 找到规则
        List<SecurityRule> accessSecurityRules = SecurityRuleService.queryByRuleType(ruleType);
        if (CollectionUtils.isEmpty(accessSecurityRules)) {
            return false;
        }

        // 找到有效的规则
        SecurityRule accessRule = null;
        for (SecurityRule rule : accessSecurityRules) {
            if (rule != null && rule.getStatus() == 1) {
                accessRule = rule;
                break;
            }
        }
        // 查看规则信息
        Integer checkTimeNumber = accessRule.getCheckTimeNumber();
        String checkTimeUnit = accessRule.getCheckTimeUnit();

        // 查询监测时间内设备违反访问次数
        List<AccessLog> accessLogs = null;
        boolean accessFrequent = false;
        if (ruleType == SecurityRuleService.RULE_ACCESS_FREQUENCY) {
            // 访问频率
            accessFrequent = true;
            accessLogs = AccessLogService.queryAccessCount(deviceUid, checkTimeNumber, checkTimeUnit);
        } else {
            // 违规次数
            accessLogs = AccessLogService.queryIllegalAccess(deviceUid, checkTimeNumber, checkTimeUnit, ruleType);
        }

        int accessCount = CollectionUtils.isNotEmpty(accessLogs) ? accessLogs.size() : 0;

        // 保证访问频率次数限制
        if (accessFrequent) {
            accessCount = accessCount - 1;
        }

        // 超过访问次数，进行惩罚
        if (accessCount >= accessRule.getCheckCount()) {
            // 根据规则进行惩罚
            punishDevice(deviceUid, accessRule);
            return true;
        }

        return false;
    }

    /**
     * 根据设备违反的规则进行惩罚
     *
     * @param deviceUid
     * @param securityRule
     */
    public static void punishDevice(String deviceUid, SecurityRule securityRule) {

        //构建惩罚对象
        PunishInfo punishInfo = new PunishInfo();
        punishInfo.setDeviceUid(deviceUid);
        punishInfo.setRuleName(securityRule.getName());
        punishInfo.setPunish(securityRule.getKickOut() ? 2 : 1);
        punishInfo.setPunishStart(new Date());
        if (securityRule.getKickOut()) {
            punishInfo.setPunishEnd(punishInfo.getPunishStart());
        } else {
            punishInfo.setPunishEnd(MyStringUtils.plusDate(punishInfo.getPunishStart(), securityRule.getPunishNumber(), securityRule.getPunishUnit()));
        }

        insert(punishInfo);
        // 将驱逐的设备设为不可用
        if (securityRule.getKickOut()) {
            DeviceInfoService.setDeviceUnable(deviceUid);
        }
    }

    /**
     * 填充展示字段
     *
     * @param info
     * @return
     */
    public static PunishInfo fillShowValue(PunishInfo info) {
        if (info == null) {
            return null;
        }
        if (info.getPunishStart() != null) {
            info.setPunishStartStr(MyStringUtils.parseDateToStr(info.getPunishStart()));
        }
        if (info.getPunishEnd() != null) {
            info.setPunishEndStr(MyStringUtils.parseDateToStr(info.getPunishEnd()));
        }

        return info;
    }

    /**
     * 填充插入字段
     *
     * @param info
     * @return
     */
    public static PunishInfo fillInsertValue(PunishInfo info) {
        if (info == null) {
            return null;
        }
        if (StringUtils.isNotEmpty(info.getPunishStartStr())) {
            info.setPunishStart(MyStringUtils.parseStrToDate(info.getPunishStartStr()));
        }
        if (StringUtils.isNotEmpty(info.getPunishEndStr())) {
            info.setPunishEnd(MyStringUtils.parseStrToDate(info.getPunishEndStr()));
        }

        return info;
    }

}
