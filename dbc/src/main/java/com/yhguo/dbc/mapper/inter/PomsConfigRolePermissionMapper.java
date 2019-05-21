package com.yhguo.dbc.mapper.inter;

import com.yhguo.dbc.entity.PomsConfigRolePermission;

public interface PomsConfigRolePermissionMapper {
    int deleteByPrimaryKey(Short id);

    int insert(PomsConfigRolePermission record);

    int insertSelective(PomsConfigRolePermission record);

    PomsConfigRolePermission selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(PomsConfigRolePermission record);

    int updateByPrimaryKey(PomsConfigRolePermission record);
}