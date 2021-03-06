package com.yhguo.common.bean.response;

import com.yhguo.common.bean.common.AttributeBean;

import java.util.List;

public class RoleResBean {

    private Integer id;
    private String name;
    private String description;
    private Integer systemId;
    private String systemName;
    private List<AttributeBean> permissions;
    private String operateTime;
    private String operator;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public List<AttributeBean> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<AttributeBean> permissions) {
        this.permissions = permissions;
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
}
