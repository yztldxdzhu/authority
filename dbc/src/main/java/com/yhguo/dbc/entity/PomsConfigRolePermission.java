package com.yhguo.dbc.entity;

public class PomsConfigRolePermission {
    private Short id;

    private Short roleId;

    private Short permissionId;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Short getRoleId() {
        return roleId;
    }

    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }

    public Short getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Short permissionId) {
        this.permissionId = permissionId;
    }
}