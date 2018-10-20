package com.example.testplatform.mapper;

import com.example.testplatform.model.Apis;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestCaseMapper {

    int insertTestcase(Apis apis);

    List<Apis> selectApisByExpected(@Param("expected")String expected);

    int deleteTestcaseByTestname(@Param("testname") String testname);
}
