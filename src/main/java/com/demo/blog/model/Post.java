package com.demo.blog.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.demo.blog.model.audit.UserDateAudit;

@Entity
@Table(name = "posts")
public class Post extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 150)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	@NotBlank
	private String body;
	
	@OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
	@Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
	private List<Comment> comments = new ArrayList<>();
	
	public Post() {
		
	}

	public Post(String title, String body, List<Comment> comments) {
		this.title = title;
		this.body = body;
		this.comments = comments;
	}

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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
}
