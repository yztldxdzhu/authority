package com.yhguo.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value = "file:/poms/conf/config.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "file:${user.home}/poms/conf/config.properties", ignoreResourceNotFound = true)
})
public class GlobalConfig {

    @Value("${httpUrlPrefix}")
    private String httpUrlPrefix;

    @Value("${systemImgPath}")
    private String systemImgPath;

    // tomcat 不活跃超时时间
    @Value("${inActiveTimeMin}")
    private int inActiveTimeMin;

    // 用户密码加密密钥
    @Value("${secureKey}")
    private String secureKey;

    // 用户密码加密密钥偏移量
    @Value("${secureIv}")
    private String secureIv;

    public String getHttpUrlPrefix() {
        return httpUrlPrefix;
    }

    public void setHttpUrlPrefix(String httpUrlPrefix) {
        this.httpUrlPrefix = httpUrlPrefix;
    }

    public String getSystemImgPath() {
        return systemImgPath;
    }

    public void setSystemImgPath(String systemImgPath) {
        this.systemImgPath = systemImgPath;
    }

    public int getInActiveTimeMin() {
        return inActiveTimeMin;
    }

    public void setInActiveTimeMin(int inActiveTimeMin) {
        this.inActiveTimeMin = inActiveTimeMin;
    }

    public String getSecureKey() {
        return secureKey;
    }

    public void setSecureKey(String secureKey) {
        this.secureKey = secureKey;
    }

    public String getSecureIv() {
        return secureIv;
    }

    public void setSecureIv(String secureIv) {
        this.secureIv = secureIv;
    }
}
