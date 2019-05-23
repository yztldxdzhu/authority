package com.yhguo.dbs.mgmt_user;

import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.common.bean.request.RoleReqBean;
import com.yhguo.common.bean.response.RoleResBean;
import com.yhguo.dbc.entity.PomsConfigRole;
import com.yhguo.dbc.mapper.inter.PomsConfigRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDao {

    @Autowired
    private PomsConfigRoleMapper pomsConfigRoleMapper;

    /*前台页面调的接口*/
    public List<AttributeBean> getRoleAttrList(Integer systemId) {
        return pomsConfigRoleMapper.getRoleAttrList(systemId);
    }

    public List<RoleResBean> searchRoleList(RoleReqBean roleReqBean) {
        return pomsConfigRoleMapper.searchRoleList(roleReqBean);
    }

    public int addRole(RoleReqBean roleReqBean) {
        // 这里必须要用实体类
        PomsConfigRole role = new PomsConfigRole();
        role.setName(roleReqBean.getName());
        role.setDescription(roleReqBean.getDescription());
        role.setSystemId(roleReqBean.getSystemId().shortValue());
        pomsConfigRoleMapper.insertSelective(role);
        return role.getId();
        // pomsConfigRoleMapper.addRole(roleReqBean);
    }

    public void editRole(RoleReqBean roleReqBean) {
        pomsConfigRoleMapper.editRole(roleReqBean);
    }

    public void deleteRole(Integer roleId) {
        pomsConfigRoleMapper.deleteRole(roleId);
    }

    /*后台自己调的接口*/
    public boolean checkRoleNameExist(String roleName, Integer systemId, Integer roleId) {
        int count = pomsConfigRoleMapper.checkRoleNameExist(roleName, systemId, roleId);
        return count > 0;
    }

}
