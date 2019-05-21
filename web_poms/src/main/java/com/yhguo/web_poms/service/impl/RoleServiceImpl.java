package com.yhguo.web_poms.service.impl;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.RoleReqBean;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.mgmt_user.RoleMgmt;
import com.yhguo.web_poms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMgmt roleMgmt;

    public ResultObject getRoleAttrList(Integer systemId) {
        ResultObject resultObject = new ResultObject();
        if (systemId == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("获取角色属性列表出错：系统ID不能为空");
            return resultObject;
        }
        return roleMgmt.getRoleAttrList(systemId);
    }

    public ResultObject searchRoleList(RoleReqBean roleReqBean, PageInfoBean pageInfoBean) {
        return roleMgmt.searchRoleList(roleReqBean, pageInfoBean);
    }

    public ResultObject addRole(RoleReqBean roleReqBean) {
        ResultObject resultObject = new ResultObject();
        if (roleReqBean.getSystemId() == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加角色出错：角色所属系统不能为空！");
            return resultObject;
        }
        if (roleReqBean.getName() == null || "".equals(roleReqBean.getName())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加角色出错：角色名称不能为空！");
            return resultObject;
        }
        return roleMgmt.addRole(roleReqBean);
    }

    public ResultObject editRole(RoleReqBean roleReqBean) {
        ResultObject resultObject = new ResultObject();
        if (roleReqBean.getId() == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑角色出错：角色ID不能为空！");
            return resultObject;
        }
        if (roleReqBean.getSystemId() == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑角色出错：角色所属系统不能为空！");
            return resultObject;
        }
        if (roleReqBean.getName() == null || "".equals(roleReqBean.getName())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑角色出错：角色名称不能为空！");
            return resultObject;
        }
        return roleMgmt.editRole(roleReqBean);
    }

    public ResultObject deleteRole(Integer roleId) {
        ResultObject resultObject = new ResultObject();
        if (roleId == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("删除角色出错：角色ID不能为空！");
            return resultObject;
        }
        return roleMgmt.deleteRole(roleId);
    }
}
