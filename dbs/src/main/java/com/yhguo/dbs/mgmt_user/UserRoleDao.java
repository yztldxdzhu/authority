package com.yhguo.dbs.mgmt_user;

import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.dbc.mapper.inter.PomsConfigUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRoleDao {

    @Autowired
    private PomsConfigUserRoleMapper pomsConfigUserRoleMapper;

    /*后台自己调的接口*/
    public List<AttributeBean> getUserSystemRoleAttrList(Integer userId, Integer systemId) {
        return pomsConfigUserRoleMapper.getUserSystemRoleAttrList(userId, systemId);
    }

    public Integer[] getUserSystemRole(Integer userId, Integer systemId) {
        return pomsConfigUserRoleMapper.getUserSystemRole(userId, systemId);
    }

    public void addUserRole(Integer userId, Integer[] roleIds) {
        pomsConfigUserRoleMapper.addUserRole(userId, roleIds);
    }

    public void deleteUserRole(Integer userId, Integer[] roleIds) {
        pomsConfigUserRoleMapper.deleteUserRole(userId, roleIds);
    }

    public boolean checkRoleBindUser(Integer roleId) {
        int count = pomsConfigUserRoleMapper.checkRoleBindUser(roleId);
        return count > 0;
    }
}
