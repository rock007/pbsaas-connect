package com.pbsaas.connect.web.web.front.controller;

import java.util.Date;
import java.util.Map;

import com.pbsaas.connect.model.vo.AccountVO;
import com.pbsaas.connect.service.AccountFeignService;
import com.pbsaas.connect.web.web.backend.controller.BaseController;
import com.pbsaas.connect.web.web.model.JsonBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController  extends BaseController {

	@Autowired
	AccountFeignService accountFeignService;

	@RequestMapping("/")
	public  String index(Map<String, Object> model) {

		AccountVO ooo= accountFeignService.findByName("aaaaa");

		model.put("time", new Date());
		model.put("message", "hello the world");
		return "index";
	}
	
	@RequestMapping("/api.html")
	public  String api(Map<String, Object> model) {
		
		return "api";
	}
	
	@RequestMapping("/about-us.html")
	public  String about_us(Map<String, Object> model) {
		
		return "about-us";
	}
	
	@RequestMapping("/login.html")
	public  String login(Map<String, Object> model) {
		
		model.put("time", new Date());

		return "login";
	}
	
	@RequestMapping("/logout.action")
	public  String logout(Map<String, Object> model) {
		
		return "login";
	}

	@RequestMapping("/test/notify.action")
	public @ResponseBody
	JsonBody<String> doBusNotify(String msg){

		//eventBus.notify("quotes", Event.wrap(msg));

		return new JsonBody<String>(1,"测试数据"+msg);
	}


	@GetMapping("/{name}/articles/")
	public  String articles(Map<String, Object> model, @PathVariable String name, String searchkey) {

		return "page/article/article-search";
	}

	@RequestMapping("/{name}/articles/{arcicleId}")
	public  String article_view(Map<String, Object> model, @PathVariable String name,
								@PathVariable String arcicleId) {

		return "page/article/article-view";
	}

}
