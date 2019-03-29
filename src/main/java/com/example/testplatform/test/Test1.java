package com.example.testplatform.test;

import com.example.testplatform.model.Apis;
import com.example.testplatform.util.TestCaseExecution;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class Test1 {
    @Test
    public void test(){
        JsonPath jsonPath = new JsonPath("{\n" +
                "\t\"name\": \"查询插入的数据id\",\n" +
                "\t\"descs\": \"在测试执行后需要删除插入的数据\",\n" +
                "\t\"actionType\": \"SQL\",\n" +
                "\t\"action\": {\n" +
                "\t\t\"type\": \"SqlPrePostAction\",\n" +
                "\t\t\"host\": \"localhost\",\n" +
                "\t\t\"port\": \"3306\",\n" +
                "\t\t\"database\": \"tplatform\",\n" +
                "\t\t\"user\": \"root\",\n" +
                "\t\t\"password\": \"tx123456\",\n" +
                "\t\t\"sql\": \"DELETE FROM zsi_test_case WHERE id=81\"\n" +
                "\t}\n" +
                "}");
        String value = jsonPath.getString("name");
        System.out.println(value);
        Assert.assertEquals(jsonPath.getString("name"),"123");
    }

    @Test
    public void test2(){
    try {
        Assert.assertNotEquals(1, 1);
    }catch (AssertionError e){
        System.out.println("hello");
//        e.toString();
//        e.printStackTrace();
    }
    }

//    @Test
//    public void test3(){
//        String sql = "DELETE FROM zsi_test_case";
//        String str = "${pre.id[0]}";
//        JsonPath jp = new JsonPath("{\n" +
//                "    \"pre\": [\n" +
//                "        {\n" +
//                "            \"id\": 120,\n" +
//                "            \"testname\": \"test\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}");
//        String result = replacePlaceholder(sql, jp);
//        System.out.println(result);
//    }

//    private void replacePlaceHolder(String source, JsonPath jp){
//
//    }
//
//
//
//    @Test
//    private void test4(){
//        String str1 = "DELETE FROM zsi_test_case WHERE id=${pre.id[0]},testname=${pre.testname[0]}";  //==>结果:["${pre.id[0]}","${pre.testname[0]"]
//        String str2 = "DELETE FROM zsi_test_case WHERE id='10',testname=${pre.testname[0]}"; //===>结果:["10","${pre.testname[0]}"]
//        String str3 = "SELECT * FROM zsi_test_case WHERE suite='suite',testname=${pre.testname[0]},id='20'"; //===>结果:["*".suite","${pre.testname[0]}","20"]
//        String str = "SELECT id,${pre.id[0]} FROM zsi_test_case WHERE suite='suite',testname=${pre.testname[0]},id='20'"; //===>结果:["id","${pre.id[0]}.suite","${pre.testname[0]}","20"]
//        String str4 = "UPDATE Person SET Address = 'Zhongshan 23', City = 'Nanjing' WHERE LastName = ${pre.testname[0]}"; //===>结果：["Zhongshan 23","Nanjing","${pre.testname[0]}"]
//        String str5 = "INSERT INTO Persons VALUES ( ${pre.testname[0]}, 'Bill') WHERE id = ${pre.id[0]}"; //===>结果：[" ${pre.testname[0]}","Bill","${pre.id[0]}"]
//
//
//    }
//
//    public static String replacePlaceholder(String source, JsonPath jp) {
//        return replacePlaceholder("\\$\\{.*?}", source,jp);
//    }
//
//    public static String replacePlaceholder(String pattern, String source, JsonPath jp) {
//        if (StringUtils.isBlank(source) || pattern == null) {
//            return null;
//        }
//        Pattern compile = Pattern.compile(pattern);
//        Matcher matcher = compile.matcher(source);
//        ArrayList<String> result = new ArrayList<>();
//        while (matcher.find()) {
//            String match = matcher.group();
//            result.add(match);
//            source = source.replace(match,parsePreField(match,jp));
//        }
//
//        return source;
//    }
//
//    private static String parsePreField(String str, JsonPath jp){
//
//        if(str.startsWith("${pre.")){
//            String regrex = str.substring(str.indexOf("{")+1,str.indexOf("}"));
//            System.out.println("regrex: "+regrex);
//            String actualValue = jp.getString(regrex);
//            return actualValue;
//        }
//        return null;
//    }

    @Test
    public void test5(){
        RequestSpecification spec = new TestCaseExecution().initSpecific();
//        JsonPath jp = given()
//                .spec(spec)
//                .when()
//                .delete("/testcase/delete/137")
//                .then()
//                .extract()
//                .jsonPath();
//        System.out.println(jp.prettyPrint());

//        String postStr ="{\n" +
//                "\t\"suite\":\"testcase\",\n" +
//                "\t\"testModule\":\"query\",\n" +
//                "\t\"descs\":\"完整测试\",\n" +
//                "\t\"testname\":\"test\",\n" +
//                "\t\"method\":\"GET\",\n" +
//                "\t\"url\":\"/testcase/id/\",\n" +
//                "\t\"headers\":{\n" +
//                "\t\t\"Content-Type\":\"application/json\"\n" +
//                "\t},\n" +
//                "\t\"parameters\":\"{'id':'${testCaseId}'}\",\n" +
//                "    \"preActionNameList\":[\"插入一条数据测试\",\"获取插入数据的id\"],\n" +
//                "    \"preActionName\":\"插入一条数据测试\",\n" +
//                "    \"postActionName\":\"删除一条数据测试\",\n" +
//                "    \"checkPoints\":[\n" +
//                "    \t{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STREQUAL\",\"checkKey\":\"testname\",\"expected\":\"test\"},\n" +
//                "    \t{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STRNOTEQUAL\",\"checkKey\":\"descs\",\"expected\":\"完整测试\"},\n" +
//                "    \t{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STRINCLUDE\",\"checkKey\":\"testModule\",\"expected\":\"ll\"},\n" +
//                "    \t{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STRNOTINCLUDE\",\"checkKey\":\"method\",\"expected\":\"GET\"},\n" +
//                "    \t{\"type\":\"NumCheckPoint\",\"numCheckPointType\":\"NUM_EQ\",\"checkKey\":\"number\",\"expected\":123.56},\n" +
//                "    \t{\"type\":\"NumCheckPoint\",\"numCheckPointType\":\"NUM_GT\",\"checkKey\":\"number\",\"expected\":1.2},\n" +
//                "    \t{\"type\":\"NumCheckPoint\",\"numCheckPointType\":\"NUM_LT\",\"checkKey\":\"number\",\"expected\":1},\n" +
//                "    \t{\"type\":\"NumCheckPoint\",\"numCheckPointType\":\"NUM_GT_EQ\",\"checkKey\":\"number\",\"expected\":56.7},\n" +
//                "    \t{\"type\":\"NumCheckPoint\",\"numCheckPointType\":\"NUM_LT_EQ\",\"checkKey\":\"number\",\"expected\":300.90},\n" +
//                "    \t{\"type\":\"ListCheckPoint\",\"listCheckPointType\":\"LIST_SIZE\",\"checkKey\":\"checkPoints\",\"expected\":10},\n" +
//                "    \t{\"type\":\"ListCheckPoint\",\"listCheckPointType\":\"LIST_CONTAINS\",\"checkKey\":\"checkPoints.type\",\"expected\":\"ListCheckPoint\"},\n" +
//                "    \t{\"type\":\"ListCheckPoint\",\"listCheckPointType\":\"LIST_GET\",\"checkKey\":\"checkPoints\",\"expected\":\"{expected=test, type=StrCheckPoint, strCheckPointType=STREQUAL, checkKey=testname}\"}\n" +
//                "    \t\n" +
//                "    \t],\n" +
//                "    \"expected\":{\"error\":{\"code\":\"USER_SERVER_10002\",\"reason\":\"账号或密码错误\"}}\n" +
//                "}";
//        JsonPath jp = given()
//                .spec(spec)
//                .body(postStr)
//                .when()
//                .post("/testcase/create")
//                .then()
//                .extract()
//                .jsonPath();
//        jp.prettyPrint();

        String s = "{\"name\": \"测试插入数据的id\",\"descs\": \"测试在上一步骤中，插入到数据表中的数据的id值,为了能在后置动作中，根据id值清除这条数据，保持测试数据库干净\",\"actionType\": \"SQL\",\"action\": {\"type\": \"SqlPrePostAction\",\"host\": \"localhost\",\"port\": \"3306\",\"database\": \"tplatform_pro\",\"user\": \"root\",\"password\": \"tx123456\",\"sql\": \"SELECT id FROM tplatform_pro.zsi_test_case WHERE testname='test'\"}}";
        System.out.println(s);
    }

    @Test
    public void test6() {
//        Assert.assertEquals(1+1,2);
        TestCaseExecution execution = new TestCaseExecution();
        execution.execTestCaseById(Integer.parseInt("139"));
    }
}
