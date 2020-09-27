package com.gowyn.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTest {

    public static final String URI = "/v1/gowyn/";

    @Autowired
    MockMvc mvc;

    private String body;

    @Before
    public void init() {
        body = "test";
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getById() throws Exception {

        ResultActions result = mvc.perform(post(URI + 12)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk());

        String data = result.toString();

        assertThat(data, containsString(body));

    }

}