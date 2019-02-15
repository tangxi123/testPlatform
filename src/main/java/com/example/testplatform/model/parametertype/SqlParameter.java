package com.example.testplatform.model.parametertype;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.web.bind.annotation.RestController;

@JsonTypeName("SqlParameter")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SqlParameter implements Parameter {
    //对应的sql参数id
    private int id;
//    //对应的type
//    private String type;
    //数据库host
    private String host;
    //数据库端口
    private String port;
    //数据库名字
    private String database;
    //数据库用户名
    private String user;
    //数据库密码
    private String password;
    //sql语句
    private String sql;
    //获取字段内容
    private String param;
    //parameter的Id
    private int paramId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }


    @Override
    public int getParamId() {
        return paramId;
    }

    @Override
    public void setParamId(int paramId) {
        this.paramId = paramId;
    }
}
