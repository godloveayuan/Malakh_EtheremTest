package myUtils;

import bean.DeviceInfo;
import com.alibaba.druid.pool.DruidDataSource;
import mapper.DeviceInfoMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Author: Malakh
 * @Date: 2020/2/24
 * @Description:
 */
public class JDBCUtils {
    private static JdbcTemplate jdbcTemplate;

    static {
        /**
         * 加载 jdbc.properties 里的配置
         */
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(PropertyUtils.getJdbcUrl());
        dataSource.setUsername(PropertyUtils.getJdbcUsername());
        dataSource.setPassword(PropertyUtils.getJdbcPassword());
        dataSource.setInitialSize(Integer.valueOf(PropertyUtils.getJdbcInitSize()));
        dataSource.setMaxActive(Integer.valueOf(PropertyUtils.getJdbcMaxActive()));
        dataSource.setMinIdle(Integer.valueOf(PropertyUtils.getJdbcMinIdle()));
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 返回生成的jdbcTemplate对象
     *
     * @return
     */
    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * jdbcTemplate 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        // 查询对象
        String sql2 = "select * from device_info where device_uid='5067bf08-84a6-452c-80f5-d03f58d32af1'";
        DeviceInfo queryObj = jdbcTemplate.queryForObject(sql2, new DeviceInfoMapper());
        System.out.println("queryObj:" + queryObj);

        // 查询list
        String sql = "select * from device_info ";
        List<DeviceInfo> queryList = jdbcTemplate.query(sql, new DeviceInfoMapper());
        System.out.println("queryList:" + queryList);
    }
}
