package com.egnyte.blog.server.gateway;

import com.egnyte.blog.server.model.BlogPost;
import com.egnyte.blog.server.service.BlogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RestController
public class BlogGateway {

    private static final Logger LOGGER = LogManager.getLogger();

    private final BlogService blogService;

    public BlogGateway(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping(path = "/posts")
    public String blogPostGet(@QueryParam("postId") int postId) throws Exception {
        return blogService.blogPostGet(postId);
    }

    @PostMapping(path = "/posts", consumes = MediaType.APPLICATION_JSON)
    public BlogPost blogPostCreate(BlogPost body) {
        return blogService.blogPostCreate(body);
    }

}
