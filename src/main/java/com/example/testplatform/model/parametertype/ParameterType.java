package com.example.testplatform.model.parametertype;

/**
 * @author tangx
 * 2019-02-13 14:00
 */
public enum ParameterType {
    SQL("sql");

    private String field;

    ParameterType(String field){
        this.field= field;
    }

}
