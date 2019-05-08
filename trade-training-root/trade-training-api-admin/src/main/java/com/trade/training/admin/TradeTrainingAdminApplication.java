package com.trade.training.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author herry-zhang
 * @date 2018-7-16 19:20:38
 */
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.trade.training.admin.*", "com.trade.training.service"})
@MapperScan("com.trade.training.mapper")
public class TradeTrainingAdminApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TradeTrainingAdminApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TradeTrainingAdminApplication.class);
    }
}
