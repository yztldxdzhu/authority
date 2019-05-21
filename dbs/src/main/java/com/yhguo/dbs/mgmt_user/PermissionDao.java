package com.yhguo.dbs.mgmt_user;

import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.common.bean.request.PermissionReqBean;
import com.yhguo.common.bean.response.PermissionResBean;
import com.yhguo.dbc.entity.PomsConfigPermission;
import com.yhguo.dbc.mapper.inter.PomsConfigPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class PermissionDao {

    @Autowired
    private PomsConfigPermissionMapper pomsConfigPermissionMapper;

    /*前台页面调的接口*/
    public List<PermissionResBean> getPermissionList(Integer systemId) {
        return pomsConfigPermissionMapper.getPermissionList(systemId);
    }

    public List<PermissionResBean> searchPermissionList(PermissionReqBean permissionReqBean) {
        return pomsConfigPermissionMapper.searchPermissionList(permissionReqBean);
    }

    public void addPermission(PermissionReqBean permissionReqBean) {
        pomsConfigPermissionMapper.addPermission(permissionReqBean);
    }

    public void editPermission(PermissionReqBean permissionReqBean) {
        pomsConfigPermissionMapper.editPermission(permissionReqBean);
    }

    public void deletePermission(Integer permissionId) {
        pomsConfigPermissionMapper.deletePermission(permissionId);
    }

    /*后台自己调的接口*/
    public boolean checkPermissionNameExist(String permissionName, Integer parentId, Integer permissionId) {
        int count = pomsConfigPermissionMapper.checkPermissionNameExist(permissionName, parentId, permissionId);
        return count > 0;
    }

}
