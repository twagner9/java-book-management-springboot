package com.twag.book_management_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

// Annotation contains the following (info from https://spring.io/guides/gs/spring-boot):
/*
 * 
    @Configuration: Tags the class as a source of bean definitions for the application context.

    @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. 
	For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a DispatcherServlet.

    @ComponentScan: Tells Spring to look for other components, configurations, and services in the com/example package, letting it find the controllers.


 */
@SpringBootApplication
public class BookManagementApp {
	public static void main(String[] args) {
		SpringApplication.run(BookManagementApp.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Main application has started.");
		};
	}
}
