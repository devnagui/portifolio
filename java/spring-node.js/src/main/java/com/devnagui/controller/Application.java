package com.devnagui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devnagui.business.triangle.TriangleIdentificatorBO;
import com.devnagui.exception.InvalidPolygonException;
/**
 * @author devnagui
 *
 */
import com.devnagui.model.triangle.Triangle;
import com.devnagui.model.triangle.TriangleVO;
@SpringBootApplication
@Controller
@ComponentScan(basePackages="com.devnagui")
public class Application {
	
	@Autowired()
	private TriangleIdentificatorBO triangleIdentificatorBO;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	@RequestMapping(value="/identify", method = RequestMethod.POST,produces="application/json")
	@ResponseBody
	public Triangle identify(@RequestBody TriangleVO triangle) throws InvalidPolygonException{
		return triangleIdentificatorBO.identify(triangle.getAsTriangle());
	}
	
}