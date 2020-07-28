package service;

import bean.AccessLog;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import dao.AccessLogDao;
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
public class AccessLogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogService.class);

    /**
     * 查询设备在监测时间内违反规则的次数
     *
     * @return
     */
    public static List<AccessLog> queryIllegalAccess(String deviceUid, Integer checkTimeNumber, String checkTimeUnit, Integer failReason) {
        if (StringUtils.isEmpty(deviceUid)) {
            return null;
        }

        List<AccessLog> accessLogs = AccessLogDao.selectIllegalAccess(deviceUid, failReason);
        List<AccessLog> resultLogs = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(accessLogs)) {
            for (AccessLog accessLog : accessLogs) {
                fillShowValue(accessLog);
                String requestTimeStr = accessLog.getRequestTimeStr();
                if (MyStringUtils.checkDateIsBetweenNow(requestTimeStr, checkTimeNumber, checkTimeUnit)) {
                    resultLogs.add(accessLog);
                }
            }
        }
        return resultLogs;
    }

    /**
     * 查询设备在监测时间内访问次数
     *
     * @return
     */
    public static List<AccessLog> queryAccessCount(String deviceUid, Integer checkTimeNumber, String checkTimeUnit) {
        if (StringUtils.isEmpty(deviceUid)) {
            return null;
        }

        List<AccessLog> accessLogs = AccessLogDao.selectAccessCount(deviceUid);
        List<AccessLog> resultLogs = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(accessLogs)) {
            for (AccessLog accessLog : accessLogs) {
                fillShowValue(accessLog);
                String requestTimeStr = accessLog.getRequestTimeStr();
                if (MyStringUtils.checkDateIsBetweenNow(requestTimeStr, checkTimeNumber, checkTimeUnit)) {
                    resultLogs.add(accessLog);
                }
            }
        }
        return resultLogs;
    }

    public static AccessLog queryMaxIdLog() {

        return fillShowValue(AccessLogDao.selectMaxId());
    }

    public static AccessLog queryById(Integer id) {

        return fillShowValue(AccessLogDao.selectById(id));
    }

    /**
     * 插入数据
     *
     * @param insertInfo
     */
    public static AccessLog addAccessLog(AccessLog insertInfo) {
        fillInsertValue(insertInfo);

        if (insertInfo.getRequestTime() == null) {
            insertInfo.setResponseTime(new Date());
        }

        // 会把自增的id封装到插入的对象里
        Boolean insert = AccessLogDao.insert(insertInfo);
        if (insert != null && insert) {
            insertInfo = queryMaxIdLog();
        }

        // 请求到达添加访问日志时，判断是否有违规行为，如果有则执行惩罚
        PunishInfoService.executePunish(insertInfo.getSubjectUid());

        return insertInfo;
    }

    /**
     * 更新数据
     *
     * @param updateInfo
     */
    public static AccessLog updateAccessLog(AccessLog updateInfo) {
        Preconditions.checkArgument(updateInfo != null, "未接收到更新数据");
        Preconditions.checkArgument(updateInfo.getId() != null, "请提供更新数据的ID");
        fillInsertValue(updateInfo);

        AccessLogDao.update(updateInfo);

        return fillShowValue(queryById(updateInfo.getId()));
    }
    /**
     * 更新数据
     *
     * @param subjectUid
     */
    public static void updateStatusDisable(String subjectUid) {
        AccessLogDao.updateStatus(subjectUid, 0);
    }

    /**
     * 填充展示字段
     *
     * @param info
     * @return
     */
    public static AccessLog fillShowValue(AccessLog info) {
        if (info == null) {
            return null;
        }

        if (info.getRequestTime() != null) {
            info.setRequestTimeStr(MyStringUtils.parseDateToStr(info.getRequestTime()));
        }
        if (info.getResponseTime() != null) {
            info.setResponseTimeStr(MyStringUtils.parseDateToStr(info.getResponseTime()));
        }
        if (StringUtils.isEmpty(info.getOperateData())) {
            info.setOperateData("");
        }
        if (StringUtils.isEmpty(info.getResultData())) {
            info.setResultData("");
        }

        return info;
    }

    /**
     * 填充插入字段
     *
     * @param info
     * @return
     */
    public static AccessLog fillInsertValue(AccessLog info) {
        if (info == null) {
            return null;
        }
        if (info.getAccessAllow() == null) {
            info.setAccessAllow(true);
        }

        if (info.getFailReason() == null) {
            info.setFailReason(-1);
        }

        if (info.getResultData() == null) {
            info.setResultData("");
        }

        if (info.getRequestTime() == null) {
            info.setRequestTime(new Date());
        }

        if (info.getResponseTime() == null) {
            info.setResponseTime(new Date());
        }

        if (StringUtils.isNotEmpty(info.getRequestTimeStr())) {
            info.setRequestTime(MyStringUtils.parseStrToDate(info.getRequestTimeStr()));
        }
        if (StringUtils.isNotEmpty(info.getResponseTimeStr())) {
            info.setResponseTime(MyStringUtils.parseStrToDate(info.getResponseTimeStr()));
        }

        return info;
    }

}
