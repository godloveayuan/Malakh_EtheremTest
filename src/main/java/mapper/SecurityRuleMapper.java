package mapper;

import bean.SecurityRule;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Malakh
 * @Date: 2020/3/1
 * @Description: SecurityRule 对象的mapper文件
 */
public class SecurityRuleMapper implements RowMapper<SecurityRule> {

    /*
    <resultMap id="SecurityRuleMap" type="SecurityRule">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <result property="type" column="type"/>
        <result property="checkCount" column="check_count"/>
        <result property="checkTimeNumber" column="check_time_number"/>
        <result property="checkTimeUnit" column="check_time_unit"/>
        <result property="punishNumber" column="punish_number"/>
        <result property="punishUnit" column="punish_unit"/>
        <result property="kickOut" column="kick_out"/>
        <result property="status" column="status"/>
        <result property="createTime" column="create_time"/>
        <result property="updateTime" column="update_time"/>
    </resultMap>
    */

    @Override
    public SecurityRule mapRow(ResultSet rs, int rowNum) throws SQLException {
        SecurityRule rule = new SecurityRule();
        rule.setId(rs.getInt("id"));
        rule.setName(rs.getString("name"));
        rule.setType(rs.getInt("type"));
        rule.setCheckCount(rs.getInt("check_count"));
        rule.setCheckTimeNumber(rs.getInt("check_time_number"));
        rule.setCheckTimeUnit(rs.getString("check_time_unit"));
        rule.setPunishNumber(rs.getInt("punish_number"));
        rule.setPunishUnit(rs.getString("punish_unit"));
        rule.setKickOut(rs.getBoolean("kick_out"));
        rule.setStatus(rs.getInt("status"));
        rule.setCreateTime(rs.getTimestamp("create_time"));
        rule.setUpdateTime(rs.getTimestamp("update_time"));

        return rule;
    }
}
