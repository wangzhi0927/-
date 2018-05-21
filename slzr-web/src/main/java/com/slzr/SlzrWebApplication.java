package com.slzr;

import com.bstek.ureport.console.UReportServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.slzr.*.dao")
@SpringBootApplication
@ImportResource("classpath:UReport/context.xml")
public class SlzrWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(SlzrWebApplication.class, args);
        System.out.println("---------启动成功---------");
    }

    @Bean
    public ServletRegistrationBean buildUReportServlet(){
        return new ServletRegistrationBean(new UReportServlet(),"/ureport/*");
    }

}
