package com.yhguo.web_poms.controller;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.SystemReqBean;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.web_poms.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/poms/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @GetMapping("/getSystemAttrList")
    public ResultObject getSystemAttrList(Integer disableFlag){
        return systemService.getSystemAttrList(disableFlag);
    }

    @GetMapping("/searchSystemList")
    public ResultObject searchSystemList(SystemReqBean systemReqBean, PageInfoBean pageInfoBean) {
        return systemService.searchSystemList(systemReqBean, pageInfoBean);
    }

    @PostMapping("/addSystem")
    public ResultObject addSystem(SystemReqBean systemReqBean, MultipartFile multipartFile) {
        systemReqBean.setOperator("yhguo");
        return systemService.addSystem(systemReqBean, multipartFile);
    }

    @PostMapping("/editSystem")
    public ResultObject editSystem(SystemReqBean systemReqBean, @RequestParam(value = "multipartFile", required = false) MultipartFile multipartFile) {
        systemReqBean.setOperator("yhguo");
        return systemService.editSystem(systemReqBean, multipartFile);
    }

    @DeleteMapping("/deleteSystem")
    public ResultObject deleteSystem(Integer systemId) {
        return systemService.deleteSystem(systemId);
    }

    @PostMapping("/disableSystem")
    public ResultObject disableSystem(Integer systemId) {
        return systemService.disableSystem(systemId);
    }

    @PostMapping("/enableSystem")
    public ResultObject enableSystem(Integer systemId) {
        return systemService.enableSystem(systemId);
    }

}
