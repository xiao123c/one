package com.example.ruiji;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan//加载过滤器
@EnableTransactionManagement//开启事务支持
public class RuijiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuijiApplication.class, args);
        log.info("项目启动完成");
    }
}
