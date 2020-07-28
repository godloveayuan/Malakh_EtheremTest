package dao;

import bean.ContractInfo;
import mapper.ContractInfoMapper;
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
public class ContractInfoDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractInfoDao.class);
    private static JdbcTemplate jdbcTemplate = JDBCUtils.getJdbcTemplate();
    private static boolean showSQL = false;     // 是否显示执行的sql

    /**
     * 查询公共策略合约
     *
     * @return
     */
    public static List<ContractInfo> selectPublicContract() {
        try {
            String sql = "SELECT contract.*,\n" +
                    "        device.device_name as owner_name\n" +
                    "        FROM contract_info as contract\n" +
                    "        LEFT JOIN device_info as device ON contract.owner=device.device_uid\n" +
                    "        where contract.status=1 and contract.type='public'";

            printSQL(sql);
            return jdbcTemplate.query(sql, new ContractInfoMapper());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 查询设备的专属策略合约
     *
     * @return
     */
    public static List<ContractInfo> selectPrivateContract(String deviceUid) {
        try {
            String sql = "SELECT contract.*,\n" +
                    "        device.device_name as owner_name\n" +
                    "        FROM contract_info as contract\n" +
                    "        LEFT JOIN device_info as device ON contract.owner=device.device_uid\n" +
                    "        where contract.status=1 and contract.type='private' and contract.owner=#{deviceUid}";

            sql = sql.replaceAll("#\\{deviceUid\\}", "'" + deviceUid + "'");
            printSQL(sql);
            return jdbcTemplate.query(sql, new ContractInfoMapper());
        } catch (Exception e) {
            return null;
        }
    }

    private static void printSQL(String sql) {
        if (showSQL) {
            LOGGER.info("[DeviceInfoDao-SQL]:{}", sql);
        }
    }
}
