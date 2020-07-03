package com.gowyn.repo;

import com.gowyn.data.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void getDatasById() {

        Object dbValues = repo.getDataById(1L, Collections.emptyList(), "User");

        List<Object> results = (List<Object>) dbValues;

        assertThat(results.size(), is(1));
        assertThat(results.get(0).getClass(), is(User.class));
        User user = (User) results.get(0);
        assertThat(user.getId(), is(1L));
        assertThat(user.getName(), is("nameTest"));
        assertThat(user.getLastname(), is("lastnameTest"));

    }

    @Sql("/test-insert-user.sql")
    @Test
    public void getDatasByIdUsingOneParam() {

        Object dbValues = repo.getDataById(1L, Collections.singletonList("name"), "User");

        List<Object> result = (List<Object>) dbValues;
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is("nameTest"));

    }

    @Sql("/test-insert-user.sql")
    @Test
    public void getDatasByIdUsingTwoParam() {

        Object dbValues = repo.getDataById(1L, Arrays.asList("name", "lastname"), "User");

        List<Object> result = (List<Object>) dbValues;
        assertThat(result.size(), is(2));
        assertThat(result.get(0), is("nameTest"));
        assertThat(result.get(1), is("lastnameTest"));

    }

}