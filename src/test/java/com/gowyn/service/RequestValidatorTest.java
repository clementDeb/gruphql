package com.gowyn.service;

import com.gowyn.data.User;
import com.gowyn.exceptions.NoEntityObjectFound;
import com.gowyn.exceptions.ObjectUnavailable;
import com.gowyn.invariant.DataInput;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.given;


@RunWith(SpringJUnit4ClassRunner.class)
public class RequestValidatorTest {

    @InjectMocks
    RequestValidator requestValidator;

    @Mock
    ReflectionService reflectionService;


    @SuppressWarnings("DefaultAnnotationParam")
    @Test(expected = Test.None.class)
    public void mustPassedUsingLowercaseField() throws Exception {

        Set<Class<?>> classSet = new HashSet<>();
        classSet.add(User.class);

        given(reflectionService.getAvailablesObjects()).willReturn(classSet);
        DataInput data = DataInput.builder().objectName("User").fields(Collections.singletonList("lastname")).build();

        requestValidator.validateRequestedObject(data);
    }

    @SuppressWarnings("DefaultAnnotationParam")
    @Test(expected = Test.None.class)
    public void mustPassedUsingUppercaseField() throws Exception {

        Set<Class<?>> classSet = new HashSet<>();
        classSet.add(User.class);

        given(reflectionService.getAvailablesObjects()).willReturn(classSet);
        DataInput data = DataInput.builder().objectName("User").fields(Collections.singletonList("LastName")).build();

        requestValidator.validateRequestedObject(data);
    }

    @SuppressWarnings("DefaultAnnotationParam")
    @Test(expected = Test.None.class)
    public void mustPassedUsingValidObject() throws Exception {

        Set<Class<?>> classSet = new HashSet<>();
        classSet.add(User.class);

        given(reflectionService.getAvailablesObjects()).willReturn(classSet);
        DataInput data = DataInput.builder().objectName("User").fields(Collections.singletonList("LastName")).build();

        requestValidator.validateRequestedObject(data);
    }

    @Test
    public void mustNotPassedUsingNoExistingField() {

        Set<Class<?>> classSet = new HashSet<>();
        classSet.add(User.class);

        given(reflectionService.getAvailablesObjects()).willReturn(classSet);
        DataInput data = DataInput.builder().objectName("User").fields(Collections.singletonList("field")).build();

        NoSuchFieldException exception = assertThrows(NoSuchFieldException.class, () -> requestValidator.validateRequestedObject(data));

        MatcherAssert.assertThat(exception.getMessage(), containsString("field"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void mustNotPassedUsingNoExistingObject() {

        given(reflectionService.getAvailablesObjects()).willReturn(Collections.EMPTY_SET);

        DataInput data = DataInput.builder().objectName("").build();

        NoEntityObjectFound exception = assertThrows(NoEntityObjectFound.class, () -> requestValidator.validateRequestedObject(data));

        MatcherAssert.assertThat(exception.getMessage(), containsString("No object are annotated"));
    }

    @Test
    public void mustNotPassedUsingNoAvailableObject() {

        Set<Class<?>> classSet = new HashSet<>();
        classSet.add(User.class);

        given(reflectionService.getAvailablesObjects()).willReturn(classSet);
        DataInput data = DataInput.builder().objectName("user").fields(Collections.singletonList("lastname")).build();

        ObjectUnavailable exception = assertThrows(ObjectUnavailable.class, () -> requestValidator.validateRequestedObject(data));

        MatcherAssert.assertThat(exception.getMessage(), containsString("user"));
    }


}