package com.yhguo.dbs.mgmt_user;

import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.dbc.mapper.inter.PomsConfigRolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolePermissionDao {

    @Autowired
    private PomsConfigRolePermissionMapper pomsConfigRolePermissionMapper;

    public List<AttributeBean> getRolePermissionAttrList(Integer roleId){
        return pomsConfigRolePermissionMapper.getRolePermissionAttrList(roleId);
    }

    public Integer[] getRolePermission(Integer roleId){
        return pomsConfigRolePermissionMapper.getRolePermission(roleId);
    }

    public void addRolePermission(Integer roleId, Integer[] permissionIds){
        pomsConfigRolePermissionMapper.addRolePermission(roleId, permissionIds);
    }

    public void deleteRolePermission(Integer roleId, Integer[] permissionIds){
        pomsConfigRolePermissionMapper.deleteRolePermission(roleId, permissionIds);
    }

}
