package com.example.testplatform.mapper;

import com.example.testplatform.model.Apis;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface TestCaseMapper {
    @Options(useGeneratedKeys = true, keyColumn = "id")
    @Insert("INSERT INTO test_case(testname, method, headers, parameters, expected, url, created_at, updated_at) " +
            "VALUES (#{testname}, #{method}, #{headers}, #{parameters}, #{expected}, #{url}, #{createdAt}, #{updatedAt})")
    int insertTestcase(Apis apis);
}
