package com.example.testplatform.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.*;

import java.io.IOException;
import java.sql.*;
import java.util.Map;

import static org.apache.ibatis.type.JdbcType.VARCHAR;


@MappedJdbcTypes({VARCHAR})
@MappedTypes({Map.class})
public class MapTypeHandler extends BaseTypeHandler<Map<String,? super Object>>{
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Map<String,? super Object> map, JdbcType jdbcType) throws SQLException {
        try {
            String json = new ObjectMapper().writeValueAsString(map);
            preparedStatement.setString(i,json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<String,? super Object> getNullableResult(ResultSet resultSet, String s) throws SQLException {

        try {
            return new ObjectMapper().readValue(resultSet.getString(s),Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Map<String,? super Object> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        try {
            return new ObjectMapper().readValue(resultSet.getString(i),Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String,? super Object> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        try {
            return new ObjectMapper().readValue(callableStatement.getString(i),Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
