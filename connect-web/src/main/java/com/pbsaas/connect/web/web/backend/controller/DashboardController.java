package com.pbsaas.connect.web.web.backend.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping(value="/backend")
public class DashboardController  extends BaseController{

	@RequestMapping("/")
	public  String index(Map<String, Object> model) {
		
		model.put("time", new Date());
		model.put("message", "hello the world");
		return "backend";
	}


}
