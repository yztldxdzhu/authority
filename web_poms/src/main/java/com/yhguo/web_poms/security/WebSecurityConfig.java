package com.yhguo.web_poms.security;

import com.yhguo.web_poms.jwt.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * @EnableWebSecurity 注解使得SpringMVC集成了Spring Security的web安全支持。
 * 另外，WebSecurityConfig配置类同时集成了WebSecurityConfigurerAdapter，重写了其中的特定方法，用于自定义Spring Security配置。
 * 整个Spring Security的工作量，其实都是集中在该配置类，
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    // 自定义认证token拦截器
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    //自定义无权限访问拦截器
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("http://localhost:9000");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    // 注意在Spring Security5.x中我们要显式注入AuthenticationManager不然会报错
    /*@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/


    /**
     * configure(HttpSecurity) 定义了哪些URL路径应该被拦截
     * 页面提交用户名和密码的表单,其中name=”username”，name=”password”是默认的表单值，并发送到“/ login”。
     * 在默认配置中，Spring Security提供了一个拦截该请求并验证用户的过滤器。
     * 如果验证失败，该页面将重定向到“/login?error”，并显示相应的错误消息。
     * 当用户选择注销，请求会被发送到“/login?logout”。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 禁用缓存，这样前端就无法使用 memory cache 和 disk cache
        http.headers().cacheControl().disable();

        // 关闭 csrf 攻击
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                // authorizeRequests()配置路径拦截，表明路径访问所对应的权限，角色，认证信息。
                .authorizeRequests()
                    .antMatchers("/poms/system/**").permitAll()
                    .antMatchers("/poms/role/**").permitAll()
                    .antMatchers("/poms/permission/**").permitAll()
                    .antMatchers("/poms/user/**").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                    .anyRequest().authenticated()
                    .and()
                // formLogin()对应表单认证相关的配置
                .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureForwardUrl("/login?error")
                    .loginPage("/login")
                    .loginProcessingUrl("/poms/user/login")
                    .permitAll()
                    .and()
                // logout()对应了注销相关的配置
                .logout()
                    .logoutUrl("/poms/user/logout")
                    .logoutSuccessUrl("/index")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll()
                    .and()
                // httpBasic()可以配置basic登录
                .httpBasic()
                    .disable();


//        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .antMatchers("/auth/**").permitAll()
//                .antMatchers("/druid/**").anonymous()
//                .antMatchers("/poms/user/test").permitAll()
//                .anyRequest().authenticated()
//                .and().headers().cacheControl();



        /**
         * 一个登陆请求过来，未认证
         *
         * UsernamePasswordAuthenticationFilter
         *
         * AuthenticationManager
         *
         * AuthenticationProvider
         *
         * UserDetailsService
         *
         * UserDetails
         *
         * Authentication 已认证
         *
         * SecurityContext
         *
         * SecurityContextHolder
         *
         * SecurityContextPersistenceFilter
         *
         *
         *
         * Spring Security 过滤器链:
         *
         * SecurityContextPersistenceFilter
         *
         * UsernamePasswordAuthenticationFilter
         *
         * BasicAuthenticationFilter
         *
         * ...
         *
         * ExceptionTranslationFilter
         *
         * FilterSecurityInterceptor
         *
         *
         * */
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);


        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        // 放行所有 preflight request(预检请求)
        registry.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
    }


    /**
     * configureGlobal(AuthenticationManagerBuilder) 在内存中配置一个用户，admin/admin分别是用户名和密码，这个用户拥有USER角色。
     */
    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("USER");
    }*/


}