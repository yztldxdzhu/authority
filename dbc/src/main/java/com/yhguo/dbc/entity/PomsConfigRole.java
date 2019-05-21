package com.yhguo.dbc.entity;

import java.util.Date;

public class PomsConfigRole {
    private Short id;

    private String name;

    private String description;

    private Date operateTime;

    private Short systemId;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
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

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Short getSystemId() {
        return systemId;
    }

    public void setSystemId(Short systemId) {
        this.systemId = systemId;
    }
}