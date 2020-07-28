package bean;

import java.util.Date;

/**
 * @Author: Malakh
 * @Date: 2020/2/17
 * @Description: 设备惩罚信息
 */
public class PunishInfo {
    private Integer id;                 // 主键ID
    private String deviceUid;           // 被惩罚的设备标识
    private String deviceName;          // 被惩罚的设备
    private String ruleName;            // 违反的规则名称
    private Integer ruleType;           // 违反的规则类型
    private Integer punish;             // 执行的惩罚 1-隔离 2-驱逐
    private Date punishStart;           // 惩罚开始时间
    private String punishStartStr;      // 惩罚开始时间
    private Date punishEnd;             // 惩罚结束时间
    private String punishEndStr;        // 惩罚结束时间
    private Integer status;             // 惩罚状态 0-惩罚解除 1-惩罚中

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceUid() {
        return deviceUid;
    }

    public void setDeviceUid(String deviceUid) {
        this.deviceUid = deviceUid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getPunish() {
        return punish;
    }

    public void setPunish(Integer punish) {
        this.punish = punish;
    }

    public Date getPunishStart() {
        return punishStart;
    }

    public void setPunishStart(Date punishStart) {
        this.punishStart = punishStart;
    }

    public String getPunishStartStr() {
        return punishStartStr;
    }

    public void setPunishStartStr(String punishStartStr) {
        this.punishStartStr = punishStartStr;
    }

    public Date getPunishEnd() {
        return punishEnd;
    }

    public void setPunishEnd(Date punishEnd) {
        this.punishEnd = punishEnd;
    }

    public String getPunishEndStr() {
        return punishEndStr;
    }

    public void setPunishEndStr(String punishEndStr) {
        this.punishEndStr = punishEndStr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PunishInfo{");
        sb.append("id=").append(id);
        sb.append(", deviceUid='").append(deviceUid).append('\'');
        sb.append(", deviceName='").append(deviceName).append('\'');
        sb.append(", ruleName='").append(ruleName).append('\'');
        sb.append(", ruleType=").append(ruleType);
        sb.append(", punish=").append(punish);
        sb.append(", punishStart=").append(punishStart);
        sb.append(", punishStartStr='").append(punishStartStr).append('\'');
        sb.append(", punishEnd=").append(punishEnd);
        sb.append(", punishEndStr='").append(punishEndStr).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
