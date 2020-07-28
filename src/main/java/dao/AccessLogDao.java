package dao;

import bean.AccessLog;
import mapper.AccessLogMapper;
import myUtils.JDBCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Author: Malakh
 * @Date: 2020/2/24
 * @Description:
 */
public class AccessLogDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogDao.class);
    private static JdbcTemplate jdbcTemplate = JDBCUtils.getJdbcTemplate();
    private static boolean showSQL = false;     // 是否显示执行的sql

    public static List<AccessLog> selectIllegalAccess(String deviceUid, Integer failReason) {
        try {
            String sql = "SELECT\n" +
                    "        access_log.*\n" +
                    "        FROM access_log\n" +
                    "        where\n" +
                    "        status=1 and\n" +
                    "        subject_uid=#{deviceUid}\n" +
                    "        and fail_reason=#{failReason}";

            sql = sql.replaceAll("#\\{failReason\\}", failReason.toString());
            sql = sql.replaceAll("#\\{deviceUid\\}", "'" + deviceUid + "'");
            printSQL(sql);
            return jdbcTemplate.query(sql, new AccessLogMapper());
        } catch (Exception e) {
            LOGGER.error("[selectIllegalAccess] Exception", e);
            return null;
        }
    }

    public static List<AccessLog> selectAccessCount(String deviceUid) {
        try {
            String sql = "SELECT\n" +
                    "        access_log.*\n" +
                    "        FROM access_log\n" +
                    "        where\n" +
                    "        status=1 and\n" +
                    "        subject_uid=#{deviceUid}";

            sql = sql.replaceAll("#\\{deviceUid\\}", "'" + deviceUid + "'");
            printSQL(sql);
            return jdbcTemplate.query(sql, new AccessLogMapper());
        } catch (Exception e) {
            LOGGER.error("[selectAccessCount] Exception", e);
            return null;
        }
    }

    public static AccessLog selectMaxId() {
        try {
            String sql = "select * from access_log order by id desc limit 1";

            printSQL(sql);
            return jdbcTemplate.queryForObject(sql, new AccessLogMapper());
        } catch (Exception e) {
            LOGGER.error("[selectMaxId] Exception", e);
            return null;
        }
    }

    public static AccessLog selectById(Integer id) {
        if (id == null) {
            return null;
        }

        try {
            String sql = "select * from access_log where id=#{id}";

            sql = sql.replaceAll("#\\{id\\}", id.toString());
            printSQL(sql);
            return jdbcTemplate.queryForObject(sql, new AccessLogMapper());
        } catch (Exception e) {
            LOGGER.error("[selectMaxId] Exception", e);
            return null;
        }
    }

    public static Boolean insert(AccessLog accessLog) {
        try {
            String sql = "INSERT INTO access_log\n" +
                    "        (subject_uid,object_uid,operate_type,operate_data,access_allow,fail_reason,result_data,request_time,response_time)\n" +
                    "        values\n" +
                    "        (#{subjectUid},#{objectUid},#{operateType},#{operateData},#{accessAllow},#{failReason},#{resultData},#{requestTime},#{responseTime})";

            sql = sql.replaceAll("#\\{subjectUid\\}", "'" + accessLog.getSubjectUid() + "'");
            sql = sql.replaceAll("#\\{objectUid\\}", "'" + accessLog.getObjectUid() + "'");
            sql = sql.replaceAll("#\\{operateType\\}", "'" + accessLog.getOperateType() + "'");
            sql = sql.replaceAll("#\\{operateData\\}", "'" + accessLog.getOperateData() + "'");
            sql = sql.replaceAll("#\\{accessAllow\\}", accessLog.getAccessAllow() != null && accessLog.getAccessAllow() ? "1" : "0");
            sql = sql.replaceAll("#\\{failReason\\}", accessLog.getFailReason().toString());
            sql = sql.replaceAll("#\\{resultData\\}", "'" + accessLog.getResultData() + "'");
            sql = sql.replaceAll("#\\{requestTime\\}", "now()");
            sql = sql.replaceAll("#\\{responseTime\\}", "now()");
            printSQL(sql);
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e) {
            LOGGER.error("[insert] Exception", e);
            return false;
        }
    }

    public static Integer update(AccessLog accessLog) {
        try {
            String sql = "UPDATE access_log\n" +
                    "        SET\n" +
                    "            subject_uid=#{subjectUid},\n" +
                    "            object_uid=#{objectUid},\n" +
                    "            operate_type=#{operateType},\n" +
                    "            operate_data=#{operateData},\n" +
                    "            access_allow=#{accessAllow},\n" +
                    "            fail_reason=#{failReason},\n" +
                    "            result_data=#{resultData},\n" +
                    "            response_time=#{responseTime}\n" +
                    "        WHERE id=#{id}";

            sql = sql.replaceAll("#\\{id\\}", accessLog.getId().toString());
            sql = sql.replaceAll("#\\{subjectUid\\}", "'" + accessLog.getSubjectUid() + "'");
            sql = sql.replaceAll("#\\{objectUid\\}", "'" + accessLog.getObjectUid() + "'");
            sql = sql.replaceAll("#\\{operateType\\}", "'" + accessLog.getOperateType() + "'");
            sql = sql.replaceAll("#\\{operateData\\}", "'" + accessLog.getOperateData() + "'");
            sql = sql.replaceAll("#\\{accessAllow\\}", accessLog.getAccessAllow() ? "1" : "0");
            sql = sql.replaceAll("#\\{failReason\\}", accessLog.getFailReason().toString());
            sql = sql.replaceAll("#\\{resultData\\}", "'" + accessLog.getResultData() + "'");
            sql = sql.replaceAll("#\\{responseTime\\}", "now()");
            printSQL(sql);
            return jdbcTemplate.update(sql);
        } catch (Exception e) {
            LOGGER.error("[update] Exception", e);
            return null;
        }
    }
    public static Integer updateStatus(String deviceUid, Integer status) {
        try {
            String sql = "UPDATE access_log\n" +
                    "        SET status=#{status}\n" +
                    "        WHERE subject_uid=#{deviceUid}";

            sql = sql.replaceAll("#\\{status\\}", status.toString());
            sql = sql.replaceAll("#\\{deviceUid\\}", "'" + deviceUid + "'");
            printSQL(sql);
            return jdbcTemplate.update(sql);
        } catch (Exception e) {
            LOGGER.error("[updateStatus] Exception", e);
            return null;
        }
    }

    private static void printSQL(String sql) {
        if (showSQL) {
            LOGGER.info("[DeviceInfoDao-SQL]:{}", sql);
        }
    }
}
