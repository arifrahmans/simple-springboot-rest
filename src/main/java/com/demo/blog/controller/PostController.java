
package com.demo.blog.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.blog.model.Comment;
import com.demo.blog.model.Post;
import com.demo.blog.repository.CommentRepository;
import com.demo.blog.request.CommentRequest;
import com.demo.blog.request.PostRequest;
import com.demo.blog.response.ApiResponse;
import com.demo.blog.response.PagedResponse;
import com.demo.blog.response.PostResponse;
import com.demo.blog.security.CurrentUser;
import com.demo.blog.security.UserPrincipal;
import com.demo.blog.service.PostService;
import com.demo.blog.util.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);
	
	@GetMapping
	public PagedResponse<PostResponse> getPosts(@CurrentUser UserPrincipal currentUser,
												@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
												@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
		
		return postService.getAllPosts(currentUser, page, size);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createPosts(@Valid @RequestBody PostRequest postRequest){
		Post post = postService.createPost(postRequest);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{postId}")
                .buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location)
				.body(new ApiResponse(true, "Post Created Successfully"));
	}
	
	@PostMapping("/{postId}/comment")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createPosts(@Valid @RequestBody CommentRequest comment, @PathVariable Long postId){
		Comment newComment = new Comment();
		newComment.setBody(comment.getBody());
		
		Post post = new Post();
		post.setId(postId);
		
		newComment.setPost(post);
		
		Comment commentSave = commentRepository.save(newComment);

		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{postId}")
                .buildAndExpand(newComment.getPost().getId()).toUri();
		
		return ResponseEntity.created(location)
				.body(new ApiResponse(true, "Comment Created Successfully"));
	}
	
	@GetMapping("/{postId}")
	public PostResponse getPostById(@CurrentUser UserPrincipal currentUser,
									@PathVariable Long postId){		
		return postService.getPostById(postId, currentUser);
	}
	
	
}
