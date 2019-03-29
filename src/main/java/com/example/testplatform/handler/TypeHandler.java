package com.example.testplatform.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.tangxi.testplatform.api.testcase.util.JacksonUtil;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TypeHandler<T> extends BaseTypeHandler<T> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        String json = JacksonUtil.toJson(parameter);
        ps.setString(i,json);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        if(columnValue !=null){
            return JacksonUtil.fromJson(rs.getString(columnName), new TypeReference<T>() {
            });
        }
        return null;
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        if(columnValue !=null) {
            return JacksonUtil.fromJson(rs.getString(columnIndex), new TypeReference<T>() {
            });
        }
        return null;
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        if(columnValue !=null) {
            return JacksonUtil.fromJson(cs.getString(columnIndex), new TypeReference<T>() {
            });
        }
        return null;
    }
}
