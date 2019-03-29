package com.example.testplatform.model.prepostactiontype;

import com.example.testplatform.model.parametertype.SqlParameter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Tangx
 * 2019-02-13 15:02
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,include = JsonTypeInfo.As.PROPERTY,property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "SqlPrePostAction",value = SqlPrePostAction.class)
})
public interface PrePostAction {
    int getActionId();
    void setActionId(int id);
}
