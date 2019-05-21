package com.yhguo.web_poms.service.impl;

import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.SystemReqBean;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.mgmt_user.SystemMgmt;
import com.yhguo.web_poms.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemMgmt systemMgmt;

    private final int MAX_FILE_SIZE = 100;

    @Override
    public ResultObject getSystemAttrList(Integer disableFlag) {
        return systemMgmt.getSystemAttrList(disableFlag);
    }

    @Override
    public ResultObject searchSystemList(SystemReqBean systemReqBean, PageInfoBean pageInfoBean) {
        return systemMgmt.searchSystemList(systemReqBean, pageInfoBean);
    }

    @Override
    public ResultObject addSystem(SystemReqBean systemReqBean, MultipartFile multipartFile) {
        ResultObject resultObject = new ResultObject();
        if (systemReqBean.getName() == null || "".equals(systemReqBean.getName().trim())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加系统出错：系统名称不能为空！");
            return resultObject;
        }
        if (systemReqBean.getName().length() > 20) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加系统出错：系统名称不能大于20字符！");
            return resultObject;
        }
        if (systemReqBean.getUrl() == null || "".equals(systemReqBean.getUrl().trim())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加系统出错：系统URL不能为空！");
            return resultObject;
        }
        if (multipartFile == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加系统出错：系统图片不能为空！");
            return resultObject;
        }
        if (multipartFile.getSize() > MAX_FILE_SIZE * 1024) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加系统出错：系统图片不能大于100KB！");
            return resultObject;
        }
        try {
            InputStream inputStream = multipartFile.getInputStream();
            return systemMgmt.addSystem(systemReqBean, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加系统出错：文件转流IO异常！");
            return resultObject;
        }
    }

    @Override
    public ResultObject editSystem(SystemReqBean systemReqBean, MultipartFile multipartFile) {
        ResultObject resultObject = new ResultObject();
        if (systemReqBean.getId() == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑系统出错：系统ID不能为空！");
            return resultObject;
        }
        if (systemReqBean.getName() == null || "".equals(systemReqBean.getName().trim())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑系统出错：系统名称不能为空！");
            return resultObject;
        }
        if (systemReqBean.getName().length() > 20) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加系统出错：系统名称不能大于20字符！");
            return resultObject;
        }
        if (systemReqBean.getUrl() == null || "".equals(systemReqBean.getUrl().trim())) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑系统出错：系统URL不能为空！");
            return resultObject;
        }
        InputStream inputStream = null;
        if (multipartFile != null) {
            try {
                inputStream = multipartFile.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                resultObject.setStatus(EnumResultStatus.FAILURE);
                resultObject.setMessage("编辑系统出错：文件转流IO异常！");
                return resultObject;
            }
        }
        return systemMgmt.editSystem(systemReqBean, inputStream);
    }

    @Override
    public ResultObject deleteSystem(Integer systemId) {
        ResultObject resultObject = new ResultObject();
        if (systemId == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("删除系统出错：系统ID不能为空！");
            return resultObject;
        }
        return systemMgmt.deleteSystem(systemId);
    }

    @Override
    public ResultObject disableSystem(Integer systemId) {
        ResultObject resultObject = new ResultObject();
        if (systemId == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("禁用系统出错：系统ID不能为空！");
            return resultObject;
        }
        return systemMgmt.disableSystem(systemId);
    }

    @Override
    public ResultObject enableSystem(Integer systemId) {
        ResultObject resultObject = new ResultObject();
        if (systemId == null) {
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("启用系统出错：系统ID不能为空！");
            return resultObject;
        }
        return systemMgmt.enableSystem(systemId);
    }

}
