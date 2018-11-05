package com.egnyte.blog.server.service;

import com.egnyte.blog.server.model.BlogPost;
import com.egnyte.blog.server.repository.BlogRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public BlogPost get(int postId) {
        return blogRepository.get(postId);
    }

    public BlogPost blogPostCreate(BlogPost blogPost) {
        try {
            return blogRepository.save(blogPost);
        } catch (Exception e) {
            LOGGER.error("Error has occurred", e);
            throw new IllegalStateException();
        }
    }

}
