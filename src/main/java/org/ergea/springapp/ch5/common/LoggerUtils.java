package org.ergea.springapp.ch5.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoggerUtils {

    public static void logDebug(String message, Class<?> clazz) {
        LoggerFactory.getLogger(clazz).debug(message);
    }

    public static void logInfo(String message) {
        log.info(message);
    }

    public static void logWarn(String message) {
        log.warn(message);
    }

    public static void logError(String message, Throwable throwable) {
        log.error(message, throwable);
    }
}
