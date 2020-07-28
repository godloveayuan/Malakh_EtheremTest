package bean;

import java.util.Date;

/**
 * @Author: Malakh
 * @Date: 2020/2/13
 * @Description: 合约信息
 */
public class ContractInfo {
    private Integer id;                     // 主键ID
    private String name;                    // 合约名称
    private String type;                    // 合约类型 public:公共合约  private:私有合约
    private String owner;                   // 合约拥有者 设备的uid
    private String ownerName;               // 合约拥有者 设备名称
    private String address;                 // 合约地址
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        final StringBuilder sb = new StringBuilder("ContractInfo{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", owner='").append(owner).append('\'');
        sb.append(", ownerName='").append(ownerName).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
