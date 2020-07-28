package mapper;

import bean.AccessLog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Malakh
 * @Date: 2020/3/1
 * @Description: AccessLog 对象的mapper文件
 */
public class AccessLogMapper implements RowMapper<AccessLog> {

    /*
    <resultMap id="AccessLogMap" type="AccessLog">
        <id property="id" column="id"/>
        <result property="subjectUid" column="subject_uid"/>
        <result property="subjectName" column="subject_name"/>
        <result property="objectUid" column="object_uid"/>
        <result property="objectName" column="object_name"/>
        <result property="operateType" column="operate_type"/>
        <result property="operateData" column="operate_data"/>
        <result property="accessAllow" column="access_allow"/>
        <result property="failReason" column="fail_reason"/>
        <result property="resultData" column="result_data"/>
        <result property="requestTime" column="request_time"/>
        <result property="responseTime" column="response_time"/>
    </resultMap>
    */

    @Override
    public AccessLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        AccessLog log = new AccessLog();
        log.setId(rs.getInt("id"));
        log.setSubjectUid(rs.getString("subject_uid"));
        log.setObjectUid(rs.getString("object_uid"));
        log.setOperateType(rs.getString("operate_type"));
        log.setOperateData(rs.getString("operate_data"));
        log.setAccessAllow(rs.getBoolean("access_allow"));
        log.setFailReason(rs.getInt("fail_reason"));
        log.setRequestTime(rs.getTimestamp("request_time"));
        log.setResponseTime(rs.getTimestamp("response_time"));
        log.setStatus(rs.getInt("status"));

        return log;
    }
}
