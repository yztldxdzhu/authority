package com.yhguo.web_poms;


import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.SystemReqBean;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.web_poms.service.SystemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemTest {

    @Autowired
    private SystemService systemService;

    @Test
    public void getSystemAttrList(){
        ResultObject resultObject = systemService.getSystemAttrList(0);
        System.out.println("---getSystemAttrList---");
        System.out.println(resultObject.getData());
    }

    @Test
    public void searchSystemList(){
        SystemReqBean systemReqBean = new SystemReqBean();
        systemReqBean.setName("poms");
        systemReqBean.setDisableFlag(0);
        PageInfoBean pageInfoBean = new PageInfoBean();
        pageInfoBean.setCurrentPage(1);
        pageInfoBean.setPageSize(20);
        ResultObject resultObject = systemService.searchSystemList(systemReqBean, pageInfoBean);
        System.out.println("---searchSystemList---");
        System.out.println(resultObject.getData());
    }

}
