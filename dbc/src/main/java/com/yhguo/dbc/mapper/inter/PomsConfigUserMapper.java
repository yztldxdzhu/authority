package com.yhguo.dbc.mapper.inter;

import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.bean.response.UserResBean;
import com.yhguo.dbc.entity.PomsConfigUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PomsConfigUserMapper {
    int deleteByPrimaryKey(Short id);

    int insert(PomsConfigUser record);

    int insertSelective(PomsConfigUser record);

    PomsConfigUser selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(PomsConfigUser record);

    int updateByPrimaryKey(PomsConfigUser record);

    /*前台页面调的接口*/
    List<UserResBean> searchUserList(@Param("userReqBean") UserReqBean userReqBean);

    void addUser(@Param("userReqBean") UserReqBean userReqBean);

    void editUser(@Param("userReqBean") UserReqBean userReqBean);

    void deleteUser(@Param("userId") Integer userId);

    void disableUser(@Param("userId") Integer userId);

    void enableUser(@Param("userId") Integer userId);

    /*后台自己调的接口*/
    int checkUserNameExist(@Param("userName") String userName, @Param("userId") Integer userId);

    String getUserPassword(@Param("userName") String userName);

    Integer getUserId(@Param("userName") String userName);

    UserResBean getUserInfo(@Param("userName") String userName);

}