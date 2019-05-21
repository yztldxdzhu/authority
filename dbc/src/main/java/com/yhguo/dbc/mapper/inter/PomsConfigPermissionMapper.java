package com.yhguo.dbc.mapper.inter;

import com.yhguo.common.bean.request.PermissionReqBean;
import com.yhguo.common.bean.response.PermissionResBean;
import com.yhguo.dbc.entity.PomsConfigPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PomsConfigPermissionMapper {
    int deleteByPrimaryKey(Short id);

    int insert(PomsConfigPermission record);

    int insertSelective(PomsConfigPermission record);

    PomsConfigPermission selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(PomsConfigPermission record);

    int updateByPrimaryKey(PomsConfigPermission record);

    /*前台页面调的接口*/
    List<PomsConfigPermission> getPermissionList(@Param("systemId") Integer systemId);

    List<PomsConfigPermission> searchPermissionList(@Param("permissionReqBean") PermissionReqBean permissionReqBean);

    void addPermission(@Param("permissionReqBean") PermissionReqBean permissionReqBean);

    void editPermission(@Param("permissionReqBean") PermissionReqBean permissionReqBean);

    void deletePermission(@Param("permissionId") Integer permissionId);

    /*后台自己调的接口*/
    int checkPermissionNameExist(@Param("permissionName") String permissionName,
                                 @Param("parentId") Integer parentId,
                                 @Param("permissionId") Integer permissionId);

}