package com.yhguo.web_poms.service.impl;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.common.tool.AesEncryptUtil;
import com.yhguo.mgmt_user.UserMgmt;
import com.yhguo.web_poms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMgmt userMgmt;

    public ResultObject login(String username, String passwordAES) throws AuthenticationException {
        ResultObject resultObject = new ResultObject();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = null;
        password = AesEncryptUtil.decrypt(passwordAES);
        if (username == null || passwordAES == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("用户名，密码不能为空！");
            return resultObject;
        }

        boolean userNameExistFlag = userMgmt.checkLoginUserNameExist(username);
        if(!userNameExistFlag){
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("用户名不存在，请重试！");
            return resultObject;
        }





      if(!encoder.matches(password,userMapper.selectPasswordByUsername(username))) {
            return new RetResult(RetCode.FAIL.getCode(), "密码输入不正确，请重试");
        }



        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtUser userDetails = (JwtUser)userDetailsService.loadUserByUsername(username);
        long refreshPeriodTime = 240L;
        String jwt = jwtTokenUtil.generateToken(userDetails);
        String userId = userMapper.selectUserByUsername(username).getId();
        //把签发的jwt token存储到redis中，时间根绝你免登录的时间来设置
        redisUtil.setAndTime(userId,jwt,refreshPeriodTime);
        return new RetResult(RetCode.SUCCESS.getCode(),"登录成功",jwt);
    }

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
