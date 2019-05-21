package com.yhguo.dbs.mgmt_user;

import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.common.bean.request.SystemReqBean;
import com.yhguo.common.bean.response.SystemResBean;
import com.yhguo.dbc.mapper.inter.PomsConfigSystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SystemDao {

    @Autowired
    private PomsConfigSystemMapper pomsConfigSystemMapper;

    /*前台页面调的接口*/
    public  List<AttributeBean> getSystemAttrList(Integer disableFlag){
        return pomsConfigSystemMapper.getSystemAttrList(disableFlag);
    }

    public List<SystemResBean> searchSystemList(SystemReqBean systemReqBean) {
        return pomsConfigSystemMapper.searchSystemList(systemReqBean);
    }

    public void addSystem(SystemReqBean systemReqBean) {
        systemReqBean.setDisableFlag(0);
        pomsConfigSystemMapper.addSystem(systemReqBean);
    }

    public void editSystem(SystemReqBean systemReqBean) {
        systemReqBean.setDisableFlag(0);
        pomsConfigSystemMapper.editSystem(systemReqBean);
    }

    public void deleteSystem(Integer systemId) {
        pomsConfigSystemMapper.deleteSystem(systemId);
    }

    public void disableSystem(Integer systemId) {
        pomsConfigSystemMapper.disableSystem(systemId);
    }

    public void enableSystem(Integer systemId) {
        pomsConfigSystemMapper.enableSystem(systemId);
    }

    /*后台自己调的接口*/
    public Boolean checkSystemNameExist(String systemName, Integer systemId){
        int count = pomsConfigSystemMapper.checkSystemNameExist(systemName, systemId);
        return !(count == 0);
    }

    public String getSystemName(Integer systemId){
        return pomsConfigSystemMapper.getSystemName(systemId);
    }

}
