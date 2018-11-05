package com.egnyte.blog.server.service;

import com.egnyte.blog.server.model.BlogPost;
import com.egnyte.blog.server.model.BlogPostDto;
import com.egnyte.blog.server.repository.BlogPostRepository;
import com.egnyte.blog.server.repository.BlogRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final BlogPostRepository blogRepository;

    public BlogService(BlogPostRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public BlogPost get(int postId) {
        Optional<BlogPostDto> blogPost = blogRepository.findById(postId);
        return blogPost
                .map(BlogPostDto::asBlogPost)
                .orElse(null);
    }

    public BlogPost blogPostCreate(BlogPost blogPost) {
        try {
            BlogPostDto savedBlogPost = blogRepository.save(blogPost.asDto());
            return savedBlogPost.asBlogPost();
        } catch (Exception e) {
            LOGGER.error("Error has occurred", e);
            throw new IllegalStateException();
        }
    }

}
