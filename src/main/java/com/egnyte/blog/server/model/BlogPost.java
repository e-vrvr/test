package com.egnyte.blog.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class BlogPost {

    private Integer id;
    private String content;
    private String subject;
    private List<String> tags;
    private LocalDate modificationDate;
    private String authorName;
    private String authorUserName;
}