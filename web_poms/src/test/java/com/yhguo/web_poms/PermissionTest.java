package com.yhguo.web_poms;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.PermissionReqBean;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.web_poms.service.PermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionTest {

    @Autowired
    private PermissionService permissionService;

    @Test
    public void searchPermissionList(){
        PermissionReqBean permissionReqBean = new PermissionReqBean();
        permissionReqBean.setName("用户管理");
        PageInfoBean pageInfoBean = new PageInfoBean();
        pageInfoBean.setCurrentPage(1);
        pageInfoBean.setPageSize(20);
        ResultObject resultObject = permissionService.searchPermissionList(permissionReqBean, pageInfoBean);
        System.out.println("---searchPermissionList---");
        System.out.println(resultObject.getData());
    }

    @Test
    public void addPermission(){
        PermissionReqBean permissionReqBean = new PermissionReqBean();
        permissionReqBean.setType("菜单");
        permissionReqBean.setName("用户管理");
        permissionReqBean.setDescription("用户管理页面");
        permissionReqBean.setState("/user");
        permissionReqBean.setIcon("icon-user");
        permissionReqBean.setSort(1);
        permissionReqBean.setSystemId(1);
        permissionReqBean.setOperator("yhguo");
        ResultObject resultObject = permissionService.addPermission(permissionReqBean);
        System.out.println(resultObject.getStatus());
        System.out.println(resultObject.getMessage());
    }

    @Test
    public void editPermission(){
        PermissionReqBean permissionReqBean = new PermissionReqBean();
        permissionReqBean.setId(1);
        permissionReqBean.setName("角色管理");
        permissionReqBean.setDescription("角色管理页面");
        permissionReqBean.setState("/role");
        permissionReqBean.setIcon("icon-role");
        permissionReqBean.setSort(2);
        permissionReqBean.setSystemId(1);
        permissionReqBean.setOperator("yhguo");
        ResultObject resultObject = permissionService.editPermission(permissionReqBean);
        System.out.println(resultObject.getStatus());
        System.out.println(resultObject.getMessage());
    }

    @Test
    public void deletePermission(){
        ResultObject resultObject = permissionService.deletePermission(1);
        System.out.println(resultObject.getStatus());
        System.out.println(resultObject.getMessage());
    }
}
