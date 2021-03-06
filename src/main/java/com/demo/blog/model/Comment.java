package com.demo.blog.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.demo.blog.model.audit.UserDateAudit;

@Entity
@Table(name = "comments")
public class Comment extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "TEXT")
	@NotBlank
	private String body;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
	
	public Comment() {
		
	}

	public Comment(@NotBlank String body, Post post) {
		this.body = body;
		this.post = post;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
