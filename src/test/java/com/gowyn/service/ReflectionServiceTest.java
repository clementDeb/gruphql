package com.gowyn.service;

import com.gowyn.data.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
public class ReflectionServiceTest {

    @Test
    public void mustReturnedZeroAvailableObject() {
        ReflectionService service =  new ReflectionService("test.package.name");
        Set<Class<?>> availablesObjects =  service.getAvailablesObjects();

        assertThat(availablesObjects.size(), is(0));
    }

    @Test
    public void mustReturnedOneAvailableObject() {
        ReflectionService service =  new ReflectionService("com.gowyn.data");
        Set<Class<?>> availablesObjects =  service.getAvailablesObjects();

        assertThat(availablesObjects.size(), is(1));
        assertThat(availablesObjects, contains(User.class));
    }

}