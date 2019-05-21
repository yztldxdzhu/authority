package com.yhguo.mgmt_user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.PermissionReqBean;
import com.yhguo.common.bean.response.PermissionResBean;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.dbs.mgmt_user.PermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionMgmt {

    @Autowired
    private PermissionDao permissionDao;

    // 不分页
    public ResultObject getPermissionList(Integer systemId) {
        ResultObject resultObject = new ResultObject();
        try {
            List<PermissionResBean> list = permissionDao.getPermissionList(systemId);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("获取权限属性列表失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject searchPermissionList(PermissionReqBean permissionReqBean, PageInfoBean pageInfoBean) {
        ResultObject resultObject = new ResultObject();
        try {
            PageHelper.startPage(pageInfoBean.getCurrentPage(), pageInfoBean.getPageSize(), true);
            List<PermissionResBean> list = permissionDao.searchPermissionList(permissionReqBean);
            PageInfo pageInfo = new PageInfo(list);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("获取权限列表失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject addPermission(PermissionReqBean permissionReqBean) {
        ResultObject resultObject = new ResultObject();
        try {
            String permissionName = permissionReqBean.getName();
            Integer parentId = permissionReqBean.getParentId();
            boolean permissionNameExistFlag = permissionDao.checkPermissionNameExist(permissionName, parentId, null);
            if (permissionNameExistFlag) {
                resultObject.setStatus(EnumResultStatus.FAILURE);
                resultObject.setMessage("添加权限失败：拥有同一个父的权限名称重复");
                return resultObject;
            }
            permissionDao.addPermission(permissionReqBean);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("添加权限成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加权限失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject editPermission(PermissionReqBean permissionReqBean) {
        ResultObject resultObject = new ResultObject();
        try {
            String permissionName = permissionReqBean.getName();
            Integer parentId = permissionReqBean.getParentId();
            Integer permissionId = permissionReqBean.getId();
            boolean permissionNameExistFlag = permissionDao.checkPermissionNameExist(permissionName, parentId, permissionId);
            if (permissionNameExistFlag) {
                resultObject.setStatus(EnumResultStatus.FAILURE);
                resultObject.setMessage("编辑权限失败：拥有同一个父的权限名称重复");
                return resultObject;
            }
            permissionDao.editPermission(permissionReqBean);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("编辑权限成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑权限失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject deletePermission(Integer permissionId) {
        ResultObject resultObject = new ResultObject();
        try {
            // TODO 检查该权限是不是某个权限的父，如果是父，要提示把下面的子权限清除再删除
            permissionDao.deletePermission(permissionId);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("删除权限成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("删除权限失败，失败原因：" + e.getMessage());
        }
        return resultObject;
    }

}
