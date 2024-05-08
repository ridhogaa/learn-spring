package org.ergea.springapp;

import org.ergea.springapp.ch5.common.LoggerUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAppApplication.class, args);
        LoggerUtils.logDebug("TEST DEBUG", SpringAppApplication.class);
        LoggerUtils.logError("TEST ERROR", new IllegalArgumentException("P"));
        LoggerUtils.logInfo("TEST INFO");
        LoggerUtils.logWarn("TEST WARN");
    }

}
