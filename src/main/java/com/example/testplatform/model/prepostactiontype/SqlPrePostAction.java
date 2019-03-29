package com.example.testplatform.model.prepostactiontype;
/**
 * @author Tangx
 * 2019-02-13 15:02
 */
public class SqlPrePostAction implements  PrePostAction{
    //对应的sql参数id
    private int id;
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
    //前后置动作的id
    private int actionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public int getActionId() {
        return actionId;
    }

    @Override
    public void setActionId(int actionId) {
        this.actionId = actionId;
    }
}
