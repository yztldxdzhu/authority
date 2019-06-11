package com.yhguo.web_poms.security;

import com.yhguo.common.bean.response.UserResBean;
import com.yhguo.mgmt_user.UserMgmt;
import com.yhguo.web_poms.security.JwtUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMgmt userMgmt;

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserResBean user = userMgmt.getUserInfo(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在！", username));
        }
//        List<SimpleGrantedAuthority> collect = user.getRoles().stream().map(Role::getRolename).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new JwtUserDetails(user.getId(), user.getName(), user.getPassword(), null, null);
    }
}
