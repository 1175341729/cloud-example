package com.springcloud.example.dynamic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.springcloud.example.dynamic.dao","com.baidu.fsg.uid.worker.dao"})
@ComponentScan(basePackages = {"com.springcloud.example","com.baidu.fsg.uid"})
@EnableRetry
@ServletComponentScan
public class DynamicApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicApplication.class, args);
	}
}
