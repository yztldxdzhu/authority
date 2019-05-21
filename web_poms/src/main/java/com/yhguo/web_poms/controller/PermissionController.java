package com.yhguo.web_poms.controller;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.PermissionReqBean;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.web_poms.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poms/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/getPermissionList")
    public ResultObject getPermissionList(Integer systemId){
        return permissionService.getPermissionList(systemId);
    }

    @GetMapping("/searchPermissionList")
    public ResultObject searchPermissionList(PermissionReqBean permissionReqBean, PageInfoBean pageInfoBean) {
        return permissionService.searchPermissionList(permissionReqBean, pageInfoBean);
    }

    @PostMapping("/addPermission")
    public ResultObject addPermission(PermissionReqBean permissionReqBean) {
        permissionReqBean.setOperator("yhguo");
        return permissionService.addPermission(permissionReqBean);
    }

    @PostMapping("/editPermission")
    public ResultObject editPermission(PermissionReqBean permissionReqBean) {
        permissionReqBean.setOperator("yhguo");
        return permissionService.editPermission(permissionReqBean);
    }

    @PostMapping("/deletePermission")
    public ResultObject deletePermission(Integer permissionId) {
        return permissionService.deletePermission(permissionId);
    }
    
}
