package com.example.testplatform.mapper;

import com.example.testplatform.model.ParameterWrapper;
import com.example.testplatform.model.parametertype.Parameter;
import com.example.testplatform.model.parametertype.SqlParameter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ParamMapper {
    //插入parameter主表数据
    int insertParameter(ParameterWrapper param);

    //插入类型为sql的parameter数据
    int insertSqlParamter(Parameter sqlPara);

    //根据Id查询parameter主表数据
    ParameterWrapper selectParameterWrapperById(int id);

    //根据参数名字查询parameter主表数据
    ParameterWrapper selectParameterWrapperByName(String name);



}
