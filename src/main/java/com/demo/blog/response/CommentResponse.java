package com.demo.blog.response;

import java.time.Instant;

public class CommentResponse {
	private Long id;
	private String body;
	private UserSummary createdBy;
	private Instant creationDateTime;
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
	public UserSummary getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserSummary createdBy) {
		this.createdBy = createdBy;
	}
	public Instant getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(Instant creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	
	
}
