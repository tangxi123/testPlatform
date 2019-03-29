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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.tangxi.testplatform.api.testcase.util.JacksonUtil;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Service
@MapperScan("com.example.testplatform.ParamMapper.xml")
public class ParameterService {
    @Autowired
    ParamMapper paramMapper;

    @Autowired
    SqlParameterService sqlParameterService;

    /**
     * 新增参数化设置方法
     * @param parameterWrapper
     * @return
     */
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

    /**
     * 替换测试用例里的所有参数方法
     * @param params
     * @return
     */
    public HashMap<String,String> replaceParams(HashMap<String,String> params){
        for(Map.Entry<String,String> entry : params.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            if(paramNeedReplace(value)){
                entry.setValue(replaceValue(value));
            }
        }
        return params;
    }

    /**
     * 判断参数是否包含$,如果包含返回true,表示需要替换参数
     * @param value
     * @return
     */
    public boolean paramNeedReplace(String value){
        if(value.contains("$")){
            return true;
        }
        return false;
    }

    /**
     * 替换参数值
     * @param value
     * @return
     */
    public String replaceValue(String value){
        ParameterWrapper parameterWrapper = paramMapper.selectParameterWrapperByName(value);
        String paramSqlStr = JacksonUtil.toJson(parameterWrapper.getValue());
        SqlParameter sqlParameter = JacksonUtil.fromJson(paramSqlStr,SqlParameter.class);
        //连接数据库源执行sql语句，返回查询到得实际参数值
        String actualValue = sqlParameterService.getSqlParameterValue(sqlParameter);
        return actualValue;
    }


}
