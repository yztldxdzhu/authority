package com.yhguo.web_poms.service;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.RoleReqBean;
import com.yhguo.common.framework.ResultObject;

public interface RoleService {

    ResultObject getRoleAttrList(Integer systemId);
    ResultObject searchRoleList(RoleReqBean roleReqBean, PageInfoBean pageInfoBean);
    ResultObject addRole(RoleReqBean roleReqBean);
    ResultObject editRole(RoleReqBean roleReqBean);
    ResultObject deleteRole(Integer roleId);

}
