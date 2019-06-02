package com.yhguo.web_poms.service.impl;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.common.tool.AesEncryptUtil;
import com.yhguo.mgmt_user.UserMgmt;
import com.yhguo.web_poms.security.JwtUserDetails;
import com.yhguo.web_poms.service.UserService;
import com.yhguo.web_poms.util.JwtTokenUtil;
import com.yhguo.web_poms.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private UserMgmt userMgmt;

    public ResultObject checkLoginUserNameExist(String username) {
        ResultObject resultObject = new ResultObject();
        boolean userNameExistFlag = userMgmt.checkLoginUserNameExist(username);
        if (!userNameExistFlag) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("用户名不存在！");
        } else {
            resultObject.setStatus(EnumResultStatus.SUCCESS);
        }
        return resultObject;
    }

    public ResultObject login(String username, String passwordAES) throws AuthenticationException {
        ResultObject resultObject = new ResultObject();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = null;
        password = AesEncryptUtil.decrypt(passwordAES); // 解密

        if (username == null || passwordAES == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("用户名，密码不能为空！");
            return resultObject;
        }

        boolean userNameExistFlag = userMgmt.checkLoginUserNameExist(username);
        if (!userNameExistFlag) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("用户名不存在！");
            return resultObject;
        }

        if (!encoder.matches(password, userMgmt.getUserPassword(username))) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("密码输入不正确！");
            return resultObject;
        }


        UsernamePasswordAuthenticationToken usernameAndPasswordToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(usernameAndPasswordToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        JwtUserDetails userDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(username);
        long refreshPeriodTime = 240L;
        String jwt = jwtTokenUtil.generateToken(userDetails);
        Integer userId = userMgmt.getUserId(username);
        //把签发的jwt token存储到redis中，时间根绝你免登录的时间来设置
        redisUtil.setAndTime(userId.toString(), jwt, refreshPeriodTime);
        resultObject.setStatus(EnumResultStatus.SUCCESS);
        resultObject.setMessage("登录成功！");
        resultObject.setData(jwt);
        return resultObject;
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
