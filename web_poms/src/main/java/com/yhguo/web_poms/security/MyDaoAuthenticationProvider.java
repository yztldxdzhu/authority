package com.yhguo.web_poms.security;

import com.yhguo.common.encryption.AesCoder;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.mgmt_user.UserMgmt;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider {

    AesCoder aesCoder;

    UserMgmt userMgmt;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        String decodePasswd = password;

        try {
            decodePasswd = aesCoder.encrypt(password);
        } catch (Exception ex) {
            throw new BadCredentialsException(ex.getMessage());
        }

        // 验证账号密码是否存在
        boolean nameExist = userMgmt.checkLoginUserNameExist(username);
        if(!nameExist){
            throw new BadCredentialsException("用户名不存在！");
        }
//        Integer userId = (Integer) ret.getData();
        // 更新用户最后登录时间
//        userMgmt.updateUserLatestLoginTime(userId);

        // 获取用户信息,角色信息
        UserDetails user = this.getUserDetailsService().loadUserByUsername(username + "[]");
        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }

        System.out.println("into here..");
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

    public AesCoder getAesCoder() {
        return aesCoder;
    }

    public void setAesCoder(AesCoder aesCoder) {
        this.aesCoder = aesCoder;
    }

    public UserMgmt getUserMgmt() {
        return userMgmt;
    }

    public void setUserMgmt(UserMgmt userMgmt) {
        this.userMgmt = userMgmt;
    }


}
