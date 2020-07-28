package mapper;

import bean.DeviceInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Malakh
 * @Date: 2020/2/24
 * @Description: DeviceInfo 对象的mapper文件
 */
public class DeviceInfoMapper implements RowMapper<DeviceInfo> {

   /*
   <resultMap id="DeviceInfoMap" type="DeviceInfo">
        <id property="id" column="id"/>
        <result property="deviceName" column="device_name"/>
        <result property="deviceMac" column="device_mac"/>
        <result property="deviceUid" column="device_uid"/>
        <result property="deviceType" column="device_type"/>
        <result property="manufacture" column="manufacture"/>
        <result property="attributeType" column="attribute_type"/>
        <result property="attributePosition" column="attribute_position"/>
        <result property="attributeSystem" column="attribute_system"/>
        <result property="agentDevice" column="agent_device"/>
        <result property="agentAddress" column="agent_address"/>
        <result property="status" column="status"/>
        <result property="createTime" column="create_time"/>
        <result property="updateTime" column="update_time"/>
    </resultMap>
    */

    @Override
    public DeviceInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setId(resultSet.getInt("id"));
        deviceInfo.setDeviceName(resultSet.getString("device_name"));
        deviceInfo.setDeviceMac(resultSet.getString("device_mac"));
        deviceInfo.setDeviceUid(resultSet.getString("device_uid"));
        deviceInfo.setDeviceType(resultSet.getString("device_type"));
        deviceInfo.setManufacture(resultSet.getString("manufacture"));
        deviceInfo.setAttributeType(resultSet.getString("attribute_type"));
        deviceInfo.setAttributePosition(resultSet.getString("attribute_position"));
        deviceInfo.setAttributeSystem(resultSet.getString("attribute_system"));
        deviceInfo.setAgentDevice(resultSet.getString("agent_device"));
        deviceInfo.setAgentAddress(resultSet.getString("agent_address"));
        deviceInfo.setStatus(resultSet.getInt("status"));
        deviceInfo.setCreateTime(resultSet.getTimestamp("create_time"));
        deviceInfo.setUpdateTime(resultSet.getTimestamp("update_time"));

        return deviceInfo;
    }
}
