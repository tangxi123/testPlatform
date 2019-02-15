package com.example.testplatform.service;

import com.example.testplatform.mapper.ParamMapper;
import com.example.testplatform.model.ParameterWrapper;
import com.example.testplatform.model.parametertype.Parameter;
import com.example.testplatform.model.parametertype.ParameterType;
import com.example.testplatform.model.parametertype.SqlParameter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Service
@MapperScan("com.example.testplatform.ParamMapper.xml")
public class ParameterService {
    @Autowired
    ParamMapper paramMapper;

    @Transactional
    public ResponseEntity<ParameterWrapper> createParameter(ParameterWrapper parameterWrapper){
        ParameterType type = parameterWrapper.getType();
        Parameter value = parameterWrapper.getValue();


        switch(type){
            case SQL:
                paramMapper.insertParameter(parameterWrapper);
                value.setParamId(parameterWrapper.getId());
                paramMapper.insertSqlParamter(value);
                return null;
             default:
                 return null;
        }


    }


}
