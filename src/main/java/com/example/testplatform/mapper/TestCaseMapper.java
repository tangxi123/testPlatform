package com.example.testplatform.mapper;

import com.example.testplatform.model.Apis;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface TestCaseMapper {

    int insertTestcase(Apis apis);

    List<Apis> selectApisByExpected(@Param("expected") String expected);

    Apis selectApisByTestname(@Param("testname") String testname);

    int deleteTestcaseByTestname(@Param("testname") String testname);

    int updateTestResult(@Param("testname") String testname, @Param("actual") String actual, @Param("isPassed") Boolean isPassed, @Param("testAt") LocalDateTime testAt);




}
