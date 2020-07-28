package dao;

import bean.SecurityRule;
import mapper.SecurityRuleMapper;
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
public class SecurityRuleDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityRuleDao.class);
    private static JdbcTemplate jdbcTemplate = JDBCUtils.getJdbcTemplate();
    private static boolean showSQL = false;     // 是否显示执行的sql

    public static List<SecurityRule> selectByRuleType(Integer type) {
        try {
            String sql = "SELECT\n" +
                    "        *\n" +
                    "        FROM security_rule\n" +
                    "        where type=#{type} and status=1";

            sql = sql.replaceAll("#\\{type\\}", type.toString());
            printSQL(sql);
            return jdbcTemplate.query(sql, new SecurityRuleMapper());
        } catch (Exception e) {
            LOGGER.error("[selectAllByStatus] Exception", e);
            return null;
        }
    }

    private static void printSQL(String sql) {
        if (showSQL) {
            LOGGER.info("[DeviceInfoDao-SQL]:{}", sql);
        }
    }
}
