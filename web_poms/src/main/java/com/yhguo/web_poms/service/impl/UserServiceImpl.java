package com.yhguo.web_poms.service.impl;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.common.tool.AesEncryptUtil;
import com.yhguo.mgmt_user.UserMgmt;
//import com.yhguo.web_poms.security.MyUserDetails;
import com.yhguo.web_poms.service.UserService;
//import com.yhguo.web_poms.jwt.JwtTokenUtil;
import com.yhguo.web_poms.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {


    /**
     *
     （三）AuthenticationManager (接口）是认证相关的核心接口，也是发起认证的出发点
     使用用户名+密码登录，同时允许用户使用邮箱+密码，手机号码+密码登录，甚至，可能允许用户使用指纹登录
     身份认证器AuthenticationManager
     * */
    /*@Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private RedisUtil redisUtil;*/

    @Autowired
    private UserMgmt userMgmt;

    /*public ResultObject checkLoginUserNameExist(String username) {
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

        *//**
         *
         （二）Authentication 最高级别的身份/认证的抽象
         我们可以得到用户拥有的权限信息列表，密码，用户细节信息，用户身份信息，认证信息。
         getAuthorities() 权限信息列表，默认是GrantedAuthority接口的一些实现类，通常是代表权限信息的一系列字符串。
         getCredentials() 密码信息，用户输入的密码字符串，在认证过后通常会被移除，用于保障安全。
         getDetails() 细节信息，web应用中的实现接口通常为 WebAuthenticationDetails，它记录了访问者的ip地址和sessionId的值。
         getPrincipal() 最重要的身份信息，大部分情况下返回的是UserDetails接口的实现类，也是框架中的常用接口之一。
         * *//*
        final Authentication authentication = authenticationManager.authenticate(usernameAndPasswordToken);

        *//**
         *
         （一）SecurityContextHolder 用于存储安全上下文信息：当前操作的用户是谁，该用户是否已经被认证，他拥有哪些角色权限…
         获取当前登录用户的姓名：
         Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
         } else {
            String username = principal.toString();
         }
         getAuthentication()返回了认证信息
         再次getPrincipal()返回了身份信息
         UserDetails便是Spring对身份信息封装的一个接口
         *
         *
         * *//*
        SecurityContextHolder.getContext().setAuthentication(authentication);


        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(username);
        long refreshPeriodTime = 240L;
        String token = jwtTokenUtil.generateToken(userDetails);
        Integer userId = userMgmt.getUserId(username);


        // 把签发的jwt token存储到redis中，时间根据你免登录的时间来设置
        redisUtil.setAndTime(userId.toString(), token, refreshPeriodTime);


        resultObject.setStatus(EnumResultStatus.SUCCESS);
        resultObject.setMessage("登录成功！");
        // 把 token 返回给前台
        resultObject.setData(token);
        return resultObject;
    }*/


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
