package com.egnyte.blog.server.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@ToString
@Builder
@Getter
@EqualsAndHashCode
public class BlogPost {

    @Id
    private Long id;
    private String content;
    private String subject;
    private List<String> tags;
    private LocalDate modificationDate;
    private String authorName;
    private String authorUserName;
}