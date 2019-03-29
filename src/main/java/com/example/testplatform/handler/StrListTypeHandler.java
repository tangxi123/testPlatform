package com.example.testplatform.handler;

import com.example.testplatform.model.checkpoint.CheckPoint;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.tangxi.testplatform.api.testcase.util.JacksonUtil;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.VARCHAR;

@MappedJdbcTypes({VARCHAR})
@MappedTypes({List.class})
public class StrListTypeHandler extends BaseTypeHandler<List<String>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        String json = JacksonUtil.toJson(parameter);
        ps.setString(i,json);
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        if(columnValue !=null){
            return JacksonUtil.fromJson(rs.getString(columnName), new TypeReference<List<String>>() {
        });
        }
        return null;
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        if(columnValue !=null) {
            return JacksonUtil.fromJson(rs.getString(columnIndex), new TypeReference<List<String>>() {
            });
        }
        return null;
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        if(columnValue !=null) {
            return JacksonUtil.fromJson(cs.getString(columnIndex), new TypeReference<List<String>>() {
            });
        }
        return null;

    }
}
