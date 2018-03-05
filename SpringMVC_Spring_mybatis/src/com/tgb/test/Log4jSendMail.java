package com.tgb.test;
import org.apache.log4j.Logger;  
import org.apache.log4j.PropertyConfigurator;  
  
public class Log4jSendMail {  
    public static void main(String args[]) throws InterruptedException {  
      PropertyConfigurator.configure(Log4jSendMail.class.getResource("/log4j.properties"));  
        Logger logger = Logger.getLogger(Log4jSendMail.class);  
        int i = 0;  
        while (i < 1) {  
            i += 1;  
            logger.error("Do you received message?");  
            logger.error("Do you received message?"); 
            logger.error("Do you received message?"); 
            logger.error("Do you received message?"); 
            logger.error("Do you received message?"); 
            logger.error("Do you received message?"); 
            logger.error("Do you received message?"); 
            logger.error("Do you received message?"); 
            logger.error("Do you received message?"); 
            logger.error("Do you received message?"); 
            logger.error("Do you received message?"); 
        }  
          
    }  
}  