package com.example.testplatform.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({URI.class})
public class UriTypeHandler extends BaseTypeHandler<URI>{
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, URI uri, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i,uri.toString());
    }

    @Override
    public URI getNullableResult(ResultSet resultSet, String s) throws SQLException {
        try {
            return new URI(resultSet.getString(s));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public URI getNullableResult(ResultSet resultSet, int i) throws SQLException {
        try {
            return new URI(resultSet.getString(i));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public URI getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        try {
            return new URI(callableStatement.getString(i));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
