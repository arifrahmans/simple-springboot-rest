package com.demo.blog.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PostRequest {
	@NotBlank
	@Size(min=2, max = 150)
	private String title;
	
	@NotBlank
	@Size(min=2, max = 255)
	private String body;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
