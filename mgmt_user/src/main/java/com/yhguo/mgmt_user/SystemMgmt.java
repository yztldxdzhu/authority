package com.yhguo.mgmt_user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhguo.common.bean.common.AttributeBean;
import com.yhguo.common.bean.common.PageInfoBean;
import com.yhguo.common.bean.request.SystemReqBean;
import com.yhguo.common.bean.response.SystemResBean;
import com.yhguo.common.config.GlobalConfig;
import com.yhguo.common.framework.EnumResultStatus;
import com.yhguo.common.framework.ResultObject;
import com.yhguo.common.tool.FileUtil;
import com.yhguo.dbs.mgmt_user.SystemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@Component
public class SystemMgmt {

    @Autowired
    private SystemDao systemDao;

    @Autowired
    private GlobalConfig globalConfig;

    public ResultObject getSystemAttrList(Integer disableFlag) {
        ResultObject resultObject = new ResultObject();
        try {
            List<AttributeBean> list = systemDao.getSystemAttrList(disableFlag);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("获取可用系统属性列表失败：" + e.getMessage());
        }
        return resultObject;
    }

    public ResultObject searchSystemList(SystemReqBean systemReqBean, PageInfoBean pageInfoBean) {
        ResultObject resultObject = new ResultObject();
        try {
            PageHelper.startPage(pageInfoBean.getCurrentPage(), pageInfoBean.getPageSize(), true);
            List<SystemResBean> list = systemDao.searchSystemList(systemReqBean);
            if (list.size() > 0) {
                for (SystemResBean systemResBean : list) {
                    systemResBean.setPicUrl(globalConfig.getHttpUrlPrefix() + systemResBean.getName() + ".png");
                }
            }
            PageInfo pageInfo = new PageInfo(list);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("获取系统列表失败：" + e.getMessage());
        }
        return resultObject;
    }

    @Transactional
    public ResultObject addSystem(SystemReqBean systemReqBean, InputStream inputStream) {
        ResultObject resultObject = new ResultObject();
        try {
            String systemName = systemReqBean.getName();
            boolean systemNameExistFlag = systemDao.checkSystemNameExist(systemName, null);
            if (systemNameExistFlag) {
                resultObject.setStatus(EnumResultStatus.FAILURE);
                resultObject.setMessage("添加系统出错：系统名称存在重复，请重新命名！");
                return resultObject;
            }
            // 1. 保存文件流到本地
            String systemImgPath = globalConfig.getSystemImgPath();
            FileUtil.saveFile(systemImgPath, inputStream, systemName + ".png");
            // 2. 执行添加
            systemDao.addSystem(systemReqBean);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("添加系统成功！");
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("添加系统出错：" + e.getMessage());
            return resultObject;
        }
    }

    @Transactional
    public ResultObject editSystem(SystemReqBean systemReqBean, InputStream inputStream) {
        ResultObject resultObject = new ResultObject();
        try {
            String systemName = systemReqBean.getName();
            Integer systemId = systemReqBean.getId();
            boolean systemNameExistFlag = systemDao.checkSystemNameExist(systemName, systemId);
            if (systemNameExistFlag) {
                resultObject.setStatus(EnumResultStatus.FAILURE);
                resultObject.setMessage("编辑系统出错：系统名称存在重复，请重新命名！");
                return resultObject;
            }
            String systemImgPath = globalConfig.getSystemImgPath();
            // 1. 修改图片名字
            String oldName = systemDao.getSystemName(systemId);
            String oldPath = systemImgPath + "/" + oldName + ".png";
            File oldFile = new File(oldPath);
            File newFile = new File(systemImgPath + "/" + systemName + ".png");
            oldFile.renameTo(newFile);
            // 2. 保存文件流到本地
            if (inputStream != null) {
                FileUtil.saveFile(systemImgPath, inputStream, systemName + ".png");
            }
            // 3. 执行数据库修改
            systemDao.editSystem(systemReqBean);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("编辑系统成功！");
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("编辑系统出错：" + e.getMessage());
            return resultObject;
        }
    }

    @Transactional
    public ResultObject deleteSystem(Integer systemId) {
        ResultObject resultObject = new ResultObject();
        try {
            // TODO 检查系统角色和用户绑定与否
            // TODO 删除系统图片
            systemDao.deleteSystem(systemId);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("删除系统成功！");
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("删除系统出错：" + e.getMessage());
            return resultObject;
        }
    }

    public ResultObject disableSystem(Integer systemId) {
        ResultObject resultObject = new ResultObject();
        try {
            systemDao.disableSystem(systemId);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("禁用系统成功！");
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("禁用系统出错：" + e.getMessage());
            return resultObject;
        }
    }

    public ResultObject enableSystem(Integer systemId) {
        ResultObject resultObject = new ResultObject();
        try {
            systemDao.enableSystem(systemId);
            resultObject.setStatus(EnumResultStatus.SUCCESS);
            resultObject.setMessage("启用系统成功！");
            return resultObject;
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(EnumResultStatus.FAILURE);
            resultObject.setMessage("启用系统出错：" + e.getMessage());
            return resultObject;
        }
    }

}
