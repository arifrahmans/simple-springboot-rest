package com.demo.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.blog.model.Post;
import com.demo.blog.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {
	Optional<Post> findById(Long postId);
	
	Page<Post> findByCreatedBy(Long userId, Pageable pageable);
	
	long countByCreatedBy(Long userid);
	
	List<Post>  findByIdIn(List<Long> postIds);

    List<Post> findByIdIn(List<Long> pollIds, Sort sort);
    
    Page<Post> findByUserOrderByCreateDateDesc(User user, Pageable pageable);

    Page<Post> findAllByOrderByCreateDateDesc(Pageable pageable);

}
