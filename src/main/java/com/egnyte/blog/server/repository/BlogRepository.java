package com.egnyte.blog.server.repository;

import com.egnyte.blog.server.model.BlogPost;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

@Repository
public class BlogRepository {

    private final DriverManagerDataSource dataSource;

    public BlogRepository(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public BlogPost get() {


        return dataSource.
    }
}
