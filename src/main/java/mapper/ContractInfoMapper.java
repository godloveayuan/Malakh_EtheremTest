package mapper;

import bean.ContractInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Malakh
 * @Date: 2020/3/1
 * @Description: ContractInfo 对象的mapper文件
 */
public class ContractInfoMapper implements RowMapper<ContractInfo> {

    /*
    <resultMap id="ContractInfoMap" type="ContractInfo">
        <id property="id" column="id"/>
        <result property="name" column="name"/>
        <result property="type" column="type"/>
        <result property="owner" column="owner"/>
        <result property="ownerName" column="owner_name"/>
        <result property="address" column="address"/>
        <result property="status" column="status"/>
        <result property="createTime" column="create_time"/>
        <result property="updateTime" column="update_time"/>
    </resultMap>
    */

    @Override
    public ContractInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        ContractInfo info = new ContractInfo();
        info.setId(rs.getInt("id"));
        info.setName(rs.getString("name"));
        info.setType(rs.getString("type"));
        info.setOwner(rs.getString("owner"));
        info.setOwnerName(rs.getString("owner_name"));
        info.setAddress(rs.getString("address"));
        info.setStatus(rs.getInt("status"));
        info.setCreateTime(rs.getTimestamp("create_time"));
        info.setUpdateTime(rs.getTimestamp("update_time"));

        return info;
    }
}
