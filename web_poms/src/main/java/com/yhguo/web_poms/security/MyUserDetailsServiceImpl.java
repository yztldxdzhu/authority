package com.yhguo.web_poms.security;

import com.yhguo.common.bean.response.UserResBean;
import com.yhguo.common.encryption.AesCoder;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.mgmt_user.UserMgmt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("userDetailsService")//在SecurityConfig中自动匹配/注入
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMgmt userMgmt;

    @Autowired
    AesCoder coder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*if("".equals(username)){
            username = "yhguo";
        }
        UserResBean user = userMgmt.getUserInfo(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在！", username));
        }
//        List<SimpleGrantedAuthority> collect = user.getRoles().stream().map(Role::getRolename).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new MyUserDetails(user.getId(), user.getName(), user.getPassword(), null, null);*/

        String realUserName = username.replace("[]", "");
        // 根据用户名获取用户信息
        ResultObject resultObject = userMgmt.getUserInfo(realUserName);
        if (resultObject.getStatus() == EnumResultStatus.FAILURE) {
            throw new UsernameNotFoundException("用户:" + realUserName + "不存在！");
        }
        UserResBean userResBean = (UserResBean) resultObject.getData();
        Integer userId = userResBean.getId();
        String lastLoginTime = userResBean.getLastLoginTime();
        List<GrantedAuthority> authorities = new ArrayList<>();
        return buildUserForAuthentication(realUserName, true, authorities, userId, lastLoginTime);
    }

    private UserDetails buildUserForAuthentication(String userName, boolean confidentialNonExpired, List<GrantedAuthority> authorities, Integer userId, String latestLoginTime) {
        return new MyUserDetails(userId, userName, "", null, authorities, latestLoginTime);
    }
}
