package com.egnyte.blog.server.repository;

import com.egnyte.blog.server.model.BlogPostDto;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BlogPostRepository extends CrudRepository<BlogPostDto, Integer> {

    BlogPostDto save(BlogPostDto blogPost);
    Optional<BlogPostDto> findById(Integer id);
}
