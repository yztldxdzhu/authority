/*
package com.yhguo.web_poms.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//允许进入页面方法前检验
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().cacheControl().disable();

        http.csrf().disable()
                .authorizeRequests()
                // 放开token验证的url,不进行拦截
//                .antMatchers("/poms/token/**").permitAll()
                // 静态文件不进行拦截
//                .antMatchers("/poms/static/**").permitAll()
                // 获取用户 session，不进行拦截
//                .antMatchers("/poms/getSessionUserInfo").permitAll()
                // 拦截 /poms 开头的所有请求
                .antMatchers("/poms/**").authenticated().anyRequest().permitAll()
                // 登录
                .and().formLogin().loginProcessingUrl("/poms/user/login")
//                .loginPage("/poms/")
//                .successHandler(new MySucAuthentifcationHandler(globalConfig.getNginxUrl()))
//                .failureHandler(new MyFailAuthentifcationHandler())
                .permitAll()
                // 登出
                .and().logout().logoutUrl("/poms/user/logout").invalidateHttpSession(true).clearAuthentication(true);
//                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                // 记住登录状态
//                .and().rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(60000)
                // 设置access denied
//                .and().exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());
    }
}
*/
