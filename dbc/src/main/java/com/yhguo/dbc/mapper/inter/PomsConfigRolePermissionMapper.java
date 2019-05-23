package com.yhguo.dbc.mapper.inter;

import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.dbc.entity.PomsConfigRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PomsConfigRolePermissionMapper {
    int deleteByPrimaryKey(Short id);

    int insert(PomsConfigRolePermission record);

    int insertSelective(PomsConfigRolePermission record);

    PomsConfigRolePermission selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(PomsConfigRolePermission record);

    int updateByPrimaryKey(PomsConfigRolePermission record);

    /*后台自己调的接口*/
    List<AttributeBean> getRolePermissionAttrList(@Param("roleId") Integer roleId);

    Integer[] getRolePermission(@Param("roleId") Integer roleId);

    void addRolePermission(@Param("roleId") Integer roleId, @Param("permissionIds") Integer[] permissionIds);

    void deleteRolePermission(@Param("roleId") Integer roleId, @Param("permissionIds") Integer[] permissionIds);
}