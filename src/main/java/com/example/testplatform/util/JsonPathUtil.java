package com.example.testplatform.util;

import com.example.testplatform.model.checkpoint.CheckPointType;
import com.example.testplatform.model.checkpoint.ListCheckPointType;
import com.example.testplatform.model.checkpoint.NumCheckPointType;
import com.example.testplatform.model.checkpoint.StrCheckPointType;
import com.jayway.restassured.path.json.JsonPath;
import org.tangxi.testplatform.api.testcase.util.JacksonUtil;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonPathUtil {
    /**
     * 根据String类型校验点进行验证
     * @param type String校验类型
     * @param actualResult 实际的JsonPath结果
     * @param checkKey 用于检查的点
     * @param expected 期望结果
     */
    public static void verify(StrCheckPointType type, JsonPath actualResult, String checkKey, String expected, SoftAssert assertion){
        if(type == null){ return; }
        switch (type){
            case STREQUAL:
                assertion.assertEquals(actualResult.getString(checkKey),expected);
                System.out.println("字符串相等判断：");
                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getString(checkKey));
                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
                break;
            case STRNOTEQUAL:
                assertion.assertNotEquals(actualResult.getString(checkKey), expected);
                System.out.println("字符串不相等判断：");
                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getString(checkKey));
                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
//                Assert.assertNotEquals("345","34");
                break;
            case STRINCLUDE:
                assertion.assertTrue(actualResult.getString(checkKey).contains(expected));
                System.out.println("字符串包含判断：");
                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getString(checkKey));
                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
//                assertion.assertTrue("hello".contains("he0"));
                break;
            case STRNOTINCLUDE:
                assertion.assertFalse(actualResult.getString(checkKey).contains(expected));
                System.out.println("字符串不包含判断：");
                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getString(checkKey));
                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
                break;
        }
    }

    /**
     * 根据Num类型校验点进行验证
     * @param type
     * @param actualResult
     * @param checkKey
     * @param expected
     * @param assertion
     */
    public static void verify(NumCheckPointType type,JsonPath actualResult, String checkKey, String expected, SoftAssert assertion){
        actualResult = new JsonPath(" {\"number\":123}");
        if(type == null){ return; }
        switch (type){
            case NUM_EQ:
                assertion.assertEquals(actualResult.getDouble(checkKey),Double.parseDouble(expected));
                System.out.println("数值相等判断：");
                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getDouble(checkKey));
                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
                break;
            case NUM_GT:
                assertion.assertTrue(actualResult.getDouble(checkKey)>actualResult.getDouble(checkKey));
                System.out.println("数值大于判断：");
                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getInt(checkKey));
                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
                break;
            case NUM_LT:
                assertion.assertTrue(actualResult.getDouble(checkKey)>Double.parseDouble(expected));
                System.out.println("数值小于判断：");
                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getDouble(checkKey));
                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
                break;
            case NUM_GT_EQ:
                assertion.assertTrue(actualResult.getDouble(checkKey)>=Double.parseDouble(expected));
                System.out.println("数值大于等于判断：");
                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getDouble(checkKey));
                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
                break;
            case NUM_LT_EQ:
                assertion.assertTrue(actualResult.getDouble(checkKey)<=Double.parseDouble(expected));
                System.out.println("数值小于等于判断：");
                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getDouble(checkKey));
                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
                break;
        }
    }

    /**
     * 根据List类型校验点进行验证
     * @param type
     * @param actualResult
     * @param checkKey
     * @param expected
     * @param assertion
     */
    public static void verify(ListCheckPointType type, JsonPath actualResult, String checkKey, String expected, SoftAssert assertion){
       actualResult = new JsonPath("{\"checkPoints\":[\n" +
                "    \t{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STREQUAL\",\"checkKey\":\"testname\",\"expected\":\"test\"},\n" +
                "    \t{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STRNOTEQUAL\",\"checkKey\":\"descs\",\"expected\":\"完整测试\"},\n" +
                "    \t{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STRINCLUDE\",\"checkKey\":\"testModule\",\"expected\":\"ll\"},\n" +
                "    \t{\"type\":\"StrCheckPoint\",\"strCheckPointType\":\"STRNOTINCLUDE\",\"checkKey\":\"method\",\"expected\":\"GET\"},\n" +
                "    \t{\"type\":\"NumCheckPoint\",\"numCheckPointType\":\"NUM_EQ\",\"checkKey\":\"number\",\"expected\":123.56},\n" +
                "    \t{\"type\":\"NumCheckPoint\",\"numCheckPointType\":\"NUM_GT\",\"checkKey\":\"number\",\"expected\":1.2},\n" +
                "    \t{\"type\":\"NumCheckPoint\",\"numCheckPointType\":\"NUM_LT\",\"checkKey\":\"number\",\"expected\":1},\n" +
                "    \t{\"type\":\"NumCheckPoint\",\"numCheckPointType\":\"NUM_GT_EQ\",\"checkKey\":\"number\",\"expected\":56.7},\n" +
                "    \t{\"type\":\"NumCheckPoint\",\"numCheckPointType\":\"NUM_LT_EQ\",\"checkKey\":\"number\",\"expected\":300.90}\n" +
                "    \t]}");
        System.out.println(actualResult.getList("checkPoints").size());
        System.out.println(actualResult.getList("checkPoints").get(4));
        System.out.println(actualResult.getList("checkPoints"));
        System.out.println(actualResult.getList("checkPoints.type").contains("NumCheckPoint"));
        if(type == null){ return; }
        switch (type){
            case LIST_SIZE:
                assertion.assertEquals(actualResult.getList(checkKey).size(),expected);
                System.out.println("List大小判断：");
                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getList(checkKey).size());
                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
                break;
            case LIST_CONTAINS:
                assertion.assertTrue(actualResult.getList(checkKey).contains(expected));
                System.out.println("List包含判断：");
                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getList(checkKey));
                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
                break;
//            case LIST_GET:
//                assertion.assertEquals(actualResult.getList(checkKey).get(0),expected);
//                System.out.println("List获取判断：");
//                System.out.println("接口返回的json字符串中"+checkKey+"的值为："+actualResult.getList(checkKey).get(0));
//                System.out.println("期望的json字符串中: "+checkKey+"的值为："+expected);
//                break;
        }

    }

    public static void main(String[] args) {
//        JsonPathUtil.verify();
    }
}
