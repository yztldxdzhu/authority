package com.yhguo.web_poms.service.impl;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.mgmt_user.UserMgmt;
import com.yhguo.web_poms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMgmt userMgmt;

    public ResultObject searchUserList(UserReqBean userReqBean, PageInfoBean pageInfoBean) {
        return userMgmt.searchUserList(userReqBean, pageInfoBean);
    }

    public ResultObject addUser(UserReqBean userReqBean) {
        ResultObject resultObject = new ResultObject();
        if (userReqBean.getName() == null || "".equals(userReqBean.getName())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加用户出错：用户名称不能为空！");
            return resultObject;
        }
        if (userReqBean.getPassword() == null || "".equals(userReqBean.getPassword())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加用户出错：用户密码不能为空！");
            return resultObject;
        }
        if (userReqBean.getPassword().length() != 8) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加用户出错：用户密码为 8 位！");
            return resultObject;
        }
        if (userReqBean.getConfirmPassword() == null || "".equals(userReqBean.getConfirmPassword())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加用户出错：用户确认密码不能为空！");
            return resultObject;
        }
        if (userReqBean.getConfirmPassword().length() != 8) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加用户出错：用户确认密码为 8 位！");
            return resultObject;
        }
        if (!userReqBean.getPassword().equals(userReqBean.getConfirmPassword())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加用户出错：用户密码和确认密码不一致！");
            return resultObject;
        }
        return userMgmt.addUser(userReqBean);
    }

    public ResultObject editUser(UserReqBean userReqBean) {
        ResultObject resultObject = new ResultObject();
        if (userReqBean.getId() == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑用户出错：ID不能为空！");
            return resultObject;
        }
        if (userReqBean.getName() == null || "".equals(userReqBean.getName())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑用户出错：用户名称不能为空！");
            return resultObject;
        }
        if (userReqBean.getPassword() == null || "".equals(userReqBean.getPassword())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑用户出错：用户密码不能为空！");
            return resultObject;
        }
        if (userReqBean.getPassword().length() != 8) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑用户出错：用户密码为 8 位！");
            return resultObject;
        }
        if (userReqBean.getConfirmPassword() == null || "".equals(userReqBean.getConfirmPassword())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑用户出错：用户确认密码不能为空！");
            return resultObject;
        }
        if (userReqBean.getConfirmPassword().length() != 8) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑用户出错：用户确认密码为 8 位！");
            return resultObject;
        }
        if (!userReqBean.getPassword().equals(userReqBean.getConfirmPassword())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑用户出错：用户密码和确认密码不一致！");
            return resultObject;
        }
        return userMgmt.editUser(userReqBean);
    }

    public ResultObject deleteUser(Integer userId) {
        ResultObject resultObject = new ResultObject();
        if (userId == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("删除用户出错：ID不能为空！");
            return resultObject;
        }
        return userMgmt.deleteUser(userId);
    }

    public ResultObject disableUser(Integer userId) {
        ResultObject resultObject = new ResultObject();
        if (userId == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("禁用用户出错：ID不能为空！");
            return resultObject;
        }
        return userMgmt.disableUser(userId);
    }

    public ResultObject enableUser(Integer userId) {
        ResultObject resultObject = new ResultObject();
        if (userId == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("启用用户出错：ID不能为空！");
            return resultObject;
        }
        return userMgmt.enableUser(userId);
    }

}
