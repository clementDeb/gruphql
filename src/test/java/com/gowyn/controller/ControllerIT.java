package com.gowyn.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerIT {

    public static final String URI = "/v1/gowyn/{id}";

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getById() {

        Map<String, Long> params = new HashMap<>();
        params.put("id", 1L);
        String url = UriComponentsBuilder.fromUriString(URI).buildAndExpand(params).toUriString();

        HttpEntity<String> request =
                new HttpEntity<>("test");

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getBody(), containsString("te"));
        assertThat(response.getStatusCodeValue(), is(200));

    }

}