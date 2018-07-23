package com.demo.blog.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.blog.exception.BadRequestException;
import com.demo.blog.exception.ResourceNotFoundException;
import com.demo.blog.model.Comment;
import com.demo.blog.model.Post;
import com.demo.blog.model.User;
import com.demo.blog.repository.CommentRepository;
import com.demo.blog.repository.PostRepository;
import com.demo.blog.repository.UserRepository;
import com.demo.blog.request.PostRequest;
import com.demo.blog.response.CommentResponse;
import com.demo.blog.response.PagedResponse;
import com.demo.blog.response.PostResponse;
import com.demo.blog.response.UserSummary;
import com.demo.blog.security.UserPrincipal;
import com.demo.blog.util.AppConstants;


@Service
public class PostService {
	
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(PostService.class);
	
	
	public PagedResponse<PostResponse> getAllPosts(UserPrincipal currentUser, int page, int size){
		// validate paging
		validatePageNumberAndSize(page, size);
		
		// retrieve posts
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
		Page<Post> posts = postRepository.findAll(pageable);
		
		// checking the posts result
		if(posts.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), posts.getNumber(), posts.getSize(), posts.getTotalElements(),
					posts.getTotalPages(), posts.isLast());
		}
		
		List<PostResponse> postResponses = posts.map(post -> 
		{
			PostResponse postResp = new PostResponse();
			postResp.setId(post.getId());
			postResp.setBody(post.getBody());
			postResp.setTitle(post.getTitle());
			postResp.setCreationDateTime(post.getCreatedAt());
			
			UserSummary creator = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
			postResp.setCreatedBy(creator);
			
			List<CommentResponse> commentResponse = post.getComments().stream().map(com -> {
				CommentResponse commentResp = new CommentResponse();
				commentResp.setId(com.getId());
				commentResp.setBody(com.getBody());
				commentResp.setCreationDateTime(com.getCreatedAt());
				commentResp.setCreatedBy(creator);
				
				return commentResp;
			}).collect(Collectors.toList());
			
			postResp.setComments(commentResponse);
			
			return postResp;
		}).getContent();
		
		return new PagedResponse<>(postResponses, posts.getNumber(), posts.getSize(), posts.getTotalElements(),
				posts.getTotalPages(), posts.isLast());
	}
	
	public Post createPost(PostRequest postRequest) {
		Post post = new Post();
		post.setBody(postRequest.getBody());
		post.setTitle(postRequest.getTitle());
		
		return postRepository.save(post);
	}
	
	public PostResponse getPostById(Long postId, UserPrincipal currentUser) {
		// check existing post
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "id", postId));
		
		// retrieve post creator details		
		PostResponse postResponse = new PostResponse();
		postResponse.setId(post.getId());
		postResponse.setTitle(post.getTitle());
		postResponse.setBody(post.getBody());
		
		UserSummary creator = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
		
		postResponse.setCreatedBy(creator);
		postResponse.setCreationDateTime(post.getCreatedAt());
		
		List<CommentResponse> commentResponse = post.getComments().stream().map(com -> {
			CommentResponse commentResp = new CommentResponse();
			commentResp.setId(com.getId());
			commentResp.setBody(com.getBody());
			commentResp.setCreationDateTime(com.getCreatedAt());
			// retrieve post creator details
			User commentar = userRepository.findById(post.getCreatedBy())
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", com.getCreatedBy()));
			UserSummary commentator = new UserSummary(commentar.getId(), commentar.getUsername(), commentar.getName());
			commentResp.setCreatedBy(commentator);
			
			return commentResp;
		}).collect(Collectors.toList());
		
		postResponse.setComments(commentResponse);

		return postResponse;
	}
	
	private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
