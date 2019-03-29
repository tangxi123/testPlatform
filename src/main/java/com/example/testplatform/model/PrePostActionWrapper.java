package com.example.testplatform.model;

import com.example.testplatform.model.prepostactiontype.PrePostAction;
import com.example.testplatform.model.prepostactiontype.PrePostActionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrePostActionWrapper {
    //动作ID
    private int id;

    //动作名
    private String name;

    //备注
    private String descs;

    //动作类型
    private PrePostActionType actionType;

    //动作
    private PrePostAction action;

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

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public PrePostActionType getActionType() {
        return actionType;
    }

    public void setActionType(PrePostActionType actionType) {
        this.actionType = actionType;
    }

    public PrePostAction getAction() {
        return action;
    }

    public void setAction(PrePostAction action) {
        this.action = action;
    }
}
