package com.demo.blog.response;

import java.time.Instant;
import java.util.List;

public class PostResponse {
	private Long id;
	private String title;
	private String body;
	private List<CommentResponse> comments;
	private UserSummary createdBy;
	private Instant creationDateTime;
    
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
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<CommentResponse> getComments() {
		return comments;
	}
	public void setComments(List<CommentResponse> comments) {
		this.comments = comments;
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
