package com.example.testplatform.service;

import com.example.testplatform.model.parametertype.SqlParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

@Service
public class SqlParameterService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    /**
     * 根据数据源获取参数的值
     * @param sqlParameter
     * @return
     */
    public String getSqlParameterValue(SqlParameter sqlParameter){
        DataSource dataSource = createDataSource(sqlParameter);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Map<String,Object> valueMap = jdbcTemplate.queryForMap(sqlParameter.getSql());

        Object value = valueMap.get(sqlParameter.getParam());
        if(value instanceof Integer){
            return String.valueOf(value);
        }
        return null;
    }

    /**
     * 创建数据库连接
     * @param sqlParameter
     * @return
     */
    public DataSource createDataSource(SqlParameter sqlParameter){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://"+sqlParameter.getHost()+":"+sqlParameter.getPort()+"/"+sqlParameter.getDatabase());
        dataSource.setUsername(sqlParameter.getUser());
        dataSource.setPassword(sqlParameter.getPassword());

        return dataSource;

    }
}
