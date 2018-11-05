package com.egnyte.blog.server.service;

import com.egnyte.blog.server.model.BlogPost;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

@Service
public class BlogService {


    public String blogPostGet(int postId) throws Exception {
        return new ObjectMapper().writeValueAsString(h2DatabaseConnection.getPost(postId));
    }

    public BlogPost blogPostCreate(BlogPost body) {
        try {
            BlogPost blogpostToSave = deserializeBlogPost(body);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            h2DatabaseConnection.insertPost(blogpostToSave.authorName, blogpostToSave.subject, blogpostToSave.authorUserName, blogpostToSave.content, df.format(blogpostToSave.modificationDate), String.join(",", blogpostToSave.tags));
            return new ObjectMapper().writeValueAsString(blogpostToSave);
        } catch (Exception e) {
            return "Error has occurred";
        }

    }

    public String blogPostCreate(String body) {
        try {
            BlogPost blogpostToSave = deserializeBlogPost(body);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            h2DatabaseConnection.insertPost(blogpostToSave.authorName, blogpostToSave.subject, blogpostToSave.authorUserName, blogpostToSave.content, df.format(blogpostToSave.modificationDate), String.join(",", blogpostToSave.tags));
            return new ObjectMapper().writeValueAsString(blogpostToSave);
        } catch (Exception e) {
            return "Error has occurred";
        }

    }

    private BlogPost deserializeBlogPost(String body) throws IOException {
        ObjectMapper om = new ObjectMapper();
        JsonNode jsonNode = om.readTree(body);
        String authorName = jsonNode.get("authorName").asText();
        ArrayNode tags = (ArrayNode) jsonNode.get("tags");
        ArrayList<String> deserializedTags = new ArrayList<String>();
        for (int i = 0; i < tags.size(); i++) {
            deserializedTags.add(tags.get(i).asText());
        }
        String subject = jsonNode.get("subject").asText();
        String authorUserName = jsonNode.get("authorUserName").asText();
        String content = jsonNode.get("content").asText();
        Date modificationDate = new Date(jsonNode.get("modificationDate").asLong());
        BlogPost bp = new BlogPost();
        bp.setAuthorName(authorName);
        bp.setAuthorUserName(authorUserName);
        bp.setContent(content);
        bp.setModificationDate(modificationDate);
        bp.setSubject(subject);
        bp.setTags(deserializedTags);
        return bp;
    }
}
