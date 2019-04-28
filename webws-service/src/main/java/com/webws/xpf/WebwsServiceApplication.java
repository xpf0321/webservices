package com.webws.xpf;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.webws.xpf.mapper")
public class WebwsServiceApplication {
    private  static Logger logger = LoggerFactory.getLogger(WebwsServiceApplication.class) ;
    public static void main(String[] args) {
        SpringApplication.run(WebwsServiceApplication.class, args);

        logger.error("1111111111111111111");
    }

}
