package dao;

import bean.PunishInfo;
import mapper.PunishInfoMapper;
import myUtils.JDBCUtils;
import myUtils.MyStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Author: Malakh
 * @Date: 2020/2/24
 * @Description:
 */
public class PunishInfoDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(PunishInfoDao.class);
    private static JdbcTemplate jdbcTemplate = JDBCUtils.getJdbcTemplate();
    private static boolean showSQL = false;     // 是否显示执行的sql

    public static List<PunishInfo> selectDevicePunishInfo(String deviceUid) {
        try {
            String sql = "SELECT\n" +
                    "        punish.*,\n" +
                    "        rule.type as rule_type,\n" +
                    "        device.device_name as device_name\n" +
                    "        FROM punish_info as punish\n" +
                    "        LEFT JOIN security_rule as rule on punish.rule_name=rule.name\n" +
                    "        LEFT JOIN device_info as device on punish.device_uid=device.device_uid\n" +
                    "        where punish.device_uid=#{deviceUid} and punish.status=1";

            sql = sql.replaceAll("#\\{deviceUid\\}", "'" + deviceUid + "'");
            printSQL(sql);
            return jdbcTemplate.query(sql, new PunishInfoMapper());
        } catch (Exception e) {
            LOGGER.error("[selectDevicePunishInfo] Exception", e);
            return null;
        }
    }

    public static List<PunishInfo> selectAllByStatus(Integer status) {
        try {
            String sql = "SELECT\n" +
                    "        punish.*,\n" +
                    "        rule.type as rule_type,\n" +
                    "        device.device_name as device_name\n" +
                    "        FROM punish_info as punish\n" +
                    "        LEFT JOIN security_rule as rule on punish.rule_name=rule.name\n" +
                    "        LEFT JOIN device_info as device on punish.device_uid=device.device_uid\n" +
                    "        where punish.status=#{status}";

            sql = sql.replaceAll("#\\{status\\}", status.toString());
            printSQL(sql);
            return jdbcTemplate.query(sql, new PunishInfoMapper());
        } catch (Exception e) {
            LOGGER.error("[selectAllByStatus] Exception", e);
            return null;
        }
    }

    public static Integer updateStatus(Integer id, Integer status) {
        try {
            String sql = "UPDATE punish_info\n" +
                    "        SET status = #{status}\n" +
                    "        WHERE id=#{id}";

            sql = sql.replaceAll("#\\{status\\}", status.toString());
            sql = sql.replaceAll("#\\{id\\}", id.toString());
            printSQL(sql);
            return jdbcTemplate.update(sql);
        } catch (Exception e) {
            LOGGER.error("[updateStatus] Exception", e);
            return null;
        }
    }

    public static Boolean insert(PunishInfo punishInfo) {
        try {
            String sql = "INSERT INTO punish_info\n" +
                    "        (device_uid,rule_name,punish,punish_start,punish_end,status)\n" +
                    "        values\n" +
                    "        (#{deviceName},#{ruleName},#{punish},#{punishStart},#{punishEnd},1)";

            sql = sql.replaceAll("#\\{deviceName\\}", "'" + punishInfo.getDeviceUid() + "'");
            sql = sql.replaceAll("#\\{ruleName\\}", "'" + punishInfo.getRuleName() + "'");
            sql = sql.replaceAll("#\\{punish\\}", punishInfo.getPunish().toString());
            sql = sql.replaceAll("#\\{punishStart\\}", "'" + MyStringUtils.parseDateToStr(punishInfo.getPunishStart()) + "'");
            sql = sql.replaceAll("#\\{punishEnd\\}", "'" + MyStringUtils.parseDateToStr(punishInfo.getPunishEnd()) + "'");
            printSQL(sql);
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e) {
            LOGGER.error("[Insert] Exception", e);
            return false;
        }
    }

    private static void printSQL(String sql) {
        if (showSQL) {
            LOGGER.info("[DeviceInfoDao-SQL]:{}", sql);
        }
    }
}
