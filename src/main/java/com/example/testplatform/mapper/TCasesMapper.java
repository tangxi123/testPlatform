package com.example.testplatform.mapper;

import com.example.testplatform.model.Apis;
import com.example.testplatform.payload.ApiEntityRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TCasesMapper {

    //插入测试用例
    int insertTestcase(Apis apis);

    List<Apis> selectApisByExpected(@Param("expected") String expected);

    Apis selectApisByTestname(@Param("testname") String testname);

    //根据id查询测试用例
    Apis selectApisById(@Param("id") int id);

    int countTestcaseById(@Param("id") Long id);

    int updateTestcase(Apis apis);

    List<Apis> selectApisByName(@Param("name") String name);

    List<Apis> selectApisByDescs(@Param("descs") String descs);

    int deleteTestcaseByTestName(@Param("testName") String testName);

    int deleteTestcaseById(@Param("id") Long id);

    int updateTestResult(@Param("testname") String testname, @Param("actual") String actual, @Param("isPassed") Boolean isPassed, @Param("testAt") LocalDateTime testAt);

    List<Apis> getAllTestcases();

    //根据id,名称，描述查询用例
    List<Apis> queryByFields(Map<String,Object> params);

    //更新测试用例测试结果和测试时间
    int updateTestCaseTestResult(@Param("id") int id,@Param("status") int status, @Param("startTestTime") Date startTestTime);

    //查询测试用例测试时间和测试结果
    Map<String,Object> queryTestCaseTestResult(@Param("id") int id);







}
