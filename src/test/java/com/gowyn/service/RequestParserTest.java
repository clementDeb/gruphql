package com.gowyn.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
public class RequestParserTest {

    RequestParser parser = new RequestParser();

    @Test
    public void buildReuestedObjectUsingOneObject() {
        String request = "user:name/lastname";

        List<String> requestedObject = parser.buildRequestedObject(request);

        assertThat(requestedObject.size(), is(1));

    }

    @Test
    public void buildReuestedObjectUsingTwoObject() {
        String request = "user:name/lastname\nadress:postalCode/city";

        List<String> requestedObject = parser.buildRequestedObject(request);

        assertThat(requestedObject.size(), is(2));

    }

    @Test
    public void parseRequestedObject() {
        String request = "user:name/lastname";

        Map<String, String[]> requestedObject = parser.parseRequestedObject(request);

        assertThat(requestedObject.size(), is(1));
        requestedObject.forEach((objectName, fields) -> {
            assertThat(objectName, is("user"));
            assertThat(fields.length, is(2));
            assertThat(Arrays.asList(fields), contains("name", "lastname"));
        });

    }

}