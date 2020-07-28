package bean;

import java.util.Date;

/**
 * @Author: Malakh
 * @Date: 2020/2/8
 * @Description: 设备信息
 */
public class DeviceInfo {
    private Integer id;
    private String deviceName;              // 设备名称
    private String deviceMac;               // 设备出厂标识
    private String deviceUid;               // 服务器下发的设备标识
    private String deviceType;              // 设备类型
    private String manufacture;             // 生产厂商
    private String attributeType;           // 设备类型属性
    private String attributePosition;       // 设备位置属性
    private String attributeSystem;         // 设备系统属性
    private String agentDevice;             // 代理设备
    private String agentAddress;            // 代理地址
    private Integer status;                 // 设备状态 0-不可用 1-可用
    private Date createTime;                // 创建时间
    private Date updateTime;                // 修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getDeviceUid() {
        return deviceUid;
    }

    public void setDeviceUid(String deviceUid) {
        this.deviceUid = deviceUid;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributePosition() {
        return attributePosition;
    }

    public void setAttributePosition(String attributePosition) {
        this.attributePosition = attributePosition;
    }

    public String getAttributeSystem() {
        return attributeSystem;
    }

    public void setAttributeSystem(String attributeSystem) {
        this.attributeSystem = attributeSystem;
    }

    public String getAgentDevice() {
        return agentDevice;
    }

    public void setAgentDevice(String agentDevice) {
        this.agentDevice = agentDevice;
    }

    public String getAgentAddress() {
        return agentAddress;
    }

    public void setAgentAddress(String agentAddress) {
        this.agentAddress = agentAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceInfo{");
        sb.append("id=").append(id);
        sb.append(", deviceName='").append(deviceName).append('\'');
        sb.append(", deviceMac='").append(deviceMac).append('\'');
        sb.append(", deviceUid='").append(deviceUid).append('\'');
        sb.append(", deviceType='").append(deviceType).append('\'');
        sb.append(", manufacture='").append(manufacture).append('\'');
        sb.append(", attributeType='").append(attributeType).append('\'');
        sb.append(", attributePosition='").append(attributePosition).append('\'');
        sb.append(", attributeSystem='").append(attributeSystem).append('\'');
        sb.append(", agentDevice='").append(agentDevice).append('\'');
        sb.append(", agentAddress='").append(agentAddress).append('\'');
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
