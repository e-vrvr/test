package com.egnyte.blog.server.repository;

import com.egnyte.blog.server.model.BlogPost;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

@Repository
public class BlogRepository {

    //transactions???? TODO
    private static final String INSERT_SQL = "INSERT INTO posts (content, subject, tags, modificationDate, authorName, authorUserName) VALUES (:content, :subject, :tags, :date, :authorName, :authorUserName)";
    private static final String SELECT_BY_POSTID = "SELECT POSTID, CONTENT, SUBJECT, TAGS, MODIFICATIONDATE, AUTHORNAME, AUTHORUSERNAME FROM posts";
    private static final String POST_ID = "postId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BlogRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BlogPost get(int id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(POST_ID, id);

        BlogPost blogPost = jdbcTemplate.queryForObject(SELECT_BY_POSTID, parameterSource, (rs, rowNum) -> {
            LocalDate modificationDate = ofNullable(rs.getDate("MODIFICATIONDATE"))
                    .map(Date::toLocalDate)
                    .orElse(null);
            List<String> tags = ofNullable(rs.getString("TAGS"))
                    .map(s -> of(s.split(",")))
                    .map(s -> s.collect(toList()))
                    .orElse(null);

            return BlogPost.builder()
                    .authorName(rs.getString("AUTHORNAME"))
                    .authorUserName(rs.getString("AUTHORUSERNAME"))
                    .content(rs.getString("CONTENT"))
                    .id(rs.getInt("POSTID"))
                    .modificationDate(modificationDate)
                    .subject(rs.getString("SUBJECT"))
                    .tags(tags)
                    .build();
        });

        return blogPost;
    }


    public BlogPost save(BlogPost post) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("content", post.getContent());
        parameterSource.addValue("subject", post.getSubject());
        parameterSource.addValue("tags", post.getTags().stream().collect(joining(",")));
        parameterSource.addValue("date", post.getModificationDate());
        parameterSource.addValue("authorName", post.getAuthorName());
        parameterSource.addValue("authorUserName", post.getAuthorUserName());

        KeyHolder primaryKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_SQL, parameterSource, primaryKeyHolder, new String[]{POST_ID});
        Integer id = (Integer) primaryKeyHolder.getKeys().get(POST_ID);

        return post.toBuilder()
                .id(id)
                .build();
    }
}
