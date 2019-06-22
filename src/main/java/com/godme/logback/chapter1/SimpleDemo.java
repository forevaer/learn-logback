package com.godme.logback.chapter1;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDemo {
    private static Logger log = LoggerFactory.getLogger(SimpleDemo.class);
    public static  Logger log2 = LoggerFactory.getLogger("simpleDemo");
    public static void main(String[] args) {
        //////////////////////////
        log.info("this is log");
        log2.error("this is log");
        ///////////////////////////
       if(log.isDebugEnabled()){
            log.debug("msg : {}", "message");
        }

//       LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//        StatusPrinter.print(lc);
    }
}
