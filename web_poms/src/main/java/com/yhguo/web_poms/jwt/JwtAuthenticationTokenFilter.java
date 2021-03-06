/*
package com.yhguo.web_poms.jwt;

import com.alibaba.fastjson.JSON;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.web_poms.security.MyUserDetails;
import com.yhguo.web_poms.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private RedisUtil redisUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        // 从客户端请求中获取 http 请求头："jwtHeader" 的值，这个值就是 token 令牌
        String jwtHeader = request.getHeader(jwtTokenUtil.getHeader());
        try {
            // 如果 jwtHeader 是有值的，说明已经认证过了
            if (StringUtils.isNotEmpty(jwtHeader)) {
                // 从 token 中获取用户名
                String username = jwtTokenUtil.getUsernameFromToken(jwtHeader);
                // 验证 token
                jwtTokenUtil.validateToken(jwtHeader);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 从数据库获取用户的信息
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    // 验证 token 是否有效
                    if (jwtTokenUtil.validateToken(jwtHeader, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            Map<String, Object> map = jwtTokenUtil.parseJwtPayload(jwtHeader);
            Integer userId = (Integer) map.get("userId");


            //这里的方案是如果令牌过期了，先去判断redis中存储的令牌是否过期，如果过期就重新登录，如果redis中存储的没有过期就可以
            //继续生成token返回给前端存储方式key:userId,value:令牌
            String redisResult = redisUtil.get(userId.toString());


            String username = (String) map.get("sub");
            if (StringUtils.isNoneEmpty(redisResult)) {
                MyUserDetails myUserDetails = new MyUserDetails();
                myUserDetails.setUserId(userId);
                myUserDetails.setUsername(username);
                Map<String, Object> claims = new HashMap<>(2);
                claims.put("sub", myUserDetails.getUsername());
                claims.put("userId", myUserDetails.getUserId());
                claims.put("created", new Date());
                String token = jwtTokenUtil.generateToken(myUserDetails);


                //更新redis中的token
                //首先获取key的有效期，把新的token的有效期设为旧的token剩余的有效期
                redisUtil.setAndTime(userId.toString(), token, redisUtil.getExpireTime(userId.toString()));


                if (token != null && StringUtils.isNotEmpty(token)) {
                    jwtTokenUtil.validateToken(token);//验证令牌
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                        if (jwtTokenUtil.validateToken(token)) {
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
                response.setHeader("newToken", token);
                response.addHeader("Access-Control-Expose-Headers", "newToken");
                response.setContentType("application/json;charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                try {
                    chain.doFilter(request, response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ServletException e1) {
                    e1.printStackTrace();
                }

            } else {
                response.addHeader("Access-Control-Allow-origin", "http://localhost:8080");
                ResultObject resultObject = new ResultObject();
                resultObject.setStatus(EnumResultStatus.FAILURE);
                resultObject.setMessage("抱歉，您的登录信息已过期，请重新登录");
                response.setContentType("application/json;charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JSON.toJSONString(resultObject));
                System.out.println("redis过期");
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
