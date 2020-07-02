package com.gowyn.repo;

import com.gowyn.data.User;
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
    public void getDatasById() {

        List<List<Object>> dbValues = repo.getDataById(1L, Collections.emptyList(), "User");

        assertThat(dbValues.size(), is(1));
        List<Object> fields = dbValues.get(0);
        assertThat(fields.size(), is(1));
        assertThat(fields.get(0).getClass(), is(User.class));
        User user = (User) fields.get(0);
        assertThat(user.getId(), is(1L));
        assertThat(user.getName(), is("nameTest"));
        assertThat(user.getLastname(), is("lastnameTest"));

    }

    @Sql("/test-insert-user.sql")
    @Test
    public void getDatasByIdUsingOneParam() {

        List<List<Object>> dbValues = repo.getDataById(1L, Collections.singletonList("name"), "User");

        assertThat(dbValues.size(), is(1));
        List<Object> fields = dbValues.get(0);
        assertThat(fields.size(), is(1));
        assertThat(fields.get(0), is("nameTest"));

    }

    @Sql("/test-insert-user.sql")
    @Test
    public void getDatasByIdUsingTwoParam() {

        List<List<Object>> dbValues = repo.getDataById(1L, Arrays.asList("name", "lastname"), "User");

        assertThat(dbValues.size(), is(1));
        List<Object> fields = dbValues.get(0);
        assertThat(fields.size(), is(2));
        assertThat(fields.get(0), is("nameTest"));
        assertThat(fields.get(1), is("lastnameTest"));

    }

}