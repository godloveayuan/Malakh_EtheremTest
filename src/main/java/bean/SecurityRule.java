package bean;

import java.util.Date;

/**
 * @Author: Malakh
 * @Date: 2020/2/13
 * @Description: 安全规则
 */
public class SecurityRule {
    private Integer id;             // 主键ID
    private String name;            // 规则名称
    private Integer type;           // 规则类型
    private Integer checkCount;     // 检查次数
    private Integer checkTimeNumber;        // 监测时间长度
    private String checkTimeUnit;           // 监测时间单位 second/minute/hour/day
    private Integer punishNumber;           // 惩罚时间长度
    private String punishUnit;              // 惩罚时间单位
    private Boolean kickOut;                // 违反规则后是否直接驱逐出网络
    private Integer status;                 // 设备状态 0-不可用 1-可用
    private Date createTime;                // 创建时间
    private Date updateTime;                // 修改时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(Integer checkCount) {
        this.checkCount = checkCount;
    }

    public Integer getCheckTimeNumber() {
        return checkTimeNumber;
    }

    public void setCheckTimeNumber(Integer checkTimeNumber) {
        this.checkTimeNumber = checkTimeNumber;
    }

    public String getCheckTimeUnit() {
        return checkTimeUnit;
    }

    public void setCheckTimeUnit(String checkTimeUnit) {
        this.checkTimeUnit = checkTimeUnit;
    }

    public Integer getPunishNumber() {
        return punishNumber;
    }

    public void setPunishNumber(Integer punishNumber) {
        this.punishNumber = punishNumber;
    }

    public String getPunishUnit() {
        return punishUnit;
    }

    public void setPunishUnit(String punishUnit) {
        this.punishUnit = punishUnit;
    }

    public Boolean getKickOut() {
        return kickOut;
    }

    public void setKickOut(Boolean kickOut) {
        this.kickOut = kickOut;
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
        final StringBuilder sb = new StringBuilder("SecurityRule{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append(", checkCount=").append(checkCount);
        sb.append(", checkTimeNumber=").append(checkTimeNumber);
        sb.append(", checkTimeUnit='").append(checkTimeUnit).append('\'');
        sb.append(", punishNumber=").append(punishNumber);
        sb.append(", punishUnit='").append(punishUnit).append('\'');
        sb.append(", kickOut=").append(kickOut);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
