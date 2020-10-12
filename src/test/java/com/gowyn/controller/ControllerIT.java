package com.gowyn.controller;

import com.gowyn.invariant.DataInput;
import com.gowyn.invariant.DatasInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
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
    @Sql("/test-insert-user.sql")
    public void getByIdValidRequest() {

        Map<String, Long> params = new HashMap<>();
        params.put("id", 1L);
        String url = UriComponentsBuilder.fromUriString(URI).buildAndExpand(params).toUriString();
        HttpEntity<DatasInput> request = new HttpEntity<>(buildDatas());

        ResponseEntity<DatasInput> response = restTemplate.postForEntity(url, request, DatasInput.class);

        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getInputs(), hasSize(1));
        assertThat(response.getStatusCodeValue(), is(200));

    }

    private DatasInput buildDatas() {
        DataInput data = DataInput.builder().objectName("User").fields(Collections.singletonList("lastname")).build();
        DatasInput datas = new DatasInput();
        datas.setInputs(Collections.singletonList(data));

        return datas;
    }

}