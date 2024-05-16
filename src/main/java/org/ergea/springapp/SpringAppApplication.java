package org.ergea.springapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.ergea.springapp.ch4.controller.upload.FileStorageProperties;
import org.ergea.springapp.ch5.common.LoggerUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@OpenAPIDefinition
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class SpringAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAppApplication.class, args);
        LoggerUtils.logDebug("TEST DEBUG", SpringAppApplication.class);
        LoggerUtils.logError("TEST ERROR", new IllegalArgumentException("P"));
        LoggerUtils.logInfo("TEST INFO");
        LoggerUtils.logWarn("TEST WARN");
    }

}
