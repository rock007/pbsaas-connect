package com.pbsaas.web.web.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.Map;

@ApiIgnore
@Controller
@RequestMapping(value="/article")
public class ArticleController extends BaseController{

	@RequestMapping("/article-list.html")
	public  String article_list(Map<String, Object> model) {
		
		model.put("time", new Date());
		model.put("message", "hello the world");
		return "page/article/article-list";
	}

	@RequestMapping("/article-edit.html")
	public  String article_edit(Map<String, Object> model) {

		return "page/article/article-edit";
	}

	@RequestMapping("/{cate}/article-search.html")
	public  String article_search(Map<String, Object> model) {

		return "page/article/article-search";
	}

	@RequestMapping("/article-view.html")
	public  String article_view(Map<String, Object> model) {

		return "page/article/article-view";
	}


}
