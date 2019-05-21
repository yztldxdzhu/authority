package com.yhguo.dbc.mapper.inter;

import com.yhguo.common.bean.common.AttributeBean;
import  com.yhguo.common.bean.request.SystemReqBean;
import com.yhguo.common.bean.response.SystemResBean;
import com.yhguo.dbc.entity.PomsConfigSystem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PomsConfigSystemMapper {
    int deleteByPrimaryKey(Short id);

    int insert(PomsConfigSystem record);

    int insertSelective(PomsConfigSystem record);

    PomsConfigSystem selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(PomsConfigSystem record);

    int updateByPrimaryKey(PomsConfigSystem record);

    /*前台页面调的接口*/
    List<AttributeBean> getSystemAttrList(@Param("disableFlag") Integer disableFlag);

    List<SystemResBean> searchSystemList(@Param("systemReqBean") SystemReqBean systemReqBean);

    void addSystem(@Param("systemReqBean") SystemReqBean systemReqBean);

    void editSystem(@Param("systemReqBean") SystemReqBean systemReqBean);

    void deleteSystem(@Param("systemId") Integer systemId);

    void disableSystem(@Param("systemId") Integer systemId);

    void enableSystem(@Param("systemId") Integer systemId);

    /*后台自己调的接口*/
    int checkSystemNameExist(@Param("systemName") String systemName, @Param("systemId") Integer systemId);

    String getSystemName(@Param("systemId") Integer systemId);
}