package org.ukdw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.ukdw.config.AppProperties;

/**
 * Project: SRM-BE
 * Package: PACKAGE_NAME
 * <p>
 * Creator: dendy
 * Date: 8/30/2024
 * Time: 7:44 AM
 * <p>
 * Description : Entry point for springboot application
 */

@SpringBootApplication
//@EnableFeignClients
@EnableConfigurationProperties(AppProperties.class)
public class AppMain {
    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }
}
