package com.yhguo.common.bean.common;

import java.util.List;

public class AttributeBean {

    private Integer id;
    private String name;
    private String description;
    private List<AttributeBean> children;

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

    public List<AttributeBean> getChildren() {
        return children;
    }

    public void setChildren(List<AttributeBean> children) {
        this.children = children;
    }
}
