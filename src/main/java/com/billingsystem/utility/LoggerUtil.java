package com.billingsystem.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
	private final Logger logger;
    private LoggerUtil(){
        logger = LoggerFactory.getLogger("ApplicationLogger");
    }
    public Logger getLogger() {
    	return logger;
    }
    private static volatile LoggerUtil loggingClass;
    public static LoggerUtil getInstance(){
        if(loggingClass == null){
            synchronized (LoggerUtil.class){
                if(loggingClass == null){
                    loggingClass = new LoggerUtil();
                }
            }
        }
        return  loggingClass;
    }
}
