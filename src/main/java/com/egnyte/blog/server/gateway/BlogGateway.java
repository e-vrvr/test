package com.egnyte.blog.server.gateway;

import com.egnyte.blog.server.model.BlogPost;
import com.egnyte.blog.server.service.BlogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

import static org.springframework.http.ResponseEntity.*;

@RestController
public class BlogGateway {

    private static final Logger LOGGER = LogManager.getLogger();

    private final BlogService blogService;

    public BlogGateway(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping(path = "/posts/{id}")
    public ResponseEntity<BlogPost> blogPostGet(@PathVariable("id") int postId) {
        LOGGER.info("Received request for blog with id = [{}]", postId);
        try {
            return ok(blogService.get(postId));
        } catch (Exception e) {
            return notFound().build();
        }
    }

    @PostMapping(path = "/posts", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<BlogPost> blogPostCreate(@RequestBody BlogPost blogPost) {
        try {
            LOGGER.info("Received request to create blog [{}]", blogPost);
            BlogPost savedBlogPost = blogService.blogPostCreate(blogPost);
            return ok(savedBlogPost);
        } catch (Exception e) {
            return badRequest().build();
        }
    }

}
