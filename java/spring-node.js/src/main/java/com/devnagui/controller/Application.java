package com.devnagui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devnagui.business.triangle.TriangleIdentificatorBO;
/**
 * @author devnagui
 *
 */
@SpringBootApplication
@Controller
@ComponentScan(basePackages="com.devnagui")
public class Application {
	
	@Autowired()
	private TriangleIdentificatorBO triangleIdentificatorBO;

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
	}
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}

}