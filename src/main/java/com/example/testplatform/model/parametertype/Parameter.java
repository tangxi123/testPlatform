package com.example.testplatform.model.parametertype;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;

@JsonTypeInfo(use = Id.NAME,include = As.PROPERTY,property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "SqlParameter",value = SqlParameter.class)
})
public interface Parameter {
    int getParamId();
    void setParamId(int paramId);
}
