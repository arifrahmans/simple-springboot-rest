package com.demo.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
