package com.gowyn.service;

import com.gowyn.data.User;
import com.gowyn.exceptions.AvailableObjectException;
import com.gowyn.exceptions.ObjectUnavailable;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
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
        String objectName = "User";
        List<String> fields = Collections.singletonList("lastname");

        requestValidator.validateRequestedObject(objectName, fields);
    }

    @SuppressWarnings("DefaultAnnotationParam")
    @Test(expected = Test.None.class)
    public void mustPassedUsingUppercaseField() throws Exception {

        Set<Class<?>> classSet = new HashSet<>();
        classSet.add(User.class);

        given(reflectionService.getAvailablesObjects()).willReturn(classSet);
        String objectName = "User";
        List<String> fields = Collections.singletonList("LastName");

        requestValidator.validateRequestedObject(objectName, fields);
    }

    @SuppressWarnings("DefaultAnnotationParam")
    @Test(expected = Test.None.class)
    public void mustPassedUsingValidObject() throws Exception {

        Set<Class<?>> classSet = new HashSet<>();
        classSet.add(User.class);

        given(reflectionService.getAvailablesObjects()).willReturn(classSet);

        String objectName = "User";
        List<String> fields = Collections.singletonList("LastName");

        requestValidator.validateRequestedObject(objectName, fields);
    }

    @Test
    public void mustNotPassedUsingNoExistingField() {

        Set<Class<?>> classSet = new HashSet<>();
        classSet.add(User.class);

        given(reflectionService.getAvailablesObjects()).willReturn(classSet);

        String objectName = "User";
        List<String> fields = Collections.singletonList("field");

        NoSuchFieldException exception = assertThrows(NoSuchFieldException.class, () -> requestValidator.validateRequestedObject(objectName, fields));

        MatcherAssert.assertThat(exception.getMessage(), containsString("field"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void mustNotPassedUsingNoExistingObject() {

        given(reflectionService.getAvailablesObjects()).willReturn(Collections.EMPTY_SET);

        AvailableObjectException exception = assertThrows(AvailableObjectException.class, () -> requestValidator.validateRequestedObject("", Collections.emptyList()));

        MatcherAssert.assertThat(exception.getMessage(), is("No object are annotated"));
    }

    @Test
    public void mustNotPassedUsingNoAvailableObject() {

        Set<Class<?>> classSet = new HashSet<>();
        classSet.add(User.class);

        given(reflectionService.getAvailablesObjects()).willReturn(classSet);

        String objectName = "user";
        List<String> fields = Collections.singletonList("lastname");

        ObjectUnavailable exception = assertThrows(ObjectUnavailable.class, () -> requestValidator.validateRequestedObject(objectName, fields));

        MatcherAssert.assertThat(exception.getMessage(), containsString("user"));
    }


}