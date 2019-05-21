package com.yhguo.mgmt_user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.RoleReqBean;
import com.yhguo.common.bean.response.RoleResBean;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.dbs.mgmt_user.RoleDao;
import com.yhguo.dbs.mgmt_user.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleMgmt {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    public ResultObject getRoleAttrList(Integer systemId) {
        ResultObject resultObject = new ResultObject();
        try {
            List<AttributeBean> list = roleDao.getRoleAttrList(systemId);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("获取属性列表失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject searchRoleList(RoleReqBean roleReqBean, PageInfoBean pageInfoBean) {
        ResultObject resultObject = new ResultObject();
        try {
            PageHelper.startPage(pageInfoBean.getCurrentPage(), pageInfoBean.getPageSize(), true);
            List<RoleResBean> list = roleDao.searchRoleList(roleReqBean);
            PageInfo pageInfo = new PageInfo(list);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("获取角色列表失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject addRole(RoleReqBean roleReqBean) {
        ResultObject resultObject = new ResultObject();
        try {
            String roleName = roleReqBean.getName();
            Integer systemId = roleReqBean.getSystemId();
            boolean roleNameExistFlag = roleDao.checkRoleNameExist(roleName, systemId, null);
            if (roleNameExistFlag) {
                resultObject.setStatus(EnumResultStatus.FAILURE);
                resultObject.setMessage("添加角色失败：角色名称重复");
                return resultObject;
            }
            roleDao.addRole(roleReqBean);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("添加角色成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加角色失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject editRole(RoleReqBean roleReqBean) {
        ResultObject resultObject = new ResultObject();
        try {
            String roleName = roleReqBean.getName();
            Integer systemId = roleReqBean.getSystemId();
            Integer roleId = roleReqBean.getId();
            boolean roleNameExistFlag = roleDao.checkRoleNameExist(roleName, systemId, roleId);
            if (roleNameExistFlag) {
                resultObject.setStatus(EnumResultStatus.FAILURE);
                resultObject.setMessage("编辑角色失败：角色名称重复");
                return resultObject;
            }
            roleDao.editRole(roleReqBean);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("编辑角色成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑角色失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject deleteRole(Integer roleId) {
        ResultObject resultObject = new ResultObject();
        try {
            // 1. 检查有没有用户绑定该角色
            boolean roleBindUserFlag = userRoleDao.checkRoleBindUser(roleId);
            if (roleBindUserFlag) {
                resultObject.setStatus(EnumResultStatus.FAILURE);
                resultObject.setMessage("删除角色失败：该角色被用户绑定");
                return resultObject;
            }
            // 2. 执行删除
            roleDao.deleteRole(roleId);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("删除角色成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("删除角色失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

}
