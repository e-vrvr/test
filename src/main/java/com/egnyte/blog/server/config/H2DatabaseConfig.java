package com.egnyte.blog.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Configuration
public class H2DatabaseConfig {

    @Bean
    public DriverManagerDataSource h2DatabaseConnection() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/myDB;MV_STORE=false");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        DatabasePopulatorUtils.execute(new ResourceDatabasePopulator(new ClassPathResource("schema1.sql"), new ClassPathResource("data-1.sql")), dataSource);
        return dataSource;
    }


    public void insertPost(String authorName, String subject, String authorUserName, String content, String date, String tags) throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:h2:~/myDB", "sa", "");
        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO posts (content, subject, tags, modificationDate, " +
                "authorName, authorUserName) VALUES ('" + content + "', '" + subject + "', " +
                "'" + tags + "', '" + date + "', '" + authorName + "', '" + authorUserName + "');");
        connection.close();
    }
}
