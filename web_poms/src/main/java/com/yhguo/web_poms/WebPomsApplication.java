package com.yhguo.web_poms;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@SpringBootApplication(scanBasePackages = {"com.yhguo.web_poms.**"})
@MapperScan(basePackages={"com.yhguo.dbc.mapper.inter"})
@ComponentScan(basePackages = {"com.yhguo"})
@EnableAutoConfiguration
@EnableTransactionManagement
public class WebPomsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebPomsApplication.class, args);
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
