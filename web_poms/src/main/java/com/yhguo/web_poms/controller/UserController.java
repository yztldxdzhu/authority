package com.yhguo.web_poms.controller;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.web_poms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poms/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/searchUserList")
    public ResultObject searchUserList(UserReqBean userReqBean, PageInfoBean pageInfoBean) {
        return userService.searchUserList(userReqBean, pageInfoBean);
    }

    @PostMapping("/addUser")
    public ResultObject addUser(UserReqBean userReqBean) {
        userReqBean.setOperator("yhguo");
        return userService.addUser(userReqBean);
    }

    @PostMapping("/editUser")
    public ResultObject editUser(UserReqBean userReqBean) {
        userReqBean.setOperator("yhguo");
        return userService.editUser(userReqBean);
    }

    @PostMapping("/deleteUser")
    public ResultObject deleteUser(Integer userId) {
        return userService.deleteUser(userId);
    }

    @PostMapping("/disableUser")
    public ResultObject disableUser(Integer userId) {
        return userService.disableUser(userId);
    }

    @PostMapping("/enableUser")
    public ResultObject enableUser(Integer userId) {
        return userService.enableUser(userId);
    }
    
}
