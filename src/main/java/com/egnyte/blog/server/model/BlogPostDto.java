package com.egnyte.blog.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static com.egnyte.blog.server.model.BlogPost.TAGS_SEPARATOR;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Entity(name = "posts")
@Table(name = "posts")
public class BlogPostDto {

    @javax.persistence.Id
    @GeneratedValue(strategy=GenerationType.AUTO)
//    @Column(name = "ID")
    private Integer id;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "TAGS")
    private String tags;

    @Column(name = "MODIFICATIONDATE")
    private LocalDate modificationDate;

    @Column(name = "AUTHORNAME")
    private String authorName;

    @Column(name = "AUTHORUSERNAME")
    private String authorUserName;

    public BlogPost asBlogPost() {
        List<String> tags = ofNullable(this.tags)
                .map(s -> of(s.split(TAGS_SEPARATOR)))
                .map(s -> s.collect(toList()))
                .orElse(null);

        return BlogPost.builder()
                .authorName(this.authorName)
                .authorUserName(this.authorUserName)
                .content(this.content)
                .id(this.id)
                .modificationDate(this.modificationDate)
                .subject(this.subject)
                .tags(tags)
                .build();
    }
}
