package com.house.transport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransportApplication {
	private final static Logger logger = LogManager.getLogger(TransportApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TransportApplication.class, args);
		logger.info("[TransportApplication][main] Application started...");
	}
}
