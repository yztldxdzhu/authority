package com.yhguo.mgmt_user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.bean.response.UserResBean;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.dbs.mgmt_user.SystemDao;
import com.yhguo.dbs.mgmt_user.UserDao;
import com.yhguo.dbs.mgmt_user.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yhguo.common.tool.IntegerArray.substract;

@Component
public class UserMgmt {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private SystemDao systemDao;

    public ResultObject searchUserList(UserReqBean userReqBean, PageInfoBean pageInfoBean) {
        ResultObject resultObject = new ResultObject();
        try {
            PageHelper.startPage(pageInfoBean.getCurrentPage(), pageInfoBean.getPageSize(), true);
            List<UserResBean> list = userDao.searchUserList(userReqBean);

            List<AttributeBean> systemAttrList = systemDao.getSystemAttrList(0);
            for (UserResBean user : list) {
                List<Map> roles = new ArrayList<>();
                for (AttributeBean sys : systemAttrList) {
                    Map<String, Object> map = new HashMap<>();
                    List<AttributeBean> userSystemRoleAttrList = userRoleDao.getUserSystemRoleAttrList(user.getId(), sys.getId());
                    if(userSystemRoleAttrList.size() > 0){
                        map.put("systemId", sys.getId());
                        map.put("systemName", sys.getName());
                        map.put("roleInfos", userSystemRoleAttrList);
                        roles.add(map);
                    }
                }
                user.setRoles(roles);
            }

            PageInfo pageInfo = new PageInfo(list);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("获取用户列表失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    @Transactional
    public ResultObject addUser(UserReqBean userReqBean) {
        ResultObject resultObject = new ResultObject();
        try {
            String userName = userReqBean.getName();
            boolean userNameExistFlag = userDao.checkUserNameExist(userName, null);
            if (userNameExistFlag) {
                resultObject.setStatus(EnumResultStatus.FAILURE);
                resultObject.setMessage("添加用户失败：用户名称重复");
                return resultObject;
            }
            // 1. 添加用户表
            int userId = userDao.addUser(userReqBean);
            // 2. 添加用户角色管理关系表
            Integer[] roleIds = userReqBean.getRoleIds();
            if (roleIds.length > 0) {
                userRoleDao.addUserRole(userId, roleIds);
            }
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("添加用户成功！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加用户失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    @Transactional
    public ResultObject editUser(UserReqBean userReqBean) {
        ResultObject resultObject = new ResultObject();
        try {
            String userName = userReqBean.getName();
            Integer userId = userReqBean.getId();
            boolean userNameExistFlag = userDao.checkUserNameExist(userName, userId);
            if (userNameExistFlag) {
                resultObject.setStatus(EnumResultStatus.FAILURE);
                resultObject.setMessage("编辑用户失败：用户名称重复");
                return resultObject;
            }
            // 1. 更新用户表
            userDao.editUser(userReqBean);
            // 2. 更新用户角色管理关系表
            Integer[] oldRoleIds = userRoleDao.getUserSystemRole(userId, userReqBean.getSystemId());
            Integer[] roleIds = userReqBean.getRoleIds();

            Integer[] deleteRoleIds = substract(oldRoleIds, roleIds);
            Integer[] addRoleIds = substract(roleIds, oldRoleIds);

            if (deleteRoleIds.length > 0) {
                userRoleDao.deleteUserRole(userId, deleteRoleIds);
            }
            if (addRoleIds.length > 0) {
                userRoleDao.addUserRole(userId, addRoleIds);
            }

            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("编辑用户成功！");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑用户失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject deleteUser(Integer userId) {
        ResultObject resultObject = new ResultObject();
        try {
            userDao.deleteUser(userId);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("删除用户成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("删除用户失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject disableUser(Integer userId) {
        ResultObject resultObject = new ResultObject();
        try {
            userDao.disableUser(userId);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("禁用用户成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("禁用用户失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject enableUser(Integer userId) {
        ResultObject resultObject = new ResultObject();
        try {
            userDao.enableUser(userId);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("启用用户成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("启用用户失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }
}
