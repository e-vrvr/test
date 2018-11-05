package com.egnyte.blog.server.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@Setter
public class BlogPost {

    private Integer id;
    private String content;
    private String subject;
    private List<String> tags;
    private LocalDate modificationDate;
    private String authorName;
    private String authorUserName;
}