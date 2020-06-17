package com.graphqllike.gruphql.parser;

import com.graphqllike.gruphql.exceptions.AvailableObjectException;
import com.graphqllike.gruphql.exceptions.ObjectUnavailable;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
import org.reflections.Reflections;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;


@RunWith(SpringJUnit4ClassRunner.class)
public class RequestedObjectValidatorTest {


    @SuppressWarnings("DefaultAnnotationParam")
    @Test(expected = Test.None.class)
    public void mustPassedUsingLowercaseField() throws Exception {
        String objectName = "User";
        List<String> fields = Collections.singletonList("lastname");

        RequestedObjectValidator.validateRequestedObject(objectName, fields);
    }

    @SuppressWarnings("DefaultAnnotationParam")
    @Test(expected = Test.None.class)
    public void mustPassedUsingUppercaseField() throws Exception {
        String objectName = "User";
        List<String> fields = Collections.singletonList("LastName");

        RequestedObjectValidator.validateRequestedObject(objectName, fields);
    }

    @SuppressWarnings("DefaultAnnotationParam")
    @Test(expected = Test.None.class)
    public void mustPassedUsingUppercaseObject() throws Exception {
        String objectName = "User";
        List<String> fields = Collections.singletonList("LastName");

        RequestedObjectValidator.validateRequestedObject(objectName, fields);
    }

    @SuppressWarnings("DefaultAnnotationParam")
    @Test(expected = Test.None.class)
    public void mustPassedUsingLowerObject() throws Exception {
        String objectName = "user";
        List<String> fields = Collections.singletonList("LastName");

        RequestedObjectValidator.validateRequestedObject(objectName, fields);
    }

    @Test
    public void mustNotPassedUsingNoExistingField() {

        String objectName = "User";
        List<String> fields = Collections.singletonList("field");

        NoSuchFieldException exception = assertThrows(NoSuchFieldException.class, () -> RequestedObjectValidator.validateRequestedObject(objectName, fields));

        MatcherAssert.assertThat(exception.getMessage(), containsString("field"));
    }

    @Test
    public void mustNotPassedUsingNoExistingObject() {

        String objectName = "object";
        List<String> fields = Collections.singletonList("lastname");

        ObjectUnavailable exception = assertThrows(ObjectUnavailable.class, () -> RequestedObjectValidator.validateRequestedObject(objectName, fields));

        MatcherAssert.assertThat(exception.getMessage(), containsString("object"));
    }

    @Test
    public void mustNotPassedUsingNoAvailableObject() {

        //BDDMockito.given(reflection.getTypesAnnotatedWith(Annotation.class)).willReturn(Collections.emptySet());
        Reflections mockReflect = new Reflections("packageName");
        Whitebox.setInternalState(RequestedObjectValidator.class,"reflect", mockReflect);
        String objectName = "user";
        List<String> fields = Collections.singletonList("lastname");

        AvailableObjectException exception = assertThrows(AvailableObjectException.class, () -> RequestedObjectValidator.validateRequestedObject(objectName, fields));

        MatcherAssert.assertThat(exception.getMessage(), is("No object are annotated"));
    }


}