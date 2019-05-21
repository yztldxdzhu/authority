package com.yhguo.web_poms;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.RoleReqBean;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.web_poms.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void searchRoleList(){
        RoleReqBean roleReqBean = new RoleReqBean();
        roleReqBean.setName("游客");
        roleReqBean.setSystemId(1);
        PageInfoBean pageInfoBean = new PageInfoBean();
        pageInfoBean.setCurrentPage(1);
        pageInfoBean.setPageSize(20);
        ResultObject resultObject = roleService.searchRoleList(roleReqBean, pageInfoBean);
        System.out.println("---searchRoleList---");
        System.out.println(resultObject.getData());
    }

    @Test
    public void addRole(){
        RoleReqBean roleReqBean = new RoleReqBean();
        roleReqBean.setName("管理员");
        roleReqBean.setDescription("--");
        roleReqBean.setSystemId(1);
        roleReqBean.setOperator("yhguo");
        ResultObject resultObject = roleService.addRole(roleReqBean);
        System.out.println("---addRole---");
        System.out.println(resultObject.getStatus());
    }

    @Test
    public void editRole(){
        RoleReqBean roleReqBean = new RoleReqBean();
        roleReqBean.setId(2);
        roleReqBean.setName("管理员0");
        roleReqBean.setDescription("---");
        roleReqBean.setSystemId(1);
        roleReqBean.setOperator("yhguo-");
        ResultObject resultObject = roleService.editRole(roleReqBean);
        System.out.println("---editRole---");
        System.out.println(resultObject.getStatus());
        System.out.println(resultObject.getMessage());
    }

    @Test
    public void deleteRole(){
        ResultObject resultObject = roleService.deleteRole(1);
        System.out.println(resultObject.getStatus());
        System.out.println(resultObject.getMessage());
    }
}
