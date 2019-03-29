package com.example.testplatform.model.checkpoint;


import com.example.testplatform.model.Apis;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,include = JsonTypeInfo.As.PROPERTY,property = "type",visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "StrCheckPoint",value = StrCheckPoint.class),
        @JsonSubTypes.Type(name = "NumCheckPoint",value = NumCheckPoint.class),
        @JsonSubTypes.Type(name = "ListCheckPoint",value = ListCheckPoint.class)
})
public interface CheckPoint {
    public String getType();
    public CheckPointType getCheckPointType();
    public String getCheckKey();
    public String getExpected();
//    public CheckPointType getCheckPointType();
}
