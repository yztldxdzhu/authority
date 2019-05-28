package com.yhguo.web_poms.controller;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.web_poms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/poms/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResultObject login(String username, String password) {
        return userService.login(username, password);
    }

    @GetMapping("/searchUserList")
    public ResultObject searchUserList(UserReqBean userReqBean, PageInfoBean pageInfoBean) {
        return userService.searchUserList(userReqBean, pageInfoBean);
    }

    @PostMapping("/addUser")
    public ResultObject addUser(@RequestBody UserReqBean userReqBean) {
        userReqBean.setOperator("yhguo");
        return userService.addUser(userReqBean);
    }

    @PostMapping("/editUser")
    public ResultObject editUser(@RequestBody UserReqBean userReqBean) {
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
