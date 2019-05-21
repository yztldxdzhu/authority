package com.yhguo.common.bean.response;

import com.yhguo.common.bean.common.AttributeBean;

import java.util.List;
import java.util.Map;

public class UserResBean {

    private Integer id;
    private String name;
    private String password;
    private Integer disableFlag;
    private List<Map> roles;
    private String operateTime;
    private String operator;
    private String lastLoginTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Integer disableFlag) {
        this.disableFlag = disableFlag;
    }

    public List<Map> getRoles() {
        return roles;
    }

    public void setRoles(List<Map> roles) {
        this.roles = roles;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
