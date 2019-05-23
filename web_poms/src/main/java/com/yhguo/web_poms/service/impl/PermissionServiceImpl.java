package com.yhguo.web_poms.service.impl;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.PermissionReqBean;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.mgmt_user.PermissionMgmt;
import com.yhguo.web_poms.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMgmt permissionMgmt;

    public ResultObject getPermissionAttrList(Integer systemId){
        return permissionMgmt.getPermissionAttrList(systemId);
    }

    public ResultObject searchPermissionList(PermissionReqBean permissionReqBean, PageInfoBean pageInfoBean) {
        return permissionMgmt.searchPermissionList(permissionReqBean, pageInfoBean);
    }

    public ResultObject addPermission(PermissionReqBean permissionReqBean) {
        return permissionMgmt.addPermission(permissionReqBean);
    }

    public ResultObject editPermission(PermissionReqBean permissionReqBean) {
        return permissionMgmt.editPermission(permissionReqBean);
    }

    public ResultObject deletePermission(Integer permissionId) {
        return permissionMgmt.deletePermission(permissionId);
    }
    
}
