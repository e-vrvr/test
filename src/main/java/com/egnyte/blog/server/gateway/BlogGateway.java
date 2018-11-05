package com.egnyte.blog.server.gateway;

import com.egnyte.blog.server.model.BlogPost;
import com.egnyte.blog.server.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.net.URI;

import static java.lang.String.format;
import static java.net.URI.create;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RestController
@RequestMapping(path = "/posts", produces = MediaType.APPLICATION_JSON)
public class BlogGateway {

    private final BlogRepository blogRepository;

    public BlogGateway(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BlogPost> blogPostGet(@PathVariable("id") int postId) {
        log.info("Received request for blog with id = [{}]", postId);

        return blogRepository.findById(postId)
                .map(post -> ok(post))
                .orElse(notFound()
                        .build());
    }

    @PostMapping(consumes = APPLICATION_JSON)
    public ResponseEntity<BlogPost> blogPostCreate(@RequestBody BlogPost blogPost) {
        log.info("Received request to create blog [{}]", blogPost);

        return blogRepository.save(blogPost)
                .map(post -> created(getLocation(post))
                        .body(post))
                .orElse(badRequest()
                        .build());
    }

    private URI getLocation(BlogPost post) {
        return create(format("/posts/%s", post.getId()));
    }

}
