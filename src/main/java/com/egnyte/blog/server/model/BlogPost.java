package com.egnyte.blog.server.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@Setter
public class BlogPost {

    public static final String TAGS_SEPARATOR = ",";

    private Integer id;
    private String content;
    private String subject;
    private List<String> tags;
    private LocalDate modificationDate;
    private String authorName;
    private String authorUserName;

    public BlogPostDto asDto() {
        String tags = Optional.ofNullable(this.tags)
                .map(t -> t.stream())
                .map(t -> t.collect(joining(TAGS_SEPARATOR)))
                .orElse(null);

        return BlogPostDto.builder()
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