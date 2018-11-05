package com.egnyte.blog.server.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.JsonContentAssert;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Month;

import static java.time.LocalDate.of;
import static java.util.Arrays.asList;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
@AutoConfigureJsonTesters
public class BlogPostTest {

    @Autowired
    private JacksonTester<BlogPost> tester;

    @Autowired
    private JsonContentAssert jsonContentAssert;

    @Test
    public void testSerialize() throws Exception {
        //given
        BlogPost blogPost = BlogPost.builder()
                .id(1)
                .authorName("John Millar")
                .authorUserName("johnMillar")
                .content("This is a real post from a real database")
                .modificationDate(of(2018, Month.JANUARY, 1))
                .subject("Super DB!")
                .tags(asList("db", "blog", "lifestyle"))
                .build();

        //when
        JsonContent<BlogPost> write = tester.write(blogPost);

        //then

        jsonContentAssert.isEqualToJson("expected.json");
        // Or use JSON path based assertions
        assertThat(this.json.write(details)).hasJsonPathStringValue("@.make");
        assertThat(this.json.write(details)).extractingJsonPathStringValue("@.make")
                .isEqualTo("Honda");
    }

    @Test
    public void testDeserialize() throws Exception {
        //given
        String content = "{\n" +
                "    \"id\": 1,\n" +
                "    \"content\": \"This is a real post from a real database\",\n" +
                "    \"subject\": \"Super DB!\",\n" +
                "    \"tags\": [\n" +
                "        \"db\",\n" +
                "        \"blog\",\n" +
                "        \"lifestyle\"\n" +
                "    ],\n" +
                "    \"modificationDate\": \"2018-01-01\",\n" +
                "    \"authorName\": \"John Millar\",\n" +
                "    \"authorUserName\": \"johnMillar\"\n" +
                "}";

        //when
        ObjectContent<BlogPost> parsed = tester.parse(content);

        //then
        assertThat(parsed)
                .isEqualTo(BlogPost.builder()
                        .id(1)
                        .authorName("John Millar")
                        .authorUserName("johnMillar")
                        .content("This is a real post from a real database")
                        .modificationDate(of(2018, Month.JANUARY, 1))
                        .subject("Super DB!")
                        .tags(asList("db", "blog", "lifestyle"))
                        .build()
                );
    }
}
