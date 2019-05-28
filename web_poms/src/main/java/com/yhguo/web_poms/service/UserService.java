package com.yhguo.web_poms.service;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.framework.ResultObject;

public interface UserService {

    ResultObject login(String username, String passwordAES);
    ResultObject searchUserList(UserReqBean userReqBean, PageInfoBean pageInfoBean);
    ResultObject addUser(UserReqBean userReqBean);
    ResultObject editUser(UserReqBean userReqBean);
    ResultObject deleteUser(Integer userId);
    ResultObject disableUser(Integer userId);
    ResultObject enableUser(Integer userId);

}
