package com.example.testplatform.model.checkpoint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import net.bytebuddy.asm.Advice;


@JsonTypeName("StrCheckPoint")

public class StrCheckPoint implements CheckPoint{
    //将要验证的类型
    private String type;

    //字符串具体验证类型
    private StrCheckPointType strCheckPointType;

    //验证key
    private String checkKey;

    //期望值
    private String expected;

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public StrCheckPointType getStrCheckPointType() {
        return strCheckPointType;
    }

    public void setStrCheckPointType(StrCheckPointType strCheckPointType) {
        this.strCheckPointType = strCheckPointType;
    }

    @JsonIgnore
    @Override
    public CheckPointType getCheckPointType() {
        return strCheckPointType;
    }


    @Override
    public String getCheckKey() {
        return checkKey;
    }

    public void setCheckKey(String checkKey) {
        this.checkKey = checkKey;
    }

    @Override
    public String getExpected() {
        return expected;
    }



    public void setExpected(String expected) {
        this.expected = expected;
    }
}
