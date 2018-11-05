package com.egnyte.blog.server.repository;

import com.egnyte.blog.server.model.BlogPost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

@Repository
public class BlogRepository {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String INSERT_SQL = "INSERT INTO posts (content, subject, tags, modificationDate, authorName, authorUserName) VALUES (:content, :subject, :tags, :date, :authorName, :authorUserName)";
    private static final String SELECT_BY_POSTID = "SELECT POSTID, CONTENT, SUBJECT, TAGS, MODIFICATIONDATE, AUTHORNAME, AUTHORUSERNAME FROM posts where POSTID = :postId";
    private static final String POST_ID = "postId";
    private static final String[] KEY_COLUMN_NAME = {POST_ID};
    private static final String TAGS_SEPARATOR = ",";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BlogRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<BlogPost> findById(int id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(POST_ID, id);

        LOGGER.debug("Using [{}] parameters to fetch blog post", parameterSource);
        try {
            BlogPost blogPost = jdbcTemplate
                    .queryForObject(SELECT_BY_POSTID, parameterSource, mapToBlogPost());
            return Optional.of(blogPost);
        } catch (Exception e) {
            LOGGER.error(format("Error occurred while looking for post with id = [%s]", id), e);
            return empty();
        }
    }

    public Optional<BlogPost> save(BlogPost post) {
        MapSqlParameterSource parameterSource = prepareParameters(post);
        LOGGER.debug("Using [{}] parameters to fetch blog post", parameterSource);

        KeyHolder primaryKeyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(INSERT_SQL, parameterSource, primaryKeyHolder, KEY_COLUMN_NAME);
            Integer generatedId = (Integer) primaryKeyHolder.getKeys().get(POST_ID);

            return Optional.of(
                    post.toBuilder()
                            .id(generatedId)
                            .build());
        } catch (Exception e) {
            LOGGER.error(format("Error occurred while saving post [%s]", post), e);
            return empty();
        }
    }

    private MapSqlParameterSource prepareParameters(BlogPost post) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("content", post.getContent());
        parameterSource.addValue("subject", post.getSubject());
        parameterSource.addValue("tags", joinTags(post));
        parameterSource.addValue("date", post.getModificationDate());
        parameterSource.addValue("authorName", post.getAuthorName());
        parameterSource.addValue("authorUserName", post.getAuthorUserName());
        return parameterSource;
    }

    private RowMapper<BlogPost> mapToBlogPost() {
        return (rs, rowNum) -> {
            LocalDate modificationDate = getModificationDate(rs);
            List<String> tags = splitTagsToList(rs);

            return BlogPost.builder()
                    .authorName(rs.getString("AUTHORNAME"))
                    .authorUserName(rs.getString("AUTHORUSERNAME"))
                    .content(rs.getString("CONTENT"))
                    .id(rs.getInt("POSTID"))
                    .modificationDate(modificationDate)
                    .subject(rs.getString("SUBJECT"))
                    .tags(tags)
                    .build();
        };
    }

    private String joinTags(BlogPost post) {
        return ofNullable(post.getTags())
                .map(Collection::stream)
                .map(s -> s.collect(joining(TAGS_SEPARATOR)))
                .orElse(null);
    }

    private List<String> splitTagsToList(ResultSet rs) throws SQLException {
        return ofNullable(rs.getString("TAGS"))
                .map(s -> of(s.split(TAGS_SEPARATOR)))
                .map(s -> s.collect(toList()))
                .orElse(null);
    }

    private LocalDate getModificationDate(ResultSet rs) throws SQLException {
        return ofNullable(rs.getDate("MODIFICATIONDATE"))
                .map(Date::toLocalDate)
                .orElse(null);
    }

}
