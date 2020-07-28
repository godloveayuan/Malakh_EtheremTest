package dao;

import bean.DeviceInfo;
import mapper.DeviceInfoMapper;
import myUtils.JDBCUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Author: Malakh
 * @Date: 2020/2/24
 * @Description:
 */
public class DeviceInfoDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceInfoDao.class);
    private static JdbcTemplate jdbcTemplate = JDBCUtils.getJdbcTemplate();
    private static boolean showSQL = false;     // 是否显示执行的sql

    /**
     * 根据设备uid查询设备信息
     *
     * @param deviceUid
     * @return
     */
    public static DeviceInfo selectByUid(String deviceUid) {
        try {
            if (StringUtils.isEmpty(deviceUid)) {
                return null;
            }
            String sql = "SELECT dev.*,\n" +
                    "        agent.address as agent_address\n" +
                    "        FROM device_info as dev\n" +
                    "        LEFT JOIN agent_info as agent ON dev.agent_device=agent.device_uid " +
                    "where dev.device_uid=#{deviceUid}";
            sql = sql.replaceAll("#\\{deviceUid\\}", "'" + deviceUid + "'");

            printSQL(sql);
            return jdbcTemplate.queryForObject(sql, new DeviceInfoMapper());
        } catch (Exception e) {
            LOGGER.error("[selectByUid] Exception", e);
            return null;
        }
    }

    /**
     * 根据设备Mac查询设备信息
     *
     * @param deviceMac
     * @return
     */
    public static DeviceInfo selectByMac(String deviceMac) {
        try {
            if (StringUtils.isEmpty(deviceMac)) {
                return null;
            }
            String sql = "SELECT dev.*,\n" +
                    "        agent.address as agent_address\n" +
                    "        FROM device_info as dev\n" +
                    "        LEFT JOIN agent_info as agent ON dev.agent_device=agent.device_uid " +
                    "where dev.device_mac=#{deviceMac}";
            sql = sql.replaceAll("#\\{deviceMac\\}", "'" + deviceMac + "'");

            printSQL(sql);
            return jdbcTemplate.queryForObject(sql, new DeviceInfoMapper());
        } catch (Exception e) {
            LOGGER.error("[selectByMac] Exception", e);
            return null;
        }
    }

    /**
     * 查询所有终端设备信息
     *
     * @return
     */
    public static List<DeviceInfo> selectAllTerminal() {
        try {
            String sql = "SELECT dev.*,\n" +
                    "        agent.address as agent_address\n" +
                    "        FROM device_info as dev\n" +
                    "        LEFT JOIN agent_info as agent ON dev.agent_device=agent.device_uid " +
                    "where dev.attribute_type='terminal'";

            printSQL(sql);
            return jdbcTemplate.query(sql, new DeviceInfoMapper());
        } catch (Exception e) {
            LOGGER.error("[selectAllTerminal] Exception", e);
            return null;
        }
    }

    private static void printSQL(String sql) {
        if (showSQL) {
            LOGGER.info("[DeviceInfoDao-SQL]:{}", sql);
        }
    }
    /**
     * 设置设备状态
     *
     * @return
     */
    public static Integer updateStatus(String deviceUid, Integer status) {
        try {
            if (StringUtils.isEmpty(deviceUid)) {
                return null;
            }
            String sql = "update device_info\n" +
                    "        SET status=#{status}\n" +
                    "where device_uid=#{deviceUid}";
            sql = sql.replaceAll("#\\{deviceUid\\}", "'" + deviceUid + "'");
            sql = sql.replaceAll("#\\{status\\}", status.toString());

            printSQL(sql);
            return jdbcTemplate.update(sql);
        } catch (Exception e) {
            LOGGER.error("[updateStatus] Exception", e);
            return null;
        }
    }
}
