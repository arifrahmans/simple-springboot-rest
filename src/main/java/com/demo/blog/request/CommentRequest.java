package com.demo.blog.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.demo.blog.model.Post;

public class CommentRequest {
	@NotBlank
	@Size(min=2, max = 250)
	private String body;
	
	@NotNull
	@Valid
	private Post post;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	
	
	
}
