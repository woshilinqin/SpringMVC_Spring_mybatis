package com.tgb.test;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jTest {

	public static void main(String[] args) {
		PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties"));
		Logger logger=Logger.getLogger(Log4jTest.class);
		logger.info("hahahahhaha");
		logger.error("error", new Throwable());
		logger.warn("warn");
		logger.debug("debug");
	}

}
