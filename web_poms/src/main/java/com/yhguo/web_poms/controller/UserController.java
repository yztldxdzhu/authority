package com.yhguo.web_poms.controller;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.web_poms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/poms/user")
public class UserController {

    @Autowired
    private UserService userService;

    /* 检查用户名是否存在 */
    /*@GetMapping("/checkLoginUserNameExist")
    public ResultObject checkLoginUserNameExist(String username) {
        return userService.checkLoginUserNameExist(username);
    }*/

    /* 用户登陆 */
    /*@PostMapping("/login")
    public ResultObject login(String username, String password) {
        return userService.login(username, password);
    }*/

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

    @GetMapping("/test")
    public ResultObject test(String firstname, String lastname){
        return null;
    }

    @PostMapping("/test")
    public ResultObject test1(HttpServletRequest request,
                              String firstname,
                              String lastname,
                              String sex,
                              String secret,
                              String image_type,
                              @RequestParam(value = "photos", required = false) MultipartFile[] photos,
                              @RequestParam(value = "vehicle", required = false) String[] vehicle,
                              String description){

        System.out.println(request.getParameter("photos"));
        System.out.println(request.getParameter("vehicle"));
        return null;
    }

    @GetMapping("/getProductTypeList")
    public ResultObject getProductTypeList(){
        ResultObject resultObject = new ResultObject();
        ArrayList arrayList = new ArrayList();

        HashMap hashMap = new HashMap();
        hashMap.put("id", 1);
        hashMap.put("name", "firewall");
        arrayList.add(hashMap);

        HashMap hashMap2 = new HashMap();
        hashMap2.put("id", 2);
        hashMap2.put("name", "vfw");
        arrayList.add(hashMap2);

        resultObject.setStatus(EnumResultStatus.SUCCESS);
        resultObject.setMessage("success");
        resultObject.setData(arrayList);
        return resultObject;
    }

    @GetMapping("/getProductList")
    public ResultObject getProductList(Integer typeId){
        ResultObject resultObject = new ResultObject();
        ArrayList arrayList = new ArrayList();

        if(typeId == 1){
            HashMap hashMap = new HashMap();
            hashMap.put("id", 11);
            hashMap.put("name", "e");
            arrayList.add(hashMap);

            HashMap hashMap2 = new HashMap();
            hashMap2.put("id", 12);
            hashMap2.put("name", "x");
            arrayList.add(hashMap2);

            HashMap hashMap3 = new HashMap();
            hashMap3.put("id", 13);
            hashMap3.put("name", "t");
            arrayList.add(hashMap3);
        } else if(typeId == 2){
            HashMap hashMap = new HashMap();
            hashMap.put("id", 21);
            hashMap.put("name", "vv");
            arrayList.add(hashMap);

            HashMap hashMap2 = new HashMap();
            hashMap2.put("id", 22);
            hashMap2.put("name", "ff");
            arrayList.add(hashMap2);

            HashMap hashMap3 = new HashMap();
            hashMap3.put("id", 23);
            hashMap3.put("name", "ww");
            arrayList.add(hashMap3);


        }

        resultObject.setStatus(EnumResultStatus.SUCCESS);
        resultObject.setMessage("success");
        resultObject.setData(arrayList);
        return resultObject;
    }
    
}
