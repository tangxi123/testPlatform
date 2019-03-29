package com.example.testplatform.model;

import com.example.testplatform.model.parametertype.Parameter;
import com.example.testplatform.model.parametertype.ParameterType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParameterWrapper{
    //参数Id
    private int id;

    //参数名字
    private String name;

    //备注描述
    private String descs;

    //参数类型
    private ParameterType type;

    //参数
    private Parameter value;

//    private Map<String,Object> value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescs() {
        return descs;
    }

    public void setDesc(String descs) {
        this.descs = descs;
    }

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }

    public Parameter getValue() {
        return value;
    }

    public void setValue(Parameter value) {
        this.value = value;
    }
}
