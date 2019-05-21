package com.yhguo.dbs.mgmt_user;

import com.yhguo.common.bean.request.UserReqBean;
import com.yhguo.common.bean.response.UserResBean;
import com.yhguo.dbc.entity.PomsConfigUser;
import com.yhguo.dbc.entity.PomsConfigUserRole;
import com.yhguo.dbc.mapper.inter.PomsConfigUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao {

    @Autowired
    private PomsConfigUserMapper pomsConfigUserMapper;

    /*前台页面调的接口*/
    public List<UserResBean> searchUserList(UserReqBean userReqBean) {
        return pomsConfigUserMapper.searchUserList(userReqBean);
    }

    public int addUser(UserReqBean userReqBean) {
        // 这里必须要用实体类
        PomsConfigUser user = new PomsConfigUser();
        user.setName(userReqBean.getName());
        user.setPassword(userReqBean.getPassword());
        user.setOperator(userReqBean.getOperator());
        pomsConfigUserMapper.insertSelective(user);
        return user.getId();
        /*pomsConfigUserMapper.addUser(userReqBean);
        return userReqBean.getId();*/
    }

    public void editUser(UserReqBean userReqBean) {
        pomsConfigUserMapper.editUser(userReqBean);
    }

    public void deleteUser(Integer userId) {
        pomsConfigUserMapper.deleteUser(userId);
    }

    public void disableUser(Integer userId) {
        pomsConfigUserMapper.disableUser(userId);
    }

    public void enableUser(Integer userId) {
        pomsConfigUserMapper.enableUser(userId);
    }

    /*后台自己调的接口*/
    public boolean checkUserNameExist(String userName, Integer userId) {
        int count = pomsConfigUserMapper.checkUserNameExist(userName, userId);
        return count > 0;
    }

}
