package com.yhguo.web_poms.service;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.SystemReqBean;
import com.yhguo.common.framework.ResultObject;
import org.springframework.web.multipart.MultipartFile;

public interface SystemService {

    ResultObject getSystemAttrList(Integer disableFlag);

    ResultObject searchSystemList(SystemReqBean systemReqBean, PageInfoBean pageInfoBean);

    ResultObject addSystem(SystemReqBean systemReqBean, MultipartFile multipartFile);

    ResultObject editSystem(SystemReqBean systemReqBean, MultipartFile multipartFile);

    ResultObject deleteSystem(Integer systemId);

    ResultObject disableSystem(Integer systemId);

    ResultObject enableSystem(Integer systemId);

}
