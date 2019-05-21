package com.yhguo.dbc.mapper.inter;

import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.common.bean.request.RoleReqBean;
import com.yhguo.common.bean.response.RoleResBean;
import com.yhguo.dbc.entity.PomsConfigRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PomsConfigRoleMapper {
    int deleteByPrimaryKey(Short id);

    int insert(PomsConfigRole record);

    int insertSelective(PomsConfigRole record);

    PomsConfigRole selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(PomsConfigRole record);

    int updateByPrimaryKey(PomsConfigRole record);

    /*前台页面调的接口*/
    List<AttributeBean> getRoleAttrList(@Param("systemId") Integer systemId);

    List<RoleResBean> searchRoleList(@Param("roleReqBean") RoleReqBean roleReqBean);

    void addRole(@Param("roleReqBean") RoleReqBean roleReqBean);

    void editRole(@Param("roleReqBean") RoleReqBean roleReqBean);

    void deleteRole(@Param("roleId") Integer roleId);

    /*后台自己调的接口*/
    int checkRoleNameExist(@Param("roleName") String roleName,
                           @Param("systemId") Integer systemId,
                           @Param("roleId") Integer roleId);
}