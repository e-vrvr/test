package com.egnyte.blog.server.config;

import com.egnyte.blog.server.repository.BlogPostRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = BlogPostRepository.class)
public class DbConfig2 {
}
