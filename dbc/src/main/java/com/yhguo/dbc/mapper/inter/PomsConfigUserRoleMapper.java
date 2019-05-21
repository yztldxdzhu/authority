package com.yhguo.dbc.mapper.inter;

import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.dbc.entity.PomsConfigUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PomsConfigUserRoleMapper {
    int deleteByPrimaryKey(Short id);

    int insert(PomsConfigUserRole record);

    int insertSelective(PomsConfigUserRole record);

    PomsConfigUserRole selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(PomsConfigUserRole record);

    int updateByPrimaryKey(PomsConfigUserRole record);

    /*后台自己调的接口*/
    Integer[] getUserSystemRole(@Param("userId") Integer userId, @Param("systemId") Integer systemId);

    List<AttributeBean> getUserSystemRoleAttrList(@Param("userId") Integer userId, @Param("systemId") Integer systemId);

    void deleteUserRole(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);

    void addUserRole(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);

    int checkRoleBindUser(@Param("roleId") Integer roleId);
}