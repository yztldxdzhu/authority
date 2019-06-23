package com.yhguo.web_poms;

import com.github.pagehelper.PageHelper;
import com.yhguo.common.config.GlobalConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@SpringBootApplication(scanBasePackages = {"com.yhguo.web_poms.**"})
@MapperScan(basePackages={"com.yhguo.dbc.mapper.inter"})
@ComponentScan(basePackages = {"com.yhguo"})
@EnableAutoConfiguration
@EnableTransactionManagement
public class WebPomsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebPomsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebPomsApplication.class);
    }

    @Autowired
    GlobalConfig globalConfig;

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> sessionManagerCustomizer() {
        return server -> server.addContextCustomizers(context -> context.setSessionTimeout(globalConfig.getInActiveTimeMin()));
    }

    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}
