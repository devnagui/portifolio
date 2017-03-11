package com.devnagui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devnagui.business.triangle.TriangleIdentificatorBO;
/**
 * @author devnagui
 *
 */
@SpringBootApplication
@Controller
public class Application {
	
	@Autowired
	private TriangleIdentificatorBO triangleStorageBO;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}

}