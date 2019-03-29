package com.example.testplatform.util;


import com.example.testplatform.mapper.ParamMapper;
import com.example.testplatform.mapper.PrePostMapper;
import com.example.testplatform.mapper.TCasesMapper;
import com.example.testplatform.mapper.UserMapper;
import com.example.testplatform.model.Apis;
import com.example.testplatform.model.ParameterWrapper;
import com.example.testplatform.model.PrePostActionWrapper;
import com.example.testplatform.model.checkpoint.*;
import com.example.testplatform.model.parametertype.SqlParameter;
import com.example.testplatform.model.prepostactiontype.PrePostActionType;
import com.example.testplatform.model.prepostactiontype.SqlPrePostAction;
import com.example.testplatform.test.TestExec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.tangxi.testplatform.api.testcase.util.JacksonUtil;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import javax.sql.DataSource;
import javax.swing.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class TestCaseExecution {
    private static final Logger LOG = LoggerFactory.getLogger(TestCaseExecution.class);
    private JsonPath preFieldsValuesJson;
    public void execTestCaseById(int id){
        LOG.trace("进入执行测试用例方法");
        LOG.trace(">> TestCaseExecution.TestCaseExecution()");
        LOG.trace("传入的参数为：{}",id);
        Apis testCase = null;
        List<String> preActions;
        JsonPath requestResult;
        List<String> postActions;
        try {
            testCase = getTestCaseById(id);
            preActions= testCase.getPreActionNames();
            LOG.trace("前置动作为：{}",preActions);
            executePrePostActions(preActions);
            requestResult = execTest(testCase);
            execCheck(requestResult,testCase);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            postActions = testCase.getPostActionNames();
            executePrePostActions(postActions);
        }

    }

    //1.根据id获取数据表中的测试用例
    public Apis getTestCaseById(int id){
        LOG.trace(">> TestCaseExcution.getTestCaseById");
        LOG.trace("传入的参数为：{}",id);
        SqlSessionFactory sqlSessionFactory = createDataSourceConnection();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            TCasesMapper tCasesMapper = session.getMapper(TCasesMapper.class);
            Apis testCase = tCasesMapper.selectApisById(id);
            LOG.debug("根据id查询到的测试用例为：{}",testCase);
            return testCase;
        }finally {
            session.close();
        }
    }

    //执行前置动作列表
    public void executePrePostActions(List<String> prePostActions){
        LOG.trace(">> TestCaseExecution.executePrePostActions(List<String> prePostActions)");
        LOG.trace("参数prePostActions的个数为：{},值为：{}",prePostActions.size(),prePostActions);
        if(prePostActions == null){
            return;
        }
        for(int i=0; i<prePostActions.size();i++){
            preActionExecute(prePostActions.get(i));
        }
    }

    //执行前置动作
    public void preActionExecute(String preActionName){
        LOG.trace(">> TestCaseExecution.preActionExecute(String preActionName)");
        LOG.trace("传入的参数值为：{}",preActionName);
        prePostActionExecute(preActionName);
    }

    //执行后置动作
    public void postActionExecute(String postActionName){
        prePostActionExecute(postActionName);
    }

    //执行前后置动作
    public void prePostActionExecute( String actionName){
        LOG.trace(">> TestCaseExecution.prePostActionExecute( String actionName)");
        LOG.trace("传入的参数值为：{}",actionName);
        //因为测试用例中很可能没有前置动作，所以需要判断一下，如果没有前置动作，则直接返回
        if(actionName == null){
            return;
        }
        SqlSessionFactory sqlSessionFactory = createDataSourceConnection();
        SqlSession session = sqlSessionFactory.openSession();
        try{
            PrePostMapper prePostMapper = session.getMapper(PrePostMapper.class);
            PrePostActionWrapper prePostActionWrapper = prePostMapper.selectPrePostActionWrapperByName(actionName);
            LOG.trace("查询到的前置动作对象为：{}",prePostActionWrapper);
            PrePostActionType type = prePostActionWrapper.getActionType();
            LOG.trace("前置动作的类型为：{}",type);
            switch (type){
                case SQL:
                    execSqlAction(prePostActionWrapper);
            }
        }finally {
            session.close();
        }
    }

    //执行sql操作
    public void execSqlAction(PrePostActionWrapper prePostActionWrapper) {
        LOG.trace(">> TestCaseExecution.execSqlAction(PrePostActionWrapper prePostActionWrapper)");
        LOG.trace("传入的参数值为：{}",prePostActionWrapper);
        String prePostActionStr = JacksonUtil.toJson(prePostActionWrapper.getAction());
        LOG.trace("将前后置动作对象prePostActionWrapper转为json字符串为：{}",prePostActionStr);
        SqlPrePostAction sqlPrePostAction = JacksonUtil.fromJson(prePostActionStr,SqlPrePostAction.class);
        //获取sql
        String sql = sqlPrePostAction.getSql();
        LOG.trace("执行前置动作的sql语句为：{}",sql);
        String replacedSql = replacePlaceholder(sql);
        LOG.trace("替换后的sql语句为：{}",sql);
        System.out.println("替换后的sql:"+replacedSql);
        //获取sql类型是否是SELECT或者其它
        String sqlType = replacedSql.substring(0,sql.indexOf(" "));
        System.out.println("sqlType:"+sqlType);
        //创建数据库连接
        DataSource dataSource = new TestCaseExecution().createDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        switch (sqlType){
            case "SELECT":
                //提取SELECT语句里的字段
                String[] fields = parseSql(sql);
                List<Map<String,Object>> row = jdbcTemplate.execute(new PreparedStatementCreator() {

                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        return con.prepareStatement(replacedSql);
                    }
                }, new PreparedStatementCallback<List<Map<String,Object>>>() {
                    @Override
                    public List<Map<String,Object>> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                        List<Map<String,Object>> list = new ArrayList<>();
                        ps.execute();
                        ResultSet rs = ps.getResultSet();
                        List<Object> ids = new ArrayList<>();
                        while (rs.next()) {
                            Map<String,Object> row = new HashMap<>();
                            //把SELECT的字段添加到map里
                            addFieldValueToMap(row,rs,fields);
                            list.add(row);
                        }
                        return list;
                    }
                });
                preFieldsValuesJson = new JsonPath("{\"pre\":"+JacksonUtil.toJson(row)+"}");
                System.out.println(preFieldsValuesJson.prettyPrint());
                break;
            default:
                try{
                    jdbcTemplate.execute(replacedSql);
                }catch (Exception e){
                    e.printStackTrace();
                    throw e;
                }

                break;
        }
    }

    //提取SELECT语句里的字段
    private String[] parseSql(String sql){
        Pattern pattern = Pattern.compile("SELECT\\s+(.+)\\s+FROM");
        Matcher matcher = pattern.matcher(sql);
        String[] sqlSelectParam = null;
        while(matcher.find()){
            String group = matcher.group(1);
            System.out.println(group);
            sqlSelectParam = group.split(",");
        }
        return sqlSelectParam;
    }

    //把SELECT的字段添加到map里
    private void addFieldValueToMap(Map<String,Object> row,ResultSet rs,String[] fields) throws SQLException {
        for(String field : fields){
            row.put(field.substring(field.indexOf(".")+1),rs.getObject(field));
        }
    }



    //mybatis配置
    public SqlSessionFactory createDataSourceConnection(){
        LOG.trace(">> TestCaseExecution.createDataSourceConnection()");
        LOG.trace("创建数据库连接");
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-conf.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;

    }

    //连接数据源
    public DataSource createDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/tplatform");
        dataSource.setUsername("root");
        dataSource.setPassword("tx123456");
        return dataSource;
    }

    /**
     * 连接数据源
     * @param sqlPrePostAction
     * @return
     */
    public DataSource createDataSource(SqlPrePostAction sqlPrePostAction){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://"+sqlPrePostAction.getHost()+":"+sqlPrePostAction.getPort()+"/"+sqlPrePostAction.getDatabase());
        dataSource.setUsername(sqlPrePostAction.getUser());
        dataSource.setPassword(sqlPrePostAction.getPassword());

        return dataSource;
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

    /**
     * 发起测试用例执行请求
     * @param testCase
     */
    public JsonPath execTest(Apis testCase){
        //初始化配置
        RequestSpecification spec = initSpecific();
        //发送请求
        JsonPath result = sendRequest(testCase,spec);
        System.out.println("result: "+result);
        return result;
    }

    /**
     * 初始化配置
     */
    public RequestSpecification initSpecific(){
        RequestSpecification spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:8088")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
        return spec;
    }

    /**
     * 发送请求
     * @param testCase
     * @param spec
     * @return
     */
    public JsonPath sendRequest(Apis testCase, RequestSpecification spec){
        HttpMethod method = testCase.getMethod();
        String url = testCase.getUrl().toString();
        //如果有参数替换参数
        String params =  replacePlaceholder(testCase.getParameters());
        System.out.println(params);
        switch (method){
            case GET:
                if(isToMap(params)){
                    Map<String,String> paramsMap = JacksonUtil.fromJson(params,Map.class);
                    return given()
                            .spec(spec)
                            .params(paramsMap)
                            .when()
                            .get(url)
                            .then()
                            .extract()
                            .jsonPath();
                }else {
                    return given()
                            .spec(spec)
                            .when()
                            .get(url + params)
                            .then()
                            .extract()
                            .jsonPath();
                }

            case POST:
                return given()
                        .spec(spec)
                        .body(params)
                        .when()
                        .post(url)
                        .then()
                        .extract()
                        .jsonPath();

            case PUT:
                return given()
                        .spec(spec)
                        .body(params)
                        .when()
                        .put(url)
                        .then()
                        .extract()
                        .jsonPath();

            case DELETE:
                if(isToMap(params)){
                    Map<String,String> paramsMap = JacksonUtil.fromJson(params,Map.class);
                    return given()
                            .spec(spec)
                            .params(paramsMap)
                            .when()
                            .delete(url)
                            .then()
                            .extract()
                            .jsonPath();
                }else {
                    return given()
                            .spec(spec)
                            .when()
                            .get(url)
                            .then()
                            .extract()
                            .jsonPath();
                }
        }
        return null;
    }

    public boolean isToMap(String str){
        try{
            JacksonUtil.fromJson(str,Map.class);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 替换测试用例里的所有参数方法
     * @param params
     * @return
     */
    public HashMap<String,String> replaceParams(HashMap<String,String> params){
        for(Map.Entry<String,String> entry : params.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            if(paramNeedReplace(value)){
                entry.setValue(replaceValue(value));
            }
        }
        return params;
    }

    /**
     * 判断参数是否包含$,如果包含返回true,表示需要替换参数
     * @param value
     * @return
     */
    public boolean paramNeedReplace(String value){
        if(value.contains("$")){
            return true;
        }
        return false;
    }

    /**
     * 替换参数值
     * @param value
     * @return
     */
    public String replaceValue(String value){
        SqlSessionFactory sqlSessionFactory = createDataSourceConnection();
        SqlSession session = sqlSessionFactory.openSession();
        try{
            ParamMapper paramMapper = session.getMapper(ParamMapper.class);
            ParameterWrapper parameterWrapper = paramMapper.selectParameterWrapperByName(value);
            String paramSqlStr = JacksonUtil.toJson(parameterWrapper.getValue());
            SqlParameter sqlParameter = JacksonUtil.fromJson(paramSqlStr,SqlParameter.class);
            //连接数据库源执行sql语句，返回查询到得实际参数值
            String actualValue = getSqlParameterValue(sqlParameter);
            return actualValue;

        }finally {
            session.close();
        }
    }
    /**
     * 根据数据源获取参数的值
     * @param sqlParameter
     * @return
     */
    public String getSqlParameterValue(SqlParameter sqlParameter){
        DataSource dataSource = createDataSource(sqlParameter);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String value = jdbcTemplate.queryForObject(sqlParameter.getSql(),String.class);

        return value;
    }

    /**
     * 验证期望和实际值
     * @param actualResult
     * @param checkPoint
     */
    public void execVerify(JsonPath actualResult, String checkPoint){
//        assertThat(actualResult.getInt("id")).isEqualTo(9);
        System.out.println(actualResult.prettyPrint());
        int leftBracket = checkPoint.indexOf("{")+"{".length();
        int rightBracked = checkPoint.indexOf("}");
        String check = checkPoint.substring(leftBracket,rightBracked);
        int equals = checkPoint.indexOf("in")+"in".length();
        String expected = checkPoint.substring(equals);
        System.out.println("check: "+check);
        System.out.println("expected: "+expected);

//        JsonPathUtil.verify(StrCheckPointType.STRINCLUDE,actualResult,check,expected);
//        assertThat(actualResult.get(check).equals(expected));
//        Assert.assertEquals(1+1,2);
    }

    public static void main(String[] args) {
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-conf.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        DataSource dataSource = new TestCaseExecution().createDataSource();
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        Environment environment = new Environment("development",transactionFactory, dataSource);
//        Configuration configuration = new Configuration(environment);
//        configuration.addMapper(TCasesMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        try{
            TCasesMapper tCasesMapper = session.getMapper(TCasesMapper.class);
            Apis testCase = tCasesMapper.selectApisById(90);
            System.out.println(testCase.getTestName());
        }finally {
            session.close();
        }
    }

    public void execCheck(JsonPath actualResult,Apis testCase){
        List<CheckPoint> checkPoints = testCase.getCheckPoints();
        SoftAssert assertion = new SoftAssert();
        for(int i=0; i<checkPoints.size(); i++){
            CheckPoint checkPoint = checkPoints.get(i);
            String type = checkPoint.getType();
            CheckPointType checkPointType = checkPoint.getCheckPointType();
            String checkKey = checkPoint.getCheckKey();
            String expected = checkPoint.getExpected();
            switch (type){
                case "StrCheckPoint":
                    JsonPathUtil.verify((StrCheckPointType)checkPointType,actualResult,checkKey,expected,assertion);
                    break;
                case "NumCheckPoint":
                    JsonPathUtil.verify((NumCheckPointType)checkPointType,actualResult,checkKey,expected,assertion);
                    break;
                case "ListCheckPoint":
                    JsonPathUtil.verify((ListCheckPointType)checkPointType,actualResult,checkKey,expected,assertion);
            }
        }
        assertion.assertAll();
    }

    //替换占位符
    public  String replacePlaceholder(String source) {
        LOG.trace(">> replacePlaceholder(String source)");
        LOG.trace("需要替换的语句为：{}",source);
        return replacePlaceholder("\\$\\{.*?}", source);
    }

    //替换占位符
    public  String replacePlaceholder(String pattern, String source) {
        LOG.trace(">> replacePlaceholder(String pattern, String source)");
        LOG.trace("替换的正则表达式为：{}，需要替换的语句为：{}",pattern,source);
        if (StringUtils.isBlank(source) || pattern == null) {
            return null;
        }
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(source);
        ArrayList<String> result = new ArrayList<>();
        while (matcher.find()) {
            String match = matcher.group();
            result.add(match);
            String parseFields = parseField(match);
            if(parseFields == null){
                return source;
            }
            source = source.replace(match,parseFields);
        }

        return source;
    }

    //解析占位符并替换
    private  String parseField(String str){
        String regex = str.substring(str.indexOf("{")+1,str.indexOf("}"));
        LOG.trace("需要替换的占位字符串为：{}",regex);
        if(str.startsWith("${pre.")){
            LOG.trace("替换后的字符串为：{}",preFieldsValuesJson.getString(regex));
            return preFieldsValuesJson.getString(regex);
        }
        LOG.trace("替换后的字符为：{}",replaceValue(regex));
        return replaceValue(regex);

    }
}
