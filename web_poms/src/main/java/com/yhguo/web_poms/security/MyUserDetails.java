package com.yhguo.web_poms.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserDetails implements UserDetails {

    private Integer userId;

    private String username;

    private String password;

    private Integer state;

    private String latestLoginTime;

    private Collection<? extends GrantedAuthority> authorities;


    public MyUserDetails() {
    }

    public MyUserDetails(Integer userId, String username, String password, Integer state, Collection<? extends GrantedAuthority> authorities, String latestLoginTime) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.state = state;
        this.latestLoginTime = latestLoginTime;
        this.authorities = authorities;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getLatestLoginTime() {
        return latestLoginTime;
    }

    public void setLatestLoginTime(String latestLoginTime) {
        this.latestLoginTime = latestLoginTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    // 账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未被锁
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 信用是否过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 是否启用
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
