package com.godme.logback.chapter2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Config {
    private static Logger log = LoggerFactory.getLogger(Config.class);
    public static void main(String[] args) {
        MDC.put("username", "godme");
        MDC.put("age", "32");
        log.info("i am godme");
        MDC.put("username", "judas");
        MDC.put("age", "99");
        log.info("i am judas");
    }
}
