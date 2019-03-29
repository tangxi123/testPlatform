package com.example.testplatform.test;

import com.example.testplatform.controller.TestCaseController;
import com.example.testplatform.mapper.TCasesMapper;
import com.example.testplatform.model.Apis;
import com.example.testplatform.model.checkpoint.CheckPoint;
import com.example.testplatform.model.checkpoint.StrCheckPointType;
import com.example.testplatform.service.TestCaseService;

import com.example.testplatform.util.JsonPathUtil;
import com.example.testplatform.util.TestCaseExecution;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jayway.restassured.path.json.JsonPath;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.tangxi.testplatform.api.testcase.util.JacksonUtil;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@Listeners(TestResultListener.class)
public class TestExec {
    private static final Logger LOG = LoggerFactory.getLogger(TestExec.class);

//    @Parameters({"id"})
//    @BeforeClass
//    public void getTestcase(String id){
//        TestCaseExecution execution = new TestCaseExecution();
//        execution.execTestCaseById(Integer.parseInt(id));
//    }
//
    @Parameters({"id"})
    @Test
    public void test(String id) {
//        Assert.assertEquals(1+1,2);
        if(LOG.isTraceEnabled()) {
            LOG.trace("开始执行测试方法");
            LOG.trace(">> TestExec.getAllTestcases()");
        }
        TestCaseExecution execution = new TestCaseExecution();
        execution.execTestCaseById(Integer.parseInt(id));
    }
//        JsonPath jsonPath = new JsonPath("{\n" +
//                "\t\"name\": \"查询插入的数据id\",\n" +
//                "\t\"descs\": \"在测试执行后需要删除插入的数据\",\n" +
//                "\t\"actionType\": \"SQL\",\n" +
//                "\t\"action\": {\n" +
//                "\t\t\"type\": \"SqlPrePostAction\",\n" +
//                "\t\t\"host\": \"localhost\",\n" +
//                "\t\t\"port\": \"3306\",\n" +
//                "\t\t\"database\": \"tplatform\",\n" +
//                "\t\t\"user\": \"root\",\n" +
//                "\t\t\"password\": \"tx123456\",\n" +
//                "\t\t\"sql\": \"DELETE FROM zsi_test_case WHERE id=81\"\n" +
//                "\t}\n" +
//                "}");
//        String value = jsonPath.getString("name");
//        System.out.println(value);
//        assertThat(jsonPath.getString("name").equals("123"));
//        String value = jsonPath.getString("blogs[0].posts[0].author.name");
//        testCaseController.execTestCaseById(90);
//        assertThat(1).isEqualTo(1);
//        Assert.assertEquals(1+1,2);
//    }

//    @Test
//    public void test2(){
//        SqlSessionFactory sqlSessionFactory = new TestCaseExecution().createDataSourceConnection();
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            TCasesMapper tCasesMapper = session.getMapper(TCasesMapper.class);
//            Apis testCase = tCasesMapper.selectApisById(103);
//            JsonPath actualResult = new TestCaseExecution().execTest(testCase);
//            List<CheckPoint> checkPoints = testCase.getCheckPoints();
//            for(int i=0; i<checkPoints.size(); i++){
//                CheckPoint checkPoint = checkPoints.get(i);
//                String type = checkPoint.getType();
//                StrCheckPointType strCheckPointType = checkPoint.getStrCheckPointType();
//                String checkKey = checkPoint.getCheckKey();
//                String expected = checkPoint.getExpected();
//                switch (type){
//                    case "StrCheckPoint":
//                        JsonPathUtil.verify(strCheckPointType,actualResult,checkKey,expected);
//
//                }
//            }
////            String checkPointsStr = JacksonUtil.toJson(checkPoints);
////            System.out.println("checkPointsStr: "+checkPointsStr);
//        }finally {
//            session.close();
//        }
//    }

//    @Test
//    public void test3(){
//        SoftAssert assertion = new SoftAssert();
//        assertion.assertEquals("1","4");
////        assertion.assertEquals("12","54");
////        assertion.assertEquals("3","3");
////        assertion.assertEquals("2","4");
////        assertion.executeAssert();
//        assertion.assertAll();
//    }

//    @Test
//    public void test() {
////        Assert.assertEquals(1+1,2);
//        TestCaseExecution execution = new TestCaseExecution();
//        execution.execTestCaseById(Integer.parseInt("129"));
//    }

    //数据库操作测试
//    @Test
//    public void test(){
//        String sql = "SELECT tc.id,tc.testname,tc.suite FROM zsi_test_case tc LEFT JOIN zsi_parameter p ON tc.parameters = p.name";
////        sql.split()
////        String sql = "INSERT INTO tplatform.zsi_test_case (testname, method, url, headers, parameters, expected, created_at, updated_at, actual, is_passed, test_at, suite, test_module, descs, status_code, pre_action_name, post_action_name, check_points) VALUES ('test1', 'GET', '/testcase/id/', '{\"Content-Type\":\"application/json\"}', '{\"id\":\"${testCaseId}\"}', '{\"error\":{\"code\":\"USER_SERVER_10002\",\"reason\":\"账号或密码错误\"}}', '2019-02-28 14:51:22', '2019-02-28 14:51:22', '', null, null, 'testcase', 'query', '完整测试', null, '插入一条数据测试', '删除一条数据测试', '[{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STREQUAL\",\"checkKey\":\"testname\",\"expected\":\"test\"},{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STRNOTEQUAL\",\"checkKey\":\"descs\",\"expected\":\"完整测试\"},{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STRINCLUDE\",\"checkKey\":\"testModule\",\"expected\":\"ll\"},{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STRNOTINCLUDE\",\"checkKey\":\"method\",\"expected\":\"GET\"},{\"type\":\"NumCheckPoint\",\"numCheckPointType\":\"NUM_EQ\",\"checkKey\":\"number\",\"expected\":\"1\"}]');";
//        String sqlType = sql.substring(0,sql.indexOf(" "));
//        System.out.println("sqlType:"+sqlType);
//        DataSource dataSource = new TestCaseExecution().createDataSource();
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        switch (sqlType){
//            case "SELECT":
//                //提取参数
//                String[] fields = parseSql(sql);
//                List<Map<String,Object>> rows = jdbcTemplate.execute(new PreparedStatementCreator() {
//
//                    @Override
//                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                        return con.prepareStatement(sql);
//                    }
//                }, new PreparedStatementCallback<List<Map<String,Object>>>() {
//                    @Override
//                    public List<Map<String,Object>> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//                        List<Map<String,Object>> list = new ArrayList<>();
//                        ps.execute();
//                        ResultSet rs = ps.getResultSet();
//                        List<Object> ids = new ArrayList<>();
//                        while (rs.next()) {
//                            Map<String,Object> row = new HashMap<>();
//                            //把选取的字段添加到map里
//                            addFieldValueToMap(row,rs,fields);
//                            list.add(row);
//                        }
//                        return list;
//                    }
//                });
//                JsonPath jp = new JsonPath("{\"pre\":"+JacksonUtil.toJson(rows)+"}");
//                System.out.println(jp.prettyPrint());
//                System.out.println(jp.getString("pre.id"));
//                break;
//            default:
//                jdbcTemplate.execute(sql);
//                break;
//        }
//    }
//
//    private String[] parseSql(String sql){
//        Pattern pattern = Pattern.compile("SELECT\\s+(.+)\\s+FROM");
//        Matcher matcher = pattern.matcher(sql);
//        String[] sqlSelectParam = null;
//        while(matcher.find()){
//            String group = matcher.group(1);
//            System.out.println(group);
//            sqlSelectParam = group.split(",");
//        }
//        return sqlSelectParam;
//    }
//
//    private void addFieldValueToMap(Map<String,Object> row,ResultSet rs,String[] fields) throws SQLException {
//        for(String field : fields){
//            row.put(field.substring(field.indexOf(".")+1),rs.getObject(field));
//        }
//    }

}


