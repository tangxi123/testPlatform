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
public class ListTypeHandler extends BaseTypeHandler<List<CheckPoint>>{
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<CheckPoint> parameter, JdbcType jdbcType) throws SQLException {
        String json = JacksonUtil.toJson(parameter);
        ps.setString(i,json);

    }

    @Override
    public List<CheckPoint> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JacksonUtil.fromJson(rs.getString(columnName), new TypeReference<List<CheckPoint>>() {
        });
    }

    @Override
    public List<CheckPoint> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JacksonUtil.fromJson(rs.getString(columnIndex), new TypeReference<List<CheckPoint>>() {
        });
    }

    @Override
    public List<CheckPoint> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JacksonUtil.fromJson(cs.getString(columnIndex), new TypeReference<List<CheckPoint>>() {
        });
    }
}
