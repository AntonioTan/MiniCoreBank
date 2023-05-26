package com.stori.bankuserservice;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = {"com.stori.bankuserservice"})
@ImportResource("classpath:META-INF/bean.xml")
public class TestConfiguration {
}
