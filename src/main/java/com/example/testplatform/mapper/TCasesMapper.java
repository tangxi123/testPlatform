package com.example.testplatform.mapper;

import com.example.testplatform.model.Apis;
import com.example.testplatform.payload.ApiEntityRequest;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface TCasesMapper {

    int insertTestcase(Apis apis);

    List<Apis> selectApisByExpected(@Param("expected") String expected);

    Apis selectApisByTestname(@Param("testname") String testname);

    Apis selectApisById(@Param("id") Long id);

    int countTestcaseById(@Param("id") Long id);

    int updateTestcase(Apis apis);

    List<Apis> selectApisByName(@Param("name") String name);

    List<Apis> selectApisByDescs(@Param("descs") String descs);

    int deleteTestcaseByTestname(@Param("testname") String testname);

    int deleteTestcaseById(@Param("id") Long id);

    int updateTestResult(@Param("testname") String testname, @Param("actual") String actual, @Param("isPassed") Boolean isPassed, @Param("testAt") LocalDateTime testAt);

    List<Apis> getAllTestcases();




}
