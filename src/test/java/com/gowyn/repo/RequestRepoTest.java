package com.gowyn.repo;

import com.gowyn.exceptions.NoEntityObjectFound;
import com.gowyn.exceptions.NoPrimaryKeyFoundException;
import com.gowyn.invariant.DataInput;
import com.gowyn.service.RequestRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThrows;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RequestRepoTest {

    @Autowired
    private RequestRepo repo;

    @Sql("/test-insert-user.sql")
    @SuppressWarnings("DefaultAnnotationParam, unchecked")
    @Test(expected = Test.None.class)
    public void getDatasById() throws NoPrimaryKeyFoundException, NoEntityObjectFound, IllegalAccessException {
        DataInput data = DataInput.builder().objectName("User").fields(Collections.emptyList()).build();
        List<Object> results = (List<Object>) repo.getDataByPrimaryKey(1L, data);
        List<Object> returnedValues = getReturnedObjectFieldValues(results);

        assertThat(returnedValues, containsInAnyOrder(1L, "nameTest", "lastnameTest"));
    }

    private List<Object> getReturnedObjectFieldValues(List<Object> results) throws IllegalAccessException {
        Field[] fields = results.get(0).getClass().getDeclaredFields();
        List<Object> returnedValues = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(results.get(0));
            returnedValues.add(value);
        }
        return returnedValues;
    }

    @Sql("/test-insert-user.sql")
    @SuppressWarnings("DefaultAnnotationParam, unchecked")
    @Test(expected = Test.None.class)
    public void getDatasByIdUsingOneParam() throws NoPrimaryKeyFoundException, NoEntityObjectFound {
        DataInput data = DataInput.builder().objectName("User").fields(Collections.singletonList("name")).build();
        Object dbValues = repo.getDataByPrimaryKey(1L, data);

        List<Object> result = (List<Object>) dbValues;
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is("nameTest"));

    }

    @Sql("/test-insert-user.sql")
    @SuppressWarnings("DefaultAnnotationParam, unchecked")
    @Test(expected = Test.None.class)
    public void getDatasByIdUsingTwoParam() throws NoPrimaryKeyFoundException, NoEntityObjectFound {
        DataInput data = DataInput.builder().objectName("User").fields(List.of("name", "lastname")).build();
        Object dbValues = repo.getDataByPrimaryKey(1L, data);

        List<Object> result = (List<Object>) dbValues;
        assertThat(result.size(), is(2));
        assertThat(result.get(0), is("nameTest"));
        assertThat(result.get(1), is("lastnameTest"));

    }

    @Sql("/test-insert-user.sql")
    @Test
    public void mustThrowExceptionIfNoResult() {

        DataInput data = DataInput.builder().objectName("User").fields(List.of("name", "lastname")).build();
        NoEntityObjectFound exception = assertThrows(NoEntityObjectFound.class, () -> repo.getDataByPrimaryKey(2L, data));

        assertThat(exception.getMessage(), is("the request returned no result"));

    }

}