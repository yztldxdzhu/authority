package com.yhguo.web_poms.service;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.PermissionReqBean;
import com.yhguo.common.framework.ResultObject;

public interface PermissionService {

    ResultObject getPermissionList(Integer systemId);
    ResultObject searchPermissionList(PermissionReqBean permissionReqBean, PageInfoBean pageInfoBean);
    ResultObject addPermission(PermissionReqBean permissionReqBean);
    ResultObject editPermission(PermissionReqBean permissionReqBean);
    ResultObject deletePermission(Integer permissionId);
    
}
