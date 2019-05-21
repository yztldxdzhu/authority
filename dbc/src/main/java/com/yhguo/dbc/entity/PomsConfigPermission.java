package com.yhguo.dbc.entity;

import com.yhguo.common.bean.response.PermissionResBean;

import java.util.Date;
import java.util.List;

public class PomsConfigPermission {
    private Short id;

    private String type;

    private String name;

    private String description;

    private String state;

    private String icon;

    private Short parentId;

    private Byte sort;

    private Short systemId;

    private Date operateTime;

    private String operator;

    private List<PermissionResBean> children;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Short getParentId() {
        return parentId;
    }

    public void setParentId(Short parentId) {
        this.parentId = parentId;
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }

    public Short getSystemId() {
        return systemId;
    }

    public void setSystemId(Short systemId) {
        this.systemId = systemId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public List<PermissionResBean> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionResBean> children) {
        this.children = children;
    }
}