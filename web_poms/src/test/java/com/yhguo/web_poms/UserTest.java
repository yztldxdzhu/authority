package com.yhguo.web_poms;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.web_poms.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void searchUserList(){
        UserReqBean userReqBean = new UserReqBean();
        userReqBean.setName("guoyanhang");
        userReqBean.setDisableFlag(1);
        PageInfoBean pageInfoBean = new PageInfoBean();
        pageInfoBean.setCurrentPage(1);
        pageInfoBean.setPageSize(20);
        ResultObject resultObject = userService.searchUserList(userReqBean, pageInfoBean);
        System.out.println("---searchUserList---");
        System.out.println(resultObject.getData());
    }
    @Test
    public void addUser(){
        UserReqBean userReqBean = new UserReqBean();
        userReqBean.setName("guoyanhang");
        userReqBean.setPassword("12345678");
        userReqBean.setOperator("yhguo");
        ResultObject resultObject = userService.addUser(userReqBean);
        System.out.println("---addUser---");
        System.out.println(resultObject.getStatus());
//        System.out.println(resultObject.getMessage());
    }

    @Test
    public void editUser(){
        UserReqBean userReqBean = new UserReqBean();
        userReqBean.setId(1);
        userReqBean.setName("guoyanhang0");
        userReqBean.setPassword("1234");
        userReqBean.setOperator("yhguo");
        ResultObject resultObject = userService.editUser(userReqBean);
        System.out.println("---editUser---");
        System.out.println(resultObject.getStatus());
//        System.out.println(resultObject.getMessage());
    }

}
