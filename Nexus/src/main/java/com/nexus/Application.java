package com.nexus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		System.setProperty("user.timezone", "Asia/Kolkata");
	    System.out.println("user.timezone = " + System.getProperty("user.timezone"));
	    System.out.println("default timezone = " + java.util.TimeZone.getDefault());

	    SpringApplication.run(Application.class, args);
	}
}