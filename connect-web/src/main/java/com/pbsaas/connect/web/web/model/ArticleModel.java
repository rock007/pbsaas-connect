package com.pbsaas.connect.web.web.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author sam
 *
 */
public class ArticleModel {

	private Long id;
	
	@NotNull
	@Size(min=5, max=256)
	private String title;

	@NotNull
	@Size(min=20)
	private String short_content;
	
	@NotNull
	private String content_html;
	
	@Size(min=0, max=100)
	private String source;
	
	@Size(min=0, max=512)
	private String source_url;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShort_content() {
		return short_content;
	}

	public void setShort_content(String short_content) {
		this.short_content = short_content;
	}

	public String getContent_html() {
		return content_html;
	}

	public void setContent_html(String content_html) {
		this.content_html = content_html;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}
	
}
