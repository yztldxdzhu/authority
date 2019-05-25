package com.yhguo.web_poms.controller;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.RoleReqBean;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.web_poms.service.RoleService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/poms/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/getRoleAttrList")
    public ResultObject getRoleAttrList(Integer systemId) {
        return roleService.getRoleAttrList(systemId);
    }

    @GetMapping("/searchRoleList")
    public ResultObject searchRoleList(RoleReqBean roleReqBean, PageInfoBean pageInfoBean) {
        return roleService.searchRoleList(roleReqBean, pageInfoBean);
    }

    @PostMapping("/addRole")
    public ResultObject addRole(@RequestBody RoleReqBean roleReqBean) {
        roleReqBean.setOperator("yhguo");
        return roleService.addRole(roleReqBean);
    }

    @PostMapping("/editRole")
    public ResultObject editRole(@RequestBody RoleReqBean roleReqBean) {
        roleReqBean.setOperator("yhguo");
        return roleService.editRole(roleReqBean);
    }

    @PostMapping("/deleteRole")
    public ResultObject deleteRole(Integer roleId) {
        return roleService.deleteRole(roleId);
    }



    @PostMapping("/test")
    public ResultObject test(@RequestBody RoleReqBean roleReqBean){
        return null;
    }

}
