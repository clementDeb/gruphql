package com.gowyn.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.Tuple;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class dbAccessTest {

    @Autowired
    private RequestRepo repo;

    @Sql("/test-insert-user.sql")
    @Test
    public void getDatasById (){

        List<Tuple> values = repo.getDataById(1L, Collections.emptyList(), "User");

        assertThat(values.size(), is(1));

    }

    @Sql("/test-insert-user.sql")
    @Test
    public void getDatasByIdUsingOneParam (){

        List<Tuple> values = repo.getDataById(1L, Collections.singletonList("name"), "User");

        assertThat(values.size(), is(1));

    }

    @Sql("/test-insert-user.sql")
    @Test
    public void getDatasByIdUsingTwoParam (){

        List<Tuple> values = repo.getDataById(1L, Arrays.asList("name", "lastname"), "User");

        assertThat(values.size(), is(1));

    }

}