package mapper;

import bean.PunishInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Malakh
 * @Date: 2020/3/1
 * @Description: PunishInfo 对象的mapper文件
 */
public class PunishInfoMapper implements RowMapper<PunishInfo> {
    /*
    <resultMap id="PunishInfoMap" type="PunishInfo">
        <id property="id" column="id"/>
        <result property="deviceUid" column="device_uid"/>
        <result property="deviceName" column="device_name"/>
        <result property="ruleName" column="rule_name"/>
        <result property="ruleType" column="rule_type"/>
        <result property="punish" column="punish"/>
        <result property="punishStart" column="punish_start"/>
        <result property="punishEnd" column="punish_end"/>
        <result property="status" column="status"/>
    </resultMap>
    */

    @Override
    public PunishInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        PunishInfo info = new PunishInfo();
        info.setId(rs.getInt("id"));
        info.setDeviceUid(rs.getString("device_uid"));
        info.setDeviceName(rs.getString("device_name"));
        info.setRuleName(rs.getString("rule_name"));
        info.setRuleType(rs.getInt("rule_type"));
        info.setPunish(rs.getInt("punish"));
        info.setPunishStart(rs.getTimestamp("punish_start"));
        info.setPunishEnd(rs.getTimestamp("punish_end"));
        info.setStatus(rs.getInt("status"));

        return info;
    }
}
