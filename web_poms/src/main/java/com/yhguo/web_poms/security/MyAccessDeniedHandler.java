/*
package com.yhguo.web_poms.security;

import com.alibaba.fastjson.JSON;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResultObject resultObject = new ResultObject();
        resultObject.setStatus(EnumResultStatus.FAILURE);
        resultObject.setMessage("MyAccessDeniedHandler => 抱歉，您没有访问该接口的权限！");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(resultObject));
    }

}
*/
